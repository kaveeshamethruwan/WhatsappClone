package com.example.chatter.Web;

import com.example.chatter.Models.ServerMessage;
import com.example.chatter.Models.User;
import com.example.chatter.RespondModels.MessageResponse;
import com.example.chatter.RespondModels.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface WebInterface {

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse>registerUser(
            @Field("phone_number") String phoneNumber,
            @Field("name") String name
    );

    @GET("getUser.php")
    Call<RegisterResponse>getUser(@Query("phoneNumber") String phoneNumber);

    @GET("getUser.php")
    Call<List<User>>getAllUsers();

    @FormUrlEncoded
    @POST("sendMessage.php")
    Call<MessageResponse>sendMessage(
            @Field("sender") String sender,
            @Field("receiver") String receiver,
            @Field("body") String body
    );

    @GET("get_messages.php")
    Call<List<ServerMessage>> get_chats(
            @Query("user_id") String user_id,
            @Query("min_time") String min_time
    );

}
