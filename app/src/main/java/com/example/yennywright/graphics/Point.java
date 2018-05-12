package com.example.yennywright.graphics;

import android.graphics.Color;

public class Point {
    private float x_value;
    private float y_value;
    private String mColor;

    public float getX_value(){ return x_value; }
    public float getY_value(){ return y_value; }
    public String getmColor() { return mColor; }
    public int getmColorVal(){

        int outColor = Color.BLACK;

        switch(mColor){
            case "RED":
                outColor = Color.RED;
                break;
            case "GREEN":
                outColor = Color.GREEN;
                break;
            case "BLUE":
                outColor = Color.BLUE;
                break;
            case "YELLOW":
                outColor = Color.YELLOW;
                break;
        }
        return outColor;
    }

    public void setX_value(float inXVal) {x_value = inXVal;}
    public void setY_value(float inYVal) {y_value = inYVal;}
    public void setmColor(String inColor) {mColor = inColor;}

    //constructor
    public Point(float inX, float inY, String mColor){
        this.x_value = inX;
        this.y_value = inY;
        this.mColor = mColor;
    }

    public Point(Point inPoint){
        this.x_value = inPoint.x_value;
        this.y_value = inPoint.y_value;
        this.mColor = inPoint.mColor;
    }
}
