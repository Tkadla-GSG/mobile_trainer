package cz.zeleznakoule.kebap;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;
import android.content.pm.ActivityInfo;
import android.view.Window;

/**
 * Zakladni nastaveni aktivity obshajici fragmenty.
 * Neni-li receno jinak, dedi ji vsechny ostatni fragmentActivity. 
 * @author Tkadla
 */
public abstract class BaseFragmentActivity extends SherlockFragmentActivity {
	
	protected ActionBar actionBar = null; 

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Vynucuje portrait mode pro aktivitu
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        
		// Actionbar theme
		setTheme(R.style.Theme_kebap_actionbar);
		
		actionBar = getSupportActionBar(); 
		
		// Inicializace ActionBaru
		initActionBar();
    }
    
	/**
	 * Init Spinner v Actionbaru
	 */
	public abstract void initActionBar();
    
}
