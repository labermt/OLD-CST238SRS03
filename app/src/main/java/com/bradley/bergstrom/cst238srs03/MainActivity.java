package com.bradley.bergstrom.cst238srs03;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBar;
    private Spinner spinner;
    public EditText iterationText;
    private Button  NextButton;
    private TextView seekBarText;

    public Settings settings = new Settings();

    public class Settings {
        double seekBarNumb;
        String color;
        int iterations;

        public void setSeekBarNumb(double seekBarNumb) {
            this.seekBarNumb = seekBarNumb;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = new Settings();

        seekBarText = (TextView) findViewById(R.id.SeekBarText);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
           int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress=progresValue;
                settings.seekBarNumb = (double)progresValue / 100;
                seekBarText.setText(progresValue + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        spinner = (Spinner) findViewById(R.id.spinner);
        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                settings.color = selectedItem;
//                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_LONG).show();

            }

            public void onNothingSelected(AdapterView<?> parent)
            {
            }
        });

        iterationText = (EditText) findViewById(R.id.iterationField);

        NextButton = (Button) findViewById(R.id.NextButton);
        NextButton.setOnClickListener( new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        String derp = iterationText.getText().toString();
                        if(derp.isEmpty()){
                            iterationText.setError("Please enter a number");
                        } else{
                            settings.iterations = Integer.parseInt(iterationText.getText().toString());
                            Intent myIntent = new Intent(MainActivity.this, DrawingActivity.class);
                            myIntent.putExtra("iterations",settings.iterations);
                            myIntent.putExtra("seekbar",settings.seekBarNumb);
                            myIntent.putExtra("colour",settings.color);

                            startActivity(myIntent);
                        }
                    }
                });
    }
}
