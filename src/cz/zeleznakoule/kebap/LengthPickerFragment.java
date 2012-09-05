package cz.zeleznakoule.kebap;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.NumericWheelAdapter;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.TextView;

public class LengthPickerFragment extends DialogFragment {

	private Context mContext = null;
	private View view = null;
	private TextView tv = null;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		mContext = getActivity();

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

		// TODO null RootView, nektere veci nemusi fungovat
		view = getActivity().getLayoutInflater().inflate(
				R.layout.dialog_length_picker, null);
		builder.setView(view);
		builder.setCancelable(true);

		// TODO texty presunout, neho nahradit androima
		builder.setPositiveButton(android.R.string.ok, new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				saveValues();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton(android.R.string.cancel,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		// TODO move text to values
		builder.setTitle("Doba tréninku");

		tv = (TextView) (getActivity().findViewById(R.id.lengthTextField));
		String defaultValue = (String) tv.getText();

		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(defaultValue);
		
		int minutes = 5; 
		int seconds = 0; 

		if (m.find()) {
			minutes = Integer.parseInt(m.group());
		}
		
		if (m.find()) {
			seconds = Integer.parseInt(m.group());
		}
		
		// init wheel pickers
		initMinutesWheel(R.id.wheel_minutes, minutes);
		initSecondsWheel(R.id.wheel_seconds, seconds);

		return builder.create();
	};

	/**
	 * Uklada a propaguje zmeny do textoveho pole
	 */
	private void saveValues() {
		tv.setText("" + getWheelValue(R.id.wheel_minutes) + " min "
				+ getWheelValue(R.id.wheel_seconds) + " sec");
	}

	/**
	 * Propaguj zmeny po dokonceni zadavani na nastavovacim kole V soucasnosti
	 * neni pouzivano
	 */
	private void updateStatus() {
	}

	/**
	 * Initicialize minutoveho kola
	 * 
	 * @param id
	 *            the wheel widget Id
	 * @param defaultValue
	 *            index uvodni hodnoty TODO nastaveni defaultu TODO nastaveni
	 *            zkratky "min"
	 */
	private void initMinutesWheel(int id, int defaultValue) {
		WheelView wheel = getWheel(id);
		wheel.setViewAdapter(new NumericWheelAdapter(mContext, 0, 59,
				"%02d min"));
		wheel.setCurrentItem(defaultValue);

		wheel.setCyclic(true);
		wheel.setInterpolator(new AnticipateOvershootInterpolator());
	}

	/**
	 * Initicialize sekondoveho kola
	 * 
	 * @param id
	 *            the wheel widget Id
	 * @param defaultValue
	 *            index uvodni hodnoty TODO nastaveni defaultu TODO nastaveni
	 *            zkratky "sec"
	 */
	private void initSecondsWheel(int id, int defaultValue) {
		WheelView wheel = getWheel(id);
		wheel.setViewAdapter(new NumericWheelAdapter(mContext, 0, 59,
				"%02d sec"));
		wheel.setCurrentItem(defaultValue);

		wheel.setCyclic(true);
		wheel.setInterpolator(new AnticipateOvershootInterpolator());
	}

	/**
	 * Returns wheel by Id
	 * 
	 * @param id
	 *            the wheel Id
	 * @return the wheel with passed Id
	 */
	private WheelView getWheel(int id) {
		return (WheelView) (view.findViewById(id));
	}

	/**
	 * Tests wheel value
	 * 
	 * @param id
	 *            the wheel Id
	 * @param value
	 *            the value to test
	 * @return true if wheel value is equal to passed value
	 */
	private int getWheelValue(int id) {
		return getWheel(id).getCurrentItem();
	}

}
