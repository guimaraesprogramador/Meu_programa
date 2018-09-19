package com.example.kevin.aplicativo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {
Button com_paramento;

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

    }

}
