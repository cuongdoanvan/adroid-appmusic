package com.example.vmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.vmusic.R;
import com.example.vmusic.adapter.AdapterDanhSachYeuThich;
import com.example.vmusic.adapter.AdapterQuangCao;
import com.example.vmusic.controller.PostDataReceiveAPI;
import com.example.vmusic.database.SelectDanhSachYeuThich;
import com.example.vmusic.fragment.fragmentBaiHat.FragmentBaihat;
import com.example.vmusic.fragment.fragmentBaiHat.FragmentDanhSachBaiHat;
import com.example.vmusic.interfaceClass.InterfaceApi;
import com.example.vmusic.interfaceClass.InterfaceClickDanhSachYeuThich;
import com.example.vmusic.model.Album;
import com.example.vmusic.model.BaiHat;
import com.example.vmusic.model.Playlist;
import com.example.vmusic.model.QuangCao;
import com.example.vmusic.model.TheLoai;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentDanhSachYeuThich extends Fragment {
    RecyclerView rcvDanhSachYeuThich;
    List<BaiHat> mListDanhSachYeuThich;
    AdapterDanhSachYeuThich adapterDanhSachYeuThich;
    List<String> mListDataBase;
    SwipeRefreshLayout swipeDanhSachYeuThich;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_danh_sach_yeu_thich,container,false);
        swipeDanhSachYeuThich  = view.findViewById(R.id.swipe_bai_hat_yeu_thich);
        rcvDanhSachYeuThich = view.findViewById(R.id.rcv_danh_sach_yeu_thich);
        swipeDanhSachYeuThich.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDataDanhSachYeuThich();
                swipeDanhSachYeuThich.setRefreshing(false);
            }
        });
        getDataDanhSachYeuThich();
        return view;
    }

    private void getDataDanhSachYeuThich() {
        mListDanhSachYeuThich = new ArrayList<>();
        adapterDanhSachYeuThich = new AdapterDanhSachYeuThich(getActivity(), mListDanhSachYeuThich, new InterfaceClickDanhSachYeuThich() {
            @Override
            public void clickDanhSachyeuThich(BaiHat baiHat) {
                clickDanhSach(baiHat);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcvDanhSachYeuThich.addItemDecoration(itemDecoration);
        rcvDanhSachYeuThich.setLayoutManager(linearLayoutManager);
        rcvDanhSachYeuThich.setAdapter(adapterDanhSachYeuThich);
        mListDataBase = new ArrayList<>();
        showDatabaseToListView();
        String url = "https://ninhio.000webhostapp.com/server/apiChiTietBaiHat.php";
        String keySend ="idBaiHat";
        for(int i=0;i<mListDataBase.size();i++){
            String dataSend = mListDataBase.get(i);
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
                            mListDanhSachYeuThich.add(baiHat);
                            adapterDanhSachYeuThich.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    private void showDatabaseToListView() {
        SelectDanhSachYeuThich danhSachYeuThich = new SelectDanhSachYeuThich(getActivity());
        mListDataBase = danhSachYeuThich.showDanhSachYeuThich();
    }

    private void clickDanhSach(BaiHat baiHat) {
        String idBaiHat = String.valueOf(baiHat.getIdBaiHat());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentBaihat.class,null)
                .addToBackStack("danhsachyeuthich")
                .commit();
        Bundle bundle = new Bundle();
        bundle.putString("sendid",idBaiHat);
        getParentFragmentManager().setFragmentResult("reKey",bundle);
    }
}
