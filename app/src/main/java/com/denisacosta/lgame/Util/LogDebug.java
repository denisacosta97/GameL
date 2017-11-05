package com.denisacosta.lgame.Util;

import android.util.Log;

import com.denisacosta.lgame.Component.Coord;

import static com.denisacosta.lgame.Util.Util.ETIQUETA;

/*
Clase POJO para mandar mensajes por la consola
 */

public class LogDebug {

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
