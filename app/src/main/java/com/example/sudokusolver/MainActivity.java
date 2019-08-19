package com.example.sudokusolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Board table;
    private Button solve_button;
    public static final int ROW_AND_COL_LENGTH = 9;
    public static final char MIN_VALUE = '1';
    public static final char MAX_VALUE = '9';
    public static final char EMPTY_CELL = '0';


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = (Board) findViewById(R.id.sudoku_table);
        solve_button = (Button) findViewById(R.id.solve_button);

        solve_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!table.isSolving()) {
                            table.setSolving();
                            if (table.solveBoard()) {
                                table.displayValuesOnBoard();
                            }
                            table.setSolving();
                        }
                    }
                }
        );
    }

}
