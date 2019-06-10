package com.example.tcape.customviewexample;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class CustomView extends View {

    public List<Point> Points;
    public List<Point> GenPoints;
    public Paint paintBrush = new Paint();
    public int dotColor = Color.BLACK;
    public float downX;
    public float downY;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(@Nullable AttributeSet set) {
        paintBrush.setColor(dotColor);
        paintBrush.setAntiAlias(true);
        paintBrush.setStyle(Paint.Style.FILL);
        paintBrush.setStrokeWidth(5.0f);
        Points = new ArrayList<>();
        GenPoints = new ArrayList<>();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width,height);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        if (Points.size() > 0){
            for (Point p : Points) {
                paintBrush.setColor(p.pColor);
                canvas.drawCircle(p.xCoord, p.yCoord, 8, paintBrush);
            }
        }
        if (GenPoints.size() > 0){
            for (Point p : GenPoints) {
                paintBrush.setColor(p.pColor);
                canvas.drawCircle(p.xCoord, p.yCoord, 8, paintBrush);
            }
        }
    }

    @Override
    public boolean performClick(){
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        performClick();
        float touchX = event.getX();
        float touchY = event.getY();
        int action = event.getActionMasked();

        if (action == MotionEvent.ACTION_DOWN){
            downX = touchX;
            downY = touchY;
            Point newPoint = new Point(touchX, touchY, dotColor);
            Points.add(newPoint);
            Log.d("", "size: " + Points.size());
            invalidate(); // THIS IS IMPORTANT!!
            return true;
        }
        if (action == MotionEvent.ACTION_UP){
            float finalX = event.getX();
            float finalY = event.getY();
            float deltaY = (finalY - downY);
            Log.d("tag", "Action UP");
            Log.d("tag", "touchY: " + downY);
            Log.d("tag", "finalY: " + finalY);
            Log.d("tag", "deltaY: " + deltaY);

            if (deltaY > 0 && deltaY > 500){
                Log.d("tag", "deltaY: " + deltaY);
                Points.clear();
                GenPoints.clear();
            }
            invalidate(); // THIS IS IMPORTANT!!
            return super.onTouchEvent(event);
        }
        Log.d("", String.valueOf(action));
        invalidate(); // THIS IS IMPORTANT!!
        return super.onTouchEvent(event);
    }
}
