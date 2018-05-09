package com.example.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;
import java.util.Vector;

public class GraphicImage extends AppCompatImageView implements View.OnClickListener
{
    Vector<PointF> vertices;
    Vector<PointF> points;
    String iter;
    String frac;
    String col;
    Boolean buttonPressed = false;

    public GraphicImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public void setButtonPressed()
    {
        buttonPressed = true;
    }

    public void setIter(String in)
    {
        iter = in;
    }

    public void setFrac(String in)
    {
        frac = in;
    }

    public void setCol(String in)
    {
        col = in;
    }

    public int myColor()
    {
        if (col.equalsIgnoreCase("red"))
            return Color.rgb(255,0,0);
        else if (col.equalsIgnoreCase("blue"))
            return Color.rgb(0,0,255);
        else if (col.equalsIgnoreCase("green"))
            return Color.rgb(0,255,0);
        else
            return Color.rgb(0,0,0);
    }

    public int myIter()
    {
        return Integer.parseInt(iter);
    }

    public float myFraction()
    {
        return Float.parseFloat(frac);
    }

    public void randomizeVertices()
    {
        Random rand = new Random();
        int r;
        for (int i = 0; i < 25; i++)
        {
            r = rand.nextInt(vertices.size()) + 1;
            vertices.add(vertices.remove(r));
        }
    }

    public void calculatePoints()
    {
        PointF temp = null;
        temp.set((vertices.elementAt(0).x + vertices.elementAt(1).x)*(myFraction()),
                (vertices.elementAt(0).y + vertices.elementAt(1).y)*(myFraction()));
        for (int i = 0; i < myIter(); i++)
        {
            for (PointF p : vertices)
            {
                if (i == 0 && vertices.elementAt(0) == p)
                {
                    temp.set((vertices.elementAt(0).x + vertices.elementAt(1).x)*(myFraction()),
                            (vertices.elementAt(0).y + vertices.elementAt(1).y)*(myFraction()));
                }
                else
                    temp.set((p.x + temp.x) * myFraction(), ((p.y + temp.y) * myFraction()));
            }
        }
    }

    public Paint myPaint()
    {
        Paint temp_paint = new Paint();
        temp_paint.setColor(myColor());
        return temp_paint;
    }

    public void onClick(View view)
    {
        PointF temp = null;
        temp.set(getX(), getY());
        vertices.add(temp);
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        for (PointF p : vertices)
            canvas.drawPoint(p.x, p.y, myPaint());
        if (buttonPressed == true)
        {
            for (PointF p : points)
                canvas.drawPoint(p.x, p.y, myPaint());
        }
    }
}
