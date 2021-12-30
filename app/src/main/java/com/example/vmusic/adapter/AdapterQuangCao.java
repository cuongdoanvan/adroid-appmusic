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
import com.example.vmusic.interfaceClass.InterfaceClickQuangCao;
import com.example.vmusic.model.BaiHat;
import com.example.vmusic.model.QuangCao;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterQuangCao extends RecyclerView.Adapter<AdapterQuangCao.QuangCaoViewHolder> {

    private List<QuangCao> mList;
    private Context mContext;
    private InterfaceClickQuangCao clickQuangCao;

    public AdapterQuangCao(List<QuangCao> mList, Context mContext,InterfaceClickQuangCao mclickQuangCao) {
        this.mList = mList;
        this.mContext = mContext;
        notifyDataSetChanged();
        this.clickQuangCao = mclickQuangCao;
    }

    @NonNull
    @Override
    public QuangCaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quang_cao,parent,false);
        return new QuangCaoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuangCaoViewHolder holder, int position) {
        QuangCao quangCao = mList.get(position);
        if(quangCao==null){
            return;
        }
        BaiHat baiHat = quangCao.getIdBaiHat();
        holder.txtQuangCaoTenBaiHat.setText(baiHat.getTenBaiHat());
        holder.txtNoiDungQuangCao.setText(quangCao.getNoiDungQuangCao());
        Picasso.with(mContext).load(quangCao.getHinhQuangCaoLon()).into(holder.imgQuangCaoLon);
        Picasso.with(mContext).load(quangCao.getHinhQuangCaoNho()).into(holder.imgQuangCaoNho);
        holder.layoutQuangCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickQuangCao.clickQuangCao(quangCao);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class QuangCaoViewHolder extends RecyclerView.ViewHolder{

        TextView txtQuangCaoTenBaiHat, txtNoiDungQuangCao;
        ImageView imgQuangCaoLon, imgQuangCaoNho;
        ConstraintLayout layoutQuangCao;
        public QuangCaoViewHolder(@NonNull View view) {
            super(view);
            txtQuangCaoTenBaiHat = view.findViewById(R.id.txt_quang_cao_ten_bai_hat);
            txtNoiDungQuangCao = view.findViewById(R.id.txt_noi_dung_quang_cao);
            imgQuangCaoLon = view.findViewById(R.id.img_quang_cao_lon);
            imgQuangCaoNho = view.findViewById(R.id.img_quang_cao_nho);
            layoutQuangCao = view.findViewById(R.id.layout_quang_cao);
        }
    }
}
