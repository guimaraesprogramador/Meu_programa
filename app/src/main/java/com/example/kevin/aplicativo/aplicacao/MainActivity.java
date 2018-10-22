package com.example.kevin.aplicativo.aplicacao;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kevin.aplicativo.R;
import com.example.kevin.aplicativo.Regra_de_negocio.sqlite;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;
    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        sqlite sql = new sqlite(getBaseContext());
        db = sql.getWritableDatabase();
        sql.onCreate(db);
        
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id ==R.id.sem){
            Intent i = new Intent( MainActivity.this,Main2Activity.class);
            startActivity(i);
        }
        if(id ==R.id.com){
            Intent j = new Intent( MainActivity.this,Main3Activity.class);
            j.putExtra("com","Messagem com Paramento");
            startActivityForResult(j,1);
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            mensagem("Ok você voltou ",data.getStringExtra("retornado_com"));
        }
    }
    public void mensagem(String titulo, String mensagem) {
        AlertDialog.Builder alertateste = new AlertDialog.Builder(MainActivity.this);
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
