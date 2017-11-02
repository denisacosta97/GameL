package com.denisacosta.lgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by Denis on 2/11/2017.
 */

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener {

    int COLUMNAS = 10, FILAS = 6;
    Button[][] buttons = new Button[FILAS][COLUMNAS];
    int[][] BORDES = new int[FILAS][COLUMNAS];
    int[] POSICION = {0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dinamic);

        LinearLayout layoutVertical = (LinearLayout) findViewById(R.id.livLayout);
        LinearLayout rowLayout = null;

        int count = (COLUMNAS*FILAS)+1;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1);

        for( int i = 0; i<FILAS;i++){

            if(count%COLUMNAS == 1){

                rowLayout = new LinearLayout(this);
                rowLayout.setWeightSum(COLUMNAS);
                layoutVertical.addView(rowLayout,params);
                count = count-COLUMNAS;

            }
            for (int j = 0;j<COLUMNAS;j++){

                buttons[i][j] = new Button(this);
                buttons[i][j].setTag(""+i+j+"");
                //buttons[i][j].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                rowLayout.addView(buttons[i][j],params);
            }

        }

       /* for (int i = 0;i<FILAS;i++){

            for(int j = 0;j<COLUMNAS;j++){

                buttons[i][j] = (Button) findViewById(intId[i][j]);

            }
        }*/

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
    public void onClick(View view) {

        for (int i = 0;i<FILAS;i++){

            for(int j = 0;j<COLUMNAS;j++){

                if (view.getTag().equals(buttons[i][j].getTag())){

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

