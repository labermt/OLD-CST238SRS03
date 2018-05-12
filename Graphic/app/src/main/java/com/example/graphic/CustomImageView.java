package com.example.graphic;

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

//PointF Reference: https://developer.android.com/reference/android/graphics/PointF
//Class letting us manipulate an ImageView
public class CustomImageView extends AppCompatImageView implements View.OnClickListener {
    private List<PointF> verticesLibrary = new ArrayList<>(); //holds on touch vertices
    private List<PointF> generatedVerticesLibrary = new ArrayList<>(); //holds generated vertices
    private Paint vertexDraw = new Paint(Paint.ANTI_ALIAS_FLAG); //Lets us change the color of the points drawn (ANTI_ALIAS_FLAG smooths out edges of whats being drawn)
    private int imageViewIter = 5; //Number of vertices to generate
    private float imageViewFrac = 0.5f; //distance between generated vertices
    private int color = Color.BLUE; //used to change the color of vertices
    private GestureDetector gesture = new GestureDetector(getContext(), new GestureListener()); //Lets us manipulate what happens on touch events

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setImageViewIter(int imageViewIter){
        this.imageViewIter = imageViewIter;
    }

    public int getImageViewIter() {
        return imageViewIter;
    }

    public void setImageViewFrac(float imageViewFrac){
        this.imageViewFrac = imageViewFrac;
    }

    public float getImageViewFrac() {
        return imageViewFrac;
    }

    @Override
    public void onClick(View v) {} //needed for View class

    //Gesture Listener Reference: https://developer.android.com/training/gestures/detector
    //Motion Event Reference: https://developer.android.com/reference/android/view/MotionEvent
    // Random Number Reference: https://stackoverflow.com/questions/6029495/how-can-i-generate-random-number-in-specific-range-in-android
    // Invalidate Reference: https://stackoverflow.com/questions/10647558/when-its-necessary-to-execute-invalidate-on-a-view
    public class GestureListener extends GestureDetector.SimpleOnGestureListener{
        public boolean onDown(MotionEvent event){
            PointF vertexAdd = new PointF(event.getX(),event.getY()); //sets the x and y coordinate of vertexAdd to the x and y of the touch event
            verticesLibrary.add(vertexAdd); //adds the vertex to the verticesLibrary

            if(verticesLibrary.size() > 2) //if there is more than 2 vertices in the verticesLibrary then we generate random vertices
            {
                PointF generatedVertexAdd = new PointF();
                PointF generatedVertexStart = generatedVertexAdd;
                PointF generatedVertexEnd;
                Random r = new Random();

                for (int i = 0; i < imageViewIter; i++) //generates a number of random vertices depending on the iterations desired (default is 5)
                {
                    generatedVertexEnd = verticesLibrary.get(r.nextInt(verticesLibrary.size())); //gets a random vertex in the verticesLibrary
                    generatedVertexAdd.x = (generatedVertexStart.x + generatedVertexEnd.x ) * imageViewFrac; // formula used to generate a random x coordinate
                    generatedVertexAdd.y = (generatedVertexStart.y + generatedVertexEnd.y) * imageViewFrac; // formula used to generate a random y coordinate
                    generatedVerticesLibrary.add(generatedVertexAdd); //adds the randomly generated vertex to the generatedVerticesLibrary
                    generatedVertexStart = generatedVertexAdd; //sets the start equal to the add so the points stay close to each other (depending on the fraction)
                    generatedVertexAdd = new PointF(); // had to new up generatedVertexAdd or else it would continuously put the same point into generatedVerticesLibrary
                }
            }
            invalidate(); //Calls onDraw
            return true;
        }
        @Override
        public void onLongPress(MotionEvent event) { //clears the ImageView when the screen is pressed for a long time
            verticesLibrary.clear();
            generatedVerticesLibrary.clear();
            invalidate();
            super.onLongPress(event);
        }
    }

    // View ctors and onDraw Reference: https://developer.android.com/reference/android/view/View
    // View ctors
    public CustomImageView(Context context){
        super(context);
    }
    public CustomImageView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }
    public CustomImageView(Context context, AttributeSet attributeSet, int defStyle){
        super(context, attributeSet, defStyle);
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event){ //lets gesture know that a touch event has occurred
        return gesture.onTouchEvent(event);
    }

    @Override
    public void onDraw(Canvas canvas){ //draws the points onto the ImageView
        super.onDraw(canvas);
        vertexDraw.setStrokeWidth(10); //sets how big the stroke width will be
        vertexDraw.setColor(color); //sets the color the vertices will be
        for(PointF draw : verticesLibrary) //goes through the verticesLibrary and outputs all the chosen vertices
        {
            canvas.drawPoint(draw.x, draw.y, vertexDraw); //draws the point
        }
        for (PointF draw : generatedVerticesLibrary) //goes through the generatedVerticesLibrary and outputs all the generated vertices
        {
            canvas.drawPoint(draw.x, draw.y, vertexDraw);
        }
    }
}
