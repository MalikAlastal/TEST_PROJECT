package com.mymalik.retrofitmalik;

import java.lang.reflect.Array;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MyInterface {

    @GET("users")
    Call<ArrayList<MyJsonObject>> getMyList();
}
