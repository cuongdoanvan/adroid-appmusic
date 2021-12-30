package com.example.vmusic.adapter.adapterBaiHat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmusic.R;
import com.example.vmusic.database.SelectDanhSachYeuThich;
import com.example.vmusic.database.UpdateDanhSachYeuThich;
import com.example.vmusic.interfaceClass.interfaceBaiHat.InterfaceClickDanhSachBaiHat;
import com.example.vmusic.model.BaiHat;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterDanhSachBaiHat extends RecyclerView.Adapter<AdapterDanhSachBaiHat.DanhSachBaiHatViewHolder>{

    private Context mContext;
    private List<BaiHat> mList;
    private InterfaceClickDanhSachBaiHat clickDanhSachBaiHat;

    public AdapterDanhSachBaiHat(Context mContext, List<BaiHat> mList, InterfaceClickDanhSachBaiHat clickDanhSachBaiHat) {
        this.mContext = mContext;
        this.mList = mList;
        this.clickDanhSachBaiHat = clickDanhSachBaiHat;
    }

    @NonNull
    @Override
    public DanhSachBaiHatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_danh_sach_bai_hat,parent,false);
        return new DanhSachBaiHatViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class DanhSachBaiHatViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenBaiHatDanhSach, txtTenCaSiDanhSach;
        ImageView imgHinhBaiHatDanhSach, imgYeuThichDanhSach;
        ConstraintLayout layoutDanhSachBaiHat;
        public DanhSachBaiHatViewHolder(@NonNull View view) {
            super(view);
            txtTenBaiHatDanhSach = view.findViewById(R.id.txt_ten_bai_hat_danh_sach);
            txtTenCaSiDanhSach = view.findViewById(R.id.txt_ten_ca_si_danh_sach);
            imgHinhBaiHatDanhSach = view.findViewById(R.id.img_hinh_bai_hat_danh_sach);
            imgYeuThichDanhSach = view.findViewById(R.id.img_yeu_thich_danh_sach);
            layoutDanhSachBaiHat = view.findViewById(R.id.layout_danh_sach_bai_hat);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachBaiHatViewHolder holder, int position) {
        BaiHat baiHat = mList.get(position);
        int index = position;
        if(baiHat==null){
            return;
        }
        holder.txtTenBaiHatDanhSach.setText(baiHat.getTenBaiHat());
        holder.txtTenCaSiDanhSach.setText(baiHat.getCaSi());

        Picasso.with(mContext).load(baiHat.getHinhBaiHat()).into(holder.imgHinhBaiHatDanhSach);

        holder.layoutDanhSachBaiHat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDanhSachBaiHat.clickDanhSachBaiHat(baiHat);
            }
        });
        SelectDanhSachYeuThich danhSachYeuThich = new SelectDanhSachYeuThich(mContext);
        List<String> mListYeuThich = danhSachYeuThich.showDanhSachYeuThich();
        for(int i=0;i<mListYeuThich.size();i++){
            for(int j=0;j<mList.size();j++){
                BaiHat mYeuThich = mList.get(j);
                String idBaiHatYeuThich = String.valueOf(mYeuThich.getIdBaiHat());
                if(mListYeuThich.get(i).equals(idBaiHatYeuThich)){
                    holder.imgYeuThichDanhSach.setImageResource(R.drawable.love);
                    holder.imgYeuThichDanhSach.setTag("love");
                }else {
                    holder.imgYeuThichDanhSach.setImageResource(R.drawable.hate);
                    holder.imgYeuThichDanhSach.setTag("hate");
                }
            }
        }
        holder.imgYeuThichDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickYeuThich(holder,index);
            }
        });
    }
    private void clickYeuThich(DanhSachBaiHatViewHolder holder, int index) {
        BaiHat baiHat =mList.get(index);
        UpdateDanhSachYeuThich update = new UpdateDanhSachYeuThich(mContext,baiHat);
        String checkYeuThich = (String) holder.imgYeuThichDanhSach.getTag();
        if(checkYeuThich.equals("love")){
            holder.imgYeuThichDanhSach.setImageResource(R.drawable.hate);
            holder.imgYeuThichDanhSach.setTag("hate");
            update.deleteDanhSachYeuthich();
        }else if(checkYeuThich.equals("hate")) {
            holder.imgYeuThichDanhSach.setImageResource(R.drawable.love);
            holder.imgYeuThichDanhSach.setTag("love");
            update.insertDanhSachYeuThich();
        }
    }
}
