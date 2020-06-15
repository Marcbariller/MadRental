package com.example.madrental.BDD;

import android.content.Context;

import androidx.room.Room;

public class AppDatabaseHelper {
    private static AppDatabaseHelper databaseHelper = null;
    public AppDataBase database;

    private AppDatabaseHelper(Context context) {
        database = Room.databaseBuilder(context, AppDataBase.class, "cars").allowMainThreadQueries().build();
    }

    // Getter instance :
    public static synchronized AppDataBase getDatabase(Context context){
        if (databaseHelper == null) {
            databaseHelper = new AppDatabaseHelper(context.getApplicationContext());
        }        return databaseHelper.database;
    }
}