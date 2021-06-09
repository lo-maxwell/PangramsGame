package com.example.firstapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.firstapp.ui.home.HomeFragment;
import com.example.firstapp.ui.user_stats.UserStatsFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import static com.example.firstapp.ui.home.HomeFragment.homeFragment;
import static com.example.firstapp.ui.user_stats.UserStatsFragment.userStatsFragment;

public class MainMenuNavigation extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static MediaPlayer backgroundMusic;
    static MainMenuNavigation mainMenuNavigation;
    public static boolean timerRunning;
    private int curTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainMenuNavigation = this;
        timerRunning = false;
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
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_user_stats, R.id.nav_achievements,
                R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //Testing in progress for music player

        if (backgroundMusic!=null) {
            if(backgroundMusic.isPlaying()) backgroundMusic.stop();
            backgroundMusic.reset();//It requires again setDataSource for player object.
            backgroundMusic.release();
        }
        backgroundMusic = MediaPlayer.create(getApplicationContext(), R.raw.music_a_very_brady_special);
        //"A Very Brady Special" Kevin MacLeod (incompetech.com)
        //Licensed under Creative Commons: By Attribution 4.0 License
        //http://creativecommons.org/licenses/by/4.0/
        int musicPosition = getIntent().getIntExtra("bg_music_location", 0);
        backgroundMusic.seekTo(musicPosition);
        setMusicVolume(backgroundMusic, 0); //Prevent 1-second music playing at start
        startMusicPlayer(backgroundMusic);
        System.out.println("Music is playing");
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
        if ((backgroundMusic!= null) && (backgroundMusic.isPlaying())) {
            //killMusicPlayer(backgroundMusic);
            System.out.println("Music should be stopped by onDestroy");
            //Currently hacky but works as intended
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        if ((backgroundMusic != null) && (backgroundMusic.isPlaying())) {
            pauseMusicPlayer(backgroundMusic);
            System.out.println("Music is stopped by onPause");
        }
        if (HomeFragment.playtimeHandler != null) {
            HomeFragment.playtimeHandler.removeCallbacksAndMessages(null);
            HomeFragment.playtimeHandler = null;
            timerRunning = false;
            curTime = Integer.parseInt(HomeFragment.userStatsFileStrings.get(6));
            System.out.println("timer stopped");
        }
        if (UserStatsFragment.screenUpdater != null) {
            UserStatsFragment.screenUpdater.removeCallbacksAndMessages(null);
            UserStatsFragment.screenUpdater = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((backgroundMusic!= null) && !(backgroundMusic.isPlaying())) {
            startMusicPlayer(backgroundMusic);
            System.out.println("Music is started by onResume");
        }
        if (HomeFragment.playtimeHandler == null && !timerRunning) {
            homeFragment.startPlaytimeTimer();
            HomeFragment.userStatsFileStrings.set(6,Integer.toString(curTime));
            System.out.println("timer resumed");
        }
        if (UserStatsFragment.screenUpdater == null && userStatsFragment != null) {
            userStatsFragment.updateScreen();
        }
    }

    public static MainMenuNavigation getInstance(){
        return   mainMenuNavigation;
    }

    private void startMusicPlayer(MediaPlayer music){
        music.start();
        music.setLooping(true);
    }

    private void pauseMusicPlayer(MediaPlayer music){
        music.pause();
    }

    private void killMusicPlayer(MediaPlayer mediaPlayer){
        if(mediaPlayer!=null) {
            if(mediaPlayer.isPlaying()) {
                mediaPlayer.reset();// It requires again setDataSource for player object.
                mediaPlayer.stop();// Stop it
                //mediaPlayer.release();// Release it
                mediaPlayer = null; // Initialize it to null so it can be used later
            }
        }
    }

    public static void setMusicVolume(MediaPlayer music, int volume) {
        float log1=(float)(1-(Math.log(101-volume)/Math.log(101)));
        music.setVolume(log1,log1);
    }

    public static void setNightMode(Boolean isOn) {
        if (isOn && AppCompatDelegate.MODE_NIGHT_YES != AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            System.out.println("Night mode enabled");
            mainMenuNavigation.refresh();
        } else if(!isOn && AppCompatDelegate.MODE_NIGHT_NO != AppCompatDelegate.getDefaultNightMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            System.out.println("Night mode disabled");
            mainMenuNavigation.refresh();
        } else {
            System.out.println("Night mode setting is already correct");
        }

    }

    private void refresh() {
        Intent intent = getIntent();
        intent.putExtra("bg_music_location", backgroundMusic.getCurrentPosition());
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
        //Sends user back to home screen
    }

}
