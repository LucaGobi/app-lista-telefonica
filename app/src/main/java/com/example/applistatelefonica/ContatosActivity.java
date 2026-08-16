package com.example.applistatelefonica;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.applistatelefonica.database.DBHelper;
import com.example.applistatelefonica.recyclerView.ContatoListAdapter;
import com.example.applistatelefonica.recyclerView.ContatoMock;

import java.util.ArrayList;

public class ContatosActivity extends AppCompatActivity {

    private ViewHolder vh = new ViewHolder();
    DBHelper db;
    ArrayList<Contato> lista;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && (requestCode == 1 || requestCode == 2) && resultCode == 1) {
        }else listarContatos();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        vh.novo = findViewById(R.id.listaNovo);
        vh.editar = findViewById(R.id.listaEditar);
        vh.rv = findViewById(R.id.listaContatos);
        db = new DBHelper(this);
        lista = new ArrayList<>();

        listarContatos();

        vh.novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ContatosActivity.this, NovoActivity.class);
                startActivityForResult(i, 1);
            }
        });
    }

    private void listarContatos() {
        lista.clear();
        Cursor c = db.selectAllContato();
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            long l = c.getLong(0);
            String s = c.getString(1);
            String s1 = c.getString(2);
            String s2 = c.getString(3);
            String s3 = c.getString(4);
            lista.add(new Contato(c.getLong(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4)));
            c.moveToNext();
        }

        ContatoListAdapter adapter = new ContatoListAdapter(lista);
        vh.rv.setAdapter(adapter);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        vh.rv.setLayoutManager(layout);
    }

    private class ViewHolder {
        Button novo, editar;
        RecyclerView rv;
    }
}