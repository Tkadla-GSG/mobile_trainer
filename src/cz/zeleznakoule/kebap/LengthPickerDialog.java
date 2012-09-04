package cz.zeleznakoule.kebap;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.Dialog;
import android.content.Context;
import android.view.animation.AnticipateOvershootInterpolator;

public class LengthPickerDialog extends Dialog {

	// TODO: Externalize string-array
	String wheelMenu1[] = new String[] { "name 1", "name 2", "name 3",
			"name 4", "name 5", "name 6", "name 7", "name 8", "name 9" };
	String wheelMenu2[] = new String[] { "age 1", "age 2", "age 3" };
	String wheelMenu3[] = new String[] { "10", "20", "30", "40", "50", "60" };

	// Wheel scrolled flag
	private boolean wheelScrolled = false;
	private Context context = null; 
	
	OnWheelChangedListener changeListener = new OnWheelChangedListener() {
		
		@Override
		public void onChanged(WheelView wheel, int oldValue, int newValue) {
			if (!wheelScrolled) {
				updateStatus();
			}
		}
	};
	
	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
		
		@Override
		public void onScrollingStarted(WheelView wheel) {
			wheelScrolled = true;
			
		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			wheelScrolled = false;
			updateStatus();
		}
	};

	public LengthPickerDialog(Context context) {
		super(context);
		
		this.context = context;
		
		setContentView(R.layout.dialog_length_picker);

		initMinutesWheel(R.id.wheel_minutes);
		initSecondsWheel(R.id.wheel_seconds);
	}

	/**
	 * Propaguj zmeny po dokonceni zadavani 
	 */
	private void updateStatus() {

		//TODO propagate changes

	}

	/**
     * Initicialize minutoveho kola
     * @param id the wheel widget Id
     * TODO nastaveni defaultu
     */
    private void initMinutesWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.setViewAdapter(new NumericWheelAdapter(context, 0, 59));
        wheel.setCurrentItem( 5 );
        
        wheel.addChangingListener(changeListener);
        wheel.addScrollingListener(scrollListener);
        wheel.setCyclic(true);
        wheel.setInterpolator(new AnticipateOvershootInterpolator());
    }
    
	/**
     * Initicialize sekondoveho kola
     * @param id the wheel widget Id
     * TODO nastaveni defaultu
     */
    private void initSecondsWheel(int id) {
        WheelView wheel = getWheel(id);
        wheel.setViewAdapter(new NumericWheelAdapter(context, 0, 59));
        wheel.setCurrentItem( 0 );
        
        wheel.addChangingListener(changeListener);
        wheel.addScrollingListener(scrollListener);
        wheel.setCyclic(true);
        wheel.setInterpolator(new AnticipateOvershootInterpolator());
    }

	/**
	 * Returns wheel by Id
	 * 
	 * @param id the wheel Id
	 * @return the wheel with passed Id
	 */
	private WheelView getWheel(int id) {
		return (WheelView) findViewById(id);
	}

	/**
	 * Tests wheel value
	 * 
	 * @param id the wheel Id
	 * @param value the value to test
	 * @return true if wheel value is equal to passed value
	 */
	private int getWheelValue(int id) {
		return getWheel(id).getCurrentItem();
	}



}
