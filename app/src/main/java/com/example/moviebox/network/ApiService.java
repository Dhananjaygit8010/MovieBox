package com.example.moviebox.network;

import com.example.moviebox.model.ExampleResponse;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService
{

    @GET("example/endpoint")
    Call<ExampleResponse> getExampleData();

}
