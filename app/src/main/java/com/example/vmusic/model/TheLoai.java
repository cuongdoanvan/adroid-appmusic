package com.example.vmusic.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TheLoai implements Serializable {
    private int idTheLoai;
    private String tenTheLoai;
    private String hinhTheLoai;

    public void callJsonTheLoai(JSONObject jsonObject){
        try {
            this.idTheLoai = jsonObject.getInt("idTheLoai");
            this.tenTheLoai = jsonObject.getString("tenTheLoai");
            this.hinhTheLoai = jsonObject.getString("hinhTheLoai");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public TheLoai() {
    }

    public TheLoai(int idTheLoai, String tenTheLoai, String hinhTheLoai) {
        this.idTheLoai = idTheLoai;
        this.tenTheLoai = tenTheLoai;
        this.hinhTheLoai = hinhTheLoai;
    }

    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public String getHinhTheLoai() {
        return hinhTheLoai;
    }

    public void setHinhTheLoai(String hinhTheLoai) {
        this.hinhTheLoai = hinhTheLoai;
    }
}
