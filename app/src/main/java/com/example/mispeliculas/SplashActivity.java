package com.example.mispeliculas;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private ImageView ImgLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImgLogo = findViewById(R.id.ImgLogo);
        ImgLogo.setAlpha(0f); //no
        ImgLogo.animate().alpha(1f).setDuration(2000).start(); //si - 2 segundos

        new Handler(Looper.getMainLooper()).postDelayed(()->{

            ImgLogo.animate().alpha(0f).setDuration(1000).withEndAction(()->{ //bye

                Intent intent = new Intent(SplashActivity.this, MainActivity.class); //principal
                startActivity(intent);

                overridePendingTransition( android.R.anim.fade_in, android.R.anim.fade_out); //animacion
                finish();
            })
            .start();
        }, 5000);
    }
}
