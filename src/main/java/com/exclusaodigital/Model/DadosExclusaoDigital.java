package com.exclusaodigital.Model;

public class DadosExclusaoDigital {

    private Categoria categoria;
    private Subcategoria subcategoria;
    private MotivoExclusao motivos;

    public DadosExclusaoDigital() {
    }

    public DadosExclusaoDigital(Categoria categoria, Subcategoria subcategoria, MotivoExclusao motivos) {
        this.categoria = categoria;
        this.subcategoria = subcategoria;
        this.motivos = motivos;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Subcategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public MotivoExclusao getMotivos() {
        return motivos;
    }

    public void setMotivos(MotivoExclusao motivos) {
        this.motivos = motivos;
    }

}
