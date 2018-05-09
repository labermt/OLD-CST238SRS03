package com.example.graphics;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;
import java.util.Vector;

public class ImageActivity extends AppCompatActivity
{
    String f_iter = null;
    String f_fraction = null;
    String f_color = null;
    GraphicImage drawing;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Intent intent = getIntent();
        f_iter = intent.getStringExtra(MainActivity.F_ITER);
        f_fraction = intent.getStringExtra(MainActivity.F_FRACTION);
        f_color = intent.getStringExtra(MainActivity.F_COLOR);
        drawing = findViewById(R.id.imageView);
        drawing.setIter(f_iter);
        drawing.setFrac(f_fraction);
        drawing.setCol(f_color);
    }

    public void OnButtonDraw(View view)
    {
        drawing.setButtonPressed();
        drawing.randomizeVertices();
        drawing.calculatePoints();
        drawing.invalidate();
    }
}
