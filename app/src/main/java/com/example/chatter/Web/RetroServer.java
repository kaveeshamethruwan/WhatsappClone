package com.example.chatter.Web;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer {

    private static final String BASE_URL = "https://kaveeshamethruwan.com/";
    private static Retrofit retrofit;

    public static Retrofit connectRetrofit() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;

    }

}
