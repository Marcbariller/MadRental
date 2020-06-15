package com.example.madrental.BDD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class CarDAO  {
    @Query("SELECT * FROM madCar")
    public abstract List<CarDTO> getListeCars();

    @Query("SELECT COUNT(*) FROM madCar WHERE nom = :name")
    public abstract long countCarsParName(String name);

    @Insert
    public abstract void insert(CarDTO... carDTOS);

    @Update
    public abstract void update(CarDTO... carDTOS);

    @Delete
    public abstract void delete(CarDTO... carDTOS);
}
