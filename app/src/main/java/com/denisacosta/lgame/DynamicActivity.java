package com.denisacosta.lgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Denis on 2/11/2017.
 */

public class DynamicActivity extends AppCompatActivity implements View.OnClickListener {

    int COLUMNAS = 4, FILAS = 3;
    Button[][] buttons = new Button[FILAS][COLUMNAS];
    int[][] BORDES = new int[FILAS][COLUMNAS];
    //Sirve de prueba para verificar los caminos
    int[][] BORDESTEST = new int[FILAS][COLUMNAS];
    Coord POSICION = new Coord(0, 0);
    //Coord POSICION = new Coord((int)(Math.random()* FILAS),(int) (Math.random()* COLUMNAS));  //intentando asignar una posicion aleatoria al comienzo
    int espacioTotal = (COLUMNAS * FILAS);
    int espacioOcupado = 1;
    String ETIQUETA = getClass().toString() + "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dinamic);

        LinearLayout layoutVertical = (LinearLayout) findViewById(R.id.livLayout);
        LinearLayout rowLayout = null;

        int count = (COLUMNAS * FILAS) + 1; //

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1);

        for (int i = 0; i < FILAS; i++) {

            //Esto dice que por cada vez que se complete una fila
            //baje a la siguiente
            if (count % COLUMNAS == 1) {
                rowLayout = new LinearLayout(this);
                rowLayout.setWeightSum(COLUMNAS);
                layoutVertical.addView(rowLayout, params);
                count = count - COLUMNAS;
            }

            for (int j = 0; j < COLUMNAS; j++) {
                buttons[i][j] = new Button(this);        //Crea el objeto Button
                buttons[i][j].setTag("" + i + j + "");                //Asigna un Tag al objeto
                rowLayout.addView(buttons[i][j], params);        //Los agrega a la vista
            }
        }

        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                buttons[i][j].setOnClickListener(this);
            }
        }

        BORDES[POSICION.getX()][POSICION.getY()] = 1;
        buttons[POSICION.getX()][POSICION.getY()].setBackgroundColor(getResources().getColor(R.color.colorAccent));

        //Cambiando aqui los indices puedes probar diferentes caminos y sus soluciones
        BORDESTEST[1][1] = 1;
        //Le mando espaciototal +1 porque en la posicion 0 siempre esta la
        //coordenadas donde se arranca
        Coord[] x = generatePath(new Coord(1, 1), espacioTotal + 1);


        //Esto muestra en la Consola la respuesta
        String s = "";
        for (Coord t : x) {

            if (t != null)
                s = s + "[" + t.getX() + "," + t.getY() + "]-";


        }
        Log.e(ETIQUETA, s);
    }


    private void resetGame() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                BORDES[i][j] = 0;
            }
        }
    }

    private boolean validMove(Coord slot1, Coord slot2) {
        return (((Math.abs(slot1.getX() - slot2.getX()) == 2) && (Math.abs(slot1.getY() - slot2.getY()) == 1)) || (((Math.abs(slot1.getX() - slot2.getX()) == 1) && (Math.abs(slot1.getY() - slot2.getY()) == 2))));
    }


    private void setX(int x) {
        POSICION.setX(x);
    }

    private void setY(int y) {
        POSICION.setY(y);
    }

    private boolean checkWin() {
        return espacioOcupado == espacioTotal;
    }


    //METODO NUEVO: Genera un camino basado en los movimientos desde la posicion inicial.
    private Coord[] generatePath(Coord m, int k) {
        Coord[] path = new Coord[k];
        Coord posicionNueva = m;
        path[0] = m;
        for (int c = 1; c < k; c++) { //Ciclo para lograr un camino de longitud K.
            Coord[] posibilidades = movPosibles(posicionNueva); //Determina las posibles ubicaciones a las cuales se puede mover desde posicionNueva
            showPosibilidades(posibilidades); //Muestra en la Consola las posiciones posibles
            if (posibilidades.length > 0) {//Si puede hacer al menos un movimiento desde posicionNueva
                for (Coord s : posibilidades) {
                    if (BORDESTEST[s.getX()][s.getY()] == 0) { //Por cada posibilidad pregunto si esta disponible
                        path[c] = s; //La agrego al array, siempre tiene en cuenta la primera posicion no ocupada
                        showElegida(s); //La muestro para tener un control
                        posicionNueva = s; //Y esa sera la nueva posicion donde buscare nuevas posibilidades
                        BORDESTEST[s.getX()][s.getY()] = 1; //Indico que esa posicion ya se ocupo
                        break; //Salgo el ciclo de posibilidades
                    }

                }
            } else //Si no hay posibilidades significa que ya no hay caminos posibles
                break; //Y salgo del siglo principal
        }
        return path;
    }

    private void showElegida(Coord x) {

        Log.e(ETIQUETA, "[" + x.getX() + "," + x.getY() + "]");
    }

    private void showPosibilidades(Coord[] posibilidades) {

        for (Coord x : posibilidades)

            Log.e(ETIQUETA, "[" + x.getX() + "," + x.getY() + "]");
    }

    //METODO NUEVO: Determina los movimientos posibles desde la posicion enviada,
    private Coord[] movPosibles(Coord actual) {
        ArrayList<Coord> posibles = new ArrayList<>();
        ArrayList<Coord> aux = new ArrayList<>();

        Coord c = new Coord();

        //Bloque de codigo ineficiente.
        //Que Hace? Carga los movimientos en L desde la posicion actual a una lista
        //------------------------------
        //PD: NO ME PARECE INCEFICIENTE, FUNCIONO XD. CAPAZ QUE SE PUEDEN REDUCIR LINEAS YA VEMOS...
        c.setX(actual.getX() + 1);
        c.setY(actual.getY() + 2);
        if (insideBoard(c))
            aux.add(c);
        c = new Coord(); //CADA VEZ QUE SE RENUEVA LAS POSICIONES ES NECESARIO "RESETEAR" EL ESPACIO
        //DE MEMORIA DE ESA VARIABLE YA QUE SINO ESTARIA MODIFICANDO LOS DATOS DE LAS QUE
        //YA SE ENCUENTRAN DENTRO DEL ARRAY
        c.setX(actual.getX() + 2);
        c.setY(actual.getY() + 1);
        if (insideBoard(c))
            aux.add(c);
        c = new Coord();
        c.setX(actual.getX() + 2);
        c.setY(actual.getY() - 1);
        if (insideBoard(c))
            aux.add(c);
        c = new Coord();
        c.setX(actual.getX() + 1);
        c.setY(actual.getY() - 2);
        if (insideBoard(c))
            aux.add(c);
        c = new Coord();
        c.setX(actual.getX() - 1);
        c.setY(actual.getY() - 2);
        if (insideBoard(c))
            aux.add(c);
        c = new Coord();
        c.setX(actual.getX() - 2);
        c.setY(actual.getY() - 1);
        if (insideBoard(c))
            aux.add(c);
        c = new Coord();
        c.setX(actual.getX() - 2);
        c.setY(actual.getY() + 1);
        if (insideBoard(c))
            aux.add(c);
        c = new Coord();
        c.setX(actual.getX() - 1);
        c.setY(actual.getY() + 2);
        if (insideBoard(c))
            aux.add(c);
        //------------------------------
        for (Coord temp : aux) { //Pasa todos los movimientos obtenidos que sean posibles a la lista
            if (validMove(actual, temp)) {
                posibles.add(temp);
            }
        }

        Coord[] posiblesArray = new Coord[posibles.size()];

        int i = 0;
        for (Coord temp : posibles) { //Pasa todos los elementos de la lista al Arreglo
            posiblesArray[i] = temp;
            i++;

        }
        return posiblesArray;
    }

    //METODO NUEVO: Revisa si la posicion dada se encuentra dentro del tablero.
    private boolean insideBoard(Coord pos) {
        return pos.getX() >= 0 && pos.getX() < FILAS && pos.getY() >= 0 && pos.getY() < COLUMNAS;
    }

    @Override
    public void onClick(View view) {

        boolean encontrado = false;
        for (int i = 0; i < FILAS && !encontrado; i++) {
            //Agregar condicion para salir del segundo loop una vez se evalue el boton apretado

            for (int j = 0; j < COLUMNAS; j++) {

                if (view.getTag().equals(buttons[i][j].getTag())) {

                    Toast.makeText(this, "Toque boton " + i + " " + j, Toast.LENGTH_SHORT).show();

                    if (BORDES[i][j] == 0) {
                        if (validMove(POSICION, new Coord(i, j))) {
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
                    encontrado = true;
                    break;
                }
            }
        }
    }
}

