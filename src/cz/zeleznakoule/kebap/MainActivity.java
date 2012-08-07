package cz.zeleznakoule.kebap;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        createTabs();
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

    
}
