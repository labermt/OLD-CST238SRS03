package com.example.tcape.customviewexample;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.concurrent.ThreadLocalRandom;


public class MainActivity extends AppCompatActivity {

    private CustomView myView;
    private EditText myRatio;
    private EditText myIndex;
    private Button myButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String TAG = "tag";
        myView = findViewById(R.id.customView);
        myRatio = findViewById(R.id.editText);
        myIndex = findViewById(R.id.editText4);
        myButton = findViewById(R.id.button);
        myRatio.setSelectAllOnFocus(true);
        myIndex.setSelectAllOnFocus(true);
        myView.dotColor = Color.BLACK;
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.color_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (parent.getItemAtPosition(position).toString()) {
                    case "Black":
                        myView.dotColor = Color.BLACK;
                        break;
                    case "Red":
                        myView.dotColor = Color.RED;
                        break;
                    case "Blue":
                        myView.dotColor = Color.BLUE;
                        break;
                    case "Green":
                        myView.dotColor = Color.GREEN;
                        break;
                    case "Yellow":
                        myView.dotColor = Color.YELLOW;
                        break;
                    case "Gray":
                        myView.dotColor = Color.GRAY;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        myRatio.addTextChangedListener(new TextValidator(myRatio) {
            @Override
            public void validate(TextView textView, String text) {
                Double ratio;

                if(text.trim().length() == 0 || text.equals(".") || text.equals(",")){
                    myRatio.setText("0.");
                    myRatio.setSelection(myRatio.getText().length());
                    ratio = 0.0;
                }
                else
                    ratio = Double.parseDouble(text);

                if(ratio <= 0 || ratio >= 1)
                    myRatio.setError("Invalid");
            }
        });

        myIndex.addTextChangedListener(new TextValidator(myIndex) {
            @Override
            public void validate(TextView textView, String text) {
                Double index;
                if(text.trim().length() != 0)
                    index = Double.parseDouble(text);
                else
                {
                    myIndex.setText("1");
                    myIndex.selectAll();
                    index = 1.0;
                }
                if (index < 1 || index > 10000)
                    myIndex.setError("Invalid");
            }
        });

        myButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
               //Toast.makeText(getBaseContext(), "Button Pushed", Toast.LENGTH_LONG).show();
                // run the algorithm
                if (myView.Points.size() < 3)
                {
                    Toast.makeText(getBaseContext(), "Need at least 3 dots", Toast.LENGTH_LONG).show();
                    return;
                }

                float ratio = (float)Double.parseDouble(myRatio.getText().toString());
                int index = Integer.parseInt(myIndex.getText().toString());
                float newX, newY;
                int random = ThreadLocalRandom.current().nextInt(0, myView.Points.size());
                Point current = myView.Points.get(random);
                random = ThreadLocalRandom.current().nextInt(0, myView.Points.size());
                Point next = myView.Points.get(random);

                while (next.equals(current)){
                    random = ThreadLocalRandom.current().nextInt(0, myView.Points.size());
                    next = myView.Points.get(random);
                }

                float deltaX = Math.abs(current.xCoord - next.xCoord);
                float deltaY = Math.abs(current.yCoord - next.yCoord);

                newX = current.xCoord > next.xCoord ?
                        current.xCoord - (ratio * deltaX) :
                        current.xCoord + (ratio * deltaX);

                newY = current.yCoord > next.yCoord ?
                        current.yCoord - (ratio * deltaY) :
                        current.yCoord + (ratio * deltaY);

                Point newPoint = new Point(newX, newY, myView.dotColor);
                myView.GenPoints.add(newPoint);

                for(int i = 0; i < index; i++){
                    current = newPoint;
                    random = ThreadLocalRandom.current().nextInt(0, myView.Points.size());
                    next = myView.Points.get(random);
                    deltaX = Math.abs(current.xCoord - next.xCoord);
                    deltaY = Math.abs(current.yCoord - next.yCoord);

                    newX = current.xCoord > next.xCoord ?
                            current.xCoord - (ratio * deltaX) :
                            current.xCoord + (ratio * deltaX);

                    newY = current.yCoord > next.yCoord ?
                            current.yCoord - (ratio * deltaY) :
                            current.yCoord + (ratio * deltaY);

                    newPoint = new Point(newX, newY, myView.dotColor);
                    myView.GenPoints.add(newPoint);
                }
                myView.invalidate();
            }
        });
    }
}