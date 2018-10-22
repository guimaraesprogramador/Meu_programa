package com.example.kevin.aplicativo.Regra_de_negocio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class sqlite extends SQLiteOpenHelper {
    static  final String database = "banco2db";
    static  final  int versao = 1;
    public  sqlite(Context banco){
        super(banco,database,null,versao);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table if not exists sqlite ( id int not null, nome char(500) not null,codigo int not null,cpf char(500) not null)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db,0,versao);
        onCreate(db);
    }
}
