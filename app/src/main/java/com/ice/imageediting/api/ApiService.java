package com.ice.imageediting.api;


import com.ice.imageediting.model.LoginResponse;
import com.ice.imageediting.model.RegisterResponse;
import com.ice.imageediting.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("api/register")
    Call<RegisterResponse> register(@Body User user);

    @POST("api/login")
    Call<LoginResponse> login(@Body User user);
}