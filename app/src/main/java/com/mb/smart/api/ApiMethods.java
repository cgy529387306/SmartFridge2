package com.mb.smart.api;

import com.mb.smart.entity.Subject;
import com.mb.smart.entity.UserData;
import com.mb.smart.http.HttpMethods;
import com.mb.smart.http.ResponseConvertFactory;
import com.mb.smart.http.interceptor.CacheControlInterceptor;
import com.mb.smart.http.interceptor.HttpCommonInterceptor;
import com.mb.smart.MyApplication;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2018\5\12 0012.
 */
@SuppressWarnings("unchecked")
public class ApiMethods extends HttpMethods {
    private static final String BASE_URL = "https://dev.api.bearya.com/";
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private ApiService movieService;
    /** socket连接超时时间 ，数据返回的等待时间*/
    public static final int HTTP_REQUEST_CONNECT_TIMEOUT = 120000;
    /** 读超时时间 */
    public static final int HTTP_REQUEST_READ_TIMEOUT = 120000;
    private ApiMethods() {
        File httpCacheDirectory = new File(MyApplication.getAppContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpCommonInterceptor commonInterceptors = new HttpCommonInterceptor();
        CacheControlInterceptor cacheInterceptor = new CacheControlInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(HTTP_REQUEST_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(HTTP_REQUEST_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)
                .addInterceptor(commonInterceptors)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(cacheInterceptor)
                .cache(cache)
                .build();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);


        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(ResponseConvertFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        movieService = retrofit.create(ApiService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder{
        private static final ApiMethods INSTANCE = new ApiMethods();
    }

    //获取单例
    public static ApiMethods getInstance(){
        return SingletonHolder.INSTANCE;
    }

    private CookieJar cookieJar = new CookieJar() {
        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };



    public void getTopMovie(Subscriber<List<Subject>> subscriber, int start, int count){
        Observable observable = movieService.getTopMovie(start, count)
                .map(new HttpResultFunc<List<Subject>>());
        toSubscribe(observable, subscriber);
    }

    public void doLogin(Subscriber<UserData> subscriber, String mobile, String password){
        Observable observable = movieService.doLogin(mobile, password)
                .map(new HttpResultFunc<UserData>());
        toSubscribe(observable, subscriber);
    }
    public void doRegister(Subscriber<UserData> subscriber, String mobile, String password, String code){
        Observable observable = movieService.doRegister(mobile, password,code)
                .map(new HttpResultFunc<UserData>());
        toSubscribe(observable, subscriber);
    }
    public void sendSms(Subscriber<Object> subscriber, String mobile, String action){
        Observable observable = movieService.sendSms(mobile, action)
                .map(new HttpResultFunc<Object>());
        toSubscribe(observable, subscriber);
    }
    public void getpw(Subscriber<Object> subscriber, String mobile, String password, String code){
        Observable observable = movieService.getpw(mobile, password,code)
                .map(new HttpResultFunc<Object>());
        toSubscribe(observable, subscriber);
    }
}
