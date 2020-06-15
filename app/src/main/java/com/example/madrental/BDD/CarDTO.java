package com.example.madrental.BDD;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URI;

@Entity(tableName = "madCar")
public class CarDTO {
    @PrimaryKey(autoGenerate = true)
    public long carId = 0;
    public String nom;
    public String image;
    public int prixjournalierbase;
    public String categorieco2;
    public int disponible;
    public int promotion;
    public int agemin;

    public CarDTO(String nom, String image, int prixjournalierbase, String categorieco2, int disponible, int promotion, int agemin) {
        this.nom = nom;
        this.image = image;
        this.prixjournalierbase = prixjournalierbase;
        this.categorieco2 = categorieco2;
        this.disponible = disponible;
        this.promotion = promotion;
        this.agemin = agemin;
    }

}
