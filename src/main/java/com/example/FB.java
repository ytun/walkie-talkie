package com.example;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.User;
import com.sun.istack.internal.logging.Logger;

public class FB {
	private static final String MY_ACCESS_TOKEN = "EAADdWsfVZAe0BAFLrG3rpJ87Cjh3SHXQD0k4XEDErpv33jCXX36j4ctRNA0oem7PgZBLYQ5hOf2MugnBslEcnEKwMy3nd9TYSWjdv5nWHaPGEq0uaGk6ZAsGZAoHfDk6WzQb48E1ytYnk9YyDpullO6K1YlTtvsG0pWFT4ZBKYQZDZD";
	private static final FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN, Version.VERSION_2_4);
	private static final int LIMIT = 3;
	
	private static final Logger LOGGER = Logger.getLogger(FB.class.getName(), FB.class);
//	http://restfb.com/documentation/
	/*	RestFB supports paging, but you need to fetch the endpoint with the special FacebookClient.fetchConnection()
	 *  method. One page of a pageable result set is called a connection. Now you can iterate over the connection and 
	 *  you’ll get a lot of lists of the fetched element. The loading of the next list is automatically done by RestFB 
	 *  so you don’t need to know how the URLs are built.
	 */	
	public static void search(){
		//---- searching public
		
		Connection<User> searchResult = facebookClient.fetchConnection("search", User.class, 
				Parameter.with("limit", LIMIT),
				Parameter.with("q", "Mark Zuckerberg"), 
				Parameter.with("type", "user"),
				Parameter.with("fields", "id,name,link,gender,locale,timezone,updated_time,verified"));//,firstName,lastName,significantOther"));
		
		int i = 0;
		
		outerloop:
		for(List<User> users : searchResult){
			System.out.println("	usersize: "+ users.size());
			for(User user : users){
				LOGGER.log(Level.INFO, "Name: "+ user.getName());
				LOGGER.log(Level.FINE, user.toString());

				LOGGER.log(Level.INFO, "Link: " + user.getLink());
				i++;
				LOGGER.log(Level.INFO, "" + i);
				
				if(i>=LIMIT){ // stop next paging
					break outerloop;
				}
			}
			
			
		}
		
		
		
	}
	
	public static void main(String[] args) {
		LOGGER.setLevel(Level.INFO);
		
		search();
//		searchPrivate();
	}
}
