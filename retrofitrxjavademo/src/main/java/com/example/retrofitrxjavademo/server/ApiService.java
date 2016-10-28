package com.example.retrofitrxjavademo.server;

import com.example.retrofitrxjavademo.data.NewsData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yfx on 16/10/27.
 */

public interface ApiService {
    @GET("social/")
    Observable<NewsData> getNewsData(@Query("key") String key, @Query("num") String num, @Query("page") int page);
}
