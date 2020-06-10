package com.example.uts2020_mobile_dl_00000025672_samuelputra_jukebox;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {

    boolean isPlaying = true;
    boolean isFinished = false;
    int length = 0;
    MediaPlayer mediaPlayer;
    Button play, about;
    ScrollView scrollview;
    ImageView image;
    Animation rotation;
    RotateAnimation rotate;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.controlButton);
        about = findViewById(R.id.aboutButton);
        scrollview = findViewById(R.id.lyrics);
        scrollview.scrollTo(0, 0);
        image = findViewById(R.id.record);

        image.setBackgroundResource(R.drawable.recordanim);

        playAnimation();



        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.i);

        mediaPlayer.start();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pause();
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mediaPlayer.stop();
                stopAnimation();
                if(countDownTimer != null) {
                    countDownTimer.cancel();
                    countDownTimer = null;
                }
                scrollview.scrollTo(0, 0);
                isPlaying = false;
                isFinished = false;
                startActivity(new Intent(MainActivity.this, aboutActivity.class));
            }
        });

        scrollview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                return true;
            }
        });

        startTimer();


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                isFinished = true;
                isPlaying = false;
                play.setText("Play");
                stopAnimation();
            }

        });
    }

    protected void startTimer()
    {
        countDownTimer =  new CountDownTimer(165000, 16)
        {

            public void onTick(long millisUntilFinished)
            {
                int x = 1;
                if(mediaPlayer.getCurrentPosition() < 15000)
                    x=0;
                scrollview.scrollTo(0, scrollview.getScrollY() + x); // from zero to 2000

            }

            public void onFinish() {
            }

        }.start();
    }

    protected void stopAnimation()
    {
        rotate = new RotateAnimation(
                image.getRotationX(), -360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(400);
        image.startAnimation(rotate);
    }
    protected void playAnimation()
    {
        rotate = new RotateAnimation(
                image.getRotation(), 375,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        rotate.setDuration(1000);
        rotate.setRepeatCount(Animation.INFINITE);
        image.startAnimation(rotate);
    }

    protected void Pause()
    {
        if(isPlaying)
        {
            mediaPlayer.pause();
            length = mediaPlayer.getCurrentPosition();
            isPlaying = false;
            play.setText("Play");
            stopAnimation();
            if(countDownTimer != null) {
                countDownTimer.cancel();
                countDownTimer = null;
            }
        }
        else if(isFinished)
        {
            mediaPlayer.start();
            isPlaying = true;
            isFinished = false;
            play.setText("Pause");
            playAnimation();
            startTimer();
            scrollview.scrollTo(0, 0);
        }
        else
        {
            mediaPlayer.seekTo(length);
            mediaPlayer.start();
            isPlaying = true;
            play.setText("Pause");
            playAnimation();
            startTimer();
        }
    }

}
