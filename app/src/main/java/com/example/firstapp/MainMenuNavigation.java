package com.example.firstapp;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainMenuNavigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        //mAppBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.nav_home, R.id.nav_user_stats, R.id.nav_achievements,
        //        R.id.nav_tools, R.id.nav_share, R.id.nav_send)
        //        .setDrawerLayout(drawer)
        //        .build();
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_user_stats, R.id.nav_achievements,
                R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //Testing in progress for music player
        backgroundMusic = MediaPlayer.create(getApplicationContext(), R.raw.music_fantasy);
        startMusicPlayer(backgroundMusic);
        setMusicVolume(backgroundMusic, 0); //Prevent 1-second music playing at start
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if ((backgroundMusic!= null) && (backgroundMusic.isPlaying())) {stopMusicPlayer(backgroundMusic);}

    }

    @Override
    public void onPause() {
        super.onPause();
        if ((backgroundMusic!= null) && (backgroundMusic.isPlaying())) {stopMusicPlayer(backgroundMusic);}
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((backgroundMusic!= null) && !(backgroundMusic.isPlaying())) {startMusicPlayer(backgroundMusic);}
    }

    private void startMusicPlayer(MediaPlayer music){
        music.start();
        music.setLooping(true);
    }

    private void stopMusicPlayer(MediaPlayer music){
        music.pause();
    }

    public static void setMusicVolume(MediaPlayer music, int volume) {
        float log1=(float)(1-(Math.log(101-volume)/Math.log(101)));
        music.setVolume(log1,log1);
    }
}
