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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.franzp.istisn.Istisn;
import com.franzp.istisn.R;
import com.franzp.istisn.fragment.BaseFragment.SORT;
import com.franzp.istisn.model.Quote;
import com.franzp.istisn.service.ISTISNAsyncTask;
import com.franzp.istisn.service.ISTISNAsyncTask.AllQuotesAsyncTask;
import com.franzp.istisn.service.ISTISNAsyncTask.LastSinceQuotesAsyncTask;
import com.franzp.istisn.view.QuoteView;

public class QuotesFragment extends BaseFragment {
	
	public ListView list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.quote_fragment, null);
		
		list = (ListView)view.findViewById(R.id.quote_list);
		
		list.setAdapter(this.adapter);
		
		return view;
	}
}
