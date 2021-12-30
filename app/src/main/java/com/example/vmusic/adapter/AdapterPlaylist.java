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
import com.example.vmusic.interfaceClass.InterfaceClickPlaylist;
import com.example.vmusic.interfaceClass.InterfaceClickQuangCao;
import com.example.vmusic.model.Playlist;
import com.example.vmusic.model.QuangCao;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.PlaylistViewHolder>{

    private List<Playlist> mList;
    private Context mContext;
    private InterfaceClickPlaylist interfaceClickPlaylist;

    public AdapterPlaylist(List<Playlist> mList, Context mContext,InterfaceClickPlaylist mInterfaceClickPlaylist) {
        this.mList = mList;
        this.mContext = mContext;
        this.interfaceClickPlaylist = mInterfaceClickPlaylist;
    }

    @NonNull
    @Override
    public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_playlist,parent,false);
        return new PlaylistViewHolder(view);
    }


    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class PlaylistViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenPlaylist;
        ImageView imgHinhNen;
        ConstraintLayout layoutPlaylist;
        public PlaylistViewHolder(@NonNull View view) {
            super(view);
            txtTenPlaylist = view.findViewById(R.id.txt_ten_playlist);
            imgHinhNen = view.findViewById(R.id.img_hinh_nen);
            layoutPlaylist = view.findViewById(R.id.layout_playlit);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
        Playlist playlist = mList.get(position);
        if(playlist==null){
            return;
        }
        holder.txtTenPlaylist.setText(playlist.getTenPlayList());
        Picasso.with(mContext).load(playlist.getHinhNen()).into(holder.imgHinhNen);
        holder.layoutPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickPlaylist.clickPlaylist(playlist);
            }
        });

    }
}
