package com.example.vmusic.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.vmusic.model.BaiHat;

import java.util.ArrayList;
import java.util.List;

public class SelectDanhSachYeuThich {
    String NAME_DATABASE = "vmusic.db";
    String PLACE_SAVE = "/databases/";
    SQLiteDatabase database = null;
    Context mContex;

    public SelectDanhSachYeuThich(Context mContex) {
        this.mContex = mContex;
    }

    public List<String> showDanhSachYeuThich() {
        ConnectDatabase conn = new ConnectDatabase(NAME_DATABASE,PLACE_SAVE,mContex);
        conn.readDataBase();
        database = mContex.openOrCreateDatabase(NAME_DATABASE,mContex.MODE_PRIVATE,null);
        BaiHat baiHat = new BaiHat();
        List<String> mList = baiHat.SelectDanhSach(database,mContex);
        return mList;
    }
}
