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
import com.example.vmusic.interfaceClass.interfaceToanBo.InterfaceClickAlbumToanBo;
import com.example.vmusic.model.Album;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAlbumToanBo extends RecyclerView.Adapter<AdapterAlbumToanBo.AlbumToanBoViewHolder>{

    private List<Album> mList;
    private Context mContext;
    private InterfaceClickAlbumToanBo clickAlbumToanBo;

    public AdapterAlbumToanBo(List<Album> mList, Context mContext, InterfaceClickAlbumToanBo clickAlbumToanBo) {
        this.mList = mList;
        this.mContext = mContext;
        this.clickAlbumToanBo = clickAlbumToanBo;
    }

    @NonNull
    @Override
    public AlbumToanBoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_album_toan_bo,parent,false);
        return new AlbumToanBoViewHolder(view);
    }



    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class AlbumToanBoViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinhAlbumToanBo;
        TextView txtTenAlbumToanBo,txtTenCaSiAlbumToanBo;
        LinearLayout layoutAlbumToanBo;
        public AlbumToanBoViewHolder(@NonNull View view) {
            super(view);
            imgHinhAlbumToanBo = view.findViewById(R.id.img_hinh_album_toan_bo);
            txtTenAlbumToanBo = view.findViewById(R.id.txt_ten_album_toan_bo);
            txtTenCaSiAlbumToanBo = view.findViewById(R.id.txt_ten_ca_si_album_toan_bo);
            layoutAlbumToanBo = view.findViewById(R.id.layout_album_toan_bo);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumToanBoViewHolder holder, int position) {
        Album album = mList.get(position);
        if(album==null){
            return;
        }
        holder.txtTenAlbumToanBo.setText(album.getTenAlbum());
        holder.txtTenCaSiAlbumToanBo.setText(album.getTenCaSiAlbum());
        Picasso.with(mContext).load(album.getHinhAlbum()).into(holder.imgHinhAlbumToanBo);
        holder.layoutAlbumToanBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickAlbumToanBo.clickAlbumToanBo(album);
            }
        });
    }

}
