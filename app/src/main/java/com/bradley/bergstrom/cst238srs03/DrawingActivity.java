package com.bradley.bergstrom.cst238srs03;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.support.constraint.solver.GoalRow;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class DrawingActivity extends AppCompatActivity {
    private DrawingView dv ;
    private ImageButton help;
    private Button GO;
    Toolbar myToolBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.clear: {
                dv.clear();
            }
        }
        return true;
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing);

        myToolBar = (Toolbar) findViewById(R.id.clear);

        help = (ImageButton) findViewById(R.id.helpButton);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Click 3 or more points in the frame and press GO!", Toast.LENGTH_LONG).show();
            }
        });
        //Bundle intent = getIntent().getExtras();
        dv = (DrawingView) findViewById(R.id.DrawingView1);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        if (getIntent().hasExtra("colour")) {

            String color = getIntent().getStringExtra("colour");
          //  Toast.makeText(getApplicationContext(), color, Toast.LENGTH_LONG).show();
            int paintColor = getColor(color);
            dv.changeCurrentColor(paintColor);
        }

        dv.init(metrics);

        GO = (Button) findViewById(R.id.GoButton);
        GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dv.points.size() >= 3){
                    //dv.drawLines(getIntent().getStringExtra("iterations"),getIntent().getStringExtra("seekbar"));
                }
            }
        });
    }


    public int getColor(String colour){
        int color = Color.BLUE;
        if(colour != null){
            switch (colour){
                case "Blue": color = Color.BLUE; break;
                case "Green": color = Color.GREEN; break;
                case "Red": color = Color.RED; break;
                case "Yellow": color = Color.YELLOW; break;
                case "Purple": color = Color.MAGENTA; break;
                case "Gray": color = Color.GRAY; break;

            }
        }
        return color;
    }
}

