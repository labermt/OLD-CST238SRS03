package com.example.tcape.customviewexample;


public class Point {
    public float xCoord;
    public float yCoord;
    public int pColor;

    Point(float x, float y, int color){
        this.xCoord = x;
        this.yCoord = y;
        this.pColor = color;
    }
    public void SetColor(int color)
    {
        this.pColor = color;
    }
}
