package com.example.sudokusolver;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;

public class Cell extends AppCompatEditText {
    private int cell_row;
    private int cell_column;

    public Cell(Context context) {
        super(context);
        setAttributes(context);
    }

    /* Display Methods */
    protected void displayValue(char number) {
        setText((number != '0' ? Character.toString(number) : ""));
        setEnabled(false);
    }

    protected void changeTextColor(int color) {
        switch (color) {
            case Color.RED:
                setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                break;
            case Color.GREEN:
                setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                break;
            default:
                setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
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
        changeTextColor(Color.BLACK);
    }

    /* Setters & Getters */
    public int getCell_row() { return cell_row; }

    public int getCell_column() {
        return cell_column;
    }

    public void setCell_row(int cell_row) {
        this.cell_row = cell_row;
    }

    public void setCell_column(int cell_column) {
        this.cell_column = cell_column;
    }

    protected void resetCell() {
        setText("");
        setEnabled(true);
        changeTextColor(Color.BLACK);
    }
}
