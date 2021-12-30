package com.example.vmusic.controller;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.vmusic.interfaceClass.InterfaceApi;

public class GetAPI {
    private String url;
    private Context mContext;
    private InterfaceApi mVolleyListener;

    public GetAPI(Context context, String murl){
        mContext = context;
        url = murl;
    }
    public GetAPI callBackGetData(InterfaceApi volleyListener){
        mVolleyListener = volleyListener;
        return this;
    }

    public GetAPI getJson(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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
                });
        RequestQueue queue = Volley.newRequestQueue(mContext);
        queue.add(stringRequest);
        return this;
    }
}
