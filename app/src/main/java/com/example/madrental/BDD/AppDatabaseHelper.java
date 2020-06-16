package com.example.madrental.BDD;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class AppDatabaseHelper {
    private static AppDatabaseHelper databaseHelper = null;
    public AppDataBase database;

    private AppDatabaseHelper(Context context) {
        database = Room.databaseBuilder(context, AppDataBase.class, "rentalCar").allowMainThreadQueries().build();
    }

    // Getter instance :
    public static synchronized AppDataBase getDatabase(Context context){
        if (databaseHelper == null) {
            databaseHelper = new AppDatabaseHelper(context.getApplicationContext());
        }        return databaseHelper.database;
    }

}