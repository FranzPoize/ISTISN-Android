package com.franzp.istisn.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.franzp.istisn.R;
import com.franzp.istisn.model.Quote;

public class QuoteView extends LinearLayout {
	
	private TextView message;
	private TextView author;
	private TextView top;
	private TextView flop;

	public QuoteView(Context context, AttributeSet attrs, int defStyle,Quote quote) {
		super(context, attrs, defStyle);
		
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View compView = inflater.inflate(R.layout.quote_item, this);
		
		Typeface typface=Typeface.createFromAsset(context.getAssets(),"fonts/eurof35.ttf");
		
		message = (TextView)compView.findViewById(R.id.message);
		author = (TextView)compView.findViewById(R.id.author);
		top = (TextView)compView.findViewById(R.id.Top);
		flop = (TextView)compView.findViewById(R.id.Flop);
		
		message.setTypeface(typface);
		author.setTypeface(typface);
		
		setQuote(quote);
	}
	
	public void setQuote(Quote quote) {
		message.setText(quote.getMessage());
		author.setText(quote.getAuthor());
		top.setText(quote.getNbTop().toString());
		flop.setText(quote.getNbFlop().toString());
	}

}
