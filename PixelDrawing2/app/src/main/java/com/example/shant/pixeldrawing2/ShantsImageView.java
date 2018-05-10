package com.example.shant.pixeldrawing2;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShantsImageView extends AppCompatImageView implements View.OnClickListener {
    private Paint m_paint;
    private int m_width;
    private int m_height;
    private List<PointF> vertexList;
    private List<PointF> pixelList;
    private float ratio;
    private int iterations;
    private GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureListener());
    private int PixelColor = Color.BLACK;
    private void init() {
        m_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        m_paint.setStrokeWidth(20);
        vertexList = new ArrayList<>();
        pixelList = new ArrayList<>();
        setPixelColor(Color.BLACK);
        iterations = 5;
        ratio = 0.5f;
    }

    public int getPixelColor() { return PixelColor; }

    public void setPixelColor(int pixelColor)  {
        PixelColor = pixelColor;
    }

    class GestureListener extends GestureDetector.SimpleOnGestureListener {
        public boolean onDown(MotionEvent e) {
            float x_coord = e.getX();
            float y_coord = e.getY();
            PointF vertex_draw = new PointF();
            PointF vertex1 = vertex_draw;
            PointF pixel1;
            vertex_draw.x = x_coord;
            vertex_draw.y = y_coord;
            vertexList.add(vertex_draw);
            if (vertexList.size() >=3)
            {
                PointF vertex2;
                for (int i = 0; i<iterations; i++) {
                    vertex2 = vertexList.get(new Random().nextInt(vertexList.size()));
                    pixel1 = new PointF();
                    pixel1.x = ((vertex1.x + vertex2.x) * ratio);
                    pixel1.y = ((vertex1.y + vertex2.y) * ratio);
                    vertex1 = pixel1;
                    pixelList.add(pixel1);
                }
            }
            invalidate();
            return true;
        }
        public boolean onDoubleTap(MotionEvent e) {
            vertexList.clear();
            pixelList.clear();
            return true;
        }
    }

    public void ClearGesture() {
        vertexList.clear();
        pixelList.clear();

    }

    public void SetUserInfo(int newnumberofiterations, float newratio, int newcolor) {
        iterations = newnumberofiterations;
        ratio = newratio;
        PixelColor = newcolor;
        setPixelColor(PixelColor);
        m_paint.setColor(PixelColor);
    }


    public ShantsImageView(Context context) {
        super(context);
        init();
    }

    public ShantsImageView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        init();
    }

    public ShantsImageView(Context context, AttributeSet attributeSet, int defineStyleAttribute){
        super(context, attributeSet, defineStyleAttribute);
        init();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = gestureDetector.onTouchEvent(event);
        return result;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PointF pointf : vertexList) {
            canvas.drawPoint(pointf.x, pointf.y, m_paint);
        }
        for (PointF pointf : pixelList) {
            canvas.drawPoint(pointf.x, pointf.y, m_paint);
        }
    }
}

