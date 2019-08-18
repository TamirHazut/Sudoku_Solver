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
        if (this.getText().length() == 0) {
            this.setText(number);
        }
    }

    /*
    private void checkForValidBoard() {
//        Log.d("Valid:", "Row: " + checkForValidRow() + " Col: " + checkForValidColumn() + " Block: " + checkForValidBlock());
    }

    private boolean checkForValidRow() {
        for (int j = 0; j < MainActivity.ROW_AND_COL_LENGTH; j++) {
            if ( (j != this.cell_column) && (board[this.cell_row][j] == board[this.cell_row][this.cell_column]) ) {
                return false;
            }
        }
        return true;
    }

    private boolean checkForValidColumn() {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            if ( (i != this.cell_row) && (board[i][this.cell_column] == board[this.cell_row][this.cell_column]) ) {
                return false;
            }
        }
        return true;
    }

    private boolean checkForValidBlock() {
        int startRow = getBlockStartIndex(this.cell_row);
        int endRow = startRow + 3;
        int startColumn = getBlockStartIndex(this.cell_column);
        int endColumn = startColumn + 3;
        for (int i = startRow; i < endRow; i++) {
            for (int j = startColumn; j < endColumn; j++) {
                if ( (i != this.cell_row) && (j != this.cell_column) && (board[i][j] == board[this.cell_row][this.cell_column]) ) {
                    return false;
                }
            }
        }
        return true;
    }

    private int getBlockStartIndex(int index) {
        switch ((index%3)) {
            case 0:
                break;
            case 1:
                index -= 1;
                break;
            case 2:
                index -= 2;
                break;
        }
        return index;
    }
    */
}
