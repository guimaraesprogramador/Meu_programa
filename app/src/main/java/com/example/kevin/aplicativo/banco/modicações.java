package com.example.kevin.aplicativo.banco;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kevin.aplicativo.Formulario;
import com.example.kevin.aplicativo.Pessoa;
import com.example.kevin.aplicativo.R;
import com.example.kevin.aplicativo.sqlite;

import java.time.chrono.Chronology;
import java.util.ArrayList;
import java.util.List;

public class modicações extends AppCompatActivity {

    SQLiteDatabase db;
public  modicações(Context get ){
    sqlite sqlite = new sqlite(get);
    db = sqlite.getWritableDatabase();
}


        public void iniser_banco(EditText cpf, EditText codigo, EditText id, EditText nome) {
            try {
                long resultado;
                ContentValues Values = new ContentValues();
                Values.put("cpf", cpf.getText().toString());
                Values.put("codigo", codigo.getText().toString());
                Values.put("id",id.getText().toString());
                Values.put("nome",nome.getText().toString());
                 db.insert("sqlite", null, Values);

            } catch (Exception err) {

            }
        }

        public void alter_dados(EditText cpf, EditText codigo, EditText id, EditText nome , Pessoa  pessoaclicada) {
            try {
                ContentValues v = new ContentValues();
                v.put("codigo", codigo.getText().toString());
                v.put("cpf", cpf.getText().toString());
                v.put("id", id.getText().toString());
                v.put("nome",nome.getText().toString());
                String ids = String.valueOf( pessoaclicada.getId());
                String[] args = {ids};
                db.update("sqlite", v, "id=?", args);

            } catch (Exception err) {
                Log.e("err",err.getMessage());
            }

        }

        public void lista_banco(   ListView lista, Formulario a) {
            try {

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
                        ArrayAdapter<Pessoa> arrayAdapter = new ArrayAdapter<Pessoa>( a, android.R.layout.simple_list_item_activated_1, p);
                        lista.setAdapter(arrayAdapter);
                    }
                }
            } catch (Exception err) {

            }

        }
        public void deleta(Pessoa pessoaclicada){
            
            String ids = String.valueOf( pessoaclicada.getId());
            String[] args = {ids};
            db.delete("sqlite","id=?",args);
        }
    }
