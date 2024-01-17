package com.example.chatter.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatter.Database.DatabaseRepository;
import com.example.chatter.Models.LoggedInUser;
import com.example.chatter.Models.User;
import com.example.chatter.RespondModels.RegisterResponse;
import com.example.chatter.Web.RetroServer;
import com.example.chatter.Web.WebInterface;
import com.example.chatter.databinding.ActivityLoginBinding;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginButtonProcess();

            }
        });

    }

    private void loginButtonProcess() {

        String phoneNumber = Objects.requireNonNull(binding.phoneNumberText.getText()).toString();

        if (phoneNumber.isEmpty()) {

            binding.phoneNumberText.requestFocus();
            binding.phoneNumberText.setError("Phone Number Required");

        } else {

            binding.progressBar.setVisibility(View.VISIBLE);
            binding.loginButton.setEnabled(false);
            loginUser(phoneNumber);

        }

    }

    private void loginUser(String phoneNumber) {

        WebInterface webInterface = RetroServer.connectRetrofit().create(WebInterface.class);
        Call<RegisterResponse>getUser = webInterface.getUser(phoneNumber);
        getUser.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().getCode() == 1 && response.body().getMessage().equals("User Found") && response.body().getData() != null) {

                            try {

                                User user = response.body().getData();
                                LoggedInUser loggedInUser = new LoggedInUser(Integer.parseInt(user.getUSER_ID()), user.getNAME(), user.getPHONE_NUMBER(), user.getREGISTRATION_DATE(), Integer.parseInt(user.getLAST_SEEN()),user.getPROFILE_PICTURE());
                                new DatabaseRepository(LoginActivity.this).loginUser(loggedInUser);

                                Toast.makeText(LoginActivity.this, "Successfully Logged In!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, UserChatsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                //System.out.println(response.body().getData().getNAME()+"\n"+response.body().getData().getPHONE_NUMBER());

                            } catch (Exception e) {

                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                        } else {

                            Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    } else {

                        Toast.makeText(LoginActivity.this, "Something Wrong!", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(LoginActivity.this, "Error - "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();

                }

                binding.progressBar.setVisibility(View.GONE);
                binding.loginButton.setEnabled(true);

            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {

                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);
                binding.loginButton.setEnabled(true);

            }
        });

    }
}