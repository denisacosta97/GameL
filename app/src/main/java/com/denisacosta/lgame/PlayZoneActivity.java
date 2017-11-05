package com.denisacosta.lgame;

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
import com.denisacosta.lgame.Util.Util;

/**
 * Created by Denis on 4/11/2017.
 */

public class PlayZoneActivity extends AppCompatActivity implements View.OnClickListener, LevelComunicate {

    ImageView imgBack;
    LinearLayout llButons, llControl;
    FrameLayout llLevel;
    Bundle extras;
    PlayZoneComunicate playZoneComunicate;
    Chronometer chronometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_zone);

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

            imgBack = findViewById(R.id.imgBack);
            llButons = findViewById(R.id.llButton);
            llControl = findViewById(R.id.llControl);
            llLevel = findViewById(R.id.llLevel);
            chronometer = findViewById(R.id.chronometer);

            imgBack.setOnClickListener(this);

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

            chronometer.start();

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.imgBack:
                onBackPressed();
                break;
        }
    }

    //Metodos de la Interfaz LevelComunicate
    @Override
    public void dialogGanaste(String nivel, String time, Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        chronometer.stop();

        String s = getTime(SystemClock.elapsedRealtime() - chronometer.getBase());
        builder.setTitle("GANASTE");
        builder.setMessage("Acabas de completar el "+nivel+" en "+s+"\n ¡FELICIDADES!");
        builder.setCancelable(false);
        builder.setPositiveButton("REINICIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {



                dialogInterface.dismiss();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                playZoneComunicate.resetGame(0);



            }
        });
        builder.setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
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

        chronometer.stop();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("PERDISTE");
        builder.setMessage("Te quedaste sin movimientos! Empieza de nuevo!");
        builder.setCancelable(false);
        builder.setPositiveButton("REINICIAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                playZoneComunicate.resetGame(0);



            }
        });
        builder.setNegativeButton("SALIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();
                PlayZoneActivity.this.finish();

            }
        });
        AlertDialog a = builder.create();
        a.show();

    }
    public String getTime(long millisUntilFinished){

        int dias = (int) ((millisUntilFinished/1000)/86400);
        int horas = (int) (((millisUntilFinished/1000) - (dias*86400))/3600);
        int minutos = (int) (((millisUntilFinished/1000) - (dias*86400) - (horas*3600)) / 60);
        int segundos = (int) ((millisUntilFinished/1000) % 60);

        return String.format("%2d Min %2d Seg",minutos,segundos);
    }

    @Override
    public void conectLevelconPlayZone(PlayZoneComunicate p) {
        playZoneComunicate = p;
    }
}
