package com.example.pc.avaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ItemActivity extends AppCompatActivity {

    public TextView tituloItemInterno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        Intent openItemIntentRecebido = getIntent();


        //COISAS PARA SETAR O TITULO DA ATIVIDADE
        String tituloPassado = openItemIntentRecebido.getExtras().getString("titulo");
        tituloItemInterno = (TextView) findViewById(R.id.tituloItem);
        tituloItemInterno.setText(tituloPassado);

        //COISAS PARA SETAR A DESCRICAO DA ATIVIDADE
        String descricaoPassada = openItemIntentRecebido.getExtras().getString("descricao");
        tituloItemInterno = (TextView) findViewById(R.id.descricaoItem);
        tituloItemInterno.setText(descricaoPassada);





    }
}
