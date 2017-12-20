package com.movieorganizer.business;

import com.movieorganizer.util.Constants;
import com.movieorganizer.util.Utility;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ayush on 20-12-2014.
 */
public class MovieDetail {

	private static PrintWriter printWriter;
	private static Boolean discountinue = false;
	private static String logFileName;
	private static String[] fileNames;
	private static Properties prop = new Properties();
	private String selectedRadio;
	private String correctDirName = "";
	private HashMap<String, String> ratings = new HashMap<String, String>();
	private HashMap<String, String> years = new HashMap<String, String>();

	public MovieDetail() {
		prop = Utility.getProperties();
	}

	public void start(String path, String selectedRadio) {
		try {
			this.selectedRadio = selectedRadio;

			String directory = path;
			if (!directory.endsWith(File.separator)) {
				correctDirName = directory + File.separator;
			} else {
				correctDirName = directory;
			}

			char reservedChar[] = Constants.reservedChar;

			ArrayList<String> tempArray = new ArrayList<String>(Arrays.asList(Constants.videoExtList));
			tempArray.addAll(Arrays.asList(Constants.uselessWordsList));
			String unwantedWordsList[] = tempArray.toArray(new String[1]); // size will be overwritten if needed

			ArrayList<String> videoExtList = new ArrayList<String>(Arrays.asList(Constants.videoExtList));

			File file = new File(directory);
			fileNames = file.list();

			logFileName = correctDirName + Constants.logFile + "_" + new Date().getTime() + Constants.logFileExt;
			printWriter = new PrintWriter(logFileName, Constants.charsetUsed);
			int count = 1;

			for (String originalName : fileNames) {
				try {
					String name = "";
					String yearOfRelease = "";
					/*if (count > 4) {
					    System.exit(0);
                    }*/
					boolean ifFile = false;
					String genre = "";
					String finalFolderName = "";
					String rating = "";
					String titleName = "";
					String year = "";
					String fileExtension = "";
					String description = "";
					String actor = "";
					String writer = "";
					String director = "";

					File currentFolderName = new File(correctDirName + originalName);
					File newFolderName = null;

					if ((originalName.contains(Constants.movieNameAlreadyUpdatedCheck1) &&
							originalName.contains(Constants.movieNameAlreadyUpdatedCheck2))
							|| originalName.toLowerCase().contains(Constants.noChangeCode)
							|| originalName.toLowerCase().contains(Constants.series)
							) {
						if (originalName.toLowerCase().contains(Constants.noChangeCode)) {
							originalName = originalName.replace(Constants.noChangeCode, "");
							newFolderName = new File(correctDirName + originalName);
							currentFolderName.renameTo(newFolderName);
						}

						continue;
					}

					if (!currentFolderName.isDirectory()) {

						ifFile = true;
						int extensionIndex = originalName.lastIndexOf(".");
						if (extensionIndex == -1) {
							continue;
						}
						fileExtension = originalName.substring(extensionIndex);

						if (!videoExtList.contains(fileExtension)) {
							continue;
						}
					}

					Pattern p = Pattern.compile(Constants.patternToCheckYear);
					Matcher m = p.matcher(originalName);

					name = originalName;
					Set<String> links = null;
					if (m.find()) {
						Integer n = Integer.parseInt(m.group());
						if (n > Constants.movieStartingYear && n <= (Calendar.getInstance().get(Calendar.YEAR))) {
							name = name.substring(0, name.lastIndexOf(n.toString()) + Constants.yearLenght);
						}
					} else {
						for (String formats : unwantedWordsList) {
							boolean contains = name.toLowerCase().matches(".*\\b" + formats.toLowerCase() + "\\b.*");
							if (contains) {
								name = name.replaceAll("(?i)" + formats, " ");
							}
						}
					}
					name = name.replaceAll(Constants.filterName1, "").replaceAll(Constants.filterName2, " ");
					links = getMovieDetails(name);

					if (discountinue) {
						break;
					}

					if (links.isEmpty()) {
						String[] tryDifferentNames = name.split("-");
						for (String str : tryDifferentNames) {

							// Check all combinations..
							links = getMovieDetails(str);

							if (!links.isEmpty() || discountinue) {
								break;
							}

						}
					}

					if (links != null) {
						for (String url : links) {

							// Google returns URLs in format "http://www.google.com/url?q=<url>&sa=U&ei=<someKey>".
							url = URLDecoder.decode(url.substring(0, url.indexOf('&')), Constants.charsetUsed);
							if (!url.startsWith(Constants.urlCheck)) {
								continue; // Ads/news/etc.
							}

                            /*if (!title.contains(name)) {
                                continue;
                            }*/

							try {
								int urlLength = Constants.urlCheck.length();
								String idForURL = url.substring(urlLength, url.indexOf("/", urlLength));
								String urlToHit = Constants.OMDB_API_URL_TITLE + idForURL + Constants.OMDB_API_URL_REST + Constants.OMDB_API_KEY;

								JSONParser parser = new JSONParser();

								InputStream input = new URL(urlToHit).openStream();
								Reader reader = new InputStreamReader(input, "UTF-8");

								Object obj = parser.parse(reader);

								JSONObject jsonObject = (JSONObject) obj;

								titleName = (String) jsonObject.get(Constants.OMDB_API_JSON_TITLE);
								rating = (String) jsonObject.get(Constants.OMDB_API_JSON_RATING);
								genre = (String) jsonObject.get(Constants.OMDB_API_JSON_GENRE);
								description = (String) jsonObject.get(Constants.OMDB_API_JSON_PLOT);
								actor = (String) jsonObject.get(Constants.OMDB_API_JSON_ACTOR);
								director = (String) jsonObject.get(Constants.OMDB_API_JSON_DIRECTOR);
								year = (String) jsonObject.get(Constants.OMDB_API_JSON_YEAR);
								writer = (String) jsonObject.get(Constants.OMDB_API_JSON_WRITER);

							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}

							// Changing Movie name..
							for (Character c : reservedChar) {
								if (titleName.contains(c.toString())) {
									titleName = titleName.replace(c.toString(), "-");
								}
							}

							titleName = titleName.replace(Constants.searchInImdb, "");

							finalFolderName = titleName + " (" + year + ") ," + Constants.movieNameAlreadyUpdatedCheck1 + " " +
									rating + " ," + Constants.movieNameAlreadyUpdatedCheck2 + " " + genre;

							if (ifFile) {
								finalFolderName = finalFolderName.trim() + fileExtension;
							}
							finalFolderName = finalFolderName.trim();
							System.out.println(originalName + prop.getProperty("moviedetails.moviedetail.previousname") + finalFolderName);

							// Writing logs..
							printWriter.println(count + Constants.closingBracket);
							printWriter.println(prop.getProperty("moviedetails.moviedetail.previousname") + " " + originalName);
							printWriter.println(prop.getProperty("moviedetails.moviedetail.changename") + " " + finalFolderName);
							printWriter.println(Constants.newLine + prop.getProperty("moviedetails.moviedetail.omdbdescription") + Constants.newLine);

							description = Utility.formatParagragh(description);
							printWriter.println(description);
							printWriter.println(Constants.directorLabel + director);
							printWriter.println(Constants.writerLabel + writer);
							printWriter.println(Constants.actorsLabel + actor);

							printWriter.println(Constants.descriptionEnd + Constants.newLine);
							newFolderName = new File(correctDirName + finalFolderName);

							currentFolderName.renameTo(newFolderName);

							count++;
							break;
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
			count++;

			ArrayList unchangedList = new ArrayList();
			for (String movieName : fileNames) {
				String fileExtension = "";
				File currentFolderName = new File(correctDirName + movieName);
				if (!(movieName.contains(Constants.movieNameAlreadyUpdatedCheck1) &&
						movieName.contains(Constants.movieNameAlreadyUpdatedCheck2))) {
					if (!currentFolderName.isDirectory()) {
						int extensionIndex = movieName.lastIndexOf(".");
						if (extensionIndex == -1) {
							continue;
						}
						fileExtension = movieName.substring(extensionIndex);

						if (videoExtList.contains(fileExtension)) {
							unchangedList.add(movieName);
						}
					} else {
						unchangedList.add(movieName);
					}
				}
			}
			if (!unchangedList.isEmpty()) {
				printWriter.println(prop.getProperty("moviedetails.moviedetail.unchangedlist") + Constants.newLine);
				for (int i = 0; i < unchangedList.size(); i++) {
					printWriter.println((i + 1) + Constants.closingBracket + " " + unchangedList.get(i));
				}
			}
			Desktop.getDesktop().open(new File(logFileName));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			printWriter.close();

			File file = new File(correctDirName);
			String[] newFileNames = file.list();
			for (String finalFolderName : newFileNames) {
				// Getting Rating..

				System.out.println(finalFolderName);
				String rating = "";
				if (finalFolderName.contains(Constants.movieNameAlreadyUpdatedCheck1)) {
					rating = finalFolderName.substring(finalFolderName.indexOf(Constants.movieNameAlreadyUpdatedCheck1) + Constants.movieNameAlreadyUpdatedCheck1.length(),
							finalFolderName.indexOf(",", finalFolderName.indexOf(Constants.movieNameAlreadyUpdatedCheck1)));
					Double rate1 = Math.floor(new Double(rating));
					Double rate2 = null;
					if (rate1 < 10) {
						rate2 = rate1 + 0.9;
					} else {
						rate2 = 10.0;
					}
					ratings.put(finalFolderName, String.valueOf(rate1 + " - " + rate2));
				}

				Matcher m = Pattern.compile(".+\\(([0-9]+)\\).+").matcher(finalFolderName);
				String yearOfRelease = "";
				while (m.find()) {
					yearOfRelease = m.group(1);
					years.put(finalFolderName, yearOfRelease);
				}

			}
			// Organise..
			if (selectedRadio.equals(Constants.accRating)) {
				organiseFolders(ratings);
			} else if (selectedRadio.equals(Constants.accYearOfRelease)) {
				organiseFolders(years);
			}

			System.exit(0);
		}
	}

	private void organiseFolders(HashMap<String, String> map) {
		File folder;
		for (Map.Entry<String, String> entry : map.entrySet()) {
			folder = new File(correctDirName + entry.getValue());
			folder.mkdirs();
			folder = new File(correctDirName + entry.getKey());
			folder.renameTo(new File(correctDirName + entry.getValue() + File.separator + entry.getKey()));
		}
	}


	private static Set<String> getMovieDetails(String movieName) {
		String google = Constants.googleSearchURL;
//                String bing = Constants.bingSearchURL;
		String search = movieName + " " + Constants.filterGoogleSearch;
		String charset = Constants.charsetUsed;
		String userAgent = Constants.userAgent;
		Elements links = null;
		Set<String> result = new HashSet<String>();
		try {
			links = Jsoup.connect(google + URLEncoder.encode(search, charset)).userAgent(userAgent).timeout(50000)
					.get().select("a[href]");
			for (Element link : links) {
				String temp = link.attr("href");
				if (temp.startsWith("/url?q=")) {
					temp = temp.substring(temp.indexOf("=") + 1);
					result.add(temp);
				}
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpStatusException e) {
			printWriter.println(prop.getProperty("moviedetails.moviedetail.serverrestart") + Constants.newLine);
			discountinue = true;
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}