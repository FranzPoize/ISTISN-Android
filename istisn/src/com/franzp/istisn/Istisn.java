package com.franzp.istisn;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.franzp.istisn.fragment.AboutFragment;
import com.franzp.istisn.fragment.QuotesFragment;
import com.franzp.istisn.utils.DBHelper;
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
        pageAdapter = new ISTISNPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(pageAdapter);
		indicator.setViewPager(viewPager);
		viewPager.setCurrentItem(0, false);
		
		refreshButton = (ImageView)findViewById(R.id.refresh);
		refreshButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(pageAdapter.quoteFragment != null)
					try {
						pageAdapter.quoteFragment.refresh();
					} catch (ParseException e) {
						e.printStackTrace();
					}
			}
		});
		
		Message msg = new Message();
		msg.what = STOPSPLASH;
		splashHandler.sendMessageDelayed(msg, SPLASHTIME);
    }
    
    
    
    @Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		myLayout.layout(myLayout.getWidth(), 0, 
    			myLayout.getWidth()*2, myLayout.getHeight());
	}



	private static class ISTISNPagerAdapter extends FragmentPagerAdapter {  
		private List<Fragment> pages;
		public QuotesFragment quoteFragment;
		
		private static String ALL_SUBTITLE = "QUOTES";
		private static String ABOUT_SUBTITLE = "ABOUT";
		
		private static String[] titles = new String[]
	    {
			ALL_SUBTITLE,
			ABOUT_SUBTITLE,
	    };
		
        public ISTISNPagerAdapter(FragmentManager fm) {  
             super(fm);
             pages = new ArrayList<Fragment>();
             quoteFragment = new QuotesFragment();
             pages.add(quoteFragment);
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
   }
}
