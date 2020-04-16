package com.example.networktest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    AudioManager audioManager;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("NXT Games");
        setStatusBar();

        audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });








        //Main Button Creation
        ImageView pcButton =findViewById(R.id.pc_button);
        pcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPoppingNoise();
                Intent pcActivity = new Intent (getApplicationContext(), ViewPager_Activity.class);
                pcActivity.putExtra("viewpager_position", 0);
                startActivity(pcActivity);
            }
        });

        ImageView ps4Button =findViewById(R.id.ps4_button);
        ps4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPoppingNoise();
                Intent ps4Activity = new Intent (getApplicationContext(), ViewPager_Activity.class);
                ps4Activity.putExtra("viewpager_position", 1);
                startActivity(ps4Activity);
            }
        });

        ImageView xboxButton =findViewById(R.id.xbox_button);
        xboxButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPoppingNoise();
                Intent xboxActivity = new Intent (getApplicationContext(), ViewPager_Activity.class);
                xboxActivity.putExtra("viewpager_position", 2);
                startActivity(xboxActivity);
            }
        });


        ImageView switchButton =findViewById(R.id.switch_button);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playPoppingNoise();
                Intent switchActivity = new Intent (getApplicationContext(), ViewPager_Activity.class);
                 switchActivity.putExtra("viewpager_position", 3);
                startActivity(switchActivity);
            }
        });

    }

    // Set Custom ActionBar
    public void setTitle(String title){
        this.getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_actionbar);
        getSupportActionBar().setElevation(0);
        View view = getSupportActionBar().getCustomView();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#393939")));

        ImageView options_icon = (ImageView) findViewById(R.id.genre_options);
        options_icon.setVisibility(View.GONE);


    }

    // Set Status Bar Color
    public void setStatusBar() {
        Window window = this.getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        window.setStatusBarColor(Color.parseColor("#393939"));
    }


    public void playPoppingNoise() {

                if(mediaPlayer != null) {
                    mediaPlayer.release();
                }

                mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.pop);


                int result = audioManager.requestAudioFocus(changeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN);

                changeListener.onAudioFocusChange(result);


                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();
                    }


    });
    }

    //AUDIO FOCUS STATE METHOD
    AudioManager.OnAudioFocusChangeListener changeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                mediaPlayer.release();
            }
        }

    };

    @Override
    public void onStop() {
        super.onStop();

        if(mediaPlayer != null) {
            mediaPlayer.release();
        }
    }


}
