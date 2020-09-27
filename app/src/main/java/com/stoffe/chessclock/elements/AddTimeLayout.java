package com.stoffe.chessclock.elements;

import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.NumberPicker;

import com.stoffe.chessclock.R;

import androidx.core.content.ContextCompat;

public class AddTimeLayout extends LinearLayout {
    final NumberPicker startTimePicker;
    final NumberPicker incrementPicker;

    public AddTimeLayout(Context context) {
        super(context);

        this.setOrientation(LinearLayout.HORIZONTAL);

        startTimePicker = new NumberPicker(getContext());
        startTimePicker.setMaxValue(100);
        startTimePicker.setMinValue(0);

        incrementPicker = new NumberPicker(getContext());
        incrementPicker.setMaxValue(100);
        incrementPicker.setMinValue(0);

        this.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_grey));
        startTimePicker.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_grey));
        incrementPicker.setBackgroundColor(ContextCompat.getColor(context, R.color.dark_grey));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams numPickerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPickerParams.weight = 1;

        LinearLayout.LayoutParams incPickerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        incPickerParams.weight = 1;

        this.setLayoutParams(params);
        this.addView(startTimePicker, numPickerParams);
        this.addView(incrementPicker, incPickerParams);
    }

    public int getStartTimePickerValue() {
        return startTimePicker.getValue();
    }

    public int getIncrementPickerValue() {
        return incrementPicker.getValue();
    }
}
