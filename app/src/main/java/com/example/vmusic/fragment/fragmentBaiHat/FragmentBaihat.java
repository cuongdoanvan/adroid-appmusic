package com.example.vmusic.fragment.fragmentBaiHat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.vmusic.R;
import com.example.vmusic.controller.PostDataReceiveAPI;
import com.example.vmusic.database.SelectDanhSachYeuThich;
import com.example.vmusic.database.UpdateDanhSachYeuThich;
import com.example.vmusic.interfaceClass.InterfaceApi;
import com.example.vmusic.model.BaiHat;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class FragmentBaihat extends Fragment {
    TextView txtTenBaiHatBaiHat,txtTenCaSiBaiHat,txtYeuThich,txtStart,txtEnd;
    ImageView imgDics,imgYeuThich, imgPrevious,imgNext,imgState;
    SeekBar seekBar;
    Animation anim;
    String checkYeuThich;
    List<Integer> mList;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    List<String> mDataBaseIdBaiHat;
    SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
    String result, type,image;
    String url;
    BaiHat mBaiHat;
    int id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bai_hat,container,false);

        txtTenBaiHatBaiHat = view.findViewById(R.id.txt_ten_bai_hat_bai_hat);
        txtTenCaSiBaiHat = view.findViewById(R.id.txt_ten_ca_si_bai_hat);
        imgDics = view.findViewById(R.id.img_disc);
        imgYeuThich = view.findViewById(R.id.img_yeu_thich);
        txtYeuThich = view.findViewById(R.id.txt_yeu_thich);
        txtStart = view.findViewById(R.id.txt_start);
        txtEnd = view.findViewById(R.id.txt_end);
        seekBar = view.findViewById(R.id.seekbar);
        imgPrevious = view.findViewById(R.id.img_previous);
        imgState = view.findViewById(R.id.img_state);
        imgNext = view.findViewById(R.id.img_next);
        anim = AnimationUtils.loadAnimation(getActivity(),R.anim.xoay_dia);
        seekBar = view.findViewById(R.id.seekbar);
        seekBar.setMax(100);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.stop();
        mList = new ArrayList<>();
        mDataBaseIdBaiHat = new ArrayList<>();

        getParentFragmentManager().setFragmentResultListener("reKey", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle bundle) {
                String idBaiHat = bundle.getString("sendid");
                result = bundle.getString("sendText");
                type= bundle.getString("type");
                id = bundle.getInt("id");
                image = bundle.getString("image");
                url = "https://ninhio.000webhostapp.com/server/apiChiTietBaiHat.php";
                getData(idBaiHat,url);
            }
        });
        addEvents();
        return view;
    }




    private void getData(String idBaiHat,String url) {
        String dataSend = idBaiHat;
        String keySend  = "idBaiHat";
        PostDataReceiveAPI postDataReceiveAPI = new PostDataReceiveAPI(url,getActivity(),dataSend,keySend);
        postDataReceiveAPI.getJson();

        SelectDanhSachYeuThich selectDanhSachYeuThich = new SelectDanhSachYeuThich(getActivity());
        mDataBaseIdBaiHat = selectDanhSachYeuThich.showDanhSachYeuThich();
        imgYeuThich.setImageResource(R.drawable.hate);
        imgYeuThich.setTag("hate");
        txtYeuThich.setText("Thêm vào danh sách yêu thích");
        postDataReceiveAPI.callBackGetData(new InterfaceApi() {
            @Override
            public void onRecieve(String data) {
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mBaiHat = new BaiHat();
                        mBaiHat.callJsonBaiHat(jsonObject);
                    }
                    txtTenBaiHatBaiHat.setText(mBaiHat.getTenBaiHat());
                    txtTenCaSiBaiHat.setText(mBaiHat.getCaSi());
                    String idBaiHat = String.valueOf(mBaiHat.getIdBaiHat());
                    for(int i=0;i<mDataBaseIdBaiHat.size();i++){
                        if(mDataBaseIdBaiHat.get(i).equals(idBaiHat)){
                            imgYeuThich.setImageResource(R.drawable.love);
                            imgYeuThich.setTag("love");
                            txtYeuThich.setText("Đã thêm vào danh sách yêu thích");
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void addEvents() {
        imgYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickYeuThich();
            }
        });
        imgState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickState();
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickNext();
            }
        });
        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPrevious();
            }
        });
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                SeekBar mseekBar = (SeekBar) v;
                int playPosition = (mediaPlayer.getDuration()/100)*mseekBar.getProgress();
                mediaPlayer.seekTo(playPosition);
                txtStart.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                return false;
            }
        });
        mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                // loaad xem trong seekbar den duoc den dau roi
                seekBar.setSecondaryProgress(percent);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                seekBar.setProgress(0);
                imgState.setImageResource(R.drawable.ic_pause);
                mediaPlayer.reset();
                prepareMediaPlayer();
            }
        });
    }


    private void clickYeuThich() {
        UpdateDanhSachYeuThich updateDanhSachYeuThich = new UpdateDanhSachYeuThich(getActivity(),mBaiHat);
        checkYeuThich = String.valueOf(imgYeuThich.getTag());
        if(checkYeuThich.equals("love")){
            imgYeuThich.setImageResource(R.drawable.hate);
            imgYeuThich.setTag("hate");
            txtYeuThich.setText("Thêm vào danh sách yêu thích");
            updateDanhSachYeuThich.deleteDanhSachYeuthich();
        }else {
            imgYeuThich.setImageResource(R.drawable.love);
            imgYeuThich.setTag("love");
            txtYeuThich.setText("Đã thêm vào danh sách yêu thích");
            updateDanhSachYeuThich.insertDanhSachYeuThich();
        }
    }
    private void clickNext() {
        mediaPlayer.stop();
        seekBar.setProgress(0);
        imgDics.clearAnimation();
        imgState.setImageResource(R.drawable.ic_pause);
        int idBaiHat =mBaiHat.getIdBaiHat();
        idBaiHat++;
        String idSend = String.valueOf(idBaiHat);
        url ="https://ninhio.000webhostapp.com/server/apiTiepTheo.php";
        txtStart.setText("00:00");
        getData(idSend,url);

    }
    private void clickPrevious() {
        mediaPlayer.stop();
        seekBar.setProgress(0);
        imgDics.clearAnimation();
        imgState.setImageResource(R.drawable.ic_pause);
        int idBaiHat =mBaiHat.getIdBaiHat();
        idBaiHat--;
        String idSend = String.valueOf(idBaiHat);
        url ="https://ninhio.000webhostapp.com/server/apiTiepTheo.php";
        txtStart.setText("00:00");
        getData(idSend,url);
    }

    private void clickState() {
        stateMusic();
        prepareMediaPlayer();
    }


    private void prepareMediaPlayer(){
        try {
            mediaPlayer.setDataSource(mBaiHat.getLinkBaiHat());
            mediaPlayer.prepare();
            txtEnd.setText(dinhDangGio.format(mediaPlayer.getDuration()));
            stateMusic();
        }
        catch (Exception ex){
        }
    }

    private void stateMusic(){
        if(mediaPlayer.isPlaying()){
            // xoa cac cai cap nhap cua bai truoc
            handler.removeCallbacks(update);
            mediaPlayer.pause();
            imgState.setImageResource(R.drawable.ic_pause);
            imgDics.clearAnimation();
        }else {
            mediaPlayer.start();
            imgState.setImageResource(R.drawable.ic_play);
            updateSeekbar();
            imgDics.startAnimation(anim);
        }
    }

    private Runnable update = new Runnable() {
        @Override
        public void run() {
            updateSeekbar();
            txtStart.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
        }
    };

    private void updateSeekbar(){
        if(mediaPlayer.isPlaying()){
            seekBar.setProgress((int)(((float)mediaPlayer.getCurrentPosition()/mediaPlayer.getDuration())*100));
            // update text
            handler.postDelayed(update,0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mediaPlayer.start();
        mediaPlayer.stop();
        Bundle bundle = new Bundle();
        bundle.putString("sendText",result);
        bundle.putString("type",type);
        bundle.putInt("id",id);
        bundle.putString("image",image);
        getParentFragmentManager().setFragmentResult("requestKey",bundle);
    }

}
