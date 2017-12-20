package com.movieorganizer.business;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Copyright (c) 2016 Deutsche Autmobil Treuhand GmbH. All rights reserved.
 * <p/>
 * User: ayush
 * Date: 21-02-2016
 */
public class ParsePage {
	private String path = "https://www.google.co.in/search?q=3+idiots++site:imdb.com";
	Connection.Response response = null;

	private ParsePage() {
		try {

			response = Jsoup.connect(path)
					.userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21")
					.timeout(10000)
					.execute();
		} catch (IOException e) {
			System.out.println("io - " + e);
		}
	}

	public int getSitemapStatus() {
		int statusCode = response.statusCode();
		return statusCode;
	}

	public ArrayList<String> getUrls() {
		ArrayList<String> urls = new ArrayList<String>();
		Document doc = null;
		try {
			doc = response.parse();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// do whatever you want, for example retrieving the <url> from the sitemap
		for (Element url : doc.select("url")) {
			urls.add(url.select("loc").text());
		}
		return urls;
	}

	public static void main(String[] args) {
		ParsePage pp = new ParsePage();
		System.out.println(pp.getSitemapStatus() + " " + pp.getUrls());
	}
}
