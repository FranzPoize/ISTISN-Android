package com.franzp.istisn.service;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.franzp.istisn.model.Quote;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ISTISNServices {
	private static ISTISNServices instance;
	private WebService webService;
	private final static String ALL_METHOD = "/last/all/";
	private final static String PAGINATED_METHOD = "/last/paginate/";
	private final static String TOP_DESC_METHOD = "/top/paginate/";
	private final static String FLOP_DESC_METHOD = "/flop/paginate/";
	private final static String LAST_SINCE_METHOD = "/last/since/";
	private Gson gson;
	
	private ISTISNServices() {
		this.webService = new WebService("http://www.isaythatisaynothing.com/api/messages");
		this.gson = new Gson();
	}
	
	public static ISTISNServices getInstance() {
		if (instance == null) {
			instance = new ISTISNServices();
		}
		
		return instance;
	}
	
	public List<Quote> getAllQuotes() {
		String response = this.webService.webGet(ALL_METHOD, null);
		Quote[] quotes = this.gson.fromJson(response, Quote[].class);
		
		return Arrays.asList(quotes);
	}
	
	public List<Quote> getPaginatedQuotes(Integer page) {
		String response = this.webService.webGet(PAGINATED_METHOD+page, null);
		
		Type collectionType = new TypeToken<Quote>(){}.getType();
		List<Quote> quotes = this.gson.fromJson(response, collectionType);
		
		return quotes;
	}

	public List<Quote> getTopDescQuotes(Integer page) {
		String response = this.webService.webGet(TOP_DESC_METHOD+page, null);
		
		Type collectionType = new TypeToken<Quote>(){}.getType();
		List<Quote> quotes = this.gson.fromJson(response, collectionType);
		
		return quotes;
	}

	public List<Quote> getFlopDescQuotes(Integer page) {
		String response = this.webService.webGet(FLOP_DESC_METHOD+page, null);
		
		Type collectionType = new TypeToken<Quote>(){}.getType();
		List<Quote> quotes = this.gson.fromJson(response, collectionType);
		
		return quotes;
	}

	public List<Quote> getFromDateQuotes(Date date) {
		String response = this.webService.webGet(LAST_SINCE_METHOD+date.getTime(), null);
		if (response.startsWith("[{"))
			return Arrays.asList(this.gson.fromJson(response, Quote[].class));
		
		return null;
	}
}
