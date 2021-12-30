package com.example.vmusic.fragment.fragmentToanBo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.vmusic.R;
import com.example.vmusic.adapter.adapterToanBo.AdapterTheLoaiToanBo;
import com.example.vmusic.controller.GetAPI;
import com.example.vmusic.fragment.fragmentBaiHat.FragmentDanhSachBaiHat;
import com.example.vmusic.interfaceClass.InterfaceApi;
import com.example.vmusic.interfaceClass.interfaceToanBo.InterfaceClickTheLoaiToanBo;
import com.example.vmusic.model.Album;
import com.example.vmusic.model.TheLoai;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentTheLoaiToanBo extends Fragment {
    RecyclerView rcvTheLoaiToanBo;
    List<TheLoai> mListTheLoaiToanBo;
    AdapterTheLoaiToanBo adapterTheLoaiToanBo;
    SwipeRefreshLayout swipeTheLoai;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_the_loai_toan_bo,container,false);
        rcvTheLoaiToanBo = view.findViewById(R.id.rcv_the_loai_toan_bo);
        swipeTheLoai = view.findViewById(R.id.swipe_the_loai);
        swipeTheLoai.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTheLoaiToanBo();
                swipeTheLoai.setRefreshing(false);
            }
        });
        getTheLoaiToanBo();
        return view;
    }

    private void getTheLoaiToanBo() {
        mListTheLoaiToanBo = new ArrayList<>();
        adapterTheLoaiToanBo = new AdapterTheLoaiToanBo(mListTheLoaiToanBo, getActivity(), new InterfaceClickTheLoaiToanBo() {
            @Override
            public void clickTheLoaiToanBo(TheLoai theLoai) {
                selectTheLoaiToanBo(theLoai);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rcvTheLoaiToanBo.setLayoutManager(gridLayoutManager);
        rcvTheLoaiToanBo.setAdapter(adapterTheLoaiToanBo);

        GetAPI getAPI = new GetAPI(getActivity(),"https://ninhio.000webhostapp.com/server/apiTheLoaiToanBo.php");
        getAPI.getJson();
        getAPI.callBackGetData(new InterfaceApi() {
            @Override
            public void onRecieve(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        TheLoai theLoai = new TheLoai();
                        theLoai.callJsonTheLoai(jsonObject);
                        mListTheLoaiToanBo.add(theLoai);
                        adapterTheLoaiToanBo.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void selectTheLoaiToanBo(TheLoai theLoai) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentDanhSachBaiHat.class,null)
                .addToBackStack("three")
                .commit();
        Bundle bundle = new Bundle();
        bundle.putString("sendText",theLoai.getTenTheLoai());
        bundle.putString("type","idTheLoai");
        bundle.putInt("id",theLoai.getIdTheLoai());
        bundle.putString("image",theLoai.getHinhTheLoai());
        getParentFragmentManager().setFragmentResult("requestKey",bundle);
    }
}
