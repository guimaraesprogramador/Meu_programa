package com.example.kevin.aplicativo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kevin.aplicativo.banco.modicações;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.aplicativo.R.*;

public class Formulario extends AppCompatActivity {
Button inserir;
sqlite  sqlite;
Button alterar;
    private Pessoa pessoaclicada;

    EditText codigo, id,nome;
    EditText cpf;
    ListView lista;
    Button listar, apagar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alterar = (Button) findViewById(R.id.button13);
        inserir = (Button)findViewById(R.id.button6);

        codigo = (EditText)findViewById(R.id.editText2);
         cpf = (EditText) findViewById(R.id.editText4);
         id = (EditText) findViewById(R.id.editText3);
         nome = (EditText)findViewById(R.id.editText);
        pessoaclicada = (Pessoa) getIntent().getSerializableExtra( "pessoaclicada" );
         if(pessoaclicada != null){
             codigo.setText(String.valueOf(pessoaclicada.getCodigo()));
             cpf.setText(pessoaclicada.getCpf());
             id .setText( String.valueOf(pessoaclicada.getId()));
             nome.setText(pessoaclicada.getNome());
         }
        lista = (ListView) findViewById(R.id.lista2);
          inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new modicações(getBaseContext()).iniser_banco(cpf,codigo, id,nome);
                new modicações(getBaseContext()).lista_banco(lista,Formulario.this);
            }
        });
        lista = (ListView) findViewById(R.id.lista2);
         listar = (Button) findViewById(R.id.button14);
         listar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                new modicações(getBaseContext()).lista_banco(lista,Formulario.this);
             }
         });
        apagar = (Button) findViewById(R.id.button9);
        apagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new modicações(getBaseContext()).deleta(pessoaclicada);
                finish();
            }
        });
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new modicações(getBaseContext()).alter_dados(cpf,codigo, id, nome,pessoaclicada);
                new modicações(getBaseContext()).lista_banco(lista, Formulario.this);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        lista.setOnItemClickListener(new ItemClickedListener());
    }
    private class ItemClickedListener implements android.widget.AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0, View arg1, int
                position, long id) {
            Pessoa pe = (Pessoa) arg0.getItemAtPosition(position);
            mensagem(pe.getNome() + pe.getCpf(),pe.getNome() +"  "+ pe.getCpf() );
        }
        public void mensagem(String titulo, String mensagem) {
            android.app.AlertDialog.Builder alertateste = new android.app.AlertDialog.Builder(Formulario.this);
            alertateste.setMessage(mensagem);
            alertateste.setTitle(titulo);
            alertateste.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            });
            alertateste.show();
        }
    }

}

