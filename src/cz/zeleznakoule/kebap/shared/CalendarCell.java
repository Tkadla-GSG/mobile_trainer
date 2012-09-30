package cz.zeleznakoule.kebap.shared;

import cz.zeleznakoule.kebap.R;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.content.Context;
import android.content.res.Resources;

public class CalendarCell {
	protected Rect mBound = null;
	protected int mDayOfMonth = 1;	// from 1 to 31
	protected Paint textPaint = new Paint(Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
	protected Paint bgPaint = new Paint();
	private int dx, dy;
	private boolean selected = false; 
	
	protected int textColor = Color.BLACK;
	protected int bgColor = Color.WHITE;
	
	protected Resources mRes = null; 
		
	public CalendarCell(Context context, int dayOfMon, Rect rect, float textSize, boolean bold) {
		mDayOfMonth = dayOfMon;
		mBound = rect;
		mRes = context.getResources(); 
		
		bgColor = mRes.getColor(R.color.KebapWhite);
		bgPaint.setColor(bgColor);
		
		textPaint.setTextSize( textSize );
		textPaint.setFakeBoldText( bold );
		textPaint.setColor( textColor );
		
		dx = (int) textPaint.measureText(String.valueOf(mDayOfMonth)) / 2;
		dy = (int) (-textPaint.ascent() + textPaint.descent()) / 2;
	}
	
	public CalendarCell(Context context, int dayOfMon, Rect rect, float textSize) {
		this(context, dayOfMon, rect, textSize, false);
	}
	
	protected void draw(Canvas canvas) {
		canvas.drawRect(mBound, bgPaint);
		canvas.drawText(String.valueOf(mDayOfMonth), mBound.centerX() - dx, mBound.centerY() + dy, textPaint);
	}
	
	public int getDayOfMonth() {
		return mDayOfMonth;
	}
	
	public void setTextColor( int color){
		textPaint.setColor(color);
	}
	
	public void setTextColor( int color, boolean underline ){
		textPaint.setUnderlineText(underline);
		textPaint.setColor(color);
	}
	
	public void setBgColor( int color ){
		bgPaint.setColor( color);
	}
	
	public boolean hitTest(int x, int y) {
		return mBound.contains(x, y); 
	}
	
	public Rect getBound() {
		return mBound;
	}
	
	public boolean isSelected(){
		return selected; 
	}
	
	public void isToday(){
		setBgColor( mRes.getColor( R.color.KebapRed ) );
		setTextColor( mRes.getColor( R.color.KebapWhite ) );
	}
	
	public void setSelected(){
		setBgColor( mRes.getColor( R.color.KebapLightGray ) );
		setTextColor( mRes.getColor( R.color.KebapWhite ) );
		selected = true;
	}
	
	public void setNotSelected(){
		setBgColor( bgColor );
		setTextColor( textColor );
		selected = false; 
	}
	
	@Override
	public String toString() {
		return String.valueOf(mDayOfMonth)+"("+mBound.toString()+")";
	}
	
}

