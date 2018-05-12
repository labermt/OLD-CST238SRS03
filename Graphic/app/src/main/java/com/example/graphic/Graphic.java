package com.example.graphic;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class Graphic extends AppCompatActivity {
    public CustomImageView drawing_view; //instantiates the CustomImageView class

    //Valid Number Reference: https://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-numeric-in-java
    private boolean validIteration(String iter){ //checks to see if the iteration entered is valid
        if(iter.trim().length() == 0){ //if there is nothing entered return false
            return false;
        }
        try //try to see if it's a real number
        {
            double temp = Double.parseDouble(iter);
        }
        catch(NumberFormatException nfe) //if not a real number return false
        {
            return false;
        }
        return true;
    }

    private boolean validFraction(String frac){ //checks to see if the fraction entered is a valid fraction (0.0 - 1.0)
        if(frac.trim().length() == 0){ //if nothing entered return false
            return false;
        }

        if(frac.equals(".")) //if frac only contains "." then return false
        {
            return false;
        }
        else if(frac.startsWith(".")) //if the user prefers .1 instead of 0.1 then convert to make it a valid number
        {
            String temp = frac; //sets temp to fraction
            frac = "0"; //sets fraction to 0
            frac = frac + temp; //adds temp to the end of frac
        }

        float floatFrac = Float.parseFloat(frac); //turn frac into a float

        return floatFrac >= 0.0 && floatFrac <= 1.0; //if floatFrac is 0.0 and 1.0 or between 0.0 and 1.0 then return true
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphic);
        EditText iteration = findViewById(R.id.Iterations); //lets us manipulate the EditText Iterations
        EditText fraction = findViewById(R.id.Fraction); //lets us manipulate the EditText Fraction
        drawing_view = findViewById(R.id.imageView); //sets the CustomImageView to the ImageView

        // Spinner Reference: https://developer.android.com/guide/topics/ui/controls/spinner
        final Spinner color = findViewById(R.id.Color); //sets the color to the Spinner Color
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ArrayofColors, android.R.layout.simple_spinner_item); //sets the elements of strings.xml into the adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); //sets the spinner to have a regular dropdown menu
        color.setAdapter(adapter); //sets the Spinners adapter
        color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { //controls what happens when an item in the spinner is selected
                switch (parent.getItemAtPosition(position).toString()){ //gets the item selected
                    case "BLACK": //if the item selected is "BLACK" then set color to black
                        drawing_view.setColor(Color.BLACK);
                        drawing_view.invalidate(); //calls onDraw to immediately change the color of dots to "Black"
                        break;

                    case "BLUE":
                        drawing_view.setColor(Color.BLUE);
                        drawing_view.invalidate();
                        break;

                    case "RED":
                        drawing_view.setColor(Color.RED);
                        drawing_view.invalidate();
                        break;

                    case "GREEN":
                        drawing_view.setColor(Color.GREEN);
                        drawing_view.invalidate();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { //if nothing is picked nothing happens

            }
        });

        iteration.addTextChangedListener(new TextValidator(iteration) { //gets called when iterations is changed
            @Override
            public void validate(TextView iteration, String stringIteration) {
                if(!validIteration(stringIteration)) //sets an error if the iteration entered is valid or not
                {
                    iteration.setError("Must be number"); //if invalid set error
                }
                else{
                    drawing_view.setImageViewIter(Integer.parseInt(stringIteration)); //if iteration is valid we set the iteration to the CustomImageView iteration
                }
            }
        });

        fraction.addTextChangedListener(new TextValidator(fraction) { //gets called when fraction is changed
            @Override
            public void validate(TextView fraction, String stringFraction) {
                if (!validFraction(stringFraction)) { //checks to see if the fractions entered is valid (0.0 - 1.0)
                    fraction.setError("Must be between 0.0 and 1.0"); //if invalid set error
                } //if error onDraw will output the last valid iteration
                else{
                    drawing_view.setImageViewFrac(Float.parseFloat(stringFraction)); //if iteration is valid we set the fraction to the CustomImageView fraction
                }
            }
        });
    }
}
