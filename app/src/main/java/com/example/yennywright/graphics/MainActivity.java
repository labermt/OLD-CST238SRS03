package com.example.yennywright.graphics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    List<Point> initPointsArray = new ArrayList<>();
    List<Point> calculatedPointsArray = new ArrayList<>();
    boolean startDrawingFlag = false;
    DrawPoint draw;
    SeekBar rangeControl;
    float rangeValue;
    TextView rangeView;
    EditText itrEdit;
    int itrValue;
    Spinner colorSpinner;
    String userColor;
    Button startButton;
    ImageView imageView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Seekbar manager*/
        rangeControl = findViewById(R.id.range_seekbar);
        rangeControl.setMax(10);
        rangeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rangeValue = ((float) progress / 10);
                rangeView.setText(""+ rangeValue);
                rangeView.setText(null);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                rangeView.setText("" + rangeValue);
            }
        });

        /*Iteration Number manager*/
        itrEdit = findViewById(R.id.iter_edit);
        final CharSequence itrStr = itrEdit.getText();
        if(!isAlpha(itrStr.toString())){
            itrEdit.setError("Value cannot be alphabetical.");
        }
        else{
            itrValue = Integer.parseInt(itrStr.toString());
            if(itrValue == 0){
                itrEdit.setError("Iteration cannot be zero.");
            }
        }
        itrEdit.setError(null);

        /*Color Spinner manager*/
        colorSpinner = findViewById(R.id.color_spinner);
        userColor = new String(colorSpinner.getSelectedItem().toString() + "");

        /*Start Drawing Button manager*/
        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startDrawingFlag = true;
                startDrawingThePoints(rangeValue, itrValue, userColor);
                draw.invalidate();
            }
        });

        imageView = findViewById(R.id.canvas_view);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float x = event.getX();
                float y = event.getY();

                switch (event.getAction()){
                    case MotionEvent.ACTION_BUTTON_PRESS:
                        addPointToArray(x, y, "BLACK");
                        draw.invalidate();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        clearDrawingPad();
                        break;
                }
                return false;
            }
        });
    }

    private void addPointToArray(float inXVal, float inYVal, String inColor){

        if(startDrawingFlag == false){
            Point point = new Point(inXVal, inYVal, inColor);
            initPointsArray.add(point);
        }
    }

    public void clearDrawingPad(){
        DrawPoint draw = null;
        draw.setDrawingCacheEnabled(false);
        startDrawingFlag = true;
        initPointsArray.clear();
        draw.invalidate();
        draw.setDrawingCacheEnabled(true);
    }

    public void startDrawingThePoints(float inRange, int inItrNum, String inColor)
    {
        startDrawingFlag = true;
        Random rand = new Random();
        float deltaX;
        float deltaY;

        initPointsArray.get(rand.nextInt((initPointsArray.size())));
        Point startPoint = new Point(initPointsArray.get(0));
        Point nextPoint = initPointsArray.get(initPointsArray.lastIndexOf(initPointsArray));

        if(inRange != 0){
            deltaX = (nextPoint.getX_value() - startPoint.getX_value()) * inRange;
            deltaY = (nextPoint.getX_value() - startPoint.getY_value()) * inRange;
        }
        else{ //default range
            deltaX = (nextPoint.getX_value() - startPoint.getX_value()) / 2;
            deltaY = (nextPoint.getX_value() - startPoint.getY_value()) / 2;
        }

        Point newCurrentPoint = new Point(deltaX, deltaY, inColor);
        calculatedPointsArray.add(newCurrentPoint);

        for (int i = 0; i < inItrNum; i++) {
            startPoint = newCurrentPoint;
            initPointsArray.get(rand.nextInt((initPointsArray.size())));
            nextPoint = initPointsArray.get(0);

            deltaX = (nextPoint.getX_value() - startPoint.getX_value()) * inRange;
            deltaY = (nextPoint.getX_value() - startPoint.getY_value()) * inRange;

            newCurrentPoint = new Point(deltaX, deltaY, inColor);
            calculatedPointsArray.add(newCurrentPoint);
        }
    }

    public boolean isAlpha(String str)
    {
        boolean status = true;
        char[] charArray = str.toCharArray();
        if(str.length() > 0){
            for (char c : charArray) {
                if (!Character.isLetter(c))
                    status = false;
            }
        }
        else {
            status = false;
        }
        return status;
    }

    public class DrawPoint extends AppCompatImageView {

        List<Point> initPointsArray = new ArrayList<>();
        List<Point> calculatedPointsArray = new ArrayList<>();
        Paint paint;
        Point point;

        public DrawPoint(Context context) {
            super(context);
        }

        public DrawPoint(Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public DrawPoint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            invalidate();

            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);

            for(Point point : initPointsArray){
                paint.setColor(point.getmColorVal());
                canvas.drawPoint(point.getX_value(), point.getY_value(), paint);
            }

            for(Point point : calculatedPointsArray){
                paint.setColor(point.getmColorVal());
                canvas.drawPoint(point.getX_value(), point.getY_value(), paint);
            }
        }
    }

}