package com.example.sepsisdetector;

import java.util.List;


import io.reactivex.Observable;
import okhttp3.Callback;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface myAPI {

    String BASE_URL = "https://pure-brushlands-56841.herokuapp.com/";

    @GET("patients")
    Call<ResponseBody> getPatientsList(@Body String hospitalID);

    @POST("login")
    Call<ResponseBody> hospitalLogin(@Body Hospital hospital);

    @POST("signUp")
    Call<ResponseBody> hospitalSignUp(@Body Hospital hospital);

}
