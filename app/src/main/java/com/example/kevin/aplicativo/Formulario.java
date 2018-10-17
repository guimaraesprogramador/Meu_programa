package com.example.kevin.aplicativo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.aplicativo.R.*;

public class Formulario extends AppCompatActivity {
Button inserir;
sqlite  sqlite;
Button alterar;

    EditText id;
    EditText nome;
    EditText codigo;
    EditText cpf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alterar = (Button) findViewById(R.id.button13);
        inserir = (Button)  findViewById(R.id.button12);
         id = (EditText) findViewById(R.id.editText);
         nome = (EditText)findViewById(R.id.editText2);
         codigo = (EditText)findViewById(R.id.editText3);
         cpf = (EditText) findViewById(R.id.editText4);
        inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new modificar_bacnco().iniser_banco(id,nome,cpf,codigo);
            }
        });
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new modificar_bacnco().alter_dados(id,nome,cpf,codigo);
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

    }
    class  modificar_bacnco{
        ListView lista;
        sqlite sqlite = new sqlite(getBaseContext());
        SQLiteDatabase db =  sqlite.getWritableDatabase();
        public void   iniser_banco(EditText id, EditText nome,EditText cpf,EditText codigo ){
            try{
                long resultado;
                ContentValues Values = new ContentValues();
                Values.put("id", String.valueOf(id));
                Values.put("nome", String.valueOf(nome));
                Values.put("cpf", String.valueOf(cpf));
                Values.put("codigo", String.valueOf(codigo));
                resultado = db.insert("sqlite",null,Values);

            }catch (Exception err){

            }
        }
        public void  alter_dados(EditText id, EditText nome,EditText cpf,EditText codigo){
            try{
                ContentValues v = new ContentValues();
                v.put("id", String.valueOf(id));
                v.put("codigo", String.valueOf(codigo));
                v.put("cpf", String.valueOf(cpf));
                v.put("nome", String.valueOf(nome));
                String[] coluna = {"id"};
                final int update = db.update("sqlte", v, "id", coluna);
            }catch (Exception err){

            }

        }
        public  void  lista_banco(){
            try{
                //lista = (ListView) findViewById(R.id.lista2);
                long resultado;
                List<Pessoa> p = new ArrayList<Pessoa>();
                 String[]coluna = {"id","codigo","nome","cpf"};
                Cursor c= db.query("sqlite",coluna,null,null,null,null,null);
                if(c.moveToFirst()){
                    boolean proxi =true;
                    while (proxi){
                        Pessoa pessoa = new Pessoa();
                        pessoa.setid(c.getInt(0));
                        pessoa.setNome(c.getString(1));
                        pessoa.setCodigo(c.getInt(2));
                        pessoa.setCpf(c.getString(3));
                        p.add(pessoa);
                        proxi = c.moveToNext();
                    }
                    if(p.size()>0){
                        ArrayAdapter<Pessoa> arrayAdapter = new ArrayAdapter<Pessoa>(Formulario.this,android.R.layout.simple_list_item_activated_1,p);
                        //lista.setAdapter(arrayAdapter);
                    }
                }
            }catch (Exception err){

            }
        }
    }

}

