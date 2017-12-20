package com.movieorganizer.business;

/**
 * Copyright (c) 2016 Deutsche Autmobil Treuhand GmbH. All rights reserved.
 * <p/>
 * User: ayush
 * Date: 21-02-2016
 */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunnyCrawler {

  private static Pattern patternDomainName;
  private Matcher matcher;
  private static final String DOMAIN_NAME_PATTERN
	= "(https?):\\/\\/(www\\.)?[a-z0-9\\.:].*?(?=\\s)";
  static {
	patternDomainName =  Pattern.compile(
	        "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
	                + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
	                + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
	        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
  }

  public static void main(String[] args) {

	FunnyCrawler obj = new FunnyCrawler();
	Set<String> result = obj.getDataFromGoogle("3+idiots++site:imdb.com");
	for(String temp : result){
		System.out.println(temp);
	}
	System.out.println(result.size());
  }

  public String getDomainName(String url){

	String domainName = "";
	matcher = patternDomainName.matcher(url);
	if (matcher.find()) {
		domainName = matcher.group(0).toLowerCase().trim();
		int matchStart = matcher.start(1);
  int matchEnd = matcher.end();

	}
	return domainName;

  }

  private Set<String> getDataFromGoogle(String query) {

	Set<String> result = new HashSet<String>();
	String request = "https://www.google.com/search?q=" + query + "&num=20";
	System.out.println("Sending request..." + request);

	try {

		// need http protocol, set this as a Google bot agent :)
		Document doc = Jsoup
			.connect(request)
			.userAgent(
			  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
			.timeout(5000).get();

		// get all links
		Elements links = doc.select("a[href]");
		for (Element link : links) {

			String temp = link.attr("href");
			System.out.println(link);
			if(temp.startsWith("/url?q=")){
                                //use regex to get domain name
				System.out.println("got you : "+temp);
				result.add(getDomainName(temp));
			}

		}

	} catch (IOException e) {
		e.printStackTrace();
	}

	return result;
  }

}