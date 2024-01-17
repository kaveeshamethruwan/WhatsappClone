package com.example.chatter.Database;

import static com.example.chatter.Constants.*;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.chatter.Models.LoggedInUser;
import com.example.chatter.Models.Message;
import com.example.chatter.Models.UserModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@androidx.room.Database(entities = {
        LoggedInUser.class,
        UserModel.class,
        Message.class
}, version = 4, exportSchema = false)

public abstract class Database extends RoomDatabase {

    private static Database instance;
    private static final int NUMBER_OF_THREADS = 6;
    public final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract DatabaseInterface dbInterface();

    public static synchronized Database getInstance(Context context) {

        if (instance == null) {

            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class, DB_NAME).fallbackToDestructiveMigration().build();

        }

        return instance;

    }

}
