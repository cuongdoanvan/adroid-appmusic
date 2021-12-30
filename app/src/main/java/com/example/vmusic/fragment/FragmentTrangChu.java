package com.example.vmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
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
import com.example.vmusic.adapter.AdapterAlbum;
import com.example.vmusic.adapter.AdapterPlaylist;
import com.example.vmusic.adapter.AdapterQuangCao;
import com.example.vmusic.adapter.AdapterTheLoai;
import com.example.vmusic.controller.GetAPI;
import com.example.vmusic.fragment.fragmentBaiHat.FragmentBaihat;
import com.example.vmusic.fragment.fragmentBaiHat.FragmentDanhSachBaiHat;
import com.example.vmusic.fragment.fragmentToanBo.FragmentAlbumToanBo;
import com.example.vmusic.fragment.fragmentToanBo.FragmentPlaylistToanBo;
import com.example.vmusic.fragment.fragmentToanBo.FragmentTheLoaiToanBo;
import com.example.vmusic.interfaceClass.InterfaceApi;
import com.example.vmusic.interfaceClass.InterfaceClickAlbum;
import com.example.vmusic.interfaceClass.InterfaceClickPlaylist;
import com.example.vmusic.interfaceClass.InterfaceClickQuangCao;
import com.example.vmusic.interfaceClass.InterfaceClickTheLoai;
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

public class FragmentTrangChu extends Fragment {
    RecyclerView rcvQuangCao;
    List<QuangCao> mListQuangCao;
    AdapterQuangCao adapterQuangCao;

    RecyclerView rcvPlaylist;
    List<Playlist> mListPlaylist;
    AdapterPlaylist adapterPlaylist;

    RecyclerView rcvAlbum;
    List<Album> mListAlbum;
    AdapterAlbum adapterAlbum;

    RecyclerView rcvTheLoai;
    List<TheLoai> mListTheLoai;
    AdapterTheLoai adapterTheLoai;

    ImageButton btnXemThemPlaylist;
    TextView txtXemThemTheLoai,txtXemThemAlbum;

    FragmentManager fragmentManager;
    SwipeRefreshLayout swipeTrangChu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_trang_chu,container,false);
        swipeTrangChu = view.findViewById(R.id.swipe_trang_chu);
        swipeTrangChu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getQuangCao();
                getPlayList();
                getAlbum();
                getTheLoai();
                swipeTrangChu.setRefreshing(false);
            }
        });
        rcvQuangCao = view.findViewById(R.id.rcv_quang_cao);
        getQuangCao();
        rcvPlaylist = view.findViewById(R.id.rcv_playlist);
        getPlayList();
        rcvAlbum = view.findViewById(R.id.rcv_album);
        getAlbum();
        rcvTheLoai = view.findViewById(R.id.rcv_the_loai);
        getTheLoai();

        btnXemThemPlaylist = view.findViewById(R.id.btn_xem_them_playlist);
        btnXemThemPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xemThemPlayList();
            }
        });

        txtXemThemAlbum = view.findViewById(R.id.txt_xem_them_album);
        txtXemThemAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xemThemAlbum();
            }
        });

        txtXemThemTheLoai = view.findViewById(R.id.txt_xem_them_the_loai);
        txtXemThemTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xemThemTheLoai();
            }
        });
        return view;
    }

    private void xemThemPlayList() {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main,FragmentPlaylistToanBo.class,null)
                .addToBackStack("two")
                .commit();
    }

    private void xemThemAlbum() {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentAlbumToanBo.class,null)
                .addToBackStack("two")
                .commit();
    }

    private void xemThemTheLoai() {
        fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentTheLoaiToanBo.class,null)
                .addToBackStack("two")
                .commit();
    }



    private void selectTheLoai(TheLoai theLoai) {
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

    private void selectAlbum(Album album) {
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

    private void selectPlaylist(Playlist playlist) {
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

    private void selectQuangCao(QuangCao quangCao) {
        BaiHat baiHat = quangCao.getIdBaiHat();
        String idBaiHat = String.valueOf(baiHat.getIdBaiHat());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentBaihat.class,null)
                .addToBackStack("fore")
                .commit();
        Bundle bundle = new Bundle();
        bundle.putString("sendid",idBaiHat);
        getParentFragmentManager().setFragmentResult("reKey",bundle);
    }



    private void getTheLoai() {
        mListTheLoai = new ArrayList<>();
        adapterTheLoai = new AdapterTheLoai(mListTheLoai, getActivity(), new InterfaceClickTheLoai() {
            @Override
            public void clickTheLoai(TheLoai theLoai) {
                selectTheLoai(theLoai);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        rcvTheLoai.setLayoutManager(linearLayoutManager);
        rcvTheLoai.setAdapter(adapterTheLoai);
        GetAPI getAPI = new GetAPI(getActivity(),"https://ninhio.000webhostapp.com/server/apiTheLoai.php");
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
                        mListTheLoai.add(theLoai);
                        adapterTheLoai.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getAlbum() {
        mListAlbum = new ArrayList<>();
        adapterAlbum = new AdapterAlbum(mListAlbum, getActivity(), new InterfaceClickAlbum() {
            @Override
            public void clickAlbum(Album album) {
                selectAlbum(album);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);
        rcvAlbum.setLayoutManager(linearLayoutManager);
        rcvAlbum.setAdapter(adapterAlbum);

        GetAPI getAPI = new GetAPI(getActivity(),"https://ninhio.000webhostapp.com/server/apiAlbum.php");
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
                        mListAlbum.add(album);
                        adapterAlbum.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getPlayList() {
        mListPlaylist = new ArrayList<>();
        adapterPlaylist = new AdapterPlaylist(mListPlaylist, getActivity(), new InterfaceClickPlaylist() {
            @Override
            public void clickPlaylist(Playlist playlist) {
                selectPlaylist(playlist);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        rcvPlaylist.addItemDecoration(itemDecoration);
        rcvPlaylist.setLayoutManager(linearLayoutManager);
        rcvPlaylist.setAdapter(adapterPlaylist);

        GetAPI getAPI = new GetAPI(getActivity(),"https://ninhio.000webhostapp.com/server/apiPlaylist.php");
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
                        mListPlaylist.add(playlist);
                        adapterPlaylist.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getQuangCao() {
        mListQuangCao = new ArrayList<>();
        BaiHat baiHat = new BaiHat();
        adapterQuangCao = new AdapterQuangCao(mListQuangCao, getActivity(), new InterfaceClickQuangCao() {
            @Override
            public void clickQuangCao(QuangCao quangCao) {
                selectQuangCao(quangCao);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false);

        rcvQuangCao.setLayoutManager(linearLayoutManager);
        rcvQuangCao.setAdapter(adapterQuangCao);


        GetAPI getAPI = new GetAPI(getActivity(),"https://ninhio.000webhostapp.com/server/apiQuangCao.php");
        getAPI.getJson();
        getAPI.callBackGetData(new InterfaceApi() {
            @Override
            public void onRecieve(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        QuangCao quangCao = new QuangCao();
                        quangCao.callJsonQuangCao(jsonObject);
                        mListQuangCao.add(quangCao);
                        adapterQuangCao.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
