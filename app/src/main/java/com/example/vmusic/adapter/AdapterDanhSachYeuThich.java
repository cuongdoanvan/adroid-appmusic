package com.example.vmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmusic.R;
import com.example.vmusic.database.SelectDanhSachYeuThich;
import com.example.vmusic.database.UpdateDanhSachYeuThich;
import com.example.vmusic.interfaceClass.InterfaceClickDanhSachYeuThich;
import com.example.vmusic.model.BaiHat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterDanhSachYeuThich extends RecyclerView.Adapter<AdapterDanhSachYeuThich.DanhSachYeuThichViewHolder>{

    private Context mContext;
    private List<BaiHat> mList;
    private InterfaceClickDanhSachYeuThich clickDanhSachYeuThich;

    public AdapterDanhSachYeuThich(Context mContext, List<BaiHat> mList, InterfaceClickDanhSachYeuThich clickDanhSachYeuThich) {
        this.mContext = mContext;
        this.mList = mList;
        this.clickDanhSachYeuThich = clickDanhSachYeuThich;
    }

    @NonNull
    @Override
    public DanhSachYeuThichViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_danh_sach_yeu_thich,parent,false);
        return new DanhSachYeuThichViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class DanhSachYeuThichViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout layoutDanhSachYeuThich;
        ImageView imgHinhBaiHatYeuThich,imgChonYeuThich;
        TextView txtTenBaiHatYeuThich, txtTenCaSiYeuThich;

        public DanhSachYeuThichViewHolder(@NonNull View view) {
            super(view);
            layoutDanhSachYeuThich = view.findViewById(R.id.layout_danh_sach_yeu_thich);
            imgHinhBaiHatYeuThich = view.findViewById(R.id.img_hinh_bai_hat_yeu_thich);
            imgChonYeuThich = view.findViewById(R.id.img_chon_yeu_thich);
            txtTenBaiHatYeuThich = view.findViewById(R.id.txt_ten_bai_hat_yeu_thich);
            txtTenCaSiYeuThich = view.findViewById(R.id.txt_ten_ca_si_yeu_thich);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DanhSachYeuThichViewHolder holder, int position) {
        BaiHat baiHat = mList.get(position);
        int index = position;
        if(baiHat==null){
            return;
        }
        holder.txtTenBaiHatYeuThich.setText(baiHat.getTenBaiHat());
        holder.txtTenCaSiYeuThich.setText(baiHat.getCaSi());

        Picasso.with(mContext).load(baiHat.getHinhBaiHat()).into(holder.imgHinhBaiHatYeuThich);

        holder.layoutDanhSachYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDanhSachYeuThich.clickDanhSachyeuThich(baiHat);
            }
        });
        SelectDanhSachYeuThich danhSachYeuThich = new SelectDanhSachYeuThich(mContext);
        List<String> mListYeuThich = danhSachYeuThich.showDanhSachYeuThich();
        holder.imgChonYeuThich.setImageResource(R.drawable.love);
        holder.imgChonYeuThich.setTag("love");
        holder.imgChonYeuThich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickYeuThich(holder,index);
            }
        });
    }

    private void clickYeuThich(DanhSachYeuThichViewHolder holder, int index) {
        BaiHat baiHat =mList.get(index);
        UpdateDanhSachYeuThich update = new UpdateDanhSachYeuThich(mContext,baiHat);
        String checkYeuThich = (String) holder.imgChonYeuThich.getTag();
        if(checkYeuThich.equals("love")){
            holder.imgChonYeuThich.setImageResource(R.drawable.hate);
            holder.imgChonYeuThich.setTag("hate");
            update.deleteDanhSachYeuthich();
        }else if(checkYeuThich.equals("hate")) {
            holder.imgChonYeuThich.setImageResource(R.drawable.love);
            holder.imgChonYeuThich.setTag("love");
            update.insertDanhSachYeuThich();
        }
    }
}
