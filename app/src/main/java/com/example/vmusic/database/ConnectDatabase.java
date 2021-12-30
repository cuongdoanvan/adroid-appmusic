package com.example.vmusic.database;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectDatabase {
    String NAME_DATABASE;
    String PLACE_SAVE;
    Context mContex;

    public ConnectDatabase(String NAME_DATABASE, String PLACE_SAVE, Context mContex) {
        this.NAME_DATABASE = NAME_DATABASE;
        this.PLACE_SAVE = PLACE_SAVE;

        this.mContex = mContex;
    }

    public void readDataBase() {
        File dbFile = mContex.getDatabasePath(NAME_DATABASE);
        if(!dbFile.exists()){
            try {
                copyData();
            }catch (Exception ex){
                Log.e("loi",ex.toString());
            }
        }
    }

    public void copyData() {
        try {
            InputStream inputStream = mContex.getAssets().open(NAME_DATABASE);
            String pathName = pathDatabase();
            File f = new File(mContex.getApplicationInfo().dataDir + PLACE_SAVE);
            if(!f.exists()){
                f.mkdir();
            }
            OutputStream outputStream = new FileOutputStream(pathName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        }
        catch (Exception ex){
            Log.e("loi", ex.toString());
        }
    }

    private String pathDatabase(){
        return mContex.getApplicationInfo().dataDir + PLACE_SAVE + NAME_DATABASE;
    }
}
