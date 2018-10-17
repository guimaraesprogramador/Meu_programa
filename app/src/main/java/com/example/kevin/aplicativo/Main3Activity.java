package com.example.kevin.aplicativo;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.JsonWriter;
import android.view.TextureView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static android.media.CamcorderProfile.get;

public class Main3Activity extends AppCompatActivity {
Button com_paramento;
Button carregar_banco;
Button selecionar;
ListView lista;

    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        final Intent i = getIntent();
        a = i.getStringExtra("com");
      com_paramento = (Button)findViewById(R.id.button);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      com_paramento.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              setResult(RESULT_OK,i);
              i.putExtra("retornado_com","Retornado");
              finish();
          }
      });
        lista = (ListView)findViewById(R.id.lista);
        carregar_banco  =(Button) findViewById(R.id.button2);

      carregar_banco.setOnClickListener(new View.OnClickListener() {


          @Override
          public void onClick(View v) {

         List<Pessoa> pessoaList = new downloader_json(Main3Activity.this).baixar_arquivo();
             new carregar_itens_em_thread().carregar(pessoaList);

          }
      });
      selecionar = (Button) findViewById(R.id.button3);
      selecionar.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                new carregar_itens_em_thread().Select();
          }
      });
    }
    class carregar_itens_em_thread{
        ProgressDialog dialog;
        sqlite sqlite = new sqlite(getBaseContext());
        SQLiteDatabase db =  sqlite.getWritableDatabase();

       @SuppressLint("WrongConstant")
       public  void  carregar(final  List<Pessoa> b){
          final ProgressDialog  mprogressDialog;
           mprogressDialog = ProgressDialog.show(Main3Activity.this, "Aguarde", "Verificando Produto(s)...");
           try {

               long resultado;
               ContentValues Values = null;
               for (int i = 0; i < b.size(); i++) {

                   Values = new ContentValues();
                   Values.put("id", b.get(i).getId());
                   Values.put("codigo", b.get(i).getCodigo());
                   Values.put("cpf", b.get(i).getCpf());
                   Values.put("nome", b.get(i).getNome());
                   resultado = db.insert("sqlite", null, Values);
               }
                mprogressDialog.dismiss();
               messagem("ok", "inseir com sucesso");
           }catch (Exception err){
               messagem("erro","nao foi possivel inserir os elemetntos ");
           }

               }

        public  void  messagem(String titulo ,String mensagem){
            android.app.AlertDialog.Builder alertateste = new android.app.AlertDialog.Builder(Main3Activity.this);
            alertateste.setMessage(mensagem);
            alertateste.setTitle(titulo);
            alertateste.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            });
            alertateste.show();
        }
        public void Select(){
           List<Pessoa> pessoaList =new ArrayList<Pessoa>();
           String[] colunas ={"id","nome","codigo","cpf"};
           Cursor c = db.query("sqlite",colunas,null,null,null,null,null);
           if(c.moveToFirst()){
               boolean prox = true;
               while (prox){
                   Pessoa pessoa = new Pessoa();
                   pessoa.setid(c.getInt(0));
                  pessoa.setNome(c.getString(1));
                  pessoa.setCodigo(c.getInt(2));
                  pessoa.setCpf(c.getString(3));
                   pessoaList.add(pessoa);
                   prox = c.moveToNext();
               }

           }
           if(pessoaList.size()>0){
               ArrayAdapter<Pessoa> arrayAdapter = new ArrayAdapter<Pessoa>(Main3Activity.this,android.R.layout.simple_list_item_activated_1,pessoaList);
               lista.setAdapter(arrayAdapter);
           }
        }
    }
}
