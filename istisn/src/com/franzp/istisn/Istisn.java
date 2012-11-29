package com.franzp.istisn;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.franzp.istisn.fragment.AboutFragment;
import com.franzp.istisn.fragment.BaseFragment;
import com.franzp.istisn.fragment.BaseFragment.SORT;
import com.franzp.istisn.fragment.QuotesFragment;
import com.franzp.istisn.model.Quote;
import com.franzp.istisn.service.ISTISNAsyncTask;
import com.franzp.istisn.service.ISTISNAsyncTask.AllQuotesAsyncTask;
import com.franzp.istisn.utils.DBHelper;
import com.franzp.istisn.view.QuoteView;
import com.viewpagerindicator.TabPageIndicator;

public class Istisn extends FragmentActivity {

    private ISTISNPagerAdapter pageAdapter;
    private ViewPager viewPager;
    private static LinearLayout myLayout;
    private static final int STOPSPLASH = 0;
    private static final int SPLASHTIME = 3000;
    private static Istisn context;
    public DBHelper helper;
    private ImageView refreshButton;
    
    private Handler splashHandler = new Handler() {
    	@Override
    	public void handleMessage(Message msg) {
    		switch (msg.what) {
    		case STOPSPLASH:
    			Animation anim = AnimationUtils.loadAnimation(context, R.anim.splash_animation);
    	        myLayout.setVisibility(View.VISIBLE);
    	        myLayout.startAnimation(anim);
    			break;
    		}
    		super.handleMessage(msg);
    	}
    };
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        context = this;
        setContentView(R.layout.activity_istisn);
        myLayout = (LinearLayout)findViewById(R.id.splashLayout);
        
        helper = new DBHelper(this);
        
        TabPageIndicator indicator =
		        (TabPageIndicator)findViewById( R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pageAdapter = new ISTISNPagerAdapter(getSupportFragmentManager(),this);
		viewPager.setAdapter(pageAdapter);
		indicator.setViewPager(viewPager);
		viewPager.setCurrentItem(0, false);
		
		refreshButton = (ImageView)findViewById(R.id.refresh);
		refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				refresh();
			}
		});
		
		refresh();
		
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }
    
    public void refresh() {
    	helper.deleteQuotes();
		AllQuotesAsyncTask task = (AllQuotesAsyncTask)ISTISNAsyncTask.getInstance(this.helper).getAllQuotesTask();
		for(BaseFragment fragment : pageAdapter.pages) {
			if(fragment.getSortOrder() != null)
				task.addAdapter(fragment.getSortOrder(),fragment.getAdapter());
		}
		task.execute();
    }
    
    
    
    @Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		myLayout.layout(myLayout.getWidth(), 0, 
    			myLayout.getWidth()*2, myLayout.getHeight());
	}



	private static class ISTISNPagerAdapter extends FragmentPagerAdapter {  
		public List<BaseFragment> pages;
		public QuotesFragment quoteFragment;
		public QuotesFragment topFragment;
		public QuotesFragment flopFragment;
		
		private static String ALL_SUBTITLE = "RECENTS";
		private static String TOP_SUBTITLE = "TOP";
		private static String FLOP_SUBTITLE = "FLOP";
		private static String ABOUT_SUBTITLE = "ABOUT";
		
		private static String[] titles = new String[]
	    {
			ALL_SUBTITLE,
			TOP_SUBTITLE,
			FLOP_SUBTITLE,
			ABOUT_SUBTITLE,
	    };
		
        public ISTISNPagerAdapter(FragmentManager fm,Context context) {  
             super(fm);
             pages = new ArrayList<BaseFragment>();
             quoteFragment = new QuotesFragment();
             quoteFragment.setSortOrder(SORT.TIME_DESC);
             quoteFragment.setAdapter(new myCursorAdapter(context,null,0));
             pages.add(quoteFragment);
             topFragment = new QuotesFragment();
             topFragment.setSortOrder(SORT.TOP);
             topFragment.setAdapter(new myCursorAdapter(context,null,0));
             pages.add(topFragment);
             flopFragment = new QuotesFragment();
             flopFragment.setSortOrder(SORT.FLOP);
             flopFragment.setAdapter(new myCursorAdapter(context,null,0));
             pages.add(flopFragment);
             pages.add(new AboutFragment());
        }  

        @Override  
        public Fragment getItem(int index) {  

             return pages.get(index);
        }  
        
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position % titles.length];
        }

        @Override  
        public int getCount() {  

             return titles.length;  
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
    			return new QuoteView(context, null, 0, new Quote(arg1));
    		}
    	}
   }
}
