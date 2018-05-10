package com.example.shant.pixeldrawing;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.AdapterView.*;

public class MainActivity extends AppCompatActivity {

    private EditText m_NumberOfIterations;
    private EditText m_FractionInDecimal;
    private Button m_LetsDraw;
    public ShantsImageView m_ImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m_NumberOfIterations = findViewById(R.id.editIterations);
        m_FractionInDecimal = findViewById(R.id.editRatio);
        m_ImageView = findViewById(R.id.imageView);
        m_LetsDraw = findViewById(R.id.enterButton);
        m_ImageView.setPixelColor(Color.BLACK);
        m_NumberOfIterations.setText("5");
        m_FractionInDecimal.setText("0.5");

        // used documentation: https://developer.android.com/guide/topics/ui/controls/spinner
        final Spinner m_ColorChoice = findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> ColorAdapter = ArrayAdapter.createFromResource(this,
                R.array.color_array, android.R.layout.simple_spinner_item);
        ColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        m_ColorChoice.setAdapter(ColorAdapter);
        m_ColorChoice.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                m_ImageView.setPixelColor(findColor(parent.getItemAtPosition(position).toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        m_NumberOfIterations.addTextChangedListener(new TextValidator(m_NumberOfIterations) {
            @Override
            public void validate(TextView textView, String text) {
                double Iterations;
                if (text.trim().length() == 0 || text.equals(".")) {
                    m_NumberOfIterations.setText("1");
                    m_NumberOfIterations.selectAll();
                    Iterations = 1;
                } else {
                    Iterations = Double.parseDouble(text);
                    if (Iterations <= 0 || Iterations >= 1000) {
                        textView.setError("Must be between 0 and 1000!");
                    }
                }
            }
        });

        m_FractionInDecimal.addTextChangedListener(new TextValidator(m_FractionInDecimal) {
            @Override
            public void validate(TextView textView, String text) {
                float FractionInDecimal;
                if (text.trim().length() == 0 || text.equals(".")) {
                    m_FractionInDecimal.setText("0.");
                    FractionInDecimal = 0.0f;
                } else {
                    FractionInDecimal = Float.parseFloat(text);
                    if (FractionInDecimal <= 0 || FractionInDecimal >= 1.0) {
                        textView.setError("Must be between 0 and 1.0!");
                    }
                }
            }
        });


        m_LetsDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int NewNumberOfIteratons = Integer.parseInt(String.valueOf(m_NumberOfIterations.getText()));
                float NewRatio = Float.parseFloat(String.valueOf(m_FractionInDecimal.getText()));
                int NewPixelColor = findColor(m_ColorChoice.getSelectedItem().toString());
                m_NumberOfIterations = findViewById(R.id.editIterations);
                m_FractionInDecimal = findViewById(R.id.editRatio);
                if (NewNumberOfIteratons <= 0 || NewNumberOfIteratons >= 1000 || NewRatio <= 0.0 || NewRatio >= 1.0 ) {
                    Context context = getApplicationContext();
                    CharSequence text = "Pixel Ct and Fraction fields must be valid!!";
                    int duration = Toast.LENGTH_LONG;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 0);
                    toast.show();
                }
                else {
                    m_ImageView.ClearGesture();
                    m_ImageView.SetUserInfo(NewNumberOfIteratons, NewRatio, NewPixelColor);
                }
            }
        });
    }
    public int findColor(String color_string) {
        int color = Color.BLACK;
        switch (color_string) {
            case "Blue": {
                color = Color.BLUE;
                break;
            }
            case "Red": {
                color = Color.RED;
                break;
            }
            case "Green": {
                color = Color.GREEN;
                break;
            }
            case "Black": {
                color = Color.BLACK;
                break;
            }
            case "Yellow": {
                color = Color.YELLOW;
                break;
            }
        }
        return color;
    }
}
