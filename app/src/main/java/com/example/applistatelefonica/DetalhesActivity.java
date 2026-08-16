package com.example.applistatelefonica;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applistatelefonica.database.DBHelper;

public class DetalhesActivity extends AppCompatActivity {

    private ViewHolder vh = new ViewHolder();
    DBHelper db;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);

        vh.nome = findViewById(R.id.detalheNome);
        vh.endereco = findViewById(R.id.detalheEndereco);
        vh.telefone = findViewById(R.id.detalheTelefone);
        vh.email = findViewById(R.id.detalheEmail);
        vh.editar = findViewById(R.id.editar);
        vh.deletar = findViewById(R.id.deletar);
        vh.cancelar = findViewById(R.id.detalheCancelar);
        db = new DBHelper(this);
        i = getIntent();

        carregar();

        //Toast.makeText(this, String.valueOf(i.getExtras().getLong("id")), Toast.LENGTH_SHORT).show();

        vh.cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1, i);
                finish();
            }
        });

        vh.editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vh.nome.getText().toString().isEmpty() || vh.endereco.getText().toString().isEmpty() || vh.telefone.getText().toString().isEmpty() || vh.email.getText().toString().isEmpty()) {
                    Toast.makeText(DetalhesActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                } else {
                    long res = db.updateContato(i.getExtras().getLong("id"), vh.nome.getText().toString(), vh.endereco.getText().toString(), vh.telefone.getText().toString(), vh.email.getText().toString());
                    if (res == 1) {
                        Toast.makeText(DetalhesActivity.this, "Contato editado", Toast.LENGTH_SHORT).show();
                        setResult(2, i);
                        finish();
                    } else {
                        Toast.makeText(DetalhesActivity.this, "Erro ao editar", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        vh.deletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long res = db.deleteContato(i.getExtras().getLong("id"));
                if (res == 1) {
                    Toast.makeText(DetalhesActivity.this, "Contato deletado", Toast.LENGTH_SHORT).show();
                    setResult(3, i);
                    finish();
                } else {
                    Toast.makeText(DetalhesActivity.this, "Erro ao deletar", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void carregar() {
        Cursor c = db.selectByIdContato(i.getExtras().getLong("id"));
        c.moveToFirst();
        if (c.getCount() == 1) {
            vh.nome.setText(c.getString(1));
            vh.endereco.setText(c.getString(2));
            vh.telefone.setText(c.getString(3));
            vh.email.setText(c.getString(4));
        } else {
            Toast.makeText(DetalhesActivity.this, "Erro ao carregar dados do contato", Toast.LENGTH_SHORT).show();
        }
    }

    private class ViewHolder {
        EditText nome, endereco, telefone, email;
        Button editar, deletar, cancelar;
    }
}