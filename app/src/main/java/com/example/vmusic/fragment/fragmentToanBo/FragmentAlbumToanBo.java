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
import com.example.vmusic.adapter.adapterToanBo.AdapterAlbumToanBo;
import com.example.vmusic.controller.GetAPI;
import com.example.vmusic.fragment.fragmentBaiHat.FragmentDanhSachBaiHat;
import com.example.vmusic.interfaceClass.InterfaceApi;
import com.example.vmusic.interfaceClass.interfaceToanBo.InterfaceClickAlbumToanBo;
import com.example.vmusic.model.Album;
import com.example.vmusic.model.Playlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentAlbumToanBo extends Fragment {
    RecyclerView rcvAlbumToanBo;
    List<Album> mListAlbumToanBo;
    AdapterAlbumToanBo adapterAlbumToanBo;
    SwipeRefreshLayout swipeAlbum;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_album_toan_bo,container,false);
        rcvAlbumToanBo = view.findViewById(R.id.rcv_album_toan_bo);
        swipeAlbum = view.findViewById(R.id.swipe_album);
        swipeAlbum.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAlbumToanBo();
                swipeAlbum.setRefreshing(false);
            }
        });
        getAlbumToanBo();
        return view;
    }

    private void getAlbumToanBo() {
        mListAlbumToanBo = new ArrayList<>();
        adapterAlbumToanBo = new AdapterAlbumToanBo(mListAlbumToanBo, getActivity(), new InterfaceClickAlbumToanBo() {
            @Override
            public void clickAlbumToanBo(Album album) {
                selectAlbumToanBo(album);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rcvAlbumToanBo.setLayoutManager(gridLayoutManager);
        rcvAlbumToanBo.setAdapter(adapterAlbumToanBo);

        GetAPI getAPI = new GetAPI(getActivity(),"https://ninhio.000webhostapp.com/server/apiAlbumToanBo.php");
        getAPI.getJson();
        getAPI.callBackGetData(new InterfaceApi() {
            @Override
            public void onRecieve(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Album album = new Album();
                        album.callJsonAlbum(jsonObject);
                        mListAlbumToanBo.add(album);
                        adapterAlbumToanBo.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void selectAlbumToanBo(Album album) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentDanhSachBaiHat.class,null)
                .addToBackStack("three")
                .commit();
        Bundle bundle = new Bundle();
        bundle.putString("sendText",album.getTenAlbum());
        bundle.putString("type","idAlbum");
        bundle.putInt("id",album.getIdAlbum());
        bundle.putString("image",album.getHinhAlbum());
        getParentFragmentManager().setFragmentResult("requestKey",bundle);
    }
}
