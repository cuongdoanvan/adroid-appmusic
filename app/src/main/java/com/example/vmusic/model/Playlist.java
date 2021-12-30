package com.example.vmusic.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Playlist implements Serializable {
    private int idPlayList;
    private String tenPlayList;
    private String hinhNen;
    private String hinhChinh;

    public void callJsonPlaylist(JSONObject jsonObject){
        try {
            this.idPlayList = jsonObject.getInt("idPlaylist");
            this.tenPlayList = jsonObject.getString("tenPlaylist");
            this.hinhNen = jsonObject.getString("hinhNen");
            this.hinhChinh = jsonObject.getString("hinhChinh");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Playlist() {
    }

    public Playlist(int idPlayList, String tenPlayList, String hinhNen, String hinhChinh) {
        this.idPlayList = idPlayList;
        this.tenPlayList = tenPlayList;
        this.hinhNen = hinhNen;
        this.hinhChinh = hinhChinh;
    }

    public int getIdPlayList() {
        return idPlayList;
    }

    public void setIdPlayList(int idPlayList) {
        this.idPlayList = idPlayList;
    }

    public String getTenPlayList() {
        return tenPlayList;
    }

    public void setTenPlayList(String tenPlayList) {
        this.tenPlayList = tenPlayList;
    }

    public String getHinhNen() {
        return hinhNen;
    }

    public void setHinhNen(String hinhNen) {
        this.hinhNen = hinhNen;
    }

    public String getHinhChinh() {
        return hinhChinh;
    }

    public void setHinhChinh(String hinhChinh) {
        this.hinhChinh = hinhChinh;
    }
}
