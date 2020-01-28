package com.devsai.myapplication.requests;

import android.util.Log;

import com.devsai.myapplication.requests.response.DataResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.devsai.myapplication.utils.Constants.BASE_URL;

public class ServiceGenerator {

    private static ServiceGenerator instance;

    private static final String TAG = "ServiceGenerator";
    private ServiceGenerator() {

    }

    public static ServiceGenerator getInstance() {
        if (instance == null) {
            instance = new ServiceGenerator();
        }
        return instance;
    }

    private static final OkHttpClient okHttpClient = new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(chain -> {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("secret-key","$2b$10$ANyhAyXDdFavbq/H2qRcmOMUtqwKF1mHWpsOL.4sYlKvThtWZsHnK")
                        .header("Cache-Control","max-stale=0")
                        .build();
                Log.d(TAG, ": chain called");
                return chain.proceed(request);
            })
            .connectTimeout(15, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

    private  static Retrofit.Builder retrofitBuiler = new Retrofit.Builder().client(okHttpClient)
            .baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = retrofitBuiler.build();


    private static DataApi dataApi = retrofit.create(DataApi.class);

    public static DataApi getDataApi() {
        return dataApi;
    }


}
