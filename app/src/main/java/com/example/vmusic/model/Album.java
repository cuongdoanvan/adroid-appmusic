package com.example.vmusic.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Album implements Serializable {
    private int idAlbum;
    private String tenAlbum;
    private String tenCaSiAlbum;
    private String hinhAlbum;

    public void callJsonAlbum(JSONObject jsonObject){
        try {
            this.idAlbum = jsonObject.getInt("idAlbum");
            this.tenAlbum = jsonObject.getString("tenAlbum");
            this.tenCaSiAlbum = jsonObject.getString("tenCaSiAlbum");
            this.hinhAlbum = jsonObject.getString("hinhAlbum");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Album() {
    }

    public Album(int idAlbum, String tenAlbum, String tenCaSiAlbum, String hinhAlbum) {
        this.idAlbum = idAlbum;
        this.tenAlbum = tenAlbum;
        this.tenCaSiAlbum = tenCaSiAlbum;
        this.hinhAlbum = hinhAlbum;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getTenAlbum() {
        return tenAlbum;
    }

    public void setTenAlbum(String tenAlbum) {
        this.tenAlbum = tenAlbum;
    }

    public String getTenCaSiAlbum() {
        return tenCaSiAlbum;
    }

    public void setTenCaSiAlbum(String tenCaSiAlbum) {
        this.tenCaSiAlbum = tenCaSiAlbum;
    }

    public String getHinhAlbum() {
        return hinhAlbum;
    }

    public void setHinhAlbum(String hinhAlbum) {
        this.hinhAlbum = hinhAlbum;
    }
}
