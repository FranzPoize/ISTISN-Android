package com.franzp.istisn.fragment;

import com.franzp.istisn.R;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends Fragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view = inflater.inflate(R.layout.about, null);
		
		Typeface typface=Typeface.createFromAsset(getActivity().getAssets(),"fonts/eurof35.ttf");
		
		TextView aboutText = (TextView)view.findViewById(R.id.about_text);
		
		aboutText.setTypeface(typface);
		
		return view;
	}
}
