package com.example.sudokusolver;

import android.content.Context;
import android.support.v7.widget.GridLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

import java.util.Arrays;

public class Board extends GridLayout {
    private Block[] blocks;
    private char[][] board;

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBoard();
        setAttributes();
        setBlocks();
    }

    /* Layouts */
    private void setAttributes() {
        this.setColumnCount(3);
        this.setRowCount(3);
    }

    private void setBoard() {
        board = new char[MainActivity.ROW_AND_COL_LENGTH][MainActivity.ROW_AND_COL_LENGTH];
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            Arrays.fill(board[i], '0');
        }
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
                blocks[(i*3)+j] = new Block(this.getContext(), i, j);
                setLayout(blocks[(i*3)+j], i, j);
            }
        }
    }

//    private void setTextListeners() {
//        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
//            for (int j = 0; j < MainActivity.ROW_AND_COL_LENGTH; j++) {
//                this.blocks[i].getCells()[j].addTextChangedListener(new TextWatcher() {
//                    public void afterTextChanged(Editable s) {
//                    }
//
//                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                    }
//
//                    public void onTextChanged(CharSequence s, int start, int before, int count) {
//                        if (s.length() > 0) {
//                            setValueOnBoard(s.charAt(0));
//                        } else {
//                            setValueOnBoard('0');
//                        }
//                    }
//                });
//            }
//        }
//    }

    private void setValueOnBoard(int row, int column, char c) {
        this.board[row][column] = c;
//        if (this.getText().length() > 0) {
//            checkForValidBoard();
//        }
    }

    protected void showValueOnBoard(int row, int column, char number) {
        int blockIndex = getBlockIndex(row, column);
        int cellIndex = this.blocks[blockIndex].getCellIndex(row, column);
        this.blocks[blockIndex].getCells()[cellIndex].showValue(number);
    }

    private int getBlockIndex(int row, int column) {
        int temp_row = row/3;
        int temp_column = column/3;
        return (temp_row*3)+temp_column;
    }

    protected boolean solveBoard() {
        for (int row = 0; row < MainActivity.ROW_AND_COL_LENGTH; row++) {
            for (int col = 0; col < MainActivity.ROW_AND_COL_LENGTH; col++) {
                // we search an empty cell
                if (board[row][col] == MainActivity.EMPTY_CELL) {
                    // we try possible numbers
                    for (char number = MainActivity.MIN_VALUE; number <= MainActivity.MAX_VALUE; number++) {
                        if (isAValidMove(row, col, number)) {
                            board[row][col] = number;
                            if (solveBoard()) {
                                showValueOnBoard(row, col, number);// we start backtracking recursively
                                return true;
                            } else { // if not a solution, we empty the cell and we continue
                                board[row][col] = MainActivity.EMPTY_CELL;
                            }
                        }
                    }
                    return false; // we return false
                }
            }
        }
        return true; // sudoku solved
    }

    private boolean isAValidMove(int row, int column, char number) {
        return (!isInRow(row, number) && !isInCol(column, number) && !isInBox(row, column, number));
    }

    private boolean isInRow(int row, char number) {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    // we check if a possible number is already in a column
    private boolean isInCol(int col, char number) {
        for (int i = 0; i < MainActivity.ROW_AND_COL_LENGTH; i++) {
            if (board[i][col] == number) {
                return true;
            }
        }
        return false;
    }

    // we check if a possible number is in its 3x3 box
    private boolean isInBox(int row, int col, char number) {
        int r = row - row % 3;
        int c = col - col % 3;
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }
}
