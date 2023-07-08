package com.example.eliterasi_pbo;

public class User {
    private String userName;
    private String password;
    private String email;
    private String poin;
    private String jumlahkarya;
    private String agree;
    private String denied;
    private String title;
    private String exp;
    private String level;
    private String bio;

    public User () {

    }

    public User(String userName, String password, String email, String poin, String jumlahkarya, String agree, String denied, String title, String exp, String level, String bio) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.poin = poin;
        this.jumlahkarya = jumlahkarya;
        this.agree = agree;
        this.denied = denied;
        this.title = title;
        this.exp = exp;
        this.level = level;
        this.bio = bio;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPoin() {
        return poin;
    }

    public void setPoin(String poin) {
        this.poin = poin;
    }

    public String getJumlahkarya() {
        return jumlahkarya;
    }

    public void setJumlahkarya(String jumlahkarya) {
        this.jumlahkarya = jumlahkarya;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getDenied() {
        return denied;
    }

    public void setDenied(String denied) {
        this.denied = denied;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
