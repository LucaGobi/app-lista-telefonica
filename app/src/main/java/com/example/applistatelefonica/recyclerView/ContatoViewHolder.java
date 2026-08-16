package com.example.applistatelefonica.recyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applistatelefonica.Contato;
import com.example.applistatelefonica.R;

public class ContatoViewHolder extends RecyclerView.ViewHolder {

    private TextView nome, telefone;
    Button detalhes, acoes;

    public ContatoViewHolder(@NonNull View itemView) {
        super(itemView);
        nome = itemView.findViewById(R.id.rvNome);
        telefone = itemView.findViewById(R.id.rvTelefone);
        detalhes = itemView.findViewById(R.id.rvDetalhes);
        acoes = itemView.findViewById(R.id.rvAcoes);
    }

    public void setApresentacao(Contato c){
        this.nome.setText(c.getNome() + " ");
        this.telefone.setText("(" + c.getTelefone() + ")");
    }
}
