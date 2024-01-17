package com.example.chatter.Database;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.chatter.Models.LoggedInUser;
import com.example.chatter.Models.Message;
import com.example.chatter.Models.User;
import com.example.chatter.Models.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseRepository {

    private Context context;
    private Database database;
    private DatabaseInterface databaseInterface;

    public DatabaseRepository(Context context) {

        this.context = context;
        database = Database.getInstance(context);
        databaseInterface = database.dbInterface();

    }

    public LiveData<LoggedInUser> getLoggedInUser() {
        return databaseInterface.getLoggedInUser();
    }

    public LiveData<UserModel> getUserByID(int id) {

        return databaseInterface.getUserByID(id);

    }

    public void saveUsers(List<UserModel>userList) {

        database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                try {

                    for (UserModel userData: userList) {

                        UserModel localUser = databaseInterface.getUser(userData.getUserID());
                        UserModel userModel = new UserModel(userData.getUserID(), userData.getName(), userData.getPhoneNumber(), userData.getRegistrationDate(), userData.getLastSeen(), userData.getProfilePicture());

                        if (localUser == null) {

                            System.out.println("USERS_NOT_FOUND & Can BE INSERTED - "+userData.getName());
                            databaseInterface.insertUsers(userModel);

                        } else {

                            System.out.println("USERS__FOUND & So Can BE UPDATED - "+userData.getName());
                            databaseInterface.updateUsers(userModel);

                        }

                    }

                } catch (Exception e) {

                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void loginUser(LoggedInUser loggedInUser) {

        database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                try {

                    databaseInterface.loginUser(loggedInUser);

                } catch (Exception e) {

                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public void logOutUser() {

        database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                try {

                    databaseInterface.logOutUser();
                    Toast.makeText(context, "Logged Out!", Toast.LENGTH_SHORT).show();

                } catch (Exception e) {

                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    public LiveData<List<UserModel>> getAllUsers() {

        return databaseInterface.getAllUsers();

    }

    public void saveMessages(List<Message>messageList) {

        database.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                for (Message message : messageList) {

                    Message messageObject = databaseInterface.getMessageByID(message.getMessageID());

                    if (messageObject == null) {

                        databaseInterface.saveMessage(message);
                        System.out.println("Message Inserted");

                    } else {

                        databaseInterface.updateMessage(message);
                        System.out.println("Message Updated");

                    }

                }

            }
        });

    }

    public LiveData<List<Message>>getChat(int sender, int receiver) {

        return databaseInterface.getChats(sender, receiver);

    }

}