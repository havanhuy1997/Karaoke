package com.example.huyha.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.huyha.models.Song;
import com.example.huyva.karaoke.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huyva on 7/9/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder>{
    private static final String TAG = "SongAdapter";
    private List<Song> songList;
    private onItemClickListener mClickListener;

    public SongAdapter(List<Song> songList, onItemClickListener listener){
        this.songList = songList;
        this.mClickListener = listener;
    }

    public onItemClickListener getClickListener() {
        return mClickListener;
    }

    public void setClickListener(onItemClickListener clickListener) {
        this.mClickListener = clickListener;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    @Override
    public SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new SongHolder(view);
    }

    @Override
    public void onBindViewHolder(SongAdapter.SongHolder holder, final int position) {
        holder.bind(position, mClickListener);

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onButtonFavoriteClick(v, position);
            }
        });

        holder.txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }


    public class SongHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtID)
        TextView txtId;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtAuthor)
        TextView txtAuthor;
        @BindView(R.id.txtLyric)
        TextView txtLyric;
        @BindView(R.id.btnLike)
        ImageButton btnLike;
        public SongHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        void bind(int position, final onItemClickListener onItemClickListener){
            final Song currentSong = getSongList().get(position);
            txtId.setText(currentSong.getID());
            txtName.setText(currentSong.getName());
            txtAuthor.setText(currentSong.getAuthor());
            if (currentSong.getLyric().length() > 30) {
                txtLyric.setText(currentSong.getLyric().substring(0, 30) + "...");
            }
            else{
                txtLyric.setText(currentSong.getLyric());
            }
            if (currentSong.isLike()){
                btnLike.setImageResource(R.drawable.presslike);
            }
            else{
                btnLike.setImageResource(R.drawable.like);
            }
        }
    }

    public interface onItemClickListener{
        void onItemClick(View v, int position);
        void onButtonFavoriteClick(View v, int positon);
    }
}
