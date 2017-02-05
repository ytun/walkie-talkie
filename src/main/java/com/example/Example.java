package com.example;

import java.util.List;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.Page;
import com.restfb.types.User;

public class Example {
	private static final String MY_ACCESS_TOKEN = "EAADoyAiRPZBEBAOoaojKxn1RMZCsKlHfVrvZAbAKE1syoByNeswAfKYTNH0kTdpjsRm3syRMATpaFgNDM3F2EeQGXWGmf6XbQZAJDFkmp5cfv3Bl65CIGJJspoOCy87G8jAqyhazOM1acFP29TqdbTUewmeLPcXkQESst1IG4eDCEbMTqMeo22kLIHoUGYuE4NepWFyJvQZDZD";
//	private static final String MY_ACCESS_TOKEN = "EAADdWsfVZAe0BAFLrG3rpJ87Cjh3SHXQD0k4XEDErpv33jCXX36j4ctRNA0oem7PgZBLYQ5hOf2MugnBslEcnEKwMy3nd9TYSWjdv5nWHaPGEq0uaGk6ZAsGZAoHfDk6WzQb48E1ytYnk9YyDpullO6K1YlTtvsG0pWFT4ZBKYQZDZD";
	private static final FacebookClient facebookClient = new DefaultFacebookClient(MY_ACCESS_TOKEN, Version.VERSION_2_8);
	private static final int LIMIT = 3;
	
	public static void fetchSingle(){
		//--- fetching single objects
		User user = facebookClient.fetchObject("me", User.class);
		Page page = facebookClient.fetchObject("cocacola", Page.class);

		System.out.println("User name: " + user.getName());
		System.out.println("Page likes: "+ page.getName());
	}

	public static void fetchConnections(){
		//---- fetching connections
		User user = facebookClient.fetchObject("me", User.class, Parameter.with("fields", "id,name,last_name"));
		System.out.println("User name: " + user);


	}

//	http://restfb.com/documentation/
	/*	RestFB supports paging, but you need to fetch the endpoint with the special FacebookClient.fetchConnection()
	 *  method. One page of a pageable result set is called a connection. Now you can iterate over the connection and 
	 *  you’ll get a lot of lists of the fetched element. The loading of the next list is automatically done by RestFB 
	 *  so you don’t need to know how the URLs are built.
	 */	
	public static void search(){
		//---- searching public
		long startTime = System.currentTimeMillis();
		
		Connection<User> searchResult = facebookClient.fetchConnection("search", User.class, 
				Parameter.with("LIMIT", LIMIT),
				Parameter.with("q", "Mark"), 
				Parameter.with("type", "user"));
//				Parameter.with("fields", "id,name,last_name,about"));

		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		System.out.println("conn created: "+ duration+" \n");
//		System.out.println("searchresult: "+searchResult.getData());
//
//		System.out.println(searchResult.getData().get(0));
		
		//----
		startTime = System.currentTimeMillis();

		if(searchResult.hasNext()){
			System.out.println("searchresult: "+searchResult.iterator().next());

		}
		 endTime = System.currentTimeMillis();
		 duration = endTime - startTime;
		System.out.println("conn created: "+ duration+" \n");		
		
		//--
		startTime = System.currentTimeMillis();

		int i = 0;
		
		outerloop:
		for(List<User> users : searchResult){
			System.out.println("	usersize: "+users.size());
			for(User user : users){
				System.out.println(user.getName());
				System.out.println(user.getUsername());
				i++;
				System.out.println(i);
				if(i>=LIMIT){
					break outerloop;
				}
			}
			
			
		}
		
//		System.out.println("searchresult: "+searchResult.getData().get(0));

		endTime = System.currentTimeMillis();
		 duration = endTime - startTime;
		System.out.println("conn created: "+ duration+" \n");		
		
		
//		for(List<User> users : searchResult){
//			System.out.println("	usersize: "+users.size());
//			for(User user : users){
//				System.out.println(user.getName());
//			}
////			break;
//		}

		//		System.out.println(searchResult.getData());
	}

	//---OAuthException--- Requires extended permision
	//tried changing the permission of the app on developer UI-- doesn't work
	public static void searchPrivate(){
		Connection<User> targetedSearch =
				facebookClient.fetchConnection("me/home", User.class,
						Parameter.with("q", "Mark"), 
						Parameter.with("type", "user"));

		System.out.println("Posts on my wall by friends named Mark: " + targetedSearch.getData().size());
		System.out.println(targetedSearch.getData());
	}

	public static void main(String[] args) {

		search();
		searchPrivate(); 
	}

}
