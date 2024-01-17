package com.example.chatter.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.example.chatter.Adapters.UsersAdapter;
import com.example.chatter.Database.DatabaseRepository;
import com.example.chatter.Interfaces.ListClickEvents;
import com.example.chatter.Models.LoggedInUser;
import com.example.chatter.Models.User;
import com.example.chatter.Models.UserModel;
import com.example.chatter.Web.RetroServer;
import com.example.chatter.Web.WebInterface;
import com.example.chatter.databinding.ActivityUserChatsBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserChatsActivity extends AppCompatActivity implements ListClickEvents {

    private ActivityUserChatsBinding binding;
    private List<UserModel>userList;
    private UsersAdapter usersAdapter;
    private DatabaseRepository databaseRepository;

    String task;
    private LoggedInUser loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserChatsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Users");

        }

        databaseRepository = new DatabaseRepository(this);

        if (getIntent().hasExtra("task")) {

            task = getIntent().getStringExtra("task");

        }

        setDataForRecyclerView();
        //get data from online database
        getUsersFromLocaleDatabase();
        //get data from online database
        getUsersServer();

    }

    private void getUsersFromLocaleDatabase() {

        LiveData<List<UserModel>> allUsers = databaseRepository.getAllUsers();

        if (allUsers != null) {

            binding.progressBar.setVisibility(View.VISIBLE);
            allUsers.observe(this, new Observer<List<UserModel>>() {
                @Override
                public void onChanged(List<UserModel> userModelsList) {

                    userList.clear();

                    databaseRepository.getLoggedInUser().observe(UserChatsActivity.this, new Observer<LoggedInUser>() {
                        @Override
                        public void onChanged(LoggedInUser loggedInUser) {

                            if (loggedInUser != null) {

                                UserChatsActivity.this.loggedInUser = loggedInUser;

                                for (int i = 0; i < userModelsList.size(); i++) {

                                    UserModel userModel = userModelsList.get(i);

                                    if (loggedInUser.getUserID() != userModel.getUserID())

                                        userList.add(userModel);

                                }

                            }

                            usersAdapter.notifyDataSetChanged();

                        }
                    });

                    binding.progressBar.setVisibility(View.GONE);

                }
            });

        }

    }

    private void setDataForRecyclerView() {

        userList = new ArrayList<>();
        usersAdapter = new UsersAdapter(this, userList, this);

        binding.allUsersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.allUsersRecyclerView.setHasFixedSize(true);
        binding.allUsersRecyclerView.setAdapter(usersAdapter);

    }

    private void getUsersServer() {

        binding.progressBar.setVisibility(View.VISIBLE);

        WebInterface webInterface = RetroServer.connectRetrofit().create(WebInterface.class);
        Call<List<User>>listCall = webInterface.getAllUsers();
        listCall.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {

                List<UserModel>userModelList =  new ArrayList<>();

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        //userList.clear();
                        //userList.addAll(response.body());
                        //usersAdapter.notifyDataSetChanged();
                        for (int i = 0; i < response.body().size(); i++) {

                            User user = response.body().get(i);
                            UserModel userModel = new UserModel(Integer.parseInt(user.getUSER_ID()), user.getNAME(), user.getPHONE_NUMBER(), user.getREGISTRATION_DATE(), Integer.parseInt(user.getLAST_SEEN()), user.getPROFILE_PICTURE());
                            userModelList.add(userModel);

                        }

                       databaseRepository.saveUsers(userModelList);

                    } else {

                        Toast.makeText(UserChatsActivity.this, "Something Wrong!", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(UserChatsActivity.this, "Error - "+response.errorBody().toString(), Toast.LENGTH_SHORT).show();

                }

                binding.progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {

                Toast.makeText(UserChatsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                binding.progressBar.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onClickUserItem(int position) {

        if (task != null) {

            if (task.equals("pickUser")) {

                Intent intent = new Intent();
                intent.putExtra("userID", String.valueOf(userList.get(position).getUserID()));
                intent.putExtra("senderID", String.valueOf(loggedInUser.getUserID()));
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

        }

    }
}