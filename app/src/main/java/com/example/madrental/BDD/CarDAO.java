package com.example.madrental.BDD;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class CarDAO  {
    @Query("SELECT * FROM rentalCar")
    public abstract List<CarDTO> getListeCars();

    @Query("SELECT COUNT(*) FROM rentalCar WHERE nom = :name")
    public abstract long countCarsParName(String name);

    @Insert
    public abstract void insert(CarDTO... carDTOS);

    @Update
    public abstract void update(CarDTO... carDTOS);

    @Delete
    public abstract void delete(CarDTO... carDTOS);

    @Query("DELETE from rentalCAR WHERE nom = :name")
    public abstract void deleteCar(String name);
}
