package com.denisacosta.lgame.Others;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denisacosta.lgame.Component.Casilleros;
import com.denisacosta.lgame.Component.Coord;
import com.denisacosta.lgame.R;

/**
 * Created by Denis on 2/11/2017.
 */

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener {


    Casilleros casilleros;
    TextView textSolu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            int COLUMNAS,FILAS,POSX,POSY;

            try {

                COLUMNAS = Integer.parseInt(extras.getString("COL"));
                FILAS = Integer.parseInt(extras.getString("FILA"));
                POSX = Integer.parseInt(extras.getString("X"));
                POSY = Integer.parseInt(extras.getString("Y"));

            }catch (Exception e){

                COLUMNAS = 4;
                FILAS = 4;
                POSX = 0;
                POSY = 0;

            }
            casilleros = new Casilleros(FILAS,COLUMNAS,POSX,POSY,this);
        }

        createButtons();

        casilleros.selectInicio();


    }

    public void addSolution(String s){
        textSolu.setText(s);
    }


    private void createButtons() {

        Button[][] buttons = new Button[casilleros.getFILAS()][casilleros.getCOLUMNAS()];
        LinearLayout layoutVertical = (LinearLayout) findViewById(R.id.livLayout);
        LinearLayout rowLayout = null;

        textSolu = findViewById(R.id.textSolu);

        int count = (casilleros.getCOLUMNAS() * casilleros.getFILAS()) + 1; //

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1);

        for (int i = 0; i < casilleros.getFILAS(); i++) {

            //Esto dice que por cada vez que se complete una fila
            //baje a la siguiente
            if (count % casilleros.getCOLUMNAS() == 1) {
                rowLayout = new LinearLayout(this);
                rowLayout.setWeightSum(casilleros.getCOLUMNAS());
                layoutVertical.addView(rowLayout, params);
                count = count - casilleros.getCOLUMNAS();
            }

            for (int j = 0; j < casilleros.getCOLUMNAS(); j++) {
                buttons[i][j] = new Button(this);        //Crea el objeto Button
                buttons[i][j].setTag("" + i + j + "");                //Asigna un Tag al objeto
                rowLayout.addView(buttons[i][j], params);        //Los agrega a la vista
            }
        }

        for (int i = 0; i < casilleros.getFILAS(); i++) {
            for (int j = 0; j < casilleros.getCOLUMNAS(); j++) {
                buttons[i][j].setOnClickListener(this);
            }

        }

        casilleros.setButtons(buttons);
    }


    @Override
    public void onClick(View view) {

        boolean encontrado = false;
        for (int i = 0; i < casilleros.getFILAS() && !encontrado; i++) {
            //Agregar condicion para salir del segundo loop una vez se evalue el boton apretado

            for (int j = 0; j < casilleros.getCOLUMNAS(); j++) {

                if (view.getTag().equals(casilleros.getButtons()[i][j].getTag())) {

                    Toast.makeText(this, "Toque boton " + i + " " + j, Toast.LENGTH_SHORT).show();

                    if (casilleros.getBORDES()[i][j] == 0) {
                        if (casilleros.validMove(casilleros.getPOSICION(), new Coord(i, j))) {
                            casilleros.getBORDES()[i][j] = 1;
                            casilleros.getButtons()[i][j].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            casilleros.setX(i);
                            casilleros.setY(j);
                            casilleros.setEspacioOcupado(casilleros.getEspacioOcupado()+1);
                            if (casilleros.checkWin()) {
                                Toast.makeText(this, "Ganaste", Toast.LENGTH_SHORT).show();
                                casilleros.resetGame();
                            }
                        }
                    }
                    encontrado = true;
                    break;
                }
            }
        }
    }
}

