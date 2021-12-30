package com.example.vmusic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vmusic.R;
import com.example.vmusic.interfaceClass.InterfaceClickTheLoai;
import com.example.vmusic.model.TheLoai;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterTheLoai extends RecyclerView.Adapter<AdapterTheLoai.TheLoaiViewHolder>{
    private List<TheLoai> mList;
    private Context mContext;
    private InterfaceClickTheLoai minterfaceClickTheLoai;

    public AdapterTheLoai(List<TheLoai> mList, Context mContext, InterfaceClickTheLoai minterfaceClickTheLoai) {
        this.mList = mList;
        this.mContext = mContext;
        this.minterfaceClickTheLoai = minterfaceClickTheLoai;
    }

    @NonNull
    @Override
    public TheLoaiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_the_loai,parent,false);
        return new TheLoaiViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class TheLoaiViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenTheLoai;
        ImageView imgHinhTheLoai;
        RelativeLayout layoutTheLoai;
        public TheLoaiViewHolder(@NonNull View view) {
            super(view);
            txtTenTheLoai = view.findViewById(R.id.txt_ten_the_loai);
            imgHinhTheLoai = view.findViewById(R.id.img_hinh_the_loai);
            layoutTheLoai = view.findViewById(R.id.layout_the_loai);
            // them anh

        }
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiViewHolder holder, int position) {
        TheLoai theLoai = mList.get(position);
        if(theLoai==null){
            return;
        }
        holder.txtTenTheLoai.setText(theLoai.getTenTheLoai());
        holder.layoutTheLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minterfaceClickTheLoai.clickTheLoai(theLoai);
            }
        });
        Picasso.with(mContext).load(theLoai.getHinhTheLoai()).into(holder.imgHinhTheLoai);
    }
}
