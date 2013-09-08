package com.pennapps.breakit;

import java.util.HashMap;

public class FastRepo {
	
	private HashMap<String, Object> hashMap = new HashMap<String, Object>();
	
	public Object getObject(String key) {
		return hashMap.get(key);
	}	
	public Object putObject(String key, Object value) {
		return hashMap.put(key, value);
	}
	public String get(String key) {
		return (String) hashMap.get(key);
	}	
	public String put(String key, String value) {
		return (String) hashMap.put(key, value);
	}
	
	
	
	private static HashMap<String, FastRepo> staticHashMap = new HashMap<String, FastRepo>();	
	public static FastRepo putRepo(String key, FastRepo instance) {			
		return staticHashMap.put(key, instance);		
	}
	public static FastRepo getRepo(String key) {
		return staticHashMap.get(key);
	}

}
