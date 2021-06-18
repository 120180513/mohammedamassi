package com.iug.jerusalem.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.iug.jerusalem.R;

public class SettingsActivity extends AppCompatActivity {

    RadioButton radioButtonLight, radioButtonDark, radioButtonLarge, radioButtonMiddle, radioButtonSmall;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbar = findViewById(R.id.toolbar);
        radioButtonLight = findViewById(R.id.radioButtonLight);
        radioButtonDark = findViewById(R.id.radioButtonDark);
        radioButtonLarge = findViewById(R.id.radioButtonLarge);
        radioButtonMiddle = findViewById(R.id.radioButtonMiddle);
        radioButtonSmall = findViewById(R.id.radioButtonSmall);

        initToolBar();
        getSatatus();
        getFontSize();

        radioButtonLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveSatatus(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        radioButtonDark.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveSatatus(true);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });

        radioButtonSmall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveFontSize(18);
                }
            }
        });

        radioButtonMiddle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveFontSize(20);
                }
            }
        });

        radioButtonLarge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveFontSize(22);
                }
            }
        });

    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void saveSatatus(boolean status) {
        SharedPreferences sp = getSharedPreferences("Setting", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("status", status);
        edit.apply();
    }

    private void saveFontSize(int size) {
        SharedPreferences sp = getSharedPreferences("Setting", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("FontSize", size);
        edit.apply();
    }

    private void getSatatus() {
        SharedPreferences sp = getSharedPreferences("Setting", MODE_PRIVATE);
        if (sp.getBoolean("status", false)) {
            radioButtonLight.setChecked(false);
            radioButtonDark.setChecked(true);
        } else {
            radioButtonLight.setChecked(true);
            radioButtonDark.setChecked(false);
        }
    }

    private void getFontSize() {
        SharedPreferences sp = getSharedPreferences("Setting", MODE_PRIVATE);
        int size = sp.getInt("FontSize", 18);
        switch (size){
            case 18:
                radioButtonSmall.setChecked(true);
                break;
            case 20:
                radioButtonMiddle.setChecked(true);
                break;
            case 22:
                radioButtonLarge.setChecked(true);
                break;
        }
    }

}