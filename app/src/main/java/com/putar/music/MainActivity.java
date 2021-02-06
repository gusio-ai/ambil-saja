package com.putar.music;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import com.putar.music.R;
import com.putar.music.MainActivity;

import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;
import java.io.IOException;
import android.annotation.SuppressLint;
import android.widget.MediaController;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity 
{
    private Toolbar toolbar;
    private DrawerLayout d;
	private NavigationView n;
	
	private Button Play, Pause, Stop;
    private MediaPlayer mediaPlayer;


    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        d = findViewById(R.id.mainDrawerLayout);
		n = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        ActionBarDrawerToggle t = new ActionBarDrawerToggle(this, d, toolbar, R.string.app_name, R.string.app_name);
		d.setDrawerListener(t);
		t.syncState();
		
		Play = findViewById(R.id.play);
        Pause = findViewById(R.id.pause);
        Stop = findViewById(R.id.stop);
        
        //Menambahkan Listener pada Button
        Play.setOnClickListener(this);
        Pause.setOnClickListener(this);
        Stop.setOnClickListener(this);
        
        stateAwal();
    
    }
    //Untuk menentukan kondisi saat aplikasi pertama kali berjalan
    private void stateAwal(){
        Play.setEnabled(true);
   Pause.setEnabled(false);
   Stop.setEnabled(false);
    }
    
    //Method untuk memainkan musik
    private void playAudio(){
        //Menentukan resource audio yang akan dijalankan
       mediaPlayer = MediaPlayer.create(this, R.raw.Kaliwungu_-_Daun_Bambu_Official_Video_Klip);

       //Kondisi Button setelah tombol play di klik
       Play.setEnabled(false);
       Pause.setEnabled(true);
       Stop.setEnabled(true);
    
       //Menjalankan Audio / Musik
       try{
           mediaPlayer.prepare();
       }catch (IllegalStateException ex){
           ex.printStackTrace();
       }catch (IOException ex1){
           ex1.printStackTrace();
       }
       mediaPlayer.start();

       //Setelah audio selesai dimainkan maka kondisi Button akan kembali seperti awal
       mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
           @Override
           public void onCompletion(MediaPlayer mp) {
               stateAwal();
           }
       });
    }

    //Method untuk mengentikan musik
    private void pauseAudio(){
        //Jika audio sedang dimainkan, maka audio dapat di pause
    if(mediaPlayer.isPlaying()){
        if(mediaPlayer != null){
            mediaPlayer.pause();
            Pause.setText("Lanjutkan");
        }
    }else {
            
        //Jika audio sedang di pause, maka audio dapat dilanjutkan kembali
        if(mediaPlayer != null){
            mediaPlayer.start();
            Pause.setText("Pause");
        }
    }
    }

    //Method untuk mengakhiri musik
    private void stopAudio(){
        mediaPlayer.stop();
    try {
        //Menyetel audio ke status awal
        mediaPlayer.prepare();
        mediaPlayer.seekTo(0);
    }catch (Throwable t){
        t.printStackTrace();
    }
    stateAwal();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                playAudio();
                break;

            case R.id.pause:
                pauseAudio();
                break;

            case R.id.stop:
                stopAudio();
                break;
        }
    }
    
    
}
