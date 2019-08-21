package com.example.sudokusolver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Board table;
    Button solve_button;
    Button reset_button;
    public static final int ROW_AND_COL_LENGTH = 9;
    public static final char MIN_VALUE = '1';
    public static final char MAX_VALUE = '9';
    public static final char EMPTY_CELL = '0';


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        table = findViewById(R.id.sudoku_table);
        solve_button = findViewById(R.id.solve_button);
        reset_button = findViewById(R.id.reset_button);

        solve_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!table.isSolving()) {
                            if (!table.isAValidStart()) {
                                Toast.makeText(getApplicationContext(), "Invalid Board!", Toast.LENGTH_SHORT).show();
                            } else {
                                table.setSolving();
                                Toast.makeText(getApplicationContext(), "Thinking...", Toast.LENGTH_SHORT).show();
                                table.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (table.solveBoard()) {
                                            Toast.makeText(getApplicationContext(), "Solved.", Toast.LENGTH_SHORT).show();
                                            table.displayValuesOnBoard();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Can't solve.", Toast.LENGTH_SHORT).show();
                                        }
                                        table.setSolving();
                                    }
                                }, 2000);
                            }
                        }
                    }
                });

        reset_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        table.resetBoard();
                    }
                });
    }

}
