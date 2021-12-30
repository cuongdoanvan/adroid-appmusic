package com.example.vmusic.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.vmusic.database.ConnectDatabase;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BaiHat implements Serializable {
    private int idBaiHat;
    private Album idAlbum;
    private TheLoai idTheLoai;
    private Playlist idPlaylist;
    private String tenBaiHat;
    private String hinhBaiHat;
    private String caSi;
    private String linkBaiHat;

    public List<String> SelectDanhSach(SQLiteDatabase database,Context mContext){
        List<String> mList = new ArrayList<>();
        String query = "select * from danhsachyeuthich";
        Cursor cursor = database.rawQuery(query ,null);
        while (cursor.moveToNext()){
            String idBaiHat = cursor.getString(0)+"";
            mList.add(idBaiHat);
        }
        cursor.close();
        return mList;
    }

    public void InertDanhSach(SQLiteDatabase database, Context mContext, BaiHat baiHat){
        ContentValues row = new ContentValues();
        String idbh = String.valueOf(baiHat.getIdBaiHat());
        Playlist playlist = baiHat.getIdPlaylist();
        String idpl = String.valueOf(playlist.getIdPlayList());
        Album album = baiHat.getIdAlbum();
        String idab = String.valueOf(album.getIdAlbum());
        TheLoai theLoai = baiHat.getIdTheLoai();
        String idtl = String.valueOf(theLoai.getIdTheLoai());

        row.put("idBaiHat",idbh);
        row.put("idPlaylist",idpl);
        row.put("idAlbum",idab);
        row.put("idTheLoai",idtl);
        int i = (int) database.insert("danhsachyeuthich",null,row);
        if(i==-1){
            Toast.makeText(mContext, "Đã ở trong danh sách yêu thích", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Thêm vào sách yêu thích", Toast.LENGTH_SHORT).show();
        }
    }

    public void DeleteDanhSach(SQLiteDatabase database,Context mContext,BaiHat baiHat){
        String id = String.valueOf(baiHat.idBaiHat);
        int i = database.delete("danhsachyeuthich","idBaiHat=?",new String[]{id});
        if(i==-1){
            Toast.makeText(mContext, "xóa thất bại", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Xóa bài hát khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
        }
    }
    public void callJsonBaiHat(JSONObject jsonObject){
        try {
            this.idBaiHat = jsonObject.getInt("idBaiHat");
            Album album = new Album();
            album.setIdAlbum(jsonObject.getInt("idAlbum"));
            this.idAlbum = album;
            TheLoai theLoai = new TheLoai();
            theLoai.setIdTheLoai(jsonObject.getInt("idTheLoai"));
            this.idTheLoai = theLoai;
            Playlist playlist = new Playlist();
            playlist.setIdPlayList(jsonObject.getInt("idPlaylist"));
            this.idPlaylist = playlist;
            this.tenBaiHat = jsonObject.getString("tenBaiHat");
            this.hinhBaiHat = jsonObject.getString("hinhBaiHat");
            this.caSi = jsonObject.getString("caSi");
            this.linkBaiHat = jsonObject.getString("linkBaiHat");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public BaiHat() {
    }


    public BaiHat(int idBaiHat, Album idAlbum, TheLoai idTheLoai, Playlist idPlaylist, String tenBaiHat, String hinhBaiHat, String caSi, String linkBaiHat) {
        this.idBaiHat = idBaiHat;
        this.idAlbum = idAlbum;
        this.idTheLoai = idTheLoai;
        this.idPlaylist = idPlaylist;
        this.tenBaiHat = tenBaiHat;
        this.hinhBaiHat = hinhBaiHat;
        this.caSi = caSi;
        this.linkBaiHat = linkBaiHat;
    }

    public int getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(int idBaiHat) {
        this.idBaiHat = idBaiHat;
    }

    public Album getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(Album idAlbum) {
        this.idAlbum = idAlbum;
    }

    public TheLoai getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(TheLoai idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public Playlist getIdPlaylist() {
        return idPlaylist;
    }

    public void setIdPlaylist(Playlist idPlaylist) {
        this.idPlaylist = idPlaylist;
    }

    public String getTenBaiHat() {
        return tenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        this.tenBaiHat = tenBaiHat;
    }

    public String getHinhBaiHat() {
        return hinhBaiHat;
    }

    public void setHinhBaiHat(String hinhBaiHat) {
        this.hinhBaiHat = hinhBaiHat;
    }

    public String getCaSi() {
        return caSi;
    }

    public void setCaSi(String caSi) {
        this.caSi = caSi;
    }

    public String getLinkBaiHat() {
        return linkBaiHat;
    }

    public void setLinkBaiHat(String linkBaiHat) {
        this.linkBaiHat = linkBaiHat;
    }


    @Override
    public String toString() {
        return tenBaiHat;
    }
}
