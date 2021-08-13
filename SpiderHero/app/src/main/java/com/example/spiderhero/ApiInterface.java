package com.example.spiderhero;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("all.json")
    Call<List<HERO>> getList();

    @GET("id/{id}.json")
    Call<HERO>  getHero(@Path("id") String id);
}
