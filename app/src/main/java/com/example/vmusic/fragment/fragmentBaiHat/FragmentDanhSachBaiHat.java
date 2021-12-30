package com.example.vmusic.fragment.fragmentBaiHat;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmusic.R;
import com.example.vmusic.adapter.adapterBaiHat.AdapterDanhSachBaiHat;
import com.example.vmusic.controller.PostDataReceiveAPI;
import com.example.vmusic.database.SelectDanhSachYeuThich;
import com.example.vmusic.interfaceClass.InterfaceApi;
import com.example.vmusic.interfaceClass.interfaceBaiHat.InterfaceClickDanhSachBaiHat;
import com.example.vmusic.model.Album;
import com.example.vmusic.model.BaiHat;
import com.example.vmusic.model.Playlist;
import com.example.vmusic.model.TheLoai;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentDanhSachBaiHat extends Fragment {
    private TextView txtTenDanhSach;
    private ImageView imgHinhDanhSach;
    private RecyclerView rcvDanhSach;
    private AdapterDanhSachBaiHat adapterDanhSachBaiHat;
    List<BaiHat> mList;
    String result,type,image;
    int id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_danh_sach_bai_hat,container,false);
        txtTenDanhSach = view.findViewById(R.id.txt_ten_danh_sach);
        imgHinhDanhSach =view.findViewById(R.id.img_hinh_danh_sach);
        rcvDanhSach = view.findViewById(R.id.rcv_danh_sach);
        getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {

                result = bundle.getString("sendText");
                type= bundle.getString("type");
                id = bundle.getInt("id");
                image = bundle.getString("image");
                Picasso.with(getActivity()).load(image).into(imgHinhDanhSach);
                txtTenDanhSach.setText(result);
                getData(type,id);
            }
        });
        return view;
    }


    private void getData(String type, int id) {
        String[] api = new String[10];
        api[0]="https://ninhio.000webhostapp.com/server/apiPlaylistBaiHat.php";
        api[1]="https://ninhio.000webhostapp.com/server/apiAlbumBaiHat.php";
        api[2]="https://ninhio.000webhostapp.com/server/apiTheLoaiBaiHat.php";
        String url ="";
        switch (type){
            case "idPlaylist":
                url = api[0];
                break;
            case "idAlbum":
                url =api[1];
                break;
            case "idTheLoai":
                url = api[2];
                break;
        }
        String dataSend = String.valueOf(id);
        String keySend = type;
        mList = new ArrayList<>();
        adapterDanhSachBaiHat = new AdapterDanhSachBaiHat(getActivity(), mList,new InterfaceClickDanhSachBaiHat() {
            @Override
            public void clickDanhSachBaiHat(BaiHat baiHat) {
                handleClickDanhSachBaiHat(baiHat);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rcvDanhSach.setLayoutManager(linearLayoutManager);
        rcvDanhSach.setAdapter(adapterDanhSachBaiHat);
        PostDataReceiveAPI dataReceiveAPI = new PostDataReceiveAPI(url,getActivity(),dataSend,keySend);
        dataReceiveAPI.getJson();
        dataReceiveAPI.callBackGetData(new InterfaceApi() {
            @Override
            public void onRecieve(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        BaiHat baiHat = new BaiHat();
                        baiHat.callJsonBaiHat(jsonObject);
                        mList.add(baiHat);
                        adapterDanhSachBaiHat.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        adapterDanhSachBaiHat.notifyDataSetChanged();
    }

    private void handleClickDanhSachBaiHat(BaiHat baiHat) {
        String idBaiHat = String.valueOf(baiHat.getIdBaiHat());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentBaihat.class,null)
                .addToBackStack("fore")
                .commit();
        Bundle bundle = new Bundle();
        bundle.putString("sendid",idBaiHat);
        bundle.putString("sendText",result);
        bundle.putString("type",type);
        bundle.putInt("id",id);
        bundle.putString("image",image);
        getParentFragmentManager().setFragmentResult("reKey",bundle);
    }
}
