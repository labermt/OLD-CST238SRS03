package com.example.david.graphics;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerView;


public class MainActivity extends AppCompatActivity
{
    private GraphicsView graphics;
    private float fractionValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graphics = (GraphicsView) findViewById(R.id.graphics_view);

        ((Button)findViewById(R.id.runButton)).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                graphics.createArt();
            }
        });


        SeekBar bar = (SeekBar)findViewById(R.id.fractionBar);
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                graphics.setFraction(progress);
                ((TextView)findViewById(R.id.fractionValueLabel)).setText(graphics.getFraction());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                graphics.invalidate();
            }
        });
        SeekBar itterBar = (SeekBar)findViewById(R.id.iterationBar);
        itterBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                graphics.setIteration(progress);
                ((TextView)findViewById(R.id.iterationValueLabel)).setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {  }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {  }
        });

        //https://medium.com/@skydoves/how-to-implement-color-picker-in-android-61d8be348683
        ((ColorPickerView)findViewById(R.id.colorPickerView)).setColorListener(new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                graphics.setPointColor(colorEnvelope.getColor());
            }
        });

    }

    @Override
    public void onBackPressed(){
        if(!graphics.back()) super.onBackPressed();
    }



} //end MainActivity class