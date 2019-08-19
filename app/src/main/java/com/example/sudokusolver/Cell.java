package com.example.sudokusolver;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;

import org.w3c.dom.Text;

public class Cell extends AppCompatEditText {
    private int cell_row;
    private int cell_column;

    public Cell(Context context, int row, int column) {
        super(context);
        this.cell_row = row;
        this.cell_column = column;
        setAttributes(context);
    }

    /* Display Methods */
    protected void displayValue(char number) {
        setText((number != '0' ? Character.toString(number) : ""));
    }

    protected void changeTextColor(int color) {
        switch (color) {
            case Color.RED:
                setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                break;
            case Color.GREEN:
                setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                break;
        }
    }

    /* Attributes */
    private void setAttributes(Context context) {
        this.setWidth(0);
        this.setHeight(0);
        this.setBackground(context.getResources().getDrawable(R.drawable.cell_border));
        this.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL);
        this.setInputType(InputType.TYPE_CLASS_NUMBER);
        this.setFilters(new InputFilter[] { new InputFilter.LengthFilter(1), new CellFilter() });
        this.setTextSize(20);
    }

    /* Setters & Getters */
    public int getCell_row() {
        return cell_row;
    }

    public int getCell_column() {
        return cell_column;
    }
}
