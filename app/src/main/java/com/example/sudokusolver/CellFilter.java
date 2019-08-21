package com.example.sudokusolver;

import android.text.InputFilter;
import android.text.Spanned;

public class CellFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        try {
            if (source.length() > 0) {
                if (isInRange(source.toString().charAt(0))) {
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isInRange(char c) {
        return c >= MainActivity.MIN_VALUE && c <= MainActivity.MAX_VALUE;
    }
}

