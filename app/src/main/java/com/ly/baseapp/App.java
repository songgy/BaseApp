package com.ly.baseapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ly.baseapp.http.MyRxJavaCallAdapterFactory;
import com.ly.baseapp.http.interceptor.LogInterceptor;
import com.ly.baseapp.utils.NetWorkUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/3/3.
 */
public class App extends Application {
    private static OkHttpClient mClient;
    private static final long DEFAULT_KEEP_ALIVE_DURATION_MINUTES = 5; // 5 min
    private static Retrofit mRetrofit;
    public static List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }


    /**
     * 创建Retrofit 接口
     *
     * @param context 上下文
     * @param api     retrofit接口类
     * @param <T>     泛型
     * @return retrofit接口类
     */
    public static <T> T createApi(Context context, Class<T> api) {
        return getRetrofit(context).create(api);
    }

    /**
     * 获取Retorfit
     *
     * @param context 上下文
     * @return retrofit
     */
    public static Retrofit getRetrofit(Context context) {
        if (mRetrofit == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            mRetrofit = new Retrofit.Builder()
                    .client(getOkHttpClient(context))
                    .baseUrl(Config.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(MyRxJavaCallAdapterFactory.create())
                    .build();
        }
        return mRetrofit;
    }

    /**
     * 将开启的页面加入到List中
     *
     * @param activity 各页面Activity
     */
    public static void addActivityForList(Activity activity) {
        if (activity != null) {
            activities.add(activity);
        }
    }

    /**
     * 将关闭的页面从List中删除
     *
     * @param activity 各页面Activity
     */
    public static void removeActivityFromList(Activity activity) {
        if (activity != null) {
            // activities.remove(activity);

            Iterator<Activity> iterator = activities.iterator();
            while (iterator.hasNext()) {
                Activity tmp = iterator.next();
                if (activity == tmp) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 获取OkHttpClient
     *
     * @param context 上下文
     * @return OkHttpClient
     */
    public static OkHttpClient getOkHttpClient(Context context) {
        if (mClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .readTimeout(30, TimeUnit.SECONDS)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .connectionPool(new ConnectionPool(NetWorkUtils.adjustThreadCount(context),
                            DEFAULT_KEEP_ALIVE_DURATION_MINUTES, TimeUnit.MINUTES))
                    .addNetworkInterceptor(new LogInterceptor());
            ;
            mClient = builder.build();
        }
        return mClient;
    }
}
