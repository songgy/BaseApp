package com.ly.baseapp.http.interceptor;

import com.ly.baseapp.utils.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * OkHttp网络拦截
 * Created by admin on 2016/11/24.
 */

public class LogInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        long t1 = System.nanoTime();
        Request request = chain.request();
        L.i("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers());
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        L.i("Received response for %s in %.1fms%n%s", request.url(), (t2 - t1) / 1e6d, response.headers());
        return response;
    }
}
