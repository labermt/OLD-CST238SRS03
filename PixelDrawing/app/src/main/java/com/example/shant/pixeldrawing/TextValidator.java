package com.example.shant.pixeldrawing;

import android.text.Editable;
        import android.text.TextWatcher;
        import android.widget.TextView;

        // used: https://stackoverflow.com/questions/33072569/best-practice-input-validation-android
public abstract class TextValidator implements TextWatcher {
    private final TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    public abstract void validate(TextView textView, String text);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = textView.getText().toString();
        validate(textView, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Not using this!! */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Not using this!! */ }
}
