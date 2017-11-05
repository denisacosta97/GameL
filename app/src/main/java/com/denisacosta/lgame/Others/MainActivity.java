package com.denisacosta.lgame.Others;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.denisacosta.lgame.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] buttons = new Button[3][4];
    Integer[][] intId = {{R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4},
            {R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8},
            {R.id.btn9,R.id.btn10,R.id.btn11,R.id.btn12}};

    int COLUMNAS = 4, FILAS = 3;
    int[][] BORDES = new int[3][4];
    int[] POSICION = {0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0;i<FILAS;i++){

            for(int j = 0;j<COLUMNAS;j++){

                buttons[i][j] = (Button) findViewById(intId[i][j]);

            }
        }

        for (int i = 0;i<FILAS;i++){

            for(int j = 0;j<COLUMNAS;j++){

                buttons[i][j].setOnClickListener(this);

            }
        }

        BORDES[0][0] = 1;
        buttons[0][0].setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }


    private void resetGame() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                BORDES[i][j] = 0;
            }
        }
    }

    private boolean validMove(int i, int j) {
        boolean b = false;
        if (((Math.abs(POSICION[0] - i) == 2) && (Math.abs(POSICION[1] - j) == 1)) || (((Math.abs(POSICION[0] - i) == 1) && (Math.abs(POSICION[1] - j) == 2)))) {
            b = true;
        }
        return b;
    }

    private void setX(int x) {
        POSICION[0] = x;
    }

    private void setY(int y) {
        POSICION[1] = y;
    }

    private boolean checkWin() {
        boolean b = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (BORDES[i][j] == 0) {
                    b = false;
                }
            }
        }
        return b;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_dynamic:
                startActivity(new Intent(this, DynamicActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        for (int i = 0;i<FILAS;i++){

            for(int j = 0;j<COLUMNAS;j++){

                if (view.getId() == buttons[i][j].getId()){

                    Toast.makeText(this, "Toque boton "+i+" "+j, Toast.LENGTH_SHORT).show();

                    if (BORDES[i][j] == 0) {
                        if (validMove(i, j)) {
                            BORDES[i][j] = 1;
                            buttons[i][j].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            setX(i);
                            setY(j);
                            if (checkWin()) {
                                Toast.makeText(this, "Ganaste", Toast.LENGTH_SHORT).show();
                                resetGame();
                            }
                        }
                    }

                }




            }
        }



    }
}

