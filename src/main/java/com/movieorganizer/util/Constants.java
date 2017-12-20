package com.movieorganizer.util;

import java.util.Date;

/**
 * Created by ayush on 28-12-2014.
 */
public class Constants {

	// These characters are not allowed in Folder name.
	public final static char reservedChar[] = {'<', '>', ':', '"', '\\', '/', '|', '?', '*'};

	// These characters appended in movie names which are not required.
	public final static String[] videoExtList = {".webm", ".mkv", ".flv", ".ogv", ".ogg", ".drc", ".mng", ".avi",
			".mov", ".qt", ".wmv", ".yuv", ".rm", ".rmvb", ".asf", ".mp4",
			".m4p", ".m4v", ".mpg", ".mp2", ".mpeg", ".mpe", ".mpv", ".m2v",
			".m4v", ".svi", ".3gp", ".3g2", ".mxf", ".roq", ".nsv"};

	public final static String[] uselessWordsList = {"Dual Audio", "DualAudio", "dual", "DVDRip", "XviD", "1CDRip", "DVDScr", "-", "DVDSCREENER", "1xCD", "bluray", "BRRip",
			"720p", "1080p", "HindiDub", "com", "Hindi", "Hdtv", "Premiere", "Dts", "Esir", "Rip"};

	public final static String movieNameAlreadyUpdatedCheck1 = "Rating -";
	public final static String movieNameAlreadyUpdatedCheck2 = "Genre -";
	public final static String noChangeCode = "-nc-";
	public final static String series = "series";
	public final static String patternToCheckYear = "[0-9]{4}";
	public final static int movieStartingYear = 1950;
	public final static int yearLenght = 4;
	public final static String filterName1 = "\\[.*?\\]";
	public final static String filterName2 = " +";
	public final static String urlCheck = "http://www.imdb.com/title/";
	public final static String descriptionEnd = "----------------********************************----------------------";
	public final static String href = "href";
	public final static String searchInImdb = " - IMDb";
	//	public final static String googleSearchURL = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
	public final static String googleSearchURL = "http://www.google.com/search?q=";
	public final static String bingSearchURL = "http://www.bing.com/";
	public final static String filterGoogleSearch = " site:imdb.com";
	public final static String userAgent = "Chrome/37.0.2062.124";
	public final static String charsetUsed = "UTF-8";
	public final static String propertyFileName = "UserInputProperty_en.property";
	public final static String readmeFileName = "HowToUse";
	public final static String tempPath = System.getProperty("java.io.tmpdir");
	public final static Long currentTime = new Date().getTime();
	public final static String logFile = "Movie detail of this folder";
	public final static String logFileExt = ".txt";
	public final static String newLine = System.getProperty("line.separator");
	public final static String closingBracket = ")";
	public final static String writerLabel = "Writers: ";
	public final static String directorLabel = "Director: ";
	public final static String name = "name";
	public final static String actorsLabel = "Stars: ";
	public final static String accRating = "ACC_RATING";
	public final static String accYearOfRelease = "ACC_YEAR_OF_RELEASE";
	public final static String noChangeRequired = "NO_CHANGE_REQ";
	public final static int numberOfLettersInLine = 80;
	public final static int generateEmptyRow = 5;
	public final static String OMDB_API_URL_TITLE = "http://www.omdbapi.com/?i=";
	public final static String OMDB_API_URL_REST = "&plot=short&r=json";
	public final static String OMDB_API_KEY = "&apikey=36c3bb2b";
	public final static String OMDB_API_JSON_TITLE = "Title";
	public final static String OMDB_API_JSON_YEAR = "Year";
	public final static String OMDB_API_JSON_RATING = "imdbRating";
	public final static String OMDB_API_JSON_WRITER = "Writer";
	public final static String OMDB_API_JSON_ACTOR = "Actors";
	public final static String OMDB_API_JSON_PLOT = "Plot";
	public final static String OMDB_API_JSON_DIRECTOR = "Director";
	public final static String OMDB_API_JSON_GENRE = "Genre";
	public final static String GOOGLE_SEARCH_LIMIT_EXCEED_ERROR = "Suspected Terms of Service Abuse. Please see http://code.google.com/apis/errors";

}
