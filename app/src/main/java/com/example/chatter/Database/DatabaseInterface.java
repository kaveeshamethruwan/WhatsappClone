package com.example.chatter.Database;

import static com.example.chatter.Constants.*;
import androidx.lifecycle.LiveData;
import androidx.room.*;

import com.example.chatter.Models.LoggedInUser;
import com.example.chatter.Models.Message;
import com.example.chatter.Models.User;
import com.example.chatter.Models.UserModel;

import java.util.List;

@Dao
public interface DatabaseInterface {

    @Query("SELECT * FROM "+LOGGED_IN_USER_TABLE)
    LiveData<LoggedInUser> getLoggedInUser();

    @Insert
    void loginUser(LoggedInUser loggedInUser);

    @Query("DELETE FROM "+LOGGED_IN_USER_TABLE)
    void logOutUser();

    @Query("SELECT * FROM "+USERS_TABLE+" WHERE userID = :userID")
    UserModel getUser(int userID);

    @Query("SELECT * FROM "+USERS_TABLE+" WHERE userID = :userID")
    LiveData<UserModel> getUserByID(int userID);

    @Insert
    void insertUsers(UserModel userModel);

    @Update
    void updateUsers(UserModel userModel);

    @Query("SELECT * FROM "+USERS_TABLE+" ORDER BY userID ASC")
    LiveData<List<UserModel>> getAllUsers();

    @Query("SELECT * FROM "+USER_CHATS_TABLE+" WHERE messageID = :messageID")
    Message getMessageByID(long messageID);

    @Insert
    void saveMessage(Message message);

    @Update
    void updateMessage(Message message);

    @Query("SELECT * FROM " + USER_CHATS_TABLE + " WHERE (sender = :sender AND receiver = :receiver) OR (sender = :receiver AND receiver = :sender) ORDER BY messageID ASC")
    LiveData<List<Message>> getChats(int sender, int receiver);

    @Query("SELECT * FROM " + USER_CHATS_TABLE + " WHERE messageID = :messageID")
    Message get_message(int messageID);

}