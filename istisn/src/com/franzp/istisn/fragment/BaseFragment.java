package com.franzp.istisn.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.widget.CursorAdapter;

public class BaseFragment extends Fragment {
	protected CursorAdapter adapter;
	protected SORT sortOrder;
	public enum SORT {
		TIME_DESC,
		TOP,
		FLOP,
	}
	public CursorAdapter getAdapter() {
		return adapter;
	}
	public void setAdapter(CursorAdapter adapter) {
		this.adapter = adapter;
	}
	public SORT getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(SORT sortOrder) {
		this.sortOrder = sortOrder;
	};
}
