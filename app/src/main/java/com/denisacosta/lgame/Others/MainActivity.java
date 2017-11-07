package com.denisacosta.lgame.Others;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.denisacosta.lgame.Interface.PlayZoneComunicate;
import com.denisacosta.lgame.R;

/**
 * Primera clase estatica de prueba, CANDIDATA A SER BORRADA
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button[][] buttons = new Button[3][4];
    //Integer[][] intId = {{R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4},
      //      {R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8},
        //    {R.id.btn9,R.id.btn10,R.id.btn11,R.id.btn12}};

    int COLUMNAS = 4, FILAS = 3;
    int[][] BORDES = new int[3][4];
    int[] POSICION = {0, 0};

    ImageView imgChica;
    AnimationDrawable animationDrawable;  //                                EN MS
    final int FRAME_W = 85*2, FRAME_H = 121*2, FRAMES = 14, CANT_X = 5, CANT_Y = 3, FRAME_DURATION = 120,
            SCALE_FACTOR = 5;
    Bitmap[] bitmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*imgChica = findViewById(R.id.imgPintor);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.grossini_dance1);

        bitmaps = new Bitmap[FRAMES];

        int currenFrame = 0;
        for(int i = 0; i<CANT_Y;i++){
            for(int j = 0;j<CANT_X;j++){

                bitmaps[currenFrame] = Bitmap.createBitmap(bitmap,FRAME_W * j,FRAME_H * i, FRAME_W,FRAME_H);

                //bitmaps[currenFrame] = Bitmap.createScaledBitmap(bitmaps[currenFrame],FRAME_W * SCALE_FACTOR,
                //FRAME_H * SCALE_FACTOR,true);

                if (++currenFrame >= FRAMES)
                    break;

            }
        }

        animationDrawable = new AnimationDrawable();
        animationDrawable.setOneShot(false); //Para repetir

        for (int m = 0; m<FRAMES;m++){
            animationDrawable.addFrame(new BitmapDrawable(getResources(),bitmaps[m]),FRAME_DURATION);

        }

        if (Build.VERSION.SDK_INT < 16){
            imgChica.setBackgroundDrawable(animationDrawable);
        }else{
            imgChica.setBackground(animationDrawable);

        }

        imgChica.post(new Runnable() {
            @Override
            public void run() {
                animationDrawable.start();

            }
        });

        /*imgChica = findViewById(R.id.imgPintor);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.grossini_dance1);
        ImageView imageView2, imageView3;
        imageView2 = findViewById(R.id.imgPintor2);
        imageView3 = findViewById(R.id.imgPinto3);

        Bitmap a,b,c;
        a = Bitmap.createBitmap(bitmap,0,0, FRAME_W,FRAME_H);
        b = Bitmap.createBitmap(bitmap,170,0, FRAME_W,FRAME_H);
        c = Bitmap.createBitmap(bitmap,85,0,FRAME_W,FRAME_W);

        imgChica.setImageBitmap(a);
        imageView2.setImageBitmap(b);
       // imageView3.setImageBitmap(Bitmap.createScaledBitmap(,FRAME_W * SCALE_FACTOR,
                //FRAME_H * SCALE_FACTOR,true));

        /*for (int i = 0;i<FILAS;i++){

            for(int j = 0;j<COLUMNAS;j++){

                buttons[i][j] = (Button) findViewById(intId[i][j]);

            }
        }

        for (int i = 0;i<FILAS;i++){

            for(int j = 0;j<COLUMNAS;j++){

                buttons[i][j].setOnClickListener(this);

            }
        }

        BORDES[0][0] = 1;
        buttons[0][0].setBackgroundColor(getResources().getColor(R.color.colorAccent));*/
    }


    private void resetGame() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                BORDES[i][j] = 0;
            }
        }
    }

    private boolean validMove(int i, int j) {
        boolean b = false;
        if (((Math.abs(POSICION[0] - i) == 2) && (Math.abs(POSICION[1] - j) == 1)) || (((Math.abs(POSICION[0] - i) == 1) && (Math.abs(POSICION[1] - j) == 2)))) {
            b = true;
        }
        return b;
    }

    private void setX(int x) {
        POSICION[0] = x;
    }

    private void setY(int y) {
        POSICION[1] = y;
    }

    private boolean checkWin() {
        boolean b = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (BORDES[i][j] == 0) {
                    b = false;
                }
            }
        }
        return b;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_dynamic:
                startActivity(new Intent(this, DynamicActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        for (int i = 0;i<FILAS;i++){

            for(int j = 0;j<COLUMNAS;j++){

                if (view.getId() == buttons[i][j].getId()){

                    Toast.makeText(this, "Toque boton "+i+" "+j, Toast.LENGTH_SHORT).show();

                    if (BORDES[i][j] == 0) {
                        if (validMove(i, j)) {
                            BORDES[i][j] = 1;
                            buttons[i][j].setBackgroundColor(getResources().getColor(R.color.colorAccent));
                            setX(i);
                            setY(j);
                            if (checkWin()) {
                                Toast.makeText(this, "Ganaste", Toast.LENGTH_SHORT).show();
                                resetGame();
                            }
                        }
                    }

                }




            }
        }



    }
}

