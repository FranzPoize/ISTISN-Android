package com.franzp.istisn.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.widget.CursorAdapter;

import com.franzp.istisn.fragment.BaseFragment.SORT;
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
	
	public BaseAsyncTask<Void, Void, List<Quote>> getAllQuotesTask() {
		return new AllQuotesAsyncTask();
	}

	
	public BaseAsyncTask<Integer, Void, Void> getPaginatedQuotesTask() {
		return new PaginatedQuotesAsyncTask();
	}
	
	public BaseAsyncTask<Integer, Void, Void> getTopDescQuotesTask() {
		return new TopDescQuotesAsyncTask();
	}
	
	public BaseAsyncTask<Integer, Void, Void> getFlopDescQuotesTask() {
		return new FlopDescQuotesAsyncTask();
	}
	
	public BaseAsyncTask<Date, Void, List<Quote>> getLastSincecQuotesTask() {
		return new LastSinceQuotesAsyncTask();
	}
	
	public abstract class BaseAsyncTask<Params,Progress,Result> extends AsyncTask<Params, Progress, Result> {
		private Map<SORT, CursorAdapter> adapters = new HashMap<SORT, CursorAdapter>();

		public Map<SORT, CursorAdapter> getAdapters() {
			return adapters;
		}

		public void setAdapters(Map<SORT, CursorAdapter> adapters) {
			this.adapters = adapters;
		}
		
		public void addAdapter(SORT sortOrder,CursorAdapter adapter) {
			this.adapters.put(sortOrder, adapter);
		}
	}
	
	
	
	public class AllQuotesAsyncTask extends BaseAsyncTask<Void, Void, List<Quote>> {
		
		@Override
		protected List<Quote> doInBackground(Void... params) {
			return ISTISNServices.getInstance().getAllQuotes();
		}
		
		@Override
		protected void onPostExecute(List<Quote> result) {
			for(Quote quote : result) {
				helper.insertQuote(quote);
			}
			
			for (SORT order : getAdapters().keySet()) {
				CursorAdapter adapter = getAdapters().get(order);
				Cursor cursor = helper.getQuotes(order);
				adapter.changeCursor(cursor);
				adapter.notifyDataSetChanged();
			}
		}
	}
	
	public class TopDescQuotesAsyncTask extends BaseAsyncTask<Integer, Void, Void> {

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
	
	public class FlopDescQuotesAsyncTask extends BaseAsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			ISTISNServices.getInstance().getFlopDescQuotes(params[0]);
			
			return null;
		}
	}
	
	public class PaginatedQuotesAsyncTask extends BaseAsyncTask<Integer, Void, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			ISTISNServices.getInstance().getPaginatedQuotes(params[0]);
			
			return null;
		}
	}
	
	public class LastSinceQuotesAsyncTask extends BaseAsyncTask<Date, Void, List<Quote>> {

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
