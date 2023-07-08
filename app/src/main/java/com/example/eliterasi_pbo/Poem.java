package com.example.eliterasi_pbo;

public class Poem {
    private String judulpuisi;
    private String kontenpuisi;
    private String status;
    private String score;
    private String imgurl;
    private String lovemeter;
    private String author;
    private String jenis;

    public Poem() {

    }

    public Poem(String judulpuisi, String kontenpuisi, String status, String score, String imgurl, String lovemeter, String author, String jenis) {
        this.judulpuisi = judulpuisi;
        this.kontenpuisi = kontenpuisi;
        this.status = status;
        this.score = score;
        this.imgurl = imgurl;
        this.lovemeter = lovemeter;
        this.author = author;
        this.jenis = jenis;
    }

    public String getJudulpuisi() {
        return judulpuisi;
    }

    public void setJudulpuisi(String judulpuisi) {
        this.judulpuisi = judulpuisi;
    }

    public String getKontenpuisi() {
        return kontenpuisi;
    }

    public void setKontenpuisi(String kontenpuisi) {
        this.kontenpuisi = kontenpuisi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getLovemeter() {
        return lovemeter;
    }

    public void setLovemeter(String lovemeter) {
        this.lovemeter = lovemeter;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }
}
