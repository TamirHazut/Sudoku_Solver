package com.example.sudokusolver;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;

public class Cell extends AppCompatEditText {
    private int cell_row;
    private int cell_column;

    public Cell(Context context, int row, int column) {
        super(context);
        this.cell_row = row;
        this.cell_column = column;
        setAttributes(context);
    }

    private void setAttributes(Context context) {
        this.setWidth(0);
        this.setHeight(0);
        this.setBackground(context.getResources().getDrawable(R.drawable.cell_border));
        this.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        this.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1), new CellFilter() });
        this.setTextSize(20);
    }

    protected void showValue(char number) {
        setText((number != '0' ? Character.toString(number) : ""));
    }
}
