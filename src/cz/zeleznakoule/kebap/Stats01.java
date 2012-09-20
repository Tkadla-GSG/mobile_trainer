package cz.zeleznakoule.kebap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class Stats01 extends View {

	private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private final RectF oval = new RectF();
	private final double BORDER = 0.15;

	public Stats01(Context context) {
		super(context);

	}

	@Override
	public void onDraw(Canvas canvas) {

		// TODO remove and replace with database
		int hard = 5;
		int medium = 23;
		int testing = 11;

		// set size of oval
		int height = getHeight();
		int width = getWidth();
		int minimal = 0;

		if (height < width) {
			minimal = height;
		} else {
			minimal = width;
		}

		int border = (int) (BORDER * minimal);

		oval.set((float)(1.5 * border),
				(float)(1.5 * border),
				(float)(minimal - 1.5 * border),
				(float)(minimal - 1.5 * border));

		// paint fractions of circle
		int sum = hard + medium + testing;
		int tArc = (int) (360 * testing / sum);
		int mArc = (int) (360 * medium / sum);
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth( border );

		// paint testing days
		paint.setColor(getResources().getColor(R.color.KebapLightRed));
		canvas.drawArc(oval, 0, tArc, false, paint);

		// paint medium days
		paint.setColor(getResources().getColor(R.color.KebapMediumRed));
		canvas.drawArc(oval, tArc, mArc, false, paint);

		// paint hard days
		paint.setColor(getResources().getColor(R.color.KebapRed));
		canvas.drawArc(oval, tArc + mArc, 360 - (tArc + mArc), false, paint);

	}

}