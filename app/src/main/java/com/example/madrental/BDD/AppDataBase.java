package com.example.madrental.BDD;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {CarDTO.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract CarDAO carDAO();
}
