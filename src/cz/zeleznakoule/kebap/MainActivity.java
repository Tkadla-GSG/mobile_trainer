package cz.zeleznakoule.kebap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class MainActivity extends BaseActivity {

	TextView rotatingTV = null; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        createTabs();
        
        // vytazeni vsech potrebnych referenci z layoutu
        rotatingTV = (TextView) findViewById(R.id.rotatingTextView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    /**
     * Vytvoreni a naplneni tabu a tabhostu v layout
     */
    private void createTabs(){
    	TabHost tabs = (TabHost)findViewById(R.id.tabhost);
    		tabs.setup();
    	TabSpec specs = tabs.newTabSpec("feed");
    		specs.setContent(R.id.feedTab);
    		specs.setIndicator("Feed");
    		tabs.addTab(specs);
    		
    		specs.setContent(R.id.calendarTab);
    		specs.setIndicator("Calendar");
    		tabs.addTab(specs);
    		
    		specs.setContent(R.id.statsTab);
    		specs.setIndicator("Stats");
    		tabs.addTab(specs);
    }

    /**
     * Implementuje reakci na onClick udalost tlacitka rotateBtn
     * @param view
     */
    public void onRotateBtnClick(View view) {
    	RotateAnimation rotation = (RotateAnimation)AnimationUtils.loadAnimation(this,R.anim.rotate_anim);
    	rotatingTV.clearAnimation();
    	rotatingTV.startAnimation(rotation);
    }
    
}
