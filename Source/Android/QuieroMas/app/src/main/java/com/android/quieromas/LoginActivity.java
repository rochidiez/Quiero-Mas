package com.android.quieromas;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity{

    SurfaceView mSurfaceView;
    SurfaceHolder mSurfaceHolder;
    private static final int PICK_VIDEO_REQUEST = 1001;
    private static final String TAG = "SurfaceSwitch";
    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.d(TAG, "First surface created!");

                Uri uri = Uri.parse("android.resource://" + getPackageName() + "/"
                        + R.raw.video_background);

                mSurfaceHolder = surfaceHolder;
                if (uri != null) {
                    mMediaPlayer = MediaPlayer.create(getApplicationContext(),
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
                Log.d(TAG, "First surface destroyed!");
                mMediaPlayer.stop();
            }
        });
    }




}
