package com.example.kevin.aplicativo.Regra_de_negocio;

import java.io.Serializable;

public class Pessoa implements Serializable {

    private String nome;
    private  String cpf;
    private  int codigo;
    private  int id;

    public int getId() {
        return id;
    }
public int  setid(int id){return  this.id = id;}
    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
    public int getCodigo(){return codigo;}
    public  void  setCodigo(int codigo){
         this.codigo = codigo;
    }
    public void  setNome(String nome){
         this.nome = nome;
    }
    public  void  setCpf(String cpf){
        this.cpf = cpf;
    }
    @Override
    public String toString() {
        return  nome;
    }
}
