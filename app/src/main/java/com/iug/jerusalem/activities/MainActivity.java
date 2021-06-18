package com.iug.jerusalem.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iug.jerusalem.R;

public class MainActivity extends AppCompatActivity {

    Button buttonHistory, buttonLandmarks, buttonNeighboringTowns, buttonDoors, buttonNews, buttonAddArticle, buttonSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSatatus();

        buttonHistory = findViewById(R.id.buttonHistory);
        buttonLandmarks = findViewById(R.id.buttonLandmarks);
        buttonNeighboringTowns = findViewById(R.id.buttonNeighboringTowns);
        buttonDoors = findViewById(R.id.buttonDoors);
        buttonNews = findViewById(R.id.buttonNews);
        buttonAddArticle = findViewById(R.id.buttonAddArticle);
        buttonSettings = findViewById(R.id.buttonSettings);

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        buttonLandmarks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LandmarksActivity.class);
                startActivity(intent);
            }
        });

        buttonNeighboringTowns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NeighboringTownsActivity.class);
                startActivity(intent);
            }
        });

        buttonDoors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), DoorsActivity.class);
                startActivity(intent);
            }
        });

        buttonNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NewsActivity.class);
                startActivity(intent);
            }
        });

        buttonAddArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddArticleActivity.class);
                startActivity(intent);
            }
        });

        buttonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getSatatus() {
        SharedPreferences sp = getSharedPreferences("Setting", MODE_PRIVATE);
        if (sp.getBoolean("status", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

}