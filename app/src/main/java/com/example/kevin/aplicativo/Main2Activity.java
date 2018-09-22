package com.example.kevin.aplicativo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

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
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
Button volta_sem;
Intent i;
String sem_parameto;
Button pesquisar;
ListView visualizacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        visualizacao = (ListView)findViewById(R.id.lista);
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
                //http://192.168.181.134/apicliente/api/cliente/retornaclientes?tipo=json
            }

        });
        visualizacao.setOnItemClickListener(new ItemClickedListener());
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
            HttpClient http = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            try {
                HttpResponse response = http.execute(request);
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
            super.onPostExecute(result);
            dialog.dismiss();
            if(result.size() >0){
                ArrayAdapter<Pessoa> arrayAdapter = new ArrayAdapter<Pessoa>(Main2Activity.this,android.R.layout.simple_list_item_activated_1,result);
                visualizacao.setAdapter(arrayAdapter);

            }
            else {
                AlertDialog.Builder alertateste = new AlertDialog.Builder(Main2Activity.this);
                alertateste.setMessage("ERRO");
                alertateste.setTitle("NAO FOI POSSIVEL INFORMA OS ITENS");
                alertateste.setNeutralButton("OK", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                    }
                });
                alertateste.show();
            }
        }
    }
    private class ItemClickedListener implements android.widget.AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0, View arg1, int
                position, long id) {
            Pessoa pe = (Pessoa) arg0.getItemAtPosition(position);
            mensagem("Dados",pe.getNome() + ""+pe.getCpf());
        }
    }

    private List<Pessoa> Lista_pessoar(String json){
        List<Pessoa> pessoaList = new ArrayList<Pessoa>();
        try{
            JSONArray pessoasJson = new JSONArray(json);
           JSONObject  object;
            for (int i = 0;i<pessoasJson.length();i++){
                object = new JSONObject(pessoasJson.getString(i));
                Log.i("Pessoa","nome"+ object.getString("nome"));

                Pessoa p  =new Pessoa();
                p.setCodigo(object.getInt("codigo"));
                p.setNome(object.getString("nome"));
                p.setCpf(object.getString("cpf"));
                pessoaList.add(p);
            }

        }catch (JSONException e){
            Log.e("erro","nao foi possivel termina",e);
        }
        return pessoaList;
    }
    public void mensagem(String titulo, String mensagem) {
        android.app.AlertDialog.Builder alertateste = new android.app.AlertDialog.Builder(Main2Activity.this);
        alertateste.setMessage(mensagem);
        alertateste.setTitle(titulo);
        alertateste.setNeutralButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        alertateste.show();
    }
    private  String buff(HttpResponse resposta) throws IOException {

        String line = "";
        StringBuilder  sb = new StringBuilder();
        BufferedReader reader =null;
        try {

        reader = new BufferedReader(new InputStreamReader(resposta.getEntity().getContent()));
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
        return sb.toString();
    }

}
