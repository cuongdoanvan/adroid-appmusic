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
import com.example.vmusic.adapter.adapterBaiHat.AdapterDanhSachBaiHat;
import com.example.vmusic.database.SelectDanhSachYeuThich;
import com.example.vmusic.database.UpdateDanhSachYeuThich;
import com.example.vmusic.interfaceClass.interfaceBaiHat.InterfaceClickDanhSachBaiHat;
import com.example.vmusic.model.BaiHat;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterTimKiem extends RecyclerView.Adapter<AdapterTimKiem.TimKiemViewHolder>{
    private Context mContext;
    private List<BaiHat> mList;
    private InterfaceClickDanhSachBaiHat clickDanhSachBaiHat;

    public AdapterTimKiem(Context mContext, List<BaiHat> mList, InterfaceClickDanhSachBaiHat clickDanhSachBaiHat) {
        this.mContext = mContext;
        this.mList = mList;
        this.clickDanhSachBaiHat = clickDanhSachBaiHat;
    }
    @NonNull
    @Override
    public TimKiemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_tim_kiem,parent,false);
        return new TimKiemViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class TimKiemViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenBaiHatTimKiem, txtTenCaSiTimKiem;
        ImageView imgHinhBaiHatTimKiem, imgYeuThichTimKiem;
        ConstraintLayout layoutTimKiemBaiHat;
        public TimKiemViewHolder(@NonNull View view) {
            super(view);
            txtTenBaiHatTimKiem = view.findViewById(R.id.txt_ten_bai_hat_tim_kiem);
            txtTenCaSiTimKiem = view.findViewById(R.id.txt_ten_ca_si_tim_kiem);
            imgHinhBaiHatTimKiem = view.findViewById(R.id.img_hinh_bai_hat_tim_kiem);
            imgYeuThichTimKiem = view.findViewById(R.id.img_yeu_thich_tim_kiem);
            layoutTimKiemBaiHat = view.findViewById(R.id.layout_tim_kiem_bai_hat);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull TimKiemViewHolder holder, int position) {
        BaiHat baiHat = mList.get(position);
        int index = position;
        if(baiHat==null){
            return;
        }
        holder.txtTenBaiHatTimKiem.setText(baiHat.getTenBaiHat());
        holder.txtTenCaSiTimKiem.setText(baiHat.getCaSi());
        Picasso.with(mContext).load(baiHat.getHinhBaiHat()).into(holder.imgHinhBaiHatTimKiem);
        holder.layoutTimKiemBaiHat.setOnClickListener(new View.OnClickListener() {
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
                    holder.imgYeuThichTimKiem.setImageResource(R.drawable.love);
                    holder.imgYeuThichTimKiem.setTag("love");
                }else {
                    holder.imgYeuThichTimKiem.setImageResource(R.drawable.hate);
                    holder.imgYeuThichTimKiem.setTag("hate");
                }
            }
        }
        holder.imgYeuThichTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickYeuThich(holder,index);
            }
        });
    }
    private void clickYeuThich(TimKiemViewHolder holder, int index) {
        BaiHat baiHat =mList.get(index);
        UpdateDanhSachYeuThich update = new UpdateDanhSachYeuThich(mContext,baiHat);
        String checkYeuThich = (String) holder.imgYeuThichTimKiem.getTag();
        if(checkYeuThich.equals("love")){
            holder.imgYeuThichTimKiem.setImageResource(R.drawable.hate);
            holder.imgYeuThichTimKiem.setTag("hate");
            update.deleteDanhSachYeuthich();
        }else if(checkYeuThich.equals("hate")) {
            holder.imgYeuThichTimKiem.setImageResource(R.drawable.love);
            holder.imgYeuThichTimKiem.setTag("love");
            update.insertDanhSachYeuThich();
        }
    }
}
