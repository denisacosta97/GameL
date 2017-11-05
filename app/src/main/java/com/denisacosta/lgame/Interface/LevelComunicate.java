package com.denisacosta.lgame.Interface;

import android.content.Context;

/**
 * Created by Denis on 4/11/2017.
 */

public interface LevelComunicate {

    void dialogGanaste(String nivel, String time, Context c);

    void dialogPerdiste(String nivel,String time, Context c);

    void conectLevelconPlayZone(PlayZoneComunicate p);
}
