package com.example.applistatelefonica.recyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.applistatelefonica.AcoesActivity;
import com.example.applistatelefonica.Contato;
import com.example.applistatelefonica.DetalhesActivity;
import com.example.applistatelefonica.R;

import java.util.ArrayList;

public class ContatoListAdapter extends RecyclerView.Adapter<ContatoViewHolder> {

    ArrayList<Contato> localData;

    public ContatoListAdapter(ArrayList<Contato> data){
        localData = data;
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contato_list, parent, false);
        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContatoViewHolder holder, int position) {
        holder.setApresentacao(localData.get(position));

        holder.detalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context c = holder.itemView.getContext();
                Intent i = new Intent(holder.itemView.getContext(), DetalhesActivity.class);
                i.putExtra("id", localData.get(position).getId());
                ((Activity)holder.itemView.getContext()).startActivityForResult(i ,2);
            }
        });

        holder.acoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Context c = holder.itemView.getContext();
                Intent i = new Intent(holder.itemView.getContext(), AcoesActivity.class);
                i.putExtra("id", localData.get(position).getId());
                holder.itemView.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localData.size();
    }
}
