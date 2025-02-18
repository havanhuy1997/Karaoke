package com.huyha.huyha.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.huyha.huyha.activities.lyric.LyricActivity;
import com.huyha.huyha.adapters.SongAdapter;
import com.huyha.huyha.models.Song;
import com.huyha.huyha.models.localData.Database;
import com.huyha.huyha.utils.AsynFindDatabase;
import com.huyha.van.karaoke.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ArirangFragment extends android.app.Fragment {
    private static final String TAG = "ArirangFragment";
    @BindView(R.id.txtKeySearch2)
    EditText txtKeySearch;
    @BindView(R.id.rvPage2)
    RecyclerView rvPage;
    private Context mContext;
    public static String keySearch;
    private int countSpaceKeySearch = 0;
    private static int typeSong;

    List<Song> mSonglist = new ArrayList<>();
    SongAdapter mSongAdapter;
    MainPresenter mMainPresenter;
    SongAdapter.onItemClickListener listener;
    Intent intent;

    public ArirangFragment() {
        // Required empty public constructor
    }

    public static int getTypeSong() {
        return typeSong;
    }

    public static void setTypeSong(int typeSong) {
        ArirangFragment.typeSong = typeSong;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"OnCreate View");
        View v = inflater.inflate(R.layout.fragment_arirang, container, false);
        // Inflate the layout for this fragment
        ButterKnife.bind(this,v);
        return v;
    }

    @Override
    public void onStart() {
        Log.d(TAG,"onStart"+txtKeySearch.getText().toString());
        super.onStart();
        init();
        addEvent();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"OnResume");
    }

    private void init(){
        mMainPresenter = new MainPresenter(new Database().getInstance());
        mMainPresenter.setTypeSong(typeSong);
        keySearch = txtKeySearch.getText().toString();
        if ( keySearch.length() == 0) {
            mSonglist = mMainPresenter.selectAll();
        }
        else{
            new AsynFindDatabase(mSonglist,mSongAdapter).execute(keySearch);
        }
        intent = new Intent(mContext, LyricActivity.class);

        listener = new SongAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Start Lyric Activity
                Song songClick =  mSonglist.get(position);
                Log.d(TAG,"name click" + mSonglist.get(position).getName());
                intent.putExtra("song",songClick);
                startActivity(intent);
            }

            @Override
            public void onButtonFavoriteClick(View v, int positon) {
                //Update favorite Song
                Log.d(TAG,"button click");
                Song songClick =  mSonglist.get(positon);
                String id = songClick.getID();
                if (songClick.isLike()){
                    mMainPresenter.updateFavorite(id,0);
                    songClick.setLike(false);
                    mSongAdapter.notifyDataSetChanged();
                }
                else{
                    mMainPresenter.updateFavorite(id,1);
                    songClick.setLike(true);
                    mSongAdapter.notifyDataSetChanged();
                }
            }
        };
        mSongAdapter = new SongAdapter(mSonglist, listener);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvPage.setLayoutManager(mLayoutManager);
        rvPage.setAdapter(mSongAdapter);

    }

    private void addEvent() {
        txtKeySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                keySearch = txtKeySearch.getText().toString();
                int count = keySearch.length() - keySearch.replace(" ","").length();
                if (count != countSpaceKeySearch){
                    new AsynFindDatabase(mSonglist, mSongAdapter).execute(keySearch);
                }
            }
        });

        txtKeySearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (txtKeySearch.getText().toString() == ""){
                    countSpaceKeySearch = 0;
                }
            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
        new Database().getInstance().close();
    }
}
