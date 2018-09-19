package com.example.kevin.aplicativo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.Button;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

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

            }
        });
    }

}
