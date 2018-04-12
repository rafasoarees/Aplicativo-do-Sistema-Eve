package com.example.eve.eve.model;

/**
 * Created by Detran_2 on 25/10/2017.
 */

public class ShortCurse {
    private String idUser;
    private String horaMI;


    private String horaMF;
    private String instrutorM;
    private String nomeM;
    private String periodoMI;
    private String periodoMF;
    private String localM;

    public String getLocalM() {
        return localM;
    }

    public void setLocalM(String localM) {
        this.localM = localM;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getHoraMI() {
        return horaMI;
    }

    public String getHoraMF() {
        return horaMF;
    }

    public void setHoraM(String horaM) {
        this.horaMI = horaM;
    }

    public String getInstrutorM() {
        return instrutorM;
    }

    public void setInstrutorM(String instrutorM) {
        this.instrutorM = instrutorM;
    }

    public String getNomeM() {
        return nomeM;
    }

    public void setNomeM(String nomeM) {
        this.nomeM = nomeM;
    }

    public String getPeriodoMI() {
        return periodoMI;
    }

    public String getPeriodoMF() {
        return periodoMF;
    }

    public void setPeriodoMF(String periodoMF) {
        this.periodoMF = periodoMF;
    }

    public ShortCurse(String idUser, String horaMI, String horaMF, String instrutorM, String nomeM, String periodoMI, String periodoMF, String localM) {
        this.idUser = idUser;
        this.horaMI = horaMI;
        this.horaMF = horaMF;
        this.instrutorM = instrutorM;
        this.nomeM = nomeM;
        this.periodoMI = periodoMI;
        this.periodoMF = periodoMF;
        this.localM = localM;
    }

    public ShortCurse() {
    }
}
