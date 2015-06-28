package com.test.itunes;

import junit.framework.TestCase;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;




public class SearchAPITest extends TestCase {
	Client client = null;
	ClientResponse response = null;
	JSONParser parser = new JSONParser();

	@Before
	public void setUp() throws Exception {
		client = Client.create();
	}
	
	@Test
	public void testGet() {
		WebResource webResource = client
				   .resource("https://itunes.apple.com/search").queryParam("term", "Test");
		response = webResource.accept("application/json")
		                .get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
		}
	 
		String output = response.getEntity(String.class);
		JSONObject jsonObject =  null;
		try {
			Object obj = parser.parse(output);
			jsonObject = (JSONObject) obj;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long name = (Long) jsonObject.get("resultCount");
		assertNotNull(output);
		assertEquals(50,name.longValue());
					
	}
	
	@Test
	public void testGetEmpty() {
		WebResource webResource = client
				   .resource("https://itunes.apple.com/search");
		response = webResource.accept("application/json")
		                .get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
		}
	 
		String output = response.getEntity(String.class);
		JSONObject jsonObject =  null;
		try {
			Object obj = parser.parse(output);
			jsonObject = (JSONObject) obj;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Long name = (Long) jsonObject.get("resultCount");
		assertNotNull(output);
		assertEquals(0,name.longValue());
					
	}
}
