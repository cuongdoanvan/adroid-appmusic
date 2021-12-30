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
import com.example.vmusic.interfaceClass.InterfaceClickAlbum;
import com.example.vmusic.model.Album;
import com.example.vmusic.model.Playlist;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterAlbum extends RecyclerView.Adapter<AdapterAlbum.AlbumViewHoler>{

    private List<Album> mList;
    private Context mContext;
    private InterfaceClickAlbum mInterfaceClickAlbum;

    public AdapterAlbum(List<Album> list, Context context, InterfaceClickAlbum interfaceClickAlbum) {
        this.mList = list;
        this.mContext = context;
        this.mInterfaceClickAlbum = interfaceClickAlbum;
    }

    @NonNull
    @Override
    public AlbumViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_album,parent,false);
        return new AlbumViewHoler(view);
    }

    @Override
    public int getItemCount() {
        if(mList!=null){
            return mList.size();
        }
        return 0;
    }

    public class AlbumViewHoler extends RecyclerView.ViewHolder{
        TextView txtTenAlbum;
        ImageView imgHinhAlbum;
        RelativeLayout layoutAlbum;
        public AlbumViewHoler(@NonNull View view) {
            super(view);
            txtTenAlbum = view.findViewById(R.id.txt_ten_album);
            imgHinhAlbum = view.findViewById(R.id.img_hinh_album);
            layoutAlbum = view.findViewById(R.id.layout_album);
        }
    }
    @Override
    public void onBindViewHolder(@NonNull AlbumViewHoler holder, int position) {
        Album album = mList.get(position);
        if(album==null){
            return;
        }
        holder.txtTenAlbum.setText(album.getTenAlbum());
        Picasso.with(mContext).load(album.getHinhAlbum()).into(holder.imgHinhAlbum);
        holder.layoutAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInterfaceClickAlbum.clickAlbum(album);
            }
        });
    }
}
