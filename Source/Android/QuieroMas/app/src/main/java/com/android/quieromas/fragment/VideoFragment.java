package com.android.quieromas.fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.android.quieromas.R;

public class VideoFragment extends BaseFragment {

//    @BindView(R.id.surface) SurfaceView mSurfaceView;
    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    private MediaPlayer mMediaPlayer;

    public VideoFragment() {
        // Required empty public constructor
    }

    public static VideoFragment newInstance() {
        VideoFragment fragment = new VideoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        ButterKnife.bind(getActivity());
        mSurfaceView = (SurfaceView) getView().findViewById(R.id.surface);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {

                Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/"
                        + R.raw.video_background);

                mSurfaceHolder = surfaceHolder;
                if (uri != null) {
                    mMediaPlayer = MediaPlayer.create(getActivity(),
                            uri, mSurfaceHolder);
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                        public void onCompletion(MediaPlayer arg0) {
                            mMediaPlayer.start();
                        }
                    });
                    mMediaPlayer.start();

                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                mMediaPlayer.stop();
            }
        });

    }

}
