package cz.zeleznakoule.kebap;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class Stats01 extends View {
	
	Paint paint = new Paint();
	
    public Stats01(Context context) {
		super(context);
	}

    @Override
    public void onDraw(Canvas canvas) {
    	
		// TODO remove and replace with database
		
		int hard = 10;
		int medium = 5; 
		int easy = 2; 
    	
    	
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(3);
        canvas.drawRect(30, 30, 80, 80, paint);
        paint.setStrokeWidth(0);
        paint.setColor(Color.CYAN);
        canvas.drawRect(33, 60, 77, 77, paint );
        paint.setColor(Color.YELLOW);
        canvas.drawRect(33, 33, 77, 60, paint );

    }

}