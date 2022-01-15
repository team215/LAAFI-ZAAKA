package com.example.laafizaaka;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccueilleActivity extends AppCompatActivity {
    private TextView AppVersion;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueille);

        AppVersion= findViewById(R.id.appVersion);
        image= findViewById(R.id.imageView);

        Animation animation= AnimationUtils.loadAnimation(this, R.anim.animation);
        image.startAnimation(animation);

        //Pour avoir la version de l'application
        String name= BuildConfig.VERSION_NAME;
        int appVersionCode= BuildConfig.VERSION_CODE;

        AppVersion.setText(name+"."+String.valueOf(appVersionCode));

        //Afficher la page d'acceuille pendant 5 secondes
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(AccueilleActivity.this.getApplicationContext(), MapsActivity.class);
            AccueilleActivity.this.startActivity(intent);
            AccueilleActivity.this.finish();
        }, 5000);
    }
}