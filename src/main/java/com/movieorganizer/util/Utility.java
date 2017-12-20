package com.movieorganizer.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by ayush on 28-12-2014.
 */
public class Utility {

	private static Properties prop = new Properties();
	private static InputStream input = null;

	public static Properties getProperties() {
		try {
			// load a properties file
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(Constants.propertyFileName));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public static String formatParagragh(String text) {
		String formatedText = "";
		boolean flag = false;
		int beginIndex = 0;
		int endIndex = Constants.numberOfLettersInLine;
		while (endIndex <= text.length()) {

			// Check for first space after 90 letters..
			endIndex = text.indexOf(" ", endIndex);
			if (endIndex == -1) {
				endIndex = text.length() - 1;
				flag = true;
			}
			formatedText = formatedText + text.substring(beginIndex, endIndex) + Constants.newLine;
			if (flag) {
				break;
			}
			beginIndex = endIndex + 1;
			endIndex = endIndex + Constants.numberOfLettersInLine;
			if (endIndex > text.length()) {
				endIndex = text.length() - 1;
			}
		}
		if (text.length() < endIndex) {
			formatedText = text + Constants.newLine;
		}
		return formatedText;
	}
}
