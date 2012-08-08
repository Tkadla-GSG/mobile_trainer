package cz.zeleznakoule.kebap;

import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Window;

/**
 * Zakladni nastaveni aktivity.
 * Neni-li receno jinak, dedi ji vsechny ostatni aktivity. 
 * @author Tkadla
 * 
 */
public class BaseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Odebira titulek aktivity, vcetne ikony (neni podpora pod verzi 11)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Vynucuje portrait mode pro aktivitu
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    
}
