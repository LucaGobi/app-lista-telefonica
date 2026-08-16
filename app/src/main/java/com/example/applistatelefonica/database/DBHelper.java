package com.example.applistatelefonica.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static int versao = 1;
    private static String nome = "ListaTelefonica.db";

    public DBHelper(@Nullable Context context) {
        super(context, nome, null, versao);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contato (id INTEGER NOT NULL UNIQUE,nome TEXT NOT NULL UNIQUE,endereco TEXT,telefone TEXT NOT NULL UNIQUE,email TEXT,PRIMARY KEY(id AUTOINCREMENT));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        versao++;
        db.execSQL("DROP TABLE IF EXISTS Utilizador;");
        onCreate(db);
    }

    // ========================================INSERT=====================================================
    public long insertContato(String nome, String endereco, String telefone, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("endereco", endereco);
        values.put("telefone", telefone);
        values.put("email", email);
        return db.insert("contato", null, values);
    }

    // ========================================UPDATE=====================================================
    public long updateContato(long id, String nome, String endereco, String telefone, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nome", nome);
        values.put("endereco", endereco);
        values.put("telefone", telefone);
        values.put("email", email);
        return db.update("contato", values, "id=?", new String[]{String.valueOf(id)});
    }

    // ========================================DELETE=====================================================
    public long deleteContato(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("contato", "id=?", new String[]{String.valueOf(id)});
    }

    // ========================================SELECT=====================================================
    public Cursor selectAllContato(){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM contato", null);
    }

    public Cursor selectByIdContato(long id){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM contato WHERE id=?", new String[]{String.valueOf(id)});
    }
}
