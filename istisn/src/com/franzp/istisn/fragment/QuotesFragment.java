package com.franzp.istisn.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.R;
import com.franzp.istisn.Istisn;
import com.franzp.istisn.model.Quote;
import com.franzp.istisn.service.ISTISNAsyncTask;
import com.franzp.istisn.view.QuoteView;

public class QuotesFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.quote_fragment, null);
		
		ListView list = (ListView)view.findViewById(R.id.quote_list);
		Cursor cursor = ((Istisn)getActivity()).helper.getQuotes();
		if (cursor.isAfterLast())
			ISTISNAsyncTask.getInstance(((Istisn)getActivity()).helper).getAllQuotesTask().execute();
		
		list.setAdapter(new myCursorAdapter(getActivity(),cursor,0));
		
		return view;
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
