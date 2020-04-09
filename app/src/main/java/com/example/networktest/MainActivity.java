package com.example.networktest;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle("NXT Games");
        setStatusBar();



        //Main Button Creation
        ImageView pcButton =findViewById(R.id.pc_button);
        pcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pcActivity = new Intent (getApplicationContext(), ViewPager_Activity.class);
                pcActivity.putExtra("viewpager_position", 0);
                startActivity(pcActivity);
            }
        });

        ImageView ps4Button =findViewById(R.id.ps4_button);
        ps4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ps4Activity = new Intent (getApplicationContext(), ViewPager_Activity.class);
                ps4Activity.putExtra("viewpager_position", 1);
                startActivity(ps4Activity);
            }
        });


        ///
        ///Test Button for Now
        ///
        ///
        ImageView switchButton =findViewById(R.id.switch_button);
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent switchActivity = new Intent (getApplicationContext(), GameInfo.class);
                 switchActivity.putExtra("viewpager_position", 1);
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

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP, ActionBar.DISPLAY_HOME_AS_UP);

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


}
