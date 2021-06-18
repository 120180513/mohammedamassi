package com.iug.jerusalem.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.iug.jerusalem.R;

public class ArticleDetailsActivity extends AppCompatActivity {

    TextView textView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_details);

        textView = findViewById(R.id.textView);
        toolbar = findViewById(R.id.toolbar);

        initToolBar();

        String details = getIntent().getStringExtra("details");
        textView.setText(details);
        textView.setTextSize(getFontSize());

    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }

    private int getFontSize() {
        SharedPreferences sp = getSharedPreferences("Setting", MODE_PRIVATE);
        return  sp.getInt("FontSize", 18);
    }

}