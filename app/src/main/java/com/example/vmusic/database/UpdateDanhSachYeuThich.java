package com.example.vmusic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import com.example.vmusic.model.Album;
import com.example.vmusic.model.BaiHat;
import com.example.vmusic.model.Playlist;
import com.example.vmusic.model.TheLoai;

import java.util.List;

public class UpdateDanhSachYeuThich {
    String NAME_DATABASE = "vmusic.db";
    String PLACE_SAVE = "/databases/";
    SQLiteDatabase database = null;
    Context mContex;
    BaiHat baiHat;

    public UpdateDanhSachYeuThich(Context mContex, BaiHat baiHat) {
        this.mContex = mContex;
        this.baiHat = baiHat;
    }
    public void insertDanhSachYeuThich(){
        ConnectDatabase conn = new ConnectDatabase(NAME_DATABASE,PLACE_SAVE,mContex);
        conn.readDataBase();
        database = mContex.openOrCreateDatabase(NAME_DATABASE,mContex.MODE_PRIVATE,null);
        baiHat.InertDanhSach(database,mContex,baiHat);

    }
    public void deleteDanhSachYeuthich(){
        ConnectDatabase conn = new ConnectDatabase(NAME_DATABASE,PLACE_SAVE,mContex);
        conn.readDataBase();
        database = mContex.openOrCreateDatabase(NAME_DATABASE,mContex.MODE_PRIVATE,null);
        baiHat.DeleteDanhSach(database,mContex,baiHat);

    }
}
