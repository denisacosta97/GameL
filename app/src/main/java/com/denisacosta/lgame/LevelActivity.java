package com.denisacosta.lgame;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.denisacosta.lgame.Component.Casilleros;
import com.denisacosta.lgame.Component.Coord;
import com.denisacosta.lgame.Interface.LevelComunicate;
import com.denisacosta.lgame.Interface.PlayZoneComunicate;
import com.denisacosta.lgame.Util.Util;

/**
 * Created by Denis on 4/11/2017.
 */

public class LevelActivity extends Fragment implements View.OnClickListener, PlayZoneComunicate {

    Casilleros casilleros;
    LevelComunicate levelComunicate;

    public LevelActivity() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = null;

        if (getArguments() != null) {

            view = inflater.inflate(R.layout.level_custom, container, false);

            Bundle bundle = getArguments();
            int FILAS = bundle.getInt(Util.FILAS);
            int COLUMNAS = bundle.getInt(Util.COLUMNAS);
            int POSX = bundle.getInt(Util.POSX);
            int POSY = bundle.getInt(Util.POSY);



            casilleros = new Casilleros(FILAS,COLUMNAS,POSX,POSY,getContext());

            Button[][] buttons = new Button[casilleros.getFILAS()][casilleros.getCOLUMNAS()];
            LinearLayout layoutVertical =  view.findViewById(R.id.llCasillero);
            LinearLayout rowLayout = null;


            int count = (casilleros.getCOLUMNAS() * casilleros.getFILAS()) + 1; //

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1);

            for (int i = 0; i < casilleros.getFILAS(); i++) {

                //Esto dice que por cada vez que se complete una fila
                //baje a la siguiente
                if (count % casilleros.getCOLUMNAS() == 1) {
                    rowLayout = new LinearLayout(getContext());
                    rowLayout.setWeightSum(casilleros.getCOLUMNAS());
                    layoutVertical.addView(rowLayout, params);
                    count = count - casilleros.getCOLUMNAS();
                }

                for (int j = 0; j < casilleros.getCOLUMNAS(); j++) {
                    buttons[i][j] = new Button(getContext());        //Crea el objeto Button
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

            casilleros.selectInicio();

        }

        levelComunicate.conectLevelconPlayZone(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        boolean encontrado = false;
        for (int i = 0; i < casilleros.getFILAS() && !encontrado; i++) {
            //Agregar condicion para salir del segundo loop una vez se evalue el boton apretado

            for (int j = 0; j < casilleros.getCOLUMNAS(); j++) {

                if (view.getTag().equals(casilleros.getButtons()[i][j].getTag())) {

                    //Toast.makeText(this, "Toque boton " + i + " " + j, Toast.LENGTH_SHORT).show();

                    if (casilleros.getBORDES()[i][j] == 0) {
                        if (casilleros.validMove(casilleros.getPOSICION(), new Coord(i, j))) {
                            casilleros.getBORDES()[i][j] = 1;
                            casilleros.getButtons()[i][j].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            casilleros.setX(i);
                            casilleros.setY(j);
                            casilleros.setEspacioOcupado(casilleros.getEspacioOcupado()+1);
                            if (casilleros.checkWin()) {
                                levelComunicate.dialogGanaste("NIVEL 1","",getContext());
                                //casilleros.resetGame();
                            }else if (checkMovements())
                                levelComunicate.dialogPerdiste("NIVEL 1","",getContext());
                        }
                    }

                    encontrado = true;
                    break;
                }
            }
        }
    }

    public void resetGame(){

        this.casilleros.resetGame();
    }

    private boolean checkMovements() {

        Coord[] c = casilleros.movPosibles(casilleros.getPOSICION());
        int mov = 0;
        for (Coord x:c){

            if (x!=null){

                if (casilleros.getBORDES()[x.getX()][x.getY()] == 0){

                    mov++;

                }

            }

        }

        return mov==0;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        levelComunicate = (LevelComunicate) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        levelComunicate = null;
    }

    //Metodo de la Interfraz PlayZoneComunicate
    @Override
    public void resetGame(int c) {
        casilleros.resetGame();
    }
}

