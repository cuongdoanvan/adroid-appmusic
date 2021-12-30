package com.example.vmusic.controller;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vmusic.interfaceClass.InterfaceApi;


import java.util.HashMap;
import java.util.Map;

public class PostDataReceiveAPI {
    private String url;
    private Context mContext;
    private InterfaceApi mVolleyListener;
    private String dataSend;
    private String keySend;

    public PostDataReceiveAPI(String url, Context mContext, String dataSend, String keySend) {
        this.url = url;
        this.mContext = mContext;
        this.dataSend = dataSend;
        this.keySend = keySend;
    }

    public PostDataReceiveAPI callBackGetData(InterfaceApi volleyListener){
        mVolleyListener = volleyListener;
        return this;
    }

    public PostDataReceiveAPI getJson(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mVolleyListener.onRecieve(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put(keySend,dataSend);
                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
        return this;
    }
}
