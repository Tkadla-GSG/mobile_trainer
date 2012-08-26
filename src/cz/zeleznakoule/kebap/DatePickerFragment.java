package cz.zeleznakoule.kebap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;
import android.app.Dialog;
import android.app.DatePickerDialog;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private SimpleDateFormat sdf = null;
	private TextView tv = null; 
	private Calendar cal = null; 

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		sdf = new SimpleDateFormat("dd/MM/yyyy");
		tv = (TextView) (getActivity().findViewById(R.id.dateTextField));
		cal = Calendar.getInstance();

		try {
			Date date = (Date) sdf.parse((String)tv.getText());
			cal.setTime(date);
		} catch (ParseException e) {
			// No need to act on exception, cal already has todays date
		}

		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DAY_OF_MONTH);

		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	/**
	 * Aktualizuje textfield po dokonceni
	 */
	public void onDateSet(DatePicker view, int year, int month, int day) {
		cal.set(year, month, day);
		tv.setText(sdf.format(cal.getTime()));
	}

}