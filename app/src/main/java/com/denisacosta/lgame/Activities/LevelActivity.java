package com.denisacosta.lgame.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.denisacosta.lgame.Component.Casilleros;
import com.denisacosta.lgame.Component.Coord;
import com.denisacosta.lgame.Interface.LevelComunicate;
import com.denisacosta.lgame.Interface.PlayZoneComunicate;
import com.denisacosta.lgame.R;
import com.denisacosta.lgame.Util.Util;

/*
Clase secundaria que se encargara de crear niveles
 */

public class LevelActivity extends Fragment implements View.OnClickListener, PlayZoneComunicate {

    Casilleros casilleros;
    LevelComunicate levelComunicate;

    public LevelActivity() {
    }

    /*
    Metodo que crea la vista general del Fragment LevelActivity
     */
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


            casilleros = new Casilleros(FILAS, COLUMNAS, POSX, POSY, getContext());

            AppCompatButton[][] buttons = new AppCompatButton[casilleros.getFILAS()][casilleros.getCOLUMNAS()];
            LinearLayout layoutVertical = view.findViewById(R.id.llCasillero);
            LinearLayout rowLayout = null;


            int count = (casilleros.getCOLUMNAS() * casilleros.getFILAS()) + 1; //

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1);

            for (int i = 0; i < casilleros.getFILAS(); i++) {

                /*
                Esto dice que por cada vez que se complete una fila
                baje a la siguiente
                */
                if (count % casilleros.getCOLUMNAS() == 1) {
                    rowLayout = new LinearLayout(getContext());
                    rowLayout.setWeightSum(casilleros.getCOLUMNAS());
                    layoutVertical.addView(rowLayout, params);
                    count = count - casilleros.getCOLUMNAS();
                }

                for (int j = 0; j < casilleros.getCOLUMNAS(); j++) {
                    /*
                    Creacion dinamica de los botones, asignandole una
                    etiqueta para ser reconocida en el stage
                     */
                    buttons[i][j] = new AppCompatButton(getContext());
                    buttons[i][j].setTag("" + i + j + "");
                    rowLayout.addView(buttons[i][j], params);
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

        //Estableciendo comunicacion del Nivel con el PlayZon
        levelComunicate.conectLevelconPlayZone(this);

        return view;
    }

    /*
    Metodo OnClick que controla el comportamiento de los botones,
    si cambia de color o no al ser un casillero valido
     */
    @Override
    public void onClick(View view) {
        boolean encontrado = false;
        for (int i = 0; i < casilleros.getFILAS() && !encontrado; i++) {

            for (int j = 0; j < casilleros.getCOLUMNAS(); j++) {

                if (view.getTag().equals(casilleros.getButtons()[i][j].getTag())) {

                    if (casilleros.getBORDES()[i][j] == 0) {
                        if (casilleros.validMove(casilleros.getPOSICION(), new Coord(i, j))) {
                            casilleros.getBORDES()[i][j] = 1;
                            casilleros.changeColorButton(casilleros.getButtons()[i][j], R.color.colorButtonActive, getContext());
                            casilleros.setX(i);
                            casilleros.setY(j);
                            casilleros.setEspacioOcupado(casilleros.getEspacioOcupado() + 1);
                            if (casilleros.checkWin()) {
                                levelComunicate.dialogGanaste(getResources().getString(R.string.txtNivel), "", getContext());
                            } else if (checkMovements())
                                levelComunicate.dialogPerdiste(getResources().getString(R.string.txtNivel), "", getContext());
                        }
                    }

                    encontrado = true;
                    break;
                }
            }
        }
    }

    /*
    Metodo que checkea si existen movimientos validos desde la posicion donde
    se encuentra
     */
    private boolean checkMovements() {

        Coord[] c = casilleros.movPosibles(casilleros.getPOSICION());
        int mov = 0;
        for (Coord x : c) {

            if (x != null) {

                if (casilleros.getBORDES()[x.getX()][x.getY()] == 0) {

                    mov++;

                }

            }

        }

        return mov == 0;
    }

    /*
    Metodos onAtach y onDetach sirven para establecer la comunicacion del
    Fragment con la Actividad mediante las interfaces
     */
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

    /*
    Metodo de la Interfraz PlayZoneComunicate que permite
    resetear el juego
     */
    @Override
    public void resetGame(int c) {
        casilleros.resetGame();
    }
}

