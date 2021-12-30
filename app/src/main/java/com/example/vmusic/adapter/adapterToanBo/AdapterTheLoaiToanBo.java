package com.example.vmusic.adapter.adapterToanBo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmusic.R;
import com.example.vmusic.interfaceClass.interfaceToanBo.InterfaceClickTheLoaiToanBo;
import com.example.vmusic.model.TheLoai;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterTheLoaiToanBo extends RecyclerView.Adapter<AdapterTheLoaiToanBo.TheLoaiToanBoViewHolder>{

    private List<TheLoai> mList;
    private Context mContext;
    private InterfaceClickTheLoaiToanBo clickTheLoaiToanBo;

    public AdapterTheLoaiToanBo(List<TheLoai> mList, Context mContext, InterfaceClickTheLoaiToanBo clickTheLoaiToanBo) {
        this.mList = mList;
        this.mContext = mContext;
        this.clickTheLoaiToanBo = clickTheLoaiToanBo;
    }

    @NonNull
    @Override
    public TheLoaiToanBoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_the_loai_toan_bo,parent,false);
        return new TheLoaiToanBoViewHolder(view);
    }



    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class TheLoaiToanBoViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenTheLoaiToanBo;
        ImageView imgHinhTheLoaiToanBo;
        LinearLayout layoutTheLoaiToanBo;
        public TheLoaiToanBoViewHolder(@NonNull View view) {
            super(view);
            txtTenTheLoaiToanBo = view.findViewById(R.id.txt_ten_the_loai_toan_bo);
            imgHinhTheLoaiToanBo = view.findViewById(R.id.img_hinh_the_loai_toan_bo);
            layoutTheLoaiToanBo = view.findViewById(R.id.layout_the_loai_toan_bo);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull TheLoaiToanBoViewHolder holder, int position) {
        TheLoai theLoai = mList.get(position);
        if (theLoai==null){
            return;
        }
        holder.txtTenTheLoaiToanBo.setText(theLoai.getTenTheLoai());
        Picasso.with(mContext).load(theLoai.getHinhTheLoai()).into(holder.imgHinhTheLoaiToanBo);
        holder.layoutTheLoaiToanBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickTheLoaiToanBo.clickTheLoaiToanBo(theLoai);
            }
        });
    }
}
