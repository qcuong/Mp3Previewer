package com.quoccuong.mp3previewereviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

public class SongMetadataFragment extends Fragment {

    private ShapeableImageView albumCoverIV;

    private MaterialTextView titleTV, artistTV, albumTV;

    private MainViewModel mainViewModel;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_song_metadata, container, false);

        mainViewModel =
                new ViewModelProvider(getActivity()).get(MainViewModel.class);

        this.albumCoverIV = (ShapeableImageView) rootView.findViewById(R.id.album_cover_iv);
        this.titleTV = (MaterialTextView) rootView.findViewById(R.id.title_tv);
        this.artistTV = (MaterialTextView) rootView.findViewById(R.id.artist_tv);
        this.albumTV = (MaterialTextView) rootView.findViewById(R.id.album_tv);

        mainViewModel.getFilePathLiveData().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.d("SongMetadataFragment", s);
                extract(s);
            }
        });

        return rootView;
    }

    public void extract(String songFilePath) {
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        metaRetriver.setDataSource(songFilePath);
        try {
            byte[] art = metaRetriver.getEmbeddedPicture();
            Bitmap albumCover = BitmapFactory.decodeByteArray(art, 0, art.length);
            albumCoverIV.setImageBitmap(albumCover);
            albumTV.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM));
            artistTV.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            titleTV.setText(metaRetriver.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
