package com.example.sudokusolver;

import android.content.Context;
import android.support.v7.widget.GridLayout;

public class Block extends GridLayout {
    private Cell[] cells;
    private int block_row;
    private int block_column;

    public Block(Context context) {
        super(context);
        setAttributes(context);
    }

    /* Layouts & Attributes */
    private void setAttributes(Context context) {
        this.setColumnCount(3);
        this.setRowCount(3);
        this.setBackground(context.getResources().getDrawable(R.drawable.block_border));
    }

    private void setLayout(Cell cell, int row, int column) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(row, 1F), GridLayout.spec(column, 1F));
        cell.setLayoutParams(params);
        this.addView(cell);
    }

    protected void setCells() {
        cells = new Cell[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[(i*3)+j] = new Cell(this.getContext());
                cells[(i*3)+j].setCell_row((this.block_row*3) + i);
                cells[(i*3)+j].setCell_column((this.block_column*3) + j);
                setLayout(cells[(i*3)+j], i, j);
            }
        }
    }

    /* Setters & Getters */

    public void setBlock_row(int block_row) {
        this.block_row = block_row;
    }

    public void setBlock_column(int block_column) {
        this.block_column = block_column;
    }

    public Cell[] getCells() {
        return cells;
    }

    protected int getCellIndex(int row, int column) {
        int temp_block_row = row/3;
        int temp_block_column = column/3;
        int temp_row = row - (temp_block_row*3);
        int temp_column = column - (temp_block_column*3);
        return (temp_row*3) + temp_column;
    }

    protected void resetBlock() {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            getCells()[i].resetCell();
        }
    }
}
