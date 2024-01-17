package com.example.chatter.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.chatter.Database.DatabaseRepository;
import com.example.chatter.Models.LoggedInUser;
import com.example.chatter.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding;
    private DatabaseRepository databaseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseRepository = new DatabaseRepository(this);

        checkAuth();

    }

    private void checkAuth() {

        databaseRepository.getLoggedInUser().observe(this, new Observer<LoggedInUser>() {
            @Override
            public void onChanged(LoggedInUser loggedInUser) {

                if (loggedInUser == null) {

                    startActivity(new Intent(StartActivity.this, RegisterActivity.class));
                    finish();

                } else {

                    startActivity(new Intent(StartActivity.this, MainActivity.class));
                    finish();
                    Toast.makeText(StartActivity.this, "Already Logged In!", Toast.LENGTH_SHORT).show();
                    System.out.println("Already Logged In!");

                }

            }
        });

    }
}