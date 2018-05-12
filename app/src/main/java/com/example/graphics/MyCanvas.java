package com.example.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class MyCanvas extends View {
    ArrayList<Point> points = new ArrayList<>();
    Paint paintColor = new Paint();

    public MyCanvas(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        for(Point point : points)
            canvas.drawPoint(point.x, point.y, paintColor);
    }

}