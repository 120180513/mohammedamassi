package com.iug.jerusalem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.iug.jerusalem.R;
import com.iug.jerusalem.api.CustomerApi;
import com.iug.jerusalem.api.NewsSerApi;
import com.iug.jerusalem.pogo.ArticleApi;
import com.iug.jerusalem.pogo.News;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<ArticleApi> data = new ArrayList<>();

    ArticleNewsAdapter adapter;
    String date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        initToolBar();
        initRecView();
        getNewsArticles();

    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecView() {
        adapter = new ArticleNewsAdapter(this, data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    public void getNewsArticles() {
        NewsSerApi api = CustomerApi.getCustomerApi().create(NewsSerApi.class);

        getDate();

        Call<News> call = api.getLastNews("القدس", date, "popularity", "ar", "006e7b36f15f4022ba2df4cfd920c7aa");

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(@NonNull Call<News> call, @NonNull Response<News> response) {
                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        data.addAll(response.body().getArticles());

                    }

                } else {
                    Log.e("TAG", "onResponse: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<News> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });

    }

    private void getDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        date = dateFormat.format(calendar.getTime());
    }

}