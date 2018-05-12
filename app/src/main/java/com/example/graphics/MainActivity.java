package com.example.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import static android.graphics.Color.WHITE;

public class MainActivity extends AppCompatActivity {
    ImageView myImageView;
    View v;
    Paint color;
    String colorString;
    int iterations;
    float number;
    Canvas canvas;
    EditText iterations_;
    EditText color_;
    EditText number_;
    int count;
    ArrayList<Point> points;
    ArrayList<Point> drawnPoints;
    ArrayList<Point> initialPoints;
    TextView textView;
    float lastX;
    float lastY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        points = new ArrayList<>();
        drawnPoints = new ArrayList<>();
        initialPoints = new ArrayList<>();
        color = new Paint();
        myImageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        v = new View(getApplicationContext());
        Bitmap bitmap = Bitmap.createBitmap(1000, 775, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        iterations_ = findViewById(R.id.Iterations);
        color_ = findViewById(R.id.Color);
        number_ = findViewById(R.id.Number);
        v.draw(canvas);
        myImageView.setImageBitmap(bitmap);
        myImageView.setOnTouchListener(touchListener);
        myImageView.setOnClickListener(clickListener);
        count = 0;
    }

    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return gd.onTouchEvent(event);
        }
    };

    final GestureDetector gd = new GestureDetector(new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onDoubleTap(MotionEvent event){
            Clear();
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent event){
            return true;
        }

        @Override
        public boolean onDown(MotionEvent event){
                lastX = event.getX();
                lastY = event.getY();
                return false;
        }

    });

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!color_.getText().toString().equals("")) {
                colorString = color_.getText().toString();
                if (colorString.toLowerCase().equals("green"))
                    color.setColor(Color.GREEN);
                else if (colorString.toLowerCase().equals("blue"))
                    color.setColor(Color.BLUE);
                else if (colorString.toLowerCase().equals("yellow"))
                    color.setColor(Color.YELLOW);
                else if (colorString.toLowerCase().equals("red"))
                    color.setColor(Color.RED);
                else if (colorString.toLowerCase().equals("cyan"))
                    color.setColor(Color.CYAN);
                else if (colorString.toLowerCase().equals("magenta"))
                    color.setColor(Color.MAGENTA);
                else if (colorString.toLowerCase().equals("black"))
                    color.setColor(Color.BLACK);
                else if (colorString.toLowerCase().equals("gray") || colorString.toLowerCase().equals("grey"))
                    color.setColor(Color.GRAY);
            }
                count++;
                Point point = new Point(Math.round(lastX), Math.round(lastY));
                points.add(point);
                drawnPoints.add(point);
                initialPoints.add(point);
                if (color == null) {
                    canvas.drawCircle(point.x, point.y, 5, new Paint(Color.BLACK));
                } else canvas.drawCircle(point.x, point.y, 5, color);
                v.draw(canvas);
                if (count >= 3)
                    textView.setText("");
        }
    };

    public void Clear(){
        points.clear();
        initialPoints.clear();
        drawnPoints.clear();
        canvas.drawColor(Color.WHITE);
        iterations_.setText("");
        color_.setText("");
        number_.setText("");
        count = 0;
        color.setColor(Color.BLACK);
    }

    public void Iterate() {
        Random r = new Random();
        Point start = initialPoints.get(0);
        for (int i = 0; i < iterations; i++) {
            Point end = initialPoints.get(r.nextInt(initialPoints.size()));
            int dx = end.x - start.x;
            int dy = end.y - start.y;
            Point newPoint = new Point(start.x + Math.round(number * dx), start.y + Math.round(number * dy));
            start = newPoint;
            points.add(newPoint);
        }
    }

    public void enter(View view){
        if (count >= 3){
            if(iterations_.getText().toString().equals(""))
                iterations = 5000;
            else iterations = Integer.parseInt(iterations_.getText().toString());

            if(color_.getText().toString().equals(""))
                colorString = "black";
            else colorString = color_.getText().toString();

            if(number_.getText().toString().equals(""))
                number = .5f;
            else number = Float.parseFloat(number_.getText().toString());
            if(number < 0 || number > 1)
                number = .5f;

            if (colorString.toLowerCase().equals("green"))
                color.setColor(Color.GREEN);
            else if (colorString.toLowerCase().equals("blue"))
                color.setColor(Color.BLUE);
            else if (colorString.toLowerCase().equals("yellow"))
                color.setColor(Color.YELLOW);
            else if (colorString.toLowerCase().equals("red"))
                color.setColor(Color.RED);
            else if (colorString.toLowerCase().equals("cyan"))
                color.setColor(Color.CYAN);
            else if (colorString.toLowerCase().equals("magenta"))
                color.setColor(Color.MAGENTA);
            else if (colorString.toLowerCase().equals("black"))
                color.setColor(Color.BLACK);
            else if (colorString.toLowerCase().equals("gray") || colorString.toLowerCase().equals("grey"))
                color.setColor(Color.GRAY);
            else color.setColor(Color.BLACK);

            Iterate();
            for(Point point : points) {
                if(!drawnPoints.contains(point)) {
                    canvas.drawCircle(point.x, point.y, 5, color);
                    drawnPoints.add(point);
                }
            }
        }
        else textView.setText("Error: Need at least 3 points to draw");

    }


}
