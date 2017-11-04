package com.denisacosta.lgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                Intent i = new Intent(this,DynamicActivity.class);
                i.putExtra("FILA", editFila.getText().toString());
                i.putExtra("COL", editCol.getText().toString());
                i.putExtra("X",editPosX.getText().toString());
                i.putExtra("Y",editPosY.getText().toString());
                startActivity(i);
                break;

        }


    }
}


