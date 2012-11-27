package com.franzp.istisn.fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.franzp.istisn.Istisn;
import com.franzp.istisn.R;
import com.franzp.istisn.model.Quote;
import com.franzp.istisn.service.ISTISNAsyncTask;
import com.franzp.istisn.view.QuoteView;

public class QuotesFragment extends Fragment {
	
	public ListView list;
	public enum SORT {
		TIME_DESC,
		TOP,
		FLOP,
	}
	public SORT sortOrder = SORT.TIME_DESC;
	private myCursorAdapter adapter;
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.quote_fragment, null);
		
		list = (ListView)view.findViewById(R.id.quote_list);
		
		this.adapter = new myCursorAdapter(getActivity(),null,0);
		
		setHasOptionsMenu(true);
		

		try {
			refresh();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		list.setAdapter(this.adapter);
		
		return view;
	}
	
	public void refresh() throws ParseException {
		Cursor cursor = ((Istisn)getActivity()).helper.getQuotes(sortOrder);
		if (cursor.isAfterLast())
			ISTISNAsyncTask.getInstance(((Istisn)getActivity()).helper).getAllQuotesTask().execute();
		else {
			cursor.moveToFirst();
			String date = cursor.getString(cursor.getColumnIndex("creationdate"));	
			Date newDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(date);
			ISTISNAsyncTask.getInstance(((Istisn)getActivity()).helper).getLastSincecQuotesTask().execute(newDate);
			cursor = ((Istisn)getActivity()).helper.getQuotes(sortOrder);
		}
		
		this.adapter.changeCursor(cursor);
		this.adapter.notifyDataSetChanged();
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    super.onCreateOptionsMenu(menu, inflater);
	    menu.add("Refresh");
	   	menu.add("Sort by Top");
	   	menu.add("Sort by Flop");
	   	menu.add("Sort by creation date");
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return true;
	}


	public class myCursorAdapter extends CursorAdapter {

		public myCursorAdapter(Context context, Cursor c, int flags) {
			super(context, c, flags);
		}

		@Override
		public void bindView(View arg0, Context arg1, Cursor arg2) {
			((QuoteView)arg0).setQuote(new Quote(arg2));
		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
			return new QuoteView(getActivity(), null, 0, new Quote(arg1));
		}
		
		
		
	}
}
