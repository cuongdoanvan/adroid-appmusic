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
import com.example.vmusic.interfaceClass.interfaceToanBo.InterfaceClickPlaylistToanBo;
import com.example.vmusic.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPlaylistToanBo extends RecyclerView.Adapter<AdapterPlaylistToanBo.PlaylistToanBoViewHolder>{

    private List<Playlist> mList;
    private Context mContext;
    private InterfaceClickPlaylistToanBo mClickPlaylistToanBo;

    public AdapterPlaylistToanBo(List<Playlist> mList, Context mContext, InterfaceClickPlaylistToanBo mClickPlaylistToanBo) {
        this.mList = mList;
        this.mContext = mContext;
        this.mClickPlaylistToanBo = mClickPlaylistToanBo;
    }

    @NonNull
    @Override
    public PlaylistToanBoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_playlist_toan_bo,parent,false);
        return new PlaylistToanBoViewHolder(view);
    }



    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class PlaylistToanBoViewHolder extends RecyclerView.ViewHolder{
        ImageView imgHinhChinh;
        TextView txtTenPlaylistToanBo;
        LinearLayout layoutPlaylistToanBo;
        public PlaylistToanBoViewHolder(@NonNull View view) {
            super(view);
            imgHinhChinh = view.findViewById(R.id.img_hinh_chinh);
            txtTenPlaylistToanBo = view.findViewById(R.id.txt_ten_playlist_toan_bo);
            layoutPlaylistToanBo = view.findViewById(R.id.layout_playlist_toan_bo);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistToanBoViewHolder holder, int position) {
        Playlist playlist = mList.get(position);
        if(playlist==null){
            return;
        }
        holder.txtTenPlaylistToanBo.setText(playlist.getTenPlayList());
        Picasso.with(mContext).load(playlist.getHinhChinh()).into(holder.imgHinhChinh);
        holder.layoutPlaylistToanBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickPlaylistToanBo.clickPlaylistToanBo(playlist);
            }
        });
    }
}
