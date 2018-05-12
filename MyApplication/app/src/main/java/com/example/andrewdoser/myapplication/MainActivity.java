package com.example.andrewdoser.myapplication;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PaintView paintView;
    private static SeekBar seek_bar;
    private static TextView text_view;
    private static Button mStart;
    private static Button mClear;
    private static EditText mIterations;
    public boolean startProg = false;
    public int iteration = 0;
    public float dist = 0;

    public int progress_value = 50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        paintView = (PaintView) findViewById(R.id.paintView);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mClear = (Button) findViewById(R.id.bt_clear);
        mStart = (Button) findViewById(R.id.bt_start);
        mIterations = (EditText) findViewById(R.id.editText);
        seebbarr();

        paintView.init(metrics);

        StartCheck();
        ClearCheck();


    }

    public void StartCheck()
    {


        final float div = 100;


        mStart.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {

                        if(paintView.GetCount() > 2)
                        {
                            String conv = mIterations.getText().toString();
                            iteration = Integer.parseInt(conv);
                            dist = (float)progress_value/div;
                            for(int i = iteration; i > 0; i--)
                            {
                                paintView.ExecuteSpecial(dist);

                            }
                        }
                        else
                        {
                            mStart.setError("Please input three points in the draw box.");
                        }
                    }
                }
        );
    }
    public void ClearCheck()
    {
        mClear.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v)
                    {
                        paintView.clear();
                        mIterations.setText("");
                        seek_bar.setProgress(50);
                    }
                }
        );
    }

    public void seebbarr(){
        seek_bar = (SeekBar)findViewById(R.id.seekBar);
        text_view =(TextView)findViewById(R.id.seek_textView);
        text_view.setText("Distance Will Travel: " + seek_bar.getProgress() + "%" );

        seek_bar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        progress_value = progress;
                        text_view.setText("Distance Will Travel: " + progress + "%" );
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        text_view.setText("Distance Will Travel: " + progress_value + "%" );

                    }
                }
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.normal:
                paintView.normal();
                return true;
            case R.id.emboss:
                paintView.emboss();
                return true;
            case R.id.blur:
                paintView.blur();
                return true;
            case R.id.clear:
                paintView.clear();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
