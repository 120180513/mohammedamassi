package com.iug.jerusalem.api;

import com.iug.jerusalem.pogo.ArticleApi;
import com.iug.jerusalem.pogo.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsSerApi {

    @GET("everything")
    Call<News> getLastNews(@Query("q") String q,
                           @Query("from") String from,
                           @Query("sortBy") String sortBy,
                           @Query("Language ") String Language,
                           @Query("apiKey") String apiKey);

}
