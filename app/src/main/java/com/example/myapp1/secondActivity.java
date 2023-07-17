package com.example.myapp1;

import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class secondActivity extends AppCompatActivity{
    private TextureView textureView;
    private MediaPlayer mediaPlayer;
    private AssetFileDescriptor fileDescriptor;

    private final TextureView.SurfaceTextureListener surfaceTextureListener =
            new TextureView.SurfaceTextureListener() {
                @Override
                public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {
                    Surface mSurface = new Surface(surfaceTexture);
                    play(mSurface);
                }

                @Override
                public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surfaceTexture, int i, int i1) {

                }

                @Override
                public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surfaceTexture) {
                    return false;
                }

                @Override
                public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surfaceTexture) {

                }
            };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        textureView = findViewById(R.id.video_display);
        textureView.setSurfaceTextureListener(surfaceTextureListener);
        mediaPlayer = new MediaPlayer();
        try {
            fileDescriptor = getAssets().openFd("sample-30s.mp4");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        if(mediaPlayer!=null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
            Log.v("test", "mediaPlayer.pause() call");
        }
        Log.v("test", "onPause() call");
        super.onPause();
    }


//    @Override
//    protected void onResume() {
//        Log.v("test", "onResume() call");
//        super.onResume();
//        if(mediaPlayer!=null){
//            mediaPlayer.start();
//            Log.v("test", "mediaPlayer.start() call");
//        }
//    }

    @Override
    protected void onDestroy() {
        Log.v("test", "onDestroy() call");
        //Toast.makeText(this, "Toast 基本用法", Toast.LENGTH_SHORT).show();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer=null;
            Log.v("test", "mediaPlayer.release() call");
        }
        try {
            fileDescriptor.close();
            Log.v("test", "fileDescriptor.close() call");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        super.onDestroy();
    }

    private void play(Surface mSurface){
        try {
            mediaPlayer.setSurface(mSurface);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaPlayer.setDataSource(fileDescriptor);
                mediaPlayer.prepareAsync();
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start();
                        Log.v("test", "onPrepared() call");
                    }
                });
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}