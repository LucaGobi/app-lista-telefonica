package com.example.applistatelefonica;

public class Contato{

    private long id;
    private String nome, endereco, telefone, email;

    public Contato() {
        id = 0;
        nome = "";
        endereco = "";
        telefone = "";
        email = "";
    }

    public Contato(long id, String nome, String endereco, String telefone, String email) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
    }

    public Contato(Contato c) {
        id = c.getId();
        nome = c.getNome();
        endereco = c.getEndereco();
        telefone = c.getTelefone();
        email = c.getEmail();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
