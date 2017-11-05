package com.denisacosta.lgame.Interface;

import android.content.Context;

/*
Interfaz que permite la comunicacion del Nivel con la Actividad de PlayZone
 */

public interface LevelComunicate {

    void dialogGanaste(String nivel, String time, Context c);

    void dialogPerdiste(String nivel,String time, Context c);

    void conectLevelconPlayZone(PlayZoneComunicate p);
}
