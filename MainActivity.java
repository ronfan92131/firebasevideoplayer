package com.doyen.fans.firebasevideoplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private Uri videoUri;
    private VideoView mainVideoView;

    private ImageButton playButton;
    private TextView tv_time;
    private TextView tv_duration;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainVideoView = (VideoView) findViewById(R.id.videoView);

        playButton = (ImageButton) findViewById(R.id.imageButton);
        tv_time = (TextView)findViewById(R.id.tv_time);
        tv_duration = (TextView)findViewById(R.id.tv_duration);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        videoUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/fir-videoplayer-6038d.appspot.com/o/file_example_MP4_480_1_5MG.mp4?alt=media&token=5790413e-b38d-4f18-beeb-102019fc31e0");

        mainVideoView.setVideoURI(videoUri);
        mainVideoView.requestFocus();
        mainVideoView.start();

    }
}
