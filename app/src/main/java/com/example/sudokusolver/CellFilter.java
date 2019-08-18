package com.example.sudokusolver;

import android.text.InputFilter;
import android.text.Spanned;

public class CellFilter implements InputFilter {


    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dStart, int dEnd) {
        try {
            if (source.length() > 0) {
                String newVal = dest.toString().substring(0, dStart) + dest.toString().substring(dEnd, dest.toString().length());
                newVal = newVal.substring(0, dStart) + source.toString() + newVal.substring(dStart, newVal.length());
                char input = newVal.charAt(0);
                if (isInRange(input)) {
                    return null;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "";
    }

    private boolean isInRange(char c) {
        return MainActivity.MAX_VALUE > MainActivity.MIN_VALUE ? c >= MainActivity.MIN_VALUE && c <= MainActivity.MAX_VALUE : c >= MainActivity.MAX_VALUE && c <= MainActivity.MIN_VALUE;
    }
}

