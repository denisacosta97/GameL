package com.denisacosta.lgame.Util;

import android.util.Log;

import com.denisacosta.lgame.Component.Coord;

import static com.denisacosta.lgame.Util.Util.ETIQUETA;

/**
 * Created by Denis on 4/11/2017.
 */

public class LogDebug {



    public static void showElegida(Coord x) {

        Log.e(ETIQUETA + " ELEGIDA", "[" + x.getX() + "," + x.getY() + "]");
    }

    public static void showPosibilidades(Coord[] posibilidades) {

        for (Coord x : posibilidades)

            Log.e(ETIQUETA, "[" + x.getX() + "," + x.getY() + "]");
    }
}
