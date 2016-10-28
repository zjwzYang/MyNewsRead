package com.example.retrofitrxjavademo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.retrofitrxjavademo.adapter.RecyclerAdapter;
import com.example.retrofitrxjavademo.data.NewsData;
import com.example.retrofitrxjavademo.data.UrlPath;
import com.example.retrofitrxjavademo.server.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private int page = 1;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        ArrayList<NewsData.NewslistBean> newslistBeen = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(MainActivity.this, newslistBeen);
        recyclerView.setAdapter(recyclerAdapter);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(UrlPath.path)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Observable<NewsData> newsData = apiService.getNewsData(UrlPath.key, "20", page);
        newsData.subscribeOn(Schedulers.io())
                .map(new Func1<NewsData, List<NewsData.NewslistBean>>() {
                    @Override
                    public List<NewsData.NewslistBean> call(NewsData newsData) {
                        return newsData.getNewslist();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<NewsData.NewslistBean>>() {
                    @Override
                    public void call(List<NewsData.NewslistBean> newslistBeen) {
                        //Log.i("TAG", "call: 长度" + newslistBeen.size());
                        //Log.i("TAG", "call: +"+Thread.currentThread().getName());
                        recyclerAdapter.addData(newslistBeen);
                    }
                });

    }
}
