package com.example.graphics;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    public static final String F_ITER = "com.example.happybirthday2you.ITER";
    public static final String F_FRACTION = "com.example.happybirthday2you.FRACTION";
    public static final String F_COLOR = "com.example.happybirthday2you.COLOR";

    private TextView edit_iter;
    private TextView edit_fraction;
    private TextView edit_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_iter = findViewById(R.id.edit_iter);
        edit_fraction = findViewById(R.id.edit_fraction);
        edit_color = findViewById(R.id.edit_color);
    }

    public void Startup(View view)
    {
        if (!(edit_iter.getText().toString().matches("[0-9]+")))
        {
            edit_iter.setText("");
            edit_iter.setError("Numbers only please!");
        }
        else
        {
            int l_iter = Integer.parseInt(edit_iter.getText().toString());
            if (!(edit_fraction.getText().toString().matches("[0-9.]+")))
            {
                edit_fraction.setText("");
                edit_fraction.setError("Numbers only please!");
            }
            else
            {
                double l_fraction = Double.parseDouble(edit_fraction.getText().toString());
                if (l_fraction <= 0.0 || l_fraction >= 1.0)
                {
                    edit_fraction.setText("");
                    edit_fraction.setError("Between 0.0 and 1.0 please!");
                }
                else
                {
                    String l_color = edit_color.getText().toString().toLowerCase();
                    if (!(l_color.equalsIgnoreCase("black") ||
                            l_color.equalsIgnoreCase("red") ||
                            l_color.equalsIgnoreCase("green") ||
                            l_color.equalsIgnoreCase("blue")))
                    {
                        edit_color.setText("");
                        edit_color.setError("Refer to colors below please!");
                    }
                    else // VALIDATION COMPLETE, ONTO NEXT ACTIVITY
                    {
                        Intent intent = new Intent(this, ImageActivity.class);
                        intent.putExtra(F_ITER, l_iter);
                        intent.putExtra(F_FRACTION, l_fraction);
                        intent.putExtra(F_COLOR, l_color);
                        startActivity(intent);
                    }
                }
            }
        }
    }
}
