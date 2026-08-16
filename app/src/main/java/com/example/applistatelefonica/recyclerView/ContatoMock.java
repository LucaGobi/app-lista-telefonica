package com.example.applistatelefonica.recyclerView;

import com.example.applistatelefonica.Contato;

import java.util.ArrayList;

public class ContatoMock {

    private ArrayList<Contato> lista;

    public ContatoMock() {
        lista = new ArrayList<>();
    }

    public void setLista(ArrayList<Contato> lista){
        this.lista = lista;
    }

    public ArrayList getLista() {
        return lista;
    }


}
