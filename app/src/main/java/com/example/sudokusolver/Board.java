package com.example.sudokusolver;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.util.ArrayList;
import java.util.Arrays;

public class Board extends GridLayout {
    private Block[] blocks;
    private char[][] gameBoard;
    private boolean[][] invalidBoard;
    private boolean solving = false;
    private ArrayList<Integer> invalids = new ArrayList<>();

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBoards();
        setAttributes();
        setBlocks();
    }

    /* Display Methods */
    private void displayValueOnBoard(int row, int column, char number) {
        int blockIndex = getBlockIndex(row, column);
        int cellIndex = this.blocks[blockIndex].getCellIndex(row, column);
        this.blocks[blockIndex].getCells()[cellIndex].displayValue(number);
    }

    private void changeValueColorOnBoard(int row, int column, int color) {
        int blockIndex = getBlockIndex(row, column);
        int cellIndex = this.blocks[blockIndex].getCellIndex(row, column);
        this.blocks[blockIndex].getCells()[cellIndex].changeTextColor(color);
    }

    protected void displayValuesOnBoard() {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            for (int j = 0; j < MainActivity.ROW_AND_COL_LENGTH; j++) {
                displayValueOnBoard(i, j, gameBoard[i][j]);
            }
        }
    }

    private void displayMoveOnBoard(boolean validMove, int row, int column, char c) {
        if (!solving) {
            if (c == '0') {
                if (invalidBoard[row][column]) {
                    invalidBoard[row][column] = false;
                    changeValueColorOnBoard(row, column, Color.GREEN);
                    iSANewValidMove(row, column);
                }
            } else {
                if (validMove) {
                    changeValueColorOnBoard(row, column, Color.GREEN);
                    iSANewValidMove(row, column);
                } else {
                    changeValueColorOnBoard(row, column, Color.RED);
                }
            }
        }
        this.gameBoard[row][column] = c;
    }

    protected boolean solveBoard() {
        for (int row = 0; row < MainActivity.ROW_AND_COL_LENGTH; row++) {
            for (int col = 0; col < MainActivity.ROW_AND_COL_LENGTH; col++) {
                if (gameBoard[row][col] == MainActivity.EMPTY_CELL) {
                    for (char number = MainActivity.MIN_VALUE; number <= MainActivity.MAX_VALUE; number++) {
                        if (isAValidMove(row, col, number)) {
                            displayMoveOnBoard(true, row, col, number);
                            if (solveBoard()) {
                                return true;
                            } else {
                                displayMoveOnBoard(true, row, col, MainActivity.EMPTY_CELL);
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    /* Validation Methods */
    private boolean isAValidMove(int row, int column, char number) {
        isInARow(row, number);
        isInACol(column, number);
        isInABlock(row, column, number);
        boolean valid = invalids.isEmpty();
        if (!valid) {
            invalidBoard[row][column] = true;
            invalids.clear();
        }
        return valid;
    }

    private void isInARow(int row, char number) {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            if (gameBoard[row][i] == number) {
                invalids.add(i);
                invalidBoard[row][i] = true;
                if (!solving) {
                    changeValueColorOnBoard(row, i, Color.RED);
                }
            }
        }
    }

    private void isInACol(int col, char number) {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            if (gameBoard[i][col] == number) {
                invalids.add(i);
                invalidBoard[i][col] = true;
                if (!solving) {
                    changeValueColorOnBoard(i, col, Color.RED);
                }
            }
        }
    }

    private void isInABlock(int row, int col, char number) {
        int r = row - row % 3, c = col - col % 3;
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                if (gameBoard[i][j] == number) {
                    invalids.add(i);
                    invalidBoard[i][j] = true;
                    if (!solving) {
                        changeValueColorOnBoard(i, j, Color.RED);
                    }
                }
            }
        }
    }

    private void iSANewValidMove(int row, int column) {
        char temp = gameBoard[row][column];
        gameBoard[row][column] = '0';
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            for (int j = 0; j < MainActivity.ROW_AND_COL_LENGTH; j++) {
                if (gameBoard[i][j] != MainActivity.EMPTY_CELL && invalidBoard[i][j]) {
                    char numberInValidationProcess = gameBoard[i][j];
                    gameBoard[i][j] = '0';
                    if (isAValidMove(i, j, numberInValidationProcess)) {
                        invalidBoard[i][j] = false;
                        changeValueColorOnBoard(i, j, Color.GREEN);
                    }
                    gameBoard[i][j] = numberInValidationProcess;
                }
            }
        }
        gameBoard[row][column] = temp;
    }

    protected boolean isAValidStart() {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            for (int j = 0; j < MainActivity.ROW_AND_COL_LENGTH; j++) {
                if (invalidBoard[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /* Layouts & Attributes */
    private void setAttributes() {
        this.setColumnCount(3);
        this.setRowCount(3);
    }

    private void setLayout(Block block, int row, int column) {
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(GridLayout.spec(row, 1F), GridLayout.spec(column, 1F));
        block.setLayoutParams(params);
        this.addView(block);
    }

    private void setBlocks() {
        blocks = new Block[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                blocks[(i*3)+j] = new Block(this.getContext());
                blocks[(i*3)+j].setBlock_row(i);
                blocks[(i*3)+j].setBlock_column(j);
                blocks[(i*3)+j].setCells();
                setLayout(blocks[(i*3)+j], i, j);
            }
        }
        setTextListener();
    }

    private void setTextListener() {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            for (int j = 0; j < MainActivity.ROW_AND_COL_LENGTH; j++) {
                Cell cell = this.blocks[i].getCells()[j];
                cell.addTextChangedListener(getTextListener(cell));
            }
        }
    }

    private TextWatcher getTextListener(final Cell cell) {
        return new TextWatcher() {
            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int row = cell.getCell_row(), column = cell.getCell_column();
                if (s.length() > 0) {
                    char number = s.charAt(0);
                    displayMoveOnBoard(isAValidMove(row, column, number), row, column, number);
                } else {
                    displayMoveOnBoard(true, row, column, '0');
                }
            }
        };
    }

    /* Setters And Getters */
    public boolean isSolving() {
        return solving;
    }

    public void setSolving() {
        this.solving = !this.solving;
    }

    private int getBlockIndex(int row, int column) {
        int temp_row = row/3;
        int temp_column = column/3;
        return (temp_row*3)+temp_column;
    }

    private void setBoards() {
        gameBoard = new char[MainActivity.ROW_AND_COL_LENGTH][MainActivity.ROW_AND_COL_LENGTH];
        invalidBoard = new boolean[MainActivity.ROW_AND_COL_LENGTH][MainActivity.ROW_AND_COL_LENGTH];
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            Arrays.fill(gameBoard[i], '0');
            Arrays.fill(invalidBoard[i], false);
        }
    }

    protected void resetBoard() {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            this.blocks[i].resetBlock();
        }
    }
}
