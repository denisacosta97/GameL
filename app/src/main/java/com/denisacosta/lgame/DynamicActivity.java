package com.denisacosta.lgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Denis on 2/11/2017.
 */

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener {

    int COLUMNAS = 3, FILAS = 4;
    Button[][] buttons = new Button[FILAS][COLUMNAS];
    int[][] BORDES = new int[FILAS][COLUMNAS];
    Coord POSICION = new Coord((int)(Math.random()* FILAS),(int) (Math.random()* COLUMNAS));  //intentando asignar una posicion aleatoria al comienzo
    int espacioTotal = (COLUMNAS*FILAS);
    int espacioOcupado = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dinamic);

        LinearLayout layoutVertical = (LinearLayout) findViewById(R.id.livLayout);
        LinearLayout rowLayout = null;

        int count = (COLUMNAS*FILAS)+1; //

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1);

        for( int i = 0; i<FILAS;i++){
            if(count%COLUMNAS == 1){////?????
                rowLayout = new LinearLayout(this);
                rowLayout.setWeightSum(COLUMNAS);
                layoutVertical.addView(rowLayout,params);
                count = count-COLUMNAS;
            }
            for (int j = 0;j<COLUMNAS;j++){
                buttons[i][j] = new Button(this);        //Crea el objeto Button
                buttons[i][j].setText(""+i+" "+j);
                buttons[i][j].setTag(""+i+j+"");                //Asigna un Tag al objeto
                rowLayout.addView(buttons[i][j],params);        //Los agrega a la vista
            }
        }

        for (int i = 0;i<FILAS;i++){
            for(int j = 0;j<COLUMNAS;j++){
                buttons[i][j].setOnClickListener(this);
            }
        }

        BORDES[POSICION.getX()][POSICION.getY()] = 1;
        buttons[POSICION.getX()][POSICION.getY()].setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }


    private void resetGame() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                BORDES[i][j] = 0;
            }
        }
    }

    //CAMBIOS: Recibe dos posiciones del tablero para comparar, para mejor reutilizabilidad
    //CAMBIO 2: menos lineas. codigo anterior abajo
    /*boolean b = false;
        return (((Math.abs(slot1.getX() - slot2.getX()) == 2) && (Math.abs(slot1.getY() - slot2.getY()) == 1)) || (((Math.abs(slot1.getX() - slot2.getX()) == 1) && (Math.abs(slot1.getY() - slot2.getY()) == 2)))) {
            b = true;
        }
        return b;*/
    private boolean validMove(Coord slot1, Coord slot2) {
        return ((( Math.abs(slot1.getX() - slot2.getX()) == 2) && (Math.abs(slot1.getY() - slot2.getY()) == 1)) || (((Math.abs(slot1.getX() - slot2.getX()) == 1) && (Math.abs(slot1.getY() - slot2.getY()) == 2))));
    }


    private void setX(int x) {
        POSICION.setX(x);
    }

    private void setY(int y) {
        POSICION.setX(y);
    }

/*  CAMBIO: cada vez que se hace un movimiento, aumenta el contador y se compara con el total de espacios
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

    }*/
    private boolean checkWin() {
    return espacioOcupado==espacioTotal;
    }


    //METODO NUEVO: Genera un camino basado en los movimientos desde la posicion inicial.
    private Coord[] generatePath(int n, int m, int k){
        Coord[] path = new Coord[k];
        Coord posicionNueva = new Coord();
        path[0] = posicionNueva;
        for(int c = 1; c < k; c++){ //Ciclo para lograr un camino de longitud K.
            Coord[] posibilidades = movPosibles(posicionNueva); //Determinar las posibles ubicaciones a las cuales se puede mover desde posicionNueva
            if(posibilidades.length > 0 ){      //Si puede hacer al menos un movimiento desde posicionNueva
                posicionNueva = posibilidades[(int)(Math.random()*posibilidades.length)]; //Cambia la posicionNueva, por alguna de las posibles de forma aleatoria
                path[c] =  posicionNueva; //Agrega la posicion al Path
                BORDES[posicionNueva.getX()][posicionNueva.getY()] = 1; //Inhabilita el casillero usado.
            }
        }
        return path;
    }

    //METODO NUEVO: Determina los movimientos posibles desde la posicion enviada,
    private Coord[] movPosibles(Coord actual) {
        List<Coord> posibles = new LinkedList();


        List<Coord> aux = new LinkedList();
        Coord c = new Coord();

        //Bloque de codigo ineficiente.
        //Que Hace? Carga los movimientos en L desde la posicion actual a una lista
        //------------------------------
            c.setX(actual.getX() + 1);
            c.setY(actual.getY() + 2);
            if (insideBoard(c))
                aux.add(c);

            c.setX(actual.getX() + 2);
            c.setY(actual.getY() + 1);
            if (insideBoard(c))
                aux.add(c);

            c.setX(actual.getX() + 2);
            c.setY(actual.getY() - 1);
            if (insideBoard(c))
                aux.add(c);

            c.setX(actual.getX() + 1);
            c.setY(actual.getY() - 2);
            if (insideBoard(c))
                aux.add(c);

            c.setX(actual.getX() - 1);
            c.setY(actual.getY() - 2);
            if (insideBoard(c))
                aux.add(c);

            c.setX(actual.getX() - 2);
            c.setY(actual.getY() - 1);
            if (insideBoard(c))
                aux.add(c);

            c.setX(actual.getX() - 2);
            c.setY(actual.getY() + 1);
            if (insideBoard(c))
                aux.add(c);

            c.setX(actual.getX() - 1);
            c.setY(actual.getY() + 2);
            if (insideBoard(c))
                aux.add(c);
        //------------------------------
        int i = 0;
        for (Coord temp: aux){ //Pasa todos los elementos de la lista al Arreglo
            if(validMove(actual, temp)){
                posibles.add(temp);
            }
            i++;
        }

        Coord[] posiblesArray;
        posiblesArray = new Coord[posibles.size()];

        i = 0;
        for (Coord temp: posibles){ //Pasa todos los elementos de la lista al Arreglo
            posiblesArray[i] = temp;
            i++;
        }
        return posiblesArray;
    }

    //METODO NUEVO: Revisa si la posicion dada se encuentra dentro del tablero.
    private boolean insideBoard(Coord pos){
        if(pos.getX()>=0 && pos.getX() < FILAS && pos.getY()>=0 && pos.getY() < COLUMNAS){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onClick(View view) {

        boolean encontrado = false;
        for (int i = 0;i<FILAS && !encontrado;i++){//Agregar condicion para salir del segundo loop una vez se evalue el boton apretado

            for(int j = 0;j<COLUMNAS;j++){
                Object viewGetTag = view.getTag();
                Object buttonGetTag = buttons[i][j].getTag();
                if (view.getTag().equals(buttons[i][j].getTag())){

                    Toast.makeText(this, "Toque boton "+i+" "+j, Toast.LENGTH_SHORT).show();

                    if (BORDES[i][j] == 0) {
                        if (validMove(POSICION,new Coord(i,j))) {
                            BORDES[i][j] = 1;
                            buttons[i][j].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            setX(i);
                            setY(j);
                            espacioOcupado++;
                            if (checkWin()) {
                                Toast.makeText(this, "Ganaste", Toast.LENGTH_SHORT).show();
                                resetGame();
                            }
                        }
                    }
                    encontrado=true;
                    break;
                }
            }
        }
    }
}

