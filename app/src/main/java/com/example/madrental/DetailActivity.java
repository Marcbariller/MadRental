package com.example.madrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

    public void clicBouton(View view)   {
        long nbrCar = AppDatabaseHelper.getDatabase(this).carDAO().countCarsParName(retourWS.nom);
        if (nbrCar == 0){
            CarDTO carDTO = new CarDTO(retourWS.nom, retourWS.image, retourWS.prixjournalierbase, retourWS.categorieco2, retourWS.disponible, retourWS.promotion, retourWS.agemin);
            AppDatabaseHelper.getDatabase(this).carDAO().insert(carDTO);
            Toast.makeText(view.getContext(),"Véhicule ajouté à vos favoris", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(view.getContext(),"Ce véhicule est déjà dans vos favoris", Toast.LENGTH_SHORT).show();
        }

    }

}