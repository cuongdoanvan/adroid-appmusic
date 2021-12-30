package com.example.vmusic.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmusic.R;
import com.example.vmusic.adapter.AdapterTimKiem;
import com.example.vmusic.adapter.adapterBaiHat.AdapterDanhSachBaiHat;
import com.example.vmusic.controller.MainActivity;
import com.example.vmusic.controller.PostDataReceiveAPI;
import com.example.vmusic.fragment.fragmentBaiHat.FragmentBaihat;
import com.example.vmusic.interfaceClass.InterfaceApi;
import com.example.vmusic.interfaceClass.interfaceBaiHat.InterfaceClickDanhSachBaiHat;
import com.example.vmusic.model.BaiHat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentTimKiem extends Fragment {
    private RecyclerView rcvTimKiem;
    private AdapterTimKiem adapterTimKiem;
    List<BaiHat> mList;
    String result = "",type ="",image ="";
    int id = -1;
    MainActivity mainActivity;
    String dataSend;
    TextView txtKiemTraTimKiem;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_tim_kiem,container,false);
        rcvTimKiem = view.findViewById(R.id.rcv_tim_kiem);
        txtKiemTraTimKiem = view.findViewById(R.id.txt_kiem_tra_tim_kiem);
        mainActivity = (MainActivity) getActivity();
        dataSend = mainActivity.getDataSend();
        getData();
        return view;
    }

    private void getData() {
        mList = new ArrayList<>();
        adapterTimKiem = new AdapterTimKiem(getActivity(), mList, new InterfaceClickDanhSachBaiHat() {
            @Override
            public void clickDanhSachBaiHat(BaiHat baiHat) {
                clickBaiHat(baiHat);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false);
        rcvTimKiem.setLayoutManager(linearLayoutManager);
        rcvTimKiem.setAdapter(adapterTimKiem);
        String url ="https://ninhio.000webhostapp.com/server/apiTimKiemBaiHat.php";
        String keySend ="tenBaiHat";
        PostDataReceiveAPI dataReceiveAPI = new PostDataReceiveAPI(url,getActivity(),dataSend,keySend);
        dataReceiveAPI.getJson();
        dataReceiveAPI.callBackGetData(new InterfaceApi() {
            @Override
            public void onRecieve(String data) {
                if(data.equals("[]")){
                    txtKiemTraTimKiem.setVisibility(View.VISIBLE);
                }
                else {
                    txtKiemTraTimKiem.setVisibility(View.GONE);
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            BaiHat baiHat = new BaiHat();
                            baiHat.callJsonBaiHat(jsonObject);
                            mList.add(baiHat);
                            adapterTimKiem.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        adapterTimKiem.notifyDataSetChanged();
    }

    private void clickBaiHat(BaiHat baiHat) {
        String idBaiHat = String.valueOf(baiHat.getIdBaiHat());
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_main, FragmentBaihat.class,null)
                .addToBackStack("timkiem")
                .commit();
        Bundle bundle = new Bundle();
        bundle.putString("sendid",idBaiHat);
        bundle.putString("sendText",result);
        bundle.putString("type",type);
        bundle.putInt("id",id);
        bundle.putString("image",image);
        getParentFragmentManager().setFragmentResult("reKey",bundle);
        mainActivity.cloceNavigation();

        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_navigation, FramentNull.class,null)
                .commit();
    }
}
