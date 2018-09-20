package com.example.kevin.aplicativo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.Button;

import org.apache.http.HttpMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLTransactionRollbackException;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
Button volta_sem;
Intent i;
String sem_parameto;
Button pesquisar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         i = getIntent();
         sem_parameto = i.getStringExtra("sem");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        volta_sem = (Button)findViewById(R.id.button7);
        volta_sem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    finish();

            }
        });
        pesquisar = (Button) findViewById(R.id.button8);
        pesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new  DownloadJsonAsyncTask().execute("http://nli.univale.br/apicliente/api/cliente/retornaclientes?tipo=json");
            }
        });
    }
    class  DownloadJsonAsyncTask extends AsyncTask<String, Void, List<Pessoa>> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(Main2Activity.this, "Aguarde", "fazendo downloader em json");

        }

        protected List<Pessoa> doInBackground(String... params) {
            String url = params[0];
            HttpClient HTTPCLIENTE = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            try {
                HttpResponse response = HTTPCLIENTE.execute(request);
                String paramento = buff(response);
                List<Pessoa> pessoas = Lista_pessoar(paramento);
                return pessoas;
            } catch (Exception erro) {
                Log.e("eroo", "alguma coisa não deu certo", erro);
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Pessoa> result) {
            dialog.dismiss();
            AlertDialog.Builder alertateste = new AlertDialog.Builder(Main2Activity.this);
            alertateste.setMessage("OK VOLTOU COM SUCESSO");
            alertateste.setTitle("Retornou");
            alertateste.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    // TODO Auto-generated method stub

                }
            });
            alertateste.show();
        }
    }
    private List<Pessoa> Lista_pessoar(String json){

        return null;
    }
    private  String buff(HttpResponse resposta) throws IOException {

        String line = "";
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(resposta.getEntity().getContent()));
        try {

            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }catch (Exception erro){
        Log.e("erro", "algum lugar do codigo",erro);
        }
        finally {
            if(reader != null){
                try {
                    reader.close();
                }
                catch (Exception erro){
                    Log.e("erro","erro em fechar arquivo",erro);
                }
            }
        }

        return reader.toString();
    }

}
