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
import android.widget.Button;

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
              ProgressDialog dialog = ProgressDialog.show(Main3Activity.this, "Aguarde", "mantando para o banco o arquivo em json");

         List<Pessoa> pessoaList = new downloader_json().baixar_arquivo();

             new carregar_itens_em_thread().carregar(pessoaList);
             dialog.dismiss();




          }
      });
    }
    class carregar_itens_em_thread{

        sqlite sqlite = new sqlite(getBaseContext());
        SQLiteDatabase db =  sqlite.getWritableDatabase();

       @SuppressLint("WrongConstant")
       public  void  carregar(final  List<Pessoa> b){
           final Handler handler = new Handler();

           Runnable inserir_dados_banco = new Runnable() {

                       long resultado;
                       @Override
                       public void run () {

                           try {
                               if(b != null) {


                                   JSONObject jsonObject;
                                   ContentValues Values = null;
                                   for (int i = 0; i < b.size(); i++) {
                                       JSONArray array = new JSONArray(b.get(i));
                                        jsonObject = new JSONObject(array.getString(i));
                                       Values = new ContentValues();
                                       Values.put("id", ("codigo"));
                                       Values.put("codigo", jsonObject.getInt("codigo"));
                                       Values.put("cpf", jsonObject.getString("cpf"));
                                       Values.put("nome", jsonObject.getString("nome"));
                                   }


                                   resultado = db.insert("sql", null, Values);
                                   messagem("ok", "inseir com sucesso");
                               }

                           } catch (Exception erro) {

                           }
                       }
                   };
           new Thread(inserir_dados_banco).start();
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
    }
}
