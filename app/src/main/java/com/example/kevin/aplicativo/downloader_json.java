package com.example.kevin.aplicativo;

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
    public List<Pessoa> baixar_arquivo(){
        return  Lista_pessoar("http://nli.univale.br/apicliente/api/cliente/retornaclientes?tipo=json");
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

}
