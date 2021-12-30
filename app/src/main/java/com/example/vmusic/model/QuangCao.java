package com.example.vmusic.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class QuangCao implements Serializable {
    private int idQuangCao;
    private String hinhQuangCaoLon;
    private String hinhQuangCaoNho;
    private String noiDungQuangCao;
    private BaiHat idBaiHat;

    public void callJsonQuangCao(JSONObject jsonObject){
        try {
            this.idQuangCao = jsonObject.getInt("idQuangCao");
            this.hinhQuangCaoLon=jsonObject.getString("hinhQuangCaoLon");
            this.hinhQuangCaoNho=jsonObject.getString("hinhQuangCaoNho");
            this.noiDungQuangCao=jsonObject.getString("noiDungQuangCao");
            BaiHat baiHat = new BaiHat();
            baiHat.setIdBaiHat(Integer.parseInt(jsonObject.getString("idBaiHat")));
            baiHat.setTenBaiHat(jsonObject.getString("tenBaiHat"));
            this.idBaiHat = baiHat;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public QuangCao() {
    }


    public QuangCao(int idQuangCao, String hinhQuangCaoLon, String hinhQuangCaoNho, String noiDungQuangCao, BaiHat idBaiHat) {
        this.idQuangCao = idQuangCao;
        this.hinhQuangCaoLon = hinhQuangCaoLon;
        this.hinhQuangCaoNho = hinhQuangCaoNho;
        this.noiDungQuangCao = noiDungQuangCao;
        this.idBaiHat = idBaiHat;
    }

    public int getIdQuangCao() {
        return idQuangCao;
    }

    public void setIdQuangCao(int idQuangCao) {
        this.idQuangCao = idQuangCao;
    }

    public String getHinhQuangCaoLon() {
        return hinhQuangCaoLon;
    }

    public void setHinhQuangCaoLon(String hinhQuangCaoLon) {
        this.hinhQuangCaoLon = hinhQuangCaoLon;
    }

    public String getHinhQuangCaoNho() {
        return hinhQuangCaoNho;
    }

    public void setHinhQuangCaoNho(String hinhQuangCaoNho) {
        this.hinhQuangCaoNho = hinhQuangCaoNho;
    }

    public String getNoiDungQuangCao() {
        return noiDungQuangCao;
    }

    public void setNoiDungQuangCao(String noiDungQuangCao) {
        this.noiDungQuangCao = noiDungQuangCao;
    }

    public BaiHat getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(BaiHat idBaiHat) {
        this.idBaiHat = idBaiHat;
    }
}
