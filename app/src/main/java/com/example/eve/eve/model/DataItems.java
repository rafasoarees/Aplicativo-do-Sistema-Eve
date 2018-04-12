package com.example.eve.eve.model;


public class DataItems {
    private String Image_URL;
    private String Image_Title;
    private String Image_Description;
    private String cidade;
    private String end;
    private String contenos;
    private String dataFim;
    private String dataInicio;
    private String email;
    private String site;
    private String nome;
    private String telefone;
    private String idUser;
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImage_URL() {
        return Image_URL;
    }

    public void setImage_URL(String image_URL) {
        Image_URL = image_URL;
    }

    public String getImage_Title() {
        return Image_Title;
    }

    public void setImage_Title(String image_Title) {
        Image_Title = image_Title;
    }

    public String getImage_Description() {
        return Image_Description;
    }

    public void setImage_Description(String image_Description) {
        Image_Description = image_Description;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getContenos() {
        return contenos;
    }

    public void setContenos(String contenos) {
        this.contenos = contenos;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }


    public DataItems(String image_URL, String image_Title, String image_Description, String cidade, String end, String contenos, String dataFim, String dataInicio, String email, String site, String nome, String telefone, String idUser, String estado) {
        Image_URL = image_URL;
        Image_Title = image_Title;
        Image_Description = image_Description;
        this.cidade = cidade;
        this.end = end;
        this.contenos = contenos;
        this.dataFim = dataFim;
        this.dataInicio = dataInicio;
        this.email = email;
        this.site = site;
        this.nome = nome;
        this.telefone = telefone;
        this.idUser = idUser;
        this.estado=estado;
    }

    public DataItems() {
        //Empty Constructor Needed
    }


}
