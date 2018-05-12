package com.example.andrewdoser.myapplication;

import java.util.ArrayList;
import java.util.List;

public class DataHolder {

    private List<Float> xlist;
    private List<Float> ylist;

    public DataHolder()
    {
        this.xlist = new ArrayList<Float>();
        this.ylist = new ArrayList<Float>();

    }

    public void ADD(float tx, float ty)
    {
        xlist.add(tx);
        ylist.add(ty);

    }

    public void CLEAR()
    {
        this.xlist = new ArrayList<Float>();
        this.ylist = new ArrayList<Float>();
    }
}
