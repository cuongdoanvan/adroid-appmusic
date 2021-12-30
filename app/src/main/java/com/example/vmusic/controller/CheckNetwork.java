package com.example.vmusic.controller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.vmusic.interfaceClass.InterfaceCheckingConnection;

public class CheckNetwork extends BroadcastReceiver {
    InterfaceCheckingConnection connection;

    public CheckNetwork(InterfaceCheckingConnection connection) {
        this.connection = connection;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            boolean noConnect = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
            connection.checkNetword(noConnect);
        }
    }
}
