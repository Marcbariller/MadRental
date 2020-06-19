package com.example.madrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.madrental.BDD.AppDatabaseHelper;
import com.example.madrental.BDD.CarDTO;
import com.example.madrental.WS.RetourWS;

import org.parceler.Parcels;

public class DetailActivity extends AppCompatActivity {
    public static String CAR = "CAR";

    RetourWS retourWS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        AppDatabaseHelper.getDatabase(this);

        DetailFragment detailFragment = new DetailFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main, detailFragment).commit();


        // envoi des détails de la voiture au fragment.
        Bundle bundle = new Bundle();
        retourWS = Parcels.unwrap(getIntent().getParcelableExtra(CAR));

        bundle.putString(DetailFragment.CAR_NOM, retourWS.nom);
        bundle.putString(DetailFragment.CAR_IMAGE, retourWS.image);
        bundle.putInt(DetailFragment.CAR_PRIX, retourWS.prixjournalierbase);
        bundle.putString(DetailFragment.CAR_CAT, retourWS.categorieco2);
        bundle.putInt(DetailFragment.CAR_DISPO, retourWS.disponible);
        bundle.putInt(DetailFragment.CAR_PROMO, retourWS.promotion);
        bundle.putInt(DetailFragment.CAR_AGEMIN, retourWS.agemin);
        detailFragment.setArguments(bundle);



    }

    // Fonction d'ajout dans la bdd de la voiture
    public void clicBouton(View view)   {
        Button button = findViewById(R.id.favoris);
        // Vérification de la présence de la voiture en BDD
        long nbrCar = AppDatabaseHelper.getDatabase(this).carDAO().countCarsParName(retourWS.nom);
        // si pas présente on l'ajoute
        if (nbrCar == 0){
            CarDTO carDTO = new CarDTO(retourWS.nom, retourWS.image, retourWS.prixjournalierbase, retourWS.categorieco2, retourWS.disponible, retourWS.promotion, retourWS.agemin);
            AppDatabaseHelper.getDatabase(this).carDAO().insert(carDTO);

            // changement du design du bouton pour supprimer des favoris
            button.setText("Supprimer des favoris");
            button.setBackgroundColor(Color.parseColor("#B22222"));
            Toast.makeText(view.getContext(),"Véhicule ajouté à vos favoris", Toast.LENGTH_SHORT).show();
        }
        // sinon on supprime de la BDD
        else{
            AppDatabaseHelper.getDatabase(this).carDAO().deleteCar(retourWS.nom);

            // changement du design du bouton pour ajouter au favoris
            button.setText("Ajouter aux favoris");
            button.setBackgroundColor(Color.parseColor("#007600"));
            Toast.makeText(view.getContext(),"Véhicule supprimé de vos favoris", Toast.LENGTH_SHORT).show();
        }

    }

}