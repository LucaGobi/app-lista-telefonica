package com.example.applistatelefonica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.applistatelefonica.database.DBHelper;

public class NovoActivity extends AppCompatActivity {

    private ViewHolder vh = new ViewHolder();
    DBHelper db;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo);

        vh.nome = findViewById(R.id.novoNome);
        vh.endereco = findViewById(R.id.novoEndereco);
        vh.telefone = findViewById(R.id.novoTelefone);
        vh.email = findViewById(R.id.novoEmail);
        vh.adicionar = findViewById(R.id.adicionar);
        vh.cancelar = findViewById(R.id.novoCancelar);
        db =  new DBHelper(this);
        i = getIntent();

        vh.cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1, i);
                finish();
            }
        });

        vh.adicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String s = vh.nome.getText().toString();
                //String s1 = vh.endereco.getText().toString();
                //String s2 = vh.telefone.getText().toString();
                //String s3 = vh.email.getText().toString();
                if(vh.nome.getText().toString().isEmpty() || vh.endereco.getText().toString().isEmpty() || vh.telefone.getText().toString().isEmpty() || vh.email.getText().toString().isEmpty()){
                    Toast.makeText(NovoActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }else{
                long res = db.insertContato(vh.nome.getText().toString(), vh.endereco.getText().toString(), vh.telefone.getText().toString(), vh.email.getText().toString());
                if (res != -1) {
                    Toast.makeText(NovoActivity.this, "Contato adicionado", Toast.LENGTH_SHORT).show();
                    setResult(2, i);
                    finish();
                }else {
                    Toast.makeText(NovoActivity.this, "Contato já adicionado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });

}

private class ViewHolder {
    EditText nome, endereco, telefone, email;
    Button adicionar, cancelar;
}
}