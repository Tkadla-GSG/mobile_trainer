package cz.zeleznakoule.kebap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class ProfileFragment extends Fragment {

	  private ViewPager mViewPager;  
	  private MyFragmentPagerAdapter mMyFragmentPagerAdapter;  
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_profile_, container, false); 
		
	    mViewPager = (ViewPager) view.findViewById(R.id.viewpager);  
	    mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager());  
	    mViewPager.setAdapter(mMyFragmentPagerAdapter);  
	    
	    return view; 
	}  
	


private static class MyFragmentPagerAdapter extends FragmentPagerAdapter {  

    public MyFragmentPagerAdapter(FragmentManager fm) {  
         super(fm);  
    }  

    @Override  
    public Fragment getItem(int index) {  

    	switch ( index ) {
        case 0: return new CalendarFragment();
        case 1: return new FeedFragment();
       
    	}
    return null;
    }  

    @Override  
    public int getCount() {  

         return 2;  
    }  
} 

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
	}

}
