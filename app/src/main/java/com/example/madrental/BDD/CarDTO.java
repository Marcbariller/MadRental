package com.example.madrental.BDD;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.URI;

@Entity(tableName = "cars")
public class CarDTO {
    @PrimaryKey(autoGenerate = true)
    public long carId = 0;
    public String nom;
    public String image;
    public int prixjournalierbase;
    public String categorieco2;

    public CarDTO(String nom, String image, int prixjournalierbase, String categorieco2) {
        this.nom = nom;
        this.image = image;
        this.prixjournalierbase = prixjournalierbase;
        this.categorieco2 = categorieco2;
    }
}
