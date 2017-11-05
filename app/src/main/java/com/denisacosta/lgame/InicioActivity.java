package com.denisacosta.lgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.denisacosta.lgame.Others.DynamicActivity;
import com.denisacosta.lgame.Util.Util;

/**
 * Created by Denis on 3/11/2017.
 */

public class InicioActivity extends AppCompatActivity implements View.OnClickListener {


    Button btnJugar;
    EditText editFila,editCol,editPosX,editPosY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);


        btnJugar = findViewById(R.id.btnJugar);
        editPosX = findViewById(R.id.editTPosX);
        editPosY = findViewById(R.id.editTPosY);
        editFila = findViewById(R.id.editTFila);
        editCol = findViewById(R.id.editTCol);

        btnJugar.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnJugar:
                Intent i = new Intent(this,PlayZoneActivity.class);
                i.putExtra(Util.FILAS, editFila.getText().toString());
                i.putExtra(Util.COLUMNAS, editCol.getText().toString());
                i.putExtra(Util.POSX,editPosX.getText().toString());
                i.putExtra(Util.POSY,editPosY.getText().toString());
                startActivity(i);
                break;

        }


    }
}


