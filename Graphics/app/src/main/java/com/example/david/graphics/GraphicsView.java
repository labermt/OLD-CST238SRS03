package com.example.david.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_UP;
import static java.lang.Math.abs;
import static java.lang.Math.round;

public class GraphicsView extends View
{
    private Paint graphicsPaint;
    private List<Point> points;
    private List<Point> calculatedPoints;
    private Random randGen;

    public float fraction;
    public int iterations;

    public boolean firstTime;
    private boolean isPressed;
    private int x, y;


    public GraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        initGraphics();

        setOnTouchListener( new View.OnTouchListener() {
            Calendar timeStamp;
            public boolean onTouch(View v, MotionEvent event) {
                Calendar now = Calendar.getInstance();
                    if (event.getActionMasked() == ACTION_DOWN) {
                        manageTouchDown(event);
                    } else if (event.getActionMasked() == ACTION_UP && longEnough(timeStamp, now)) {
                        timeStamp = now;
                        manageTouchUp(event);
                        performClick();
                    }
                return true;
            }
        });
    }

    private void addPoint(float x, float y) {
        points.add( new Point(round(x), round(y)) );
    }


    private void initGraphics() {
        graphicsPaint = new Paint();
        graphicsPaint.setStyle(Paint.Style.STROKE);
        graphicsPaint.setStrokeCap(Paint.Cap.ROUND);
        graphicsPaint.setColor(Color.BLUE);
        points = new ArrayList<>();
        calculatedPoints = new ArrayList<>();
        fraction = 0.5f;
        iterations = 5000;
        randGen = new Random( Calendar.getInstance().getTimeInMillis() );
    }


    @Override
    protected void onDraw(Canvas canvas) {
        graphicsPaint.setStrokeWidth(4);
        for(Point point : points)
        {
            canvas.drawCircle(point.x, point.y, 4, graphicsPaint);
        }
        graphicsPaint.setStrokeWidth(2);
        for(Point point : calculatedPoints)
        {
            canvas.drawPoint(point.x, point.y, graphicsPaint);
        }
    }

    public void clearScreen() {
        points.clear();
        calculatedPoints.clear();
        invalidate();
    }

    public void createArt() {
        if(points.size() < 2) return;
        Point currentPoint = points.get( randGen.nextInt(points.size()) );
        for(int i = 0; i < iterations; i++)
        {
            currentPoint = calculatePoint(currentPoint);
            calculatedPoints.add( currentPoint );
        }
        invalidate();
    }

    public void setFraction(int percent) {
        fraction = (1.0f + percent) / 100;
    }
    public String getFraction(){
        return String.valueOf(round(fraction*100))+"%";
    }
    public void setIteration(int iteration){
        iterations = iteration;
    }
    public boolean back(){
        if(!calculatedPoints.isEmpty()){
            calculatedPoints.clear();
            invalidate();
            return true;
        }
        else if(!points.isEmpty()){
            points.remove(points.size() - 1);
            invalidate();
            return true;
        }
        else return false;
    }
    public void setPointColor(int color){
        graphicsPaint.setColor(color);
        invalidate();
    }

    private Point calculatePoint(Point currentPoint){
        float currentFraction = 1 - fraction;
        Point randomPoint = points.get( randGen.nextInt(points.size()) );
        float x = (randomPoint.x * fraction + currentPoint.x * currentFraction);
        float y = (randomPoint.y * fraction + currentPoint.y * currentFraction);
        return new Point(round(x), round(y));
    }

    private void manageTouchUp(MotionEvent event) {
        int currentX = (int)event.getX();
        int xDifference = currentX - x;
        int currentY = (int)event.getY();
        int yDifference = currentY - y;
        if( abs(xDifference) < 30 && abs(yDifference) < 30){
            addPoint(currentX, currentY);
        }
        else {
            clearScreen();
            /*
            if(!calculatedPoints.isEmpty()){
                calculatedPoints.clear();
            }
            else points.clear();
            */
        }
        isPressed = false;
        invalidate();
    }
    private void manageTouchDown(MotionEvent event) {
        if(!isPressed){
            x = (int)event.getX();
            y = (int)event.getY();
            isPressed = true;
        }
    }

    private boolean longEnough(Calendar timeStamp, Calendar now) {
        if (timeStamp == null) {
            timeStamp = Calendar.getInstance();
            return true;
        }
        return now.getTimeInMillis() > timeStamp.getTimeInMillis() + 200;
    }
}