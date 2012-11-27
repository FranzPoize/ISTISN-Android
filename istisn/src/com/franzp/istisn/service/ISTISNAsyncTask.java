package com.franzp.istisn.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.AsyncTask;

import com.franzp.istisn.model.Quote;
import com.franzp.istisn.utils.DBHelper;

public class ISTISNAsyncTask {

	private static ISTISNAsyncTask instance = null;
	private DBHelper helper;

	private ISTISNAsyncTask() {
	}
	
	public final static ISTISNAsyncTask getInstance(DBHelper helper) {
		if (ISTISNAsyncTask.instance == null) {
			ISTISNAsyncTask.instance = new ISTISNAsyncTask();
		}
		
		if (ISTISNAsyncTask.instance.helper == null)
			ISTISNAsyncTask.instance.helper = helper;
		
		return ISTISNAsyncTask.instance;
	}
	
	public AsyncTask<Void, Void, List<Quote>> getAllQuotesTask() {
		return new AllQuotesAsyncTask();
	}

	
	public AsyncTask<Integer, Void, Void> getPaginatedQuotesTask() {
		return new PaginatedQuotesAsyncTask();
	}
	
	public AsyncTask<Integer, Void, Void> getTopDescQuotesTask() {
		return new TopDescQuotesAsyncTask();
	}
	
	public AsyncTask<Integer, Void, Void> getFlopDescQuotesTask() {
		return new FlopDescQuotesAsyncTask();
	}
	
	public AsyncTask<Date, Void, List<Quote>> getLastSincecQuotesTask() {
		return new LastSinceQuotesAsyncTask();
	}
	
	
	
	public class AllQuotesAsyncTask extends AsyncTask<Void, Void, List<Quote>> {

		List<Quote> quotes = new ArrayList<Quote>();
		@Override
		protected List<Quote> doInBackground(Void... params) {
			return ISTISNServices.getInstance().getAllQuotes();
		}
		
		@Override
		protected void onPostExecute(List<Quote> result) {
			for(Quote quote : result) {
				helper.insertQuote(quote);
			}
			
		}
	}
	
	public class TopDescQuotesAsyncTask extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			ISTISNServices.getInstance().getTopDescQuotes(params[0]);
			
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			
			super.onPostExecute(result);
		}
	}
	
	public class FlopDescQuotesAsyncTask extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			ISTISNServices.getInstance().getFlopDescQuotes(params[0]);
			
			return null;
		}
	}
	
	public class PaginatedQuotesAsyncTask extends AsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			ISTISNServices.getInstance().getPaginatedQuotes(params[0]);
			
			return null;
		}
	}
	
	public class LastSinceQuotesAsyncTask extends AsyncTask<Date, Void, List<Quote>> {

		@Override
		protected List<Quote> doInBackground(Date... params) {
			
			return ISTISNServices.getInstance().getFromDateQuotes(params[0]);
		}
		
		@Override
		protected void onPostExecute(List<Quote> result) {
			if (result != null) {
				for(Quote quote : result) {
					helper.insertQuote(quote);
				}
			}
		}
	}

}
