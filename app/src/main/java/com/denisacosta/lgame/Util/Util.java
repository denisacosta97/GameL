package com.denisacosta.lgame.Util;

/*
Clase contenedora de variables estaticas
 */

import android.util.Log;

import com.denisacosta.lgame.Component.Coord;

public class Util {

    public static final String ETIQUETA =  "TEST";

    public static final String ETIQUETA_DEBUG =  "DEBUG TEST";

    public static final String FILAS = "FILAS";

    public static final String COLUMNAS = "COLUMNAS";

    public static final String POSX = "POSX";

    public static final String POSY = "POSY";

    public static void showLog(String e,String m){
        Log.e(e,m);
    }


    public static void showElegida(Coord x) {

        Log.e(ETIQUETA + " ELEGIDA", "[" + x.getX() + "," + x.getY() + "]");
    }

    public static void showPosibilidades(Coord[] posibilidades) {

        for (Coord x : posibilidades)

            Log.e(ETIQUETA, "[" + x.getX() + "," + x.getY() + "]");
    }


}
