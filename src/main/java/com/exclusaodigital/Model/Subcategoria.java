package com.exclusaodigital.Model;

public class Subcategoria {
    private int id;
    private Categoria categoria;
    private String nome;

    public Subcategoria() {}

    public Subcategoria(int id, Categoria categoria, String nome) {
        this.id = id;
        this.categoria = categoria;
        this.nome = nome;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
