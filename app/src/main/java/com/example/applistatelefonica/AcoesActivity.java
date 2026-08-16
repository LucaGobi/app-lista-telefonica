package com.example.applistatelefonica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applistatelefonica.database.DBHelper;

public class AcoesActivity extends AppCompatActivity {

    private ViewHolder vh = new ViewHolder();
    DBHelper db;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acoes);

        vh.nome = findViewById(R.id.acaoNome);
        vh.endereco = findViewById(R.id.acaoEndereco);
        vh.telefone = findViewById(R.id.acaoTelefone);
        vh.email = findViewById(R.id.acaoEmail);
        vh.ligar = findViewById(R.id.ligar);
        vh.mensagem = findViewById(R.id.mensagem);
        vh.localizacao = findViewById(R.id.localizacao);
        vh.cancelar = findViewById(R.id.acaoCancelar);
        db = new DBHelper(this);
        i = getIntent();

        carregar();

        vh.cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        vh.ligar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tel = vh.telefone.getText().toString().replace("-", "").replace(" ", "");
                Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
                startActivity(i);
            }
        });

        vh.mensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+vh.email.getText().toString()));
                i.putExtra(Intent.EXTRA_EMAIL, vh.email.getText().toString());
                i.putExtra(Intent.EXTRA_SUBJECT, "Email de AppListaTelefonica");
                i.putExtra(Intent.EXTRA_TEXT, "Email enviado por meio de uma intent de ação ACTION_SENDTO");
                startActivity(Intent.createChooser(i, "Chooser"));
            }
        });

        vh.localizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AcoesActivity.this, MapsActivity.class);
                i.putExtra("endereco", vh.endereco.getText().toString());
                startActivity(i);
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
            Toast.makeText(AcoesActivity.this, "Erro ao carregar dados do contato", Toast.LENGTH_SHORT).show();
        }
    }

    private class ViewHolder {
        TextView nome, endereco, telefone, email;
        Button ligar, mensagem, localizacao, cancelar;
    }
}