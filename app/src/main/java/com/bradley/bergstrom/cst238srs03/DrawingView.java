package com.bradley.bergstrom.cst238srs03;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.provider.DocumentsContract;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    public static int BRUSH_SIZE = 10;
    public static final int DEFAULT_COLOR = Color.BLUE;
    public static final int DEFAULT_BG_COLOR = Color.WHITE;
    private static final float TOUCH_TOLERANCE = -4;
    private float mX, mY;
    private Path mPath;
    private Paint mPaint;
    private int currentColor;
    private int backgroundColor = DEFAULT_BG_COLOR;
    private int strokeWidth;
    public ArrayList<Point> points;
    private Paint picture;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    private Bitmap mBitmap;
    private ArrayList<FingerPath> paths = new ArrayList<>();
    private ArrayList<Float> xlist;
    private ArrayList<Float> ylist;

    public DrawingView(Context context) {
        this(context, null);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        points = new ArrayList<Point>();
        setupPaint();
    }

    private void setupPaint() {
        xlist = new ArrayList<Float>();
        ylist = new ArrayList<Float>();

        picture = new Paint();

        picture.setColor(DEFAULT_COLOR);
        picture.setAntiAlias(true);
        picture.setDither(true);
        picture.setStrokeWidth(10);
        picture.setAlpha(0xff);
        picture.setXfermode(null);
        picture.setStyle(Paint.Style.STROKE);
        picture.setStrokeJoin(Paint.Join.ROUND);
        picture.setStrokeCap(Paint.Cap.ROUND);
    }

    public void changeCurrentColor(int color){
        currentColor = color;
    }
    public void drawLines(int iterations, int seekbar){
        //TODO: this function
    }
    public void init(DisplayMetrics metrics) {
        int height = metrics.heightPixels;
        int width = metrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        if(currentColor == 0){
            currentColor = DEFAULT_COLOR;
        }

        strokeWidth = BRUSH_SIZE;
    }

    public void normal() {

    }

    public void clear() {
        backgroundColor = DEFAULT_BG_COLOR;
        paths.clear();
        normal();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mCanvas.drawColor(backgroundColor);

        for (FingerPath fp : paths) {
            picture.setColor(fp.color);
            picture.setStrokeWidth(fp.strokeWidth);
            picture.setMaskFilter(null);

            mCanvas.drawPath(fp.path, picture);
        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }


    private void touchStart(float x, float y) {
        mPath = new Path();
        FingerPath fp = new FingerPath(currentColor, strokeWidth, mPath);
        paths.add(fp);

        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touchUp(){
        mPath.lineTo(mX,mY);
        xlist.add(mX);
        ylist.add(mY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
        }

        return true;
    }

}