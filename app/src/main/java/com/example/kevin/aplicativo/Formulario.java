package com.example.kevin.aplicativo;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.kevin.aplicativo.R.*;

public class Formulario extends AppCompatActivity {
Button inserir;
sqlite  sqlite;
Button alterar;
    private Pessoa pessoaclicada;

    EditText codigo;
    EditText cpf;
    ListView lista;
    Button listar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alterar = (Button) findViewById(R.id.button13);

        codigo = (EditText)findViewById(R.id.editText2);
         cpf = (EditText) findViewById(R.id.editText4);
        pessoaclicada = (Pessoa) getIntent().getSerializableExtra( "pessoaclicada" );
         if(pessoaclicada != null){
             codigo.setText(pessoaclicada.getNome());
             cpf.setText(pessoaclicada.getCpf());
         }

        lista = (ListView) findViewById(id.lista2);
         listar = (Button) findViewById(id.button14);
         listar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 new modificar_bacnco().lista_banco();
             }
         });
       
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new modificar_bacnco().alter_dados(cpf,codigo);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        lista.setOnItemClickListener(new ItemClickedListener());
    }
    class  modificar_bacnco {
        ListView lista;
        sqlite sqlite = new sqlite(getBaseContext());
        SQLiteDatabase db = sqlite.getWritableDatabase();

        public void iniser_banco(EditText cpf, EditText codigo) {
            try {
                long resultado;
                ContentValues Values = new ContentValues();
                Values.put("cpf", cpf.getText().toString());
                Values.put("codigo", codigo.getText().toString());
                String id = pessoaclicada.getNome();
                String[]agrs= {id};
                 db.insert("sqlite", null, Values);

            } catch (Exception err) {

            }
        }

        public void alter_dados(EditText cpf, EditText codigo) {
            try {
                ContentValues v = new ContentValues();
                v.put("nome", codigo.getText().toString());
                v.put("cpf", cpf.getText().toString());
                String ids = pessoaclicada.getNome();
                String[] args = {ids};
                db.update("sqlite", v, "nome=?", args);
            } catch (Exception err) {
                Log.e("err",err.getMessage());
            }

        }

        public void lista_banco() {
            try {
                lista = (ListView) findViewById(id.lista2);
                long resultado;
                List<Pessoa> p = new ArrayList<Pessoa>();
                String[] coluna = {"id", "nome", "codigo", "cpf"};
                Cursor c = db.query("sqlite", coluna, null, null, null, null, null);
                if (c.moveToFirst()) {
                    boolean proxi = true;
                    while (proxi) {
                        Pessoa pessoa = new Pessoa();
                        pessoa.setid(c.getInt(0));
                        pessoa.setNome(c.getString(1));
                        pessoa.setCodigo(c.getInt(2));
                        pessoa.setCpf(c.getString(3));
                        p.add(pessoa);
                        proxi = c.moveToNext();
                    }
                    if (p.size() > 0) {
                        ArrayAdapter<Pessoa> arrayAdapter = new ArrayAdapter<Pessoa>(Formulario.this, android.R.layout.simple_list_item_activated_1, p);
                        lista.setAdapter(arrayAdapter);
                    }
                }
            } catch (Exception err) {

            }
        }

    }
    private class ItemClickedListener implements android.widget.AdapterView.OnItemClickListener {
        public void onItemClick(AdapterView<?> arg0, View arg1, int
                position, long id) {
            Pessoa pe = (Pessoa) arg0.getItemAtPosition(position);
            mensagem(pe.getNome() + pe.getCpf(),pe.getNome() +"  "+ pe.getCpf() );
        }
        public void mensagem(String titulo, String mensagem) {
            android.app.AlertDialog.Builder alertateste = new android.app.AlertDialog.Builder(Formulario.this);
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

