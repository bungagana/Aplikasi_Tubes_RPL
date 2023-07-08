package com.example.eliterasi_pbo;

public class Article {
    private String judulartikel;
    private String kontenartikel;
    private String status;

    public Article() {

    }

    public Article(String judulartikel, String kontenartikel, String status) {
        this.judulartikel = judulartikel;
        this.kontenartikel = kontenartikel;
        this.status = status;

    }

    public String getJudulartikel() {
        return judulartikel;
    }

    public void setJudulartikel(String judulartikel) {
        this.judulartikel = judulartikel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKontenartikel() {
        return kontenartikel;
    }

    public void setKontenartikel(String kontenartikel) {
        this.kontenartikel = kontenartikel;
    }

}
