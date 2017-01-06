package com.nulldreams.bemusic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.nulldreams.adapter.DelegateAdapter;
import com.nulldreams.adapter.DelegateParser;
import com.nulldreams.adapter.impl.DelegateImpl;
import com.nulldreams.bemusic.R;
import com.nulldreams.bemusic.adapter.AlbumDelegate;
import com.nulldreams.media.manager.PlayManager;
import com.nulldreams.media.manager.ruler.Rule;
import com.nulldreams.media.model.Album;
import com.nulldreams.media.model.Song;
import com.nulldreams.media.service.PlayService;

import java.util.List;

/**
 * Created by boybe on 2017/1/6.
 */

public class AlbumListFragment extends RvFragment implements PlayManager.Callback{

    private DelegateAdapter mAdapter;

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getContext(), 2);
    }

    @Override
    public CharSequence getTitle(Context context, Object... params) {
        return context.getString(R.string.title_album_list);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdapter = new DelegateAdapter(getContext());
        getRecyclerView().setAdapter(mAdapter);
        PlayManager.getInstance(getContext()).registerCallback(this);
        List<Album> albumList = PlayManager.getInstance(getContext()).getAlbumList();
        showAlbums(albumList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PlayManager.getInstance(getContext()).unregisterCallback(this);
    }

    private void showAlbums (List<Album> albums) {
        mAdapter.clear();
        if (albums == null || albums.isEmpty()) {

        } else {
            mAdapter.addAll(albums, new DelegateParser<Album>() {
                @Override
                public DelegateImpl parse(Album data) {
                    return new AlbumDelegate(data);
                }
            });
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPlayListPrepared(List<Song> songs) {

    }

    @Override
    public void onAlbumListPrepared(List<Album> albums) {
        showAlbums(albums);
    }

    @Override
    public void onPlayStateChanged(@PlayService.State int state, Song song) {

    }

    @Override
    public void onPlayRuleChanged(Rule rule) {

    }
}