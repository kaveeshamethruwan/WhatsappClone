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
import com.example.chatter.databinding.ActivityRegisterBinding;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = Objects.requireNonNull(binding.userNameText.getText()).toString();
                String phoneNumber = Objects.requireNonNull(binding.phoneNumberText.getText()).toString();

                if (name.isEmpty()) {

                    binding.userNameText.requestFocus();
                    binding.userNameText.setError("Name Required");

                } else if (phoneNumber.isEmpty()) {

                    binding.phoneNumberText.requestFocus();
                    binding.phoneNumberText.setError("Phone Number Required");

                } else if (phoneNumber.length() < 10) {

                    binding.phoneNumberText.requestFocus();
                    Toast.makeText(RegisterActivity.this, "Enter Valid Number!", Toast.LENGTH_SHORT).show();

                } else {

                    registerUser(name, phoneNumber);

                }

            }
        });

        binding.loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }

    private void registerUser(String name, String phoneNumber) {

        binding.progressBar.setVisibility(View.VISIBLE);
        binding.registerButton.setEnabled(false);

        WebInterface webInterface = RetroServer.connectRetrofit().create(WebInterface.class);
        Call<RegisterResponse>registerUser = webInterface.registerUser(phoneNumber, name);
        registerUser.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        if (response.body().getCode() == 1 && response.body().getMessage().equals("Registration Completed!") && response.body().getData() != null) {

                            try {

                                User user = response.body().getData();
                                DatabaseRepository databaseRepository = new DatabaseRepository(RegisterActivity.this);
                                databaseRepository.loginUser(new LoggedInUser(Integer.parseInt(user.getUSER_ID()), user.getNAME(), user.getPHONE_NUMBER(), user.getREGISTRATION_DATE(), Integer.parseInt(user.getLAST_SEEN()), user.getPROFILE_PICTURE()));

                                Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, UserChatsActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } catch (Exception e) {

                                Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            //System.out.println(response.body().getData().getNAME()+" - LAST SEEN "+response.body().getData().getLAST_SEEN());

                        } else if (response.body().getCode() == 0 && response.body().getMessage().equals("User Already Exist") && response.body().getData() == null) {

                            Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        } else {

                            Toast.makeText(RegisterActivity.this, "Something Wrong - Try Again!", Toast.LENGTH_SHORT).show();

                        }

                    }

                } else {

                    Toast.makeText(RegisterActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();

                }

                binding.progressBar.setVisibility(View.GONE);
                binding.registerButton.setEnabled(true);

            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {

                Toast.makeText(RegisterActivity.this, "ERROR - "+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getLocalizedMessage());
                binding.progressBar.setVisibility(View.GONE);
                binding.registerButton.setEnabled(true);

            }
        });

    }
}