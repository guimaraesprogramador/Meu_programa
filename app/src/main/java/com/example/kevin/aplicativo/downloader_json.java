package com.example.kevin.aplicativo;

import android.support.v7.widget.LinearSmoothScroller;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;

public class downloader_json {
    public List <Pessoa> baixar_arquivo()  {
      return doInBackground("http://nli.univale.br/apicliente/api/cliente/retornaclientes?tipo=json");
      //http://192.168.181.134/apicliente/api/cliente/retornaclientes?tipo=json
    }
    private  String buff(HttpResponse resposta) throws IOException {

        String line = "";
        StringBuilder  sb = new StringBuilder();
        BufferedReader reader = null;
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
    private List<Pessoa> Lista_pessoar(String json){
        List<Pessoa> pessoaList = new ArrayList<Pessoa>();
        try{
            JSONArray pessoasJson = new JSONArray(json);
            JSONObject object;
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
    public static String paramento;
    public  static List<Pessoa> pessoas;
    public JSONObject _mo = new JSONObject();
    private boolean open = false;
    private  List<Pessoa> doInBackground(final String params)  {
        synchronized (_mo) {
            try {
                while (!open) {
                    _mo.wait(1000);
                    Runnable Carregar = new Runnable() {

                        @Override
                        public void run() {
                            try {
                                HttpClient http = new DefaultHttpClient();
                                HttpGet request = new HttpGet(params);
                                HttpResponse response = http.execute(request);
                                paramento = buff(response);
                                pessoas = Lista_pessoar(paramento);


                            } catch (Exception err) {

                            }

                        }
                    };
                    if (pessoas != null) {
                       break;
                    }
                    new Thread(Carregar).start();

                }
            }catch (Exception err){
                Log.e("erro","erro",err);
            }

        }

     return pessoas;
    }

}
