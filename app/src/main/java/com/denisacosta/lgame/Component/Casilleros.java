package com.denisacosta.lgame.Component;

import android.content.Context;
import android.util.Log;
import android.widget.Button;

import com.denisacosta.lgame.DynamicActivity;
import com.denisacosta.lgame.R;

import java.util.ArrayList;

import static com.denisacosta.lgame.Util.LogDebug.showElegida;
import static com.denisacosta.lgame.Util.LogDebug.showPosibilidades;
import static com.denisacosta.lgame.Util.Util.ETIQUETA;

/**
 * Created by Denis on 4/11/2017.
 */

public class Casilleros {

    private int COLUMNAS = 0, FILAS = 0, POSX = 0, POSY = 0;
    private int espacioTotal = 0;
    private int espacioOcupado = 0;

    private Coord POSICION;
    private Button[][] buttons;
    private int[][] BORDES;
    //Sirve de prueba para verificar los caminos
    private int[][] CAMINOS_POSIBLES;
    public Context cont;


    public Casilleros(int filas, int columnas, int x, int y, Context c) {

        FILAS = filas;
        COLUMNAS = columnas;
        POSX = x;
        POSY = y;
        espacioOcupado = 1;
        cont = c;
        espacioTotal = (COLUMNAS * FILAS);
        POSICION = new Coord(x, y);
        buttons = new Button[filas][columnas];
        BORDES = new int[filas][columnas];
        CAMINOS_POSIBLES = new int[filas][columnas];
    }

    public int getCOLUMNAS() {
        return COLUMNAS;
    }

    public void selectInicio() {

        BORDES[POSICION.getX()][POSICION.getY()] = 1;
        buttons[POSICION.getX()][POSICION.getY()].setBackgroundColor(cont.getResources().getColor(R.color.colorAccent));

        //Cambiando aqui los indices puedes probar diferentes caminos y sus soluciones
        CAMINOS_POSIBLES[POSICION.getX()][POSICION.getY()] = 1;
        //Le mando espaciototal +1 porque en la posicion 0 siempre esta la
        //coordenadas donde se arranca
        Coord[] x = generatePath(POSICION, espacioTotal);

        invalidateCoord(x);



        String s = parseStringCord(x);

        ((DynamicActivity)cont).addSolution(s);
        Log.e(ETIQUETA, s);
    }

    public void setCOLUMNAS(int COLUMNAS) {
        this.COLUMNAS = COLUMNAS;
    }

    public int getFILAS() {
        return FILAS;
    }

    public void setFILAS(int FILAS) {
        this.FILAS = FILAS;
    }

    public int getPOSX() {
        return POSX;
    }

    public void setPOSX(int POSX) {
        this.POSX = POSX;
    }

    public int getPOSY() {
        return POSY;
    }

    public void setPOSY(int POSY) {
        this.POSY = POSY;
    }

    public int getEspacioTotal() {
        return espacioTotal;
    }

    public void setEspacioTotal(int espacioTotal) {
        this.espacioTotal = espacioTotal;
    }

    public int getEspacioOcupado() {
        return espacioOcupado;
    }

    public void setEspacioOcupado(int espacioOcupado) {
        this.espacioOcupado = espacioOcupado;
    }

    public Coord getPOSICION() {
        return POSICION;
    }

    public void setPOSICION(Coord POSICION) {
        this.POSICION = POSICION;
    }

    public Button[][] getButtons() {
        return buttons;
    }

    public void setButtons(Button[][] buttons) {
        this.buttons = buttons;
    }

    public int[][] getBORDES() {
        return BORDES;
    }

    public void setBORDES(int[][] BORDES) {
        this.BORDES = BORDES;
    }

    public int[][] getCAMINOS_POSIBLES() {
        return CAMINOS_POSIBLES;
    }

    public void setCAMINOS_POSIBLES(int[][] CAMINOS_POSIBLES) {
        this.CAMINOS_POSIBLES = CAMINOS_POSIBLES;
    }

    public void invalidateCoord(Coord[] x) {

        int[][] p = new int[FILAS][COLUMNAS];

        for (Coord c : x) {

            if (c != null)
                p[c.getX()][c.getY()] = 1;

        }

        for (int i = 0; i < FILAS; i++) {

            for (int j = 0; j < COLUMNAS; j++) {

                if (p[i][j] != 1) {
                    buttons[i][j].setBackgroundColor(cont.getResources().getColor(R.color.colorInactive));
                    BORDES[i][j] = 1;
                    espacioOcupado++;
                }


            }
        }

    }

    public String parseStringCord(Coord[] x) {

        String s = "";
        for (Coord t : x) {

            if (t != null)
                s = s + "[" + t.getX() + "," + t.getY() + "]";


        }

        return s;
    }

    public void resetGame() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                BORDES[i][j] = 0;
            }
        }
    }

    public boolean checkWin() {
        return espacioOcupado == espacioTotal;
    }


    public void setX(int x) {
        POSICION.setX(x);
    }

    public void setY(int y) {
        POSICION.setY(y);
    }

    public boolean validMove(Coord slot1, Coord slot2) {
        return (((Math.abs(slot1.getX() - slot2.getX()) == 2) && (Math.abs(slot1.getY() - slot2.getY()) == 1)) || (((Math.abs(slot1.getX() - slot2.getX()) == 1) && (Math.abs(slot1.getY() - slot2.getY()) == 2))));
    }


    //Genera un camino basado en los movimientos desde la posicion inicial de longitud K.
    private Coord[] generatePath(Coord m, int k) {
        Coord[] path = new Coord[k];
        Coord posicionNueva = m;
        path[0] = m;
        for (int c = 1; c < k; c++) { //Ciclo para lograr un camino de longitud K.
            Coord[] posibilidades = movPosibles(posicionNueva); //Determina las posibles ubicaciones a las cuales se puede mover desde posicionNueva
            showPosibilidades(posibilidades); //Muestra en la Consola las posiciones posibles
            if (posibilidades.length > 0) {//Si puede hacer al menos un movimiento desde posicionNueva
                for (Coord s : posibilidades) {
                    if (CAMINOS_POSIBLES[s.getX()][s.getY()] == 0) { //Por cada posibilidad pregunto si esta disponible
                        path[c] = s; //La agrego al array, siempre tiene en cuenta la primera posicion no ocupada
                        showElegida(s); //La muestro para tener un control
                        posicionNueva = s; //Y esa sera la nueva posicion donde buscare nuevas posibilidades
                        CAMINOS_POSIBLES[s.getX()][s.getY()] = 1; //Indico que esa posicion ya se ocupo
                        break; //Salgo el ciclo de posibilidades
                    }

                }
            } else //Si no hay posibilidades significa que ya no hay caminos posibles
                break; //Y salgo del siglo principal
        }
        return path;
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

}
