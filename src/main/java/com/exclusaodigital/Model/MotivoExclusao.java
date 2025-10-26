package com.exclusaodigital.Model;

public class MotivoExclusao {

    private int id;
    private Subcategoria subcategoria;
    private int faltaInteresse;
    private int naoSaberUsar;
    private int naoTerOndeAcessar;
    private int serMuitoCaro;
    private int segurancaPrivacidade;
    private int evitarConteudoPerigoso;
    private int outroMotivo;

    public MotivoExclusao() {
    }

    public MotivoExclusao(int id, Subcategoria subcategoria, int faltaInteresse, int naoSaberUsar,
            int naoTerOndeAcessar, int serMuitoCaro, int segurancaPrivacidade,
            int evitarConteudoPerigoso, int outroMotivo) {
        this.id = id;
        this.subcategoria = subcategoria;
        this.faltaInteresse = faltaInteresse;
        this.naoSaberUsar = naoSaberUsar;
        this.naoTerOndeAcessar = naoTerOndeAcessar;
        this.serMuitoCaro = serMuitoCaro;
        this.segurancaPrivacidade = segurancaPrivacidade;
        this.evitarConteudoPerigoso = evitarConteudoPerigoso;
        this.outroMotivo = outroMotivo;
    }

    // Getters e Setters...
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Subcategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public int getFaltaInteresse() {
        return faltaInteresse;
    }

    public void setFaltaInteresse(int faltaInteresse) {
        this.faltaInteresse = faltaInteresse;
    }
    public int getNaoSaberUsar() {
        return naoSaberUsar;
    }
    public void setNaoSaberUsar(int naoSaberUsar) {
        this.naoSaberUsar = naoSaberUsar;
    }
    public int getNaoTerOndeAcessar() {
        return naoTerOndeAcessar;
    }
    public void setNaoTerOndeAcessar(int naoTerOndeAcessar) {
        this.naoTerOndeAcessar = naoTerOndeAcessar;
    }
    public int getSerMuitoCaro() {
        return serMuitoCaro;
    }
    public void setSerMuitoCaro(int serMuitoCaro) {
        this.serMuitoCaro = serMuitoCaro;
    }
    public int getSegurancaPrivacidade() {
        return segurancaPrivacidade;
    }
    public void setSegurancaPrivacidade(int segurancaPrivacidade) {
        this.segurancaPrivacidade = segurancaPrivacidade;
    }
    public int getEvitarConteudoPerigoso() {
        return evitarConteudoPerigoso;
    }
    public void setEvitarConteudoPerigoso(int evitarConteudoPerigoso) {
        this.evitarConteudoPerigoso = evitarConteudoPerigoso;
    }
    public int getOutroMotivo() {
        return outroMotivo;
    }
    public void setOutroMotivo(int outroMotivo) {
        this.outroMotivo = outroMotivo;
    }

}
