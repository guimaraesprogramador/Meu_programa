package com.example.kevin.aplicativo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelUuid;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

import java.util.List;

public class Main3Activity extends AppCompatActivity {
Button com_paramento;
Button carregar_banco;
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

        carregar_banco  =(Button) findViewById(R.id.button2);
      carregar_banco.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

            new carregar_itens_em_thread();

          }
      });
    }
    class carregar_itens_em_thread{

        sqlite sqlite = new sqlite(getBaseContext());
        SQLiteDatabase db =  sqlite.getWritableDatabase();
       @SuppressLint("WrongConstant")
       public  String carregar(final int id, final int cpf, final int codigo, final String nome){
           try{
               final Handler handler = new Handler();
               db = openOrCreateDatabase(sqlite.database,SQLiteDatabase.CREATE_IF_NECESSARY,null);
               new Thread(new Runnable() {
                   long resutlado;
                   @Override
                   public void run() {
                       handler.post(new Runnable() {
                           @Override
                           public void run() {
                               ContentValues pessoas;
                               pessoas = new ContentValues();
                               pessoas.put("id",id);
                               pessoas.put("cpf",cpf);
                               pessoas.put("codigo",codigo);
                               pessoas.put("nome",nome);
                           }
                       });
                   }
               });
               return "ok";
           }catch (Exception err){
               return  err.getMessage();
           }

       }
    }
class  buscar_DownloadJsonAsyncTask{
        public List<Pessoa>  procurar(){
            return null;
        }
}
}
