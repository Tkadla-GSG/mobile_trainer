package cz.zeleznakoule.kebap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;

import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

public class MainActivity extends BaseActivity implements ActionBar.TabListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    // TODO remove when not needed
    public void onChooseExcerciseBtnClick(View view){
    	Intent i = new Intent(view.getContext(), ChooseExerciseActivity.class);
    	startActivityForResult(i, 0);   	
    }

	@Override
	public void initActionBar() {
		actionBar.setTitle(R.string.app_name);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab tab = actionBar.newTab();
		tab.setText(R.string.feed_tab)
			.setTabListener(this);
		actionBar.addTab(tab, true);	//vybrany tab
		
		tab = actionBar.newTab();
		tab.setText(R.string.calendar_tab)
			.setTabListener(this);
		actionBar.addTab(tab);
		
		tab = actionBar.newTab();
		tab.setText(R.string.profile_tab)
			.setTabListener(this);
		actionBar.addTab(tab);
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
    

    
}
