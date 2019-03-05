package com.doyen.fans.firebasevideoplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import static android.media.MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";

    private Uri videoUri;
    private VideoView mainVideoView;

    private ImageView playButton;
    private TextView tv_time;
    private TextView tv_duration;
    private ProgressBar currentProgress;
    private ProgressBar bufferProgress;

    private boolean isPlaying = false;

    private int current = 0;
    private int duration = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainVideoView = (VideoView) findViewById(R.id.videoView);

        playButton = (ImageView) findViewById(R.id.imageView);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_duration = (TextView)findViewById(R.id.tv_duration);
        currentProgress = (ProgressBar)findViewById(R.id.progressBar);
        currentProgress.setMax(100);
        bufferProgress = (ProgressBar)findViewById(R.id.bufferProgress);

        videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/fir-videoplayer-6038d.appspot.com/o/SampleVideo_1280x720_2mb.mp4?alt=media&token=26588770-b9f3-4036-8fc9-4dc3d966a4c7");
        mainVideoView.setVideoURI(videoUri);
        mainVideoView.requestFocus();

        mainVideoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                Log.d(TAG, "mainVideoView info what: " + what + " extra: " + extra);
               if (what == mp.MEDIA_INFO_BUFFERING_START){
                //if (what == mp.MEDIA_INFO_VIDEO_RENDERING_START){
                    bufferProgress.setVisibility(View.VISIBLE);
                }else if (what ==  mp.MEDIA_INFO_VIDEO_RENDERING_START)
                {
                    bufferProgress.setVisibility(View.INVISIBLE);
                }

                return false;
            }
        });

        mainVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                duration = mp.getDuration()/1000;  //seconds

                String durationString = String.format("%02d:%02d", duration / 60, duration %60);

                tv_duration.setText(durationString);
            }
        });

        mainVideoView.start();
        playButton.setImageResource(R.drawable.ic_pause);
        isPlaying = true;

        new VideoProgress().execute();

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying){
                    mainVideoView.pause();
                    playButton.setImageResource(R.drawable.ic_play);
                    isPlaying = false;
                }else{
                    mainVideoView.start();
                    playButton.setImageResource(R.drawable.ic_pause);
                    isPlaying = true;
                }

            }
        });
    }

    public class VideoProgress extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            do {

                current = mainVideoView.getCurrentPosition()/1000;
                try {

                    int currentPercent = current * 100 / duration;
                    publishProgress(currentPercent);

                }catch(Exception e){

                }

            } while (currentProgress.getProgress() <= 100);

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            try {

                currentProgress.setProgress(values[0]);

                int current = values[0] * duration / 100 ;
                String currentString = String.format("%02d:%02d", current / 60, current % 60);

                tv_time.setText(currentString);

            }catch(Exception e){}
        }
    }

}
