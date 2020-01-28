package com.devsai.myapplication.requests;

import com.devsai.myapplication.requests.response.DataResponse;

import java.util.List;
import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface DataApi {

    @GET("b/5e2d4159593fd74185708e7f/latest")
    Call<List<DataResponse>> getDataApiResponse();

}
