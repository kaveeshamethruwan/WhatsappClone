package com.example.chatter.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chatter.Database.DatabaseRepository;
import com.example.chatter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DatabaseRepository databaseRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseRepository = new DatabaseRepository(this);

        binding.openChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                //startActivity(new Intent(MainActivity.this, UserChatsActivity.class));
                pickUser();

            }
        });

        binding.logOutButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        logOutUser();

                    }
                });

    }

    private void pickUser() {

        Intent intent = new Intent(this, UserChatsActivity.class);
        intent.putExtra("task", "pickUser");
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {

            if (data != null) {

                if (data.hasExtra("userID")) {

                    String userID = data.getStringExtra("userID");
                    String senderID = data.getStringExtra("senderID");

                    if (userID != null && senderID != null) {

                        Intent intent = new Intent(this, ChatActivity.class);
                        intent.putExtra("userID", userID);
                        intent.putExtra("senderID", senderID);
                        startActivity(intent);

                    }

                }

            }

        }

    }

    private void logOutUser() {

        try {

            databaseRepository.logOutUser();;

            //Toast.makeText(MainActivity.this, "response.body()", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } catch (Exception e) {

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }
}