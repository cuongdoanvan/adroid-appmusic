package com.example.vmusic.fragment.fragmentToanBo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.vmusic.R;
import com.example.vmusic.adapter.adapterToanBo.AdapterPlaylistToanBo;
import com.example.vmusic.controller.GetAPI;
import com.example.vmusic.fragment.fragmentBaiHat.FragmentDanhSachBaiHat;
import com.example.vmusic.interfaceClass.InterfaceApi;
import com.example.vmusic.interfaceClass.interfaceToanBo.InterfaceClickPlaylistToanBo;
import com.example.vmusic.model.Playlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentPlaylistToanBo extends Fragment {
    RecyclerView rcvPlaylistToanBo;
    List<Playlist> mListPlaylistToanBo;
    AdapterPlaylistToanBo adapterPlaylistToanBo;
    SwipeRefreshLayout swipePlaylist;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_playlist_toan_bo,container,false);
        rcvPlaylistToanBo = view.findViewById(R.id.rcv_playlist_toan_bo);
        swipePlaylist = view.findViewById(R.id.swipe_playlist);
        swipePlaylist.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPlaylistToanBo();
                swipePlaylist.setRefreshing(false);
            }
        });
        getPlaylistToanBo();
        return view;
    }

    private void getPlaylistToanBo() {
        mListPlaylistToanBo = new ArrayList<>();
        adapterPlaylistToanBo = new AdapterPlaylistToanBo(mListPlaylistToanBo, getActivity(), new InterfaceClickPlaylistToanBo() {
            @Override
            public void clickPlaylistToanBo(Playlist playlist) {
                selectPlaylistToanBo(playlist);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rcvPlaylistToanBo.setLayoutManager(gridLayoutManager);
        rcvPlaylistToanBo.setAdapter(adapterPlaylistToanBo);
        GetAPI getAPI = new GetAPI(getActivity(),"https://ninhio.000webhostapp.com/server/apiPlaylistToanBo.php");
        getAPI.getJson();
        getAPI.callBackGetData(new InterfaceApi() {
            @Override
            public void onRecieve(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Playlist playlist = new Playlist();
                        playlist.callJsonPlaylist(jsonObject);
                        mListPlaylistToanBo.add(playlist);
                        adapterPlaylistToanBo.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void selectPlaylistToanBo(Playlist playlist) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentDanhSachBaiHat.class,null)
                .addToBackStack("three")
                .commit();
        Bundle bundle = new Bundle();
        bundle.putString("sendText",playlist.getTenPlayList());
        bundle.putString("type","idPlaylist");
        bundle.putInt("id",playlist.getIdPlayList());
        bundle.putString("image",playlist.getHinhChinh());
        getParentFragmentManager().setFragmentResult("requestKey",bundle);
    }

}
