package com.example.kevin.aplicativo;

import java.io.Serializable;

public class Pessoa implements Serializable {

    private String nome;
    private  String cpf;
    private  int codigo;

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
    public int getCodigo(){return codigo;}
}
