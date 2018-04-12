package com.example.eve.eve.model;


public class Lecture {
    private String dataP;
    private String horaPI;
    private String horaPF;
    private String idUser;
    private String localP;
    private String nomeP;
    private String tituloP;

    public String getDataP() {
        return dataP;
    }

    public void setDataP(String dataP) {
        this.dataP = dataP;
    }



    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLocalP() {
        return localP;
    }

    public void setLocalP(String localP) {
        this.localP = localP;
    }

    public String getNomeP() {
        return nomeP;
    }

    public void setNomeP(String nomeP) {
        this.nomeP = nomeP;
    }

    public String getTituloP() {
        return tituloP;
    }

    public void setTituloP(String tituloP) {
        this.tituloP = tituloP;
    }

    public String getHoraPI() {
        return horaPI;
    }

    public String getHoraPF() {
        return horaPF;
    }

    public Lecture(String dataP, String horaPI, String horaPF, String idUser, String localP, String nomeP, String tituloP) {
        this.dataP = dataP;
        this.horaPI = horaPI;
        this.horaPF = horaPF;
        this.idUser = idUser;
        this.localP = localP;
        this.nomeP = nomeP;
        this.tituloP = tituloP;
    }

    public Lecture() {
        //Empty Constructor Needed
    }


}
