package com.denisacosta.lgame.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.denisacosta.lgame.Interface.LevelComunicate;
import com.denisacosta.lgame.Interface.PlayZoneComunicate;
import com.denisacosta.lgame.R;
import com.denisacosta.lgame.Util.Util;

/*
Clase principal donde se encuentran todos los controles y niveles
 */

public class PlayZoneActivity extends AppCompatActivity implements View.OnClickListener, LevelComunicate {

    ImageView imgBack;
    LinearLayout llButons, llControl;
    FrameLayout llLevel;
    Bundle extras;
    PlayZoneComunicate playZoneComunicate;
    Chronometer chronometer;


    /*
    Metodo encargado de crear la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_zone);

        //Obtencion de los valores iniciales del juego
        extras = getIntent().getExtras();

        int COLUMNAS, FILAS, POSX, POSY;

        if (extras != null) {


            try {

                COLUMNAS = Integer.parseInt(extras.getString(Util.COLUMNAS));
                FILAS = Integer.parseInt(extras.getString(Util.FILAS));
                POSX = Integer.parseInt(extras.getString(Util.POSX));
                POSY = Integer.parseInt(extras.getString(Util.POSY));

            } catch (Exception e) {

                COLUMNAS = 4;
                FILAS = 4;
                POSX = 0;
                POSY = 0;

            }

            //Casteo de elementos
            imgBack = findViewById(R.id.imgBack);
            llButons = findViewById(R.id.llButton);
            llControl = findViewById(R.id.llControl);
            llLevel = findViewById(R.id.llLevel);
            chronometer = findViewById(R.id.chronometer);
            imgBack.setOnClickListener(this);

            //Creando vista del Nivel
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            LevelActivity fragmentDesign = new LevelActivity();
            Bundle bundle = new Bundle();
            bundle.putInt(Util.FILAS, FILAS);
            bundle.putInt(Util.COLUMNAS, COLUMNAS);
            bundle.putInt(Util.POSX, POSX);
            bundle.putInt(Util.POSY, POSY);
            fragmentDesign.setArguments(bundle);
            fragmentTransaction.replace(R.id.llLevel, fragmentDesign);
            fragmentTransaction.commit();

            //Iniciando cronometro
            chronometer.start();

        }
    }

    /*
    Metodo onClick para interactuar con todos los
    objetos
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imgBack:
                onBackPressed();
                break;
        }
    }

    /*
    Metodos de la Interfaz LevelComunicate
     */
    @Override
    public void dialogGanaste(String nivel, String time, Context c) {


        chronometer.stop();
        time = getTime(SystemClock.elapsedRealtime() - chronometer.getBase());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.txtGanaste));
        builder.setMessage(String.format(getResources().getString(R.string.txtDescGanaste),nivel,time));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.txtReiniciar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                //Reinicio del cronometro y el juego
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                playZoneComunicate.resetGame(0);



            }
        });
        builder.setNegativeButton(getResources().getString(R.string.txtSalir), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                PlayZoneActivity.this.finish();

            }
        });
        AlertDialog a = builder.create();
        a.show();

    }

    @Override
    public void dialogPerdiste(String nivel, String time, Context c) {

        //Detencion del cronometro
        chronometer.stop();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.txtPerdiste));
        builder.setMessage(getResources().getString(R.string.txtDescPerdiste));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.txtReiniciar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                //Reinicio del cronometro y el juego
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                playZoneComunicate.resetGame(0);



            }
        });
        builder.setNegativeButton(getResources().getString(R.string.txtSalir), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                PlayZoneActivity.this.finish();

            }
        });
        AlertDialog a = builder.create();
        a.show();

    }

    @Override
    public void conectLevelconPlayZone(PlayZoneComunicate p) {
        playZoneComunicate = p;
    }

    /*
   Metodo que devuelve el tiempo transcurrido
    */
    public String getTime(long millisUntilFinished){

        int dias = (int) ((millisUntilFinished/1000)/86400);
        int horas = (int) (((millisUntilFinished/1000) - (dias*86400))/3600);
        int minutos = (int) (((millisUntilFinished/1000) - (dias*86400) - (horas*3600)) / 60);
        int segundos = (int) ((millisUntilFinished/1000) % 60);

        return String.format(getResources().getString(R.string.timeFormat),minutos,segundos);
    }

}
