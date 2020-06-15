package com.example.madrental;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class DetailFragment extends Fragment {

    public static String CAR_NOM = "CAR_NOM";
    public static String CAR_IMAGE = "CAR_IMAGE";
    public static String CAR_PRIX = "CAR_PRIX";
    public static String CAR_CAT = "CAR_CAT";
    public static String CAR_DISPO = "CAR_DISPO";
    public static String CAR_PROMO = "CAR_PROMO";
    public static String CAR_AGEMIN = "CAR_AGEMIN";

    // TODO: Rename and change types of parameters
    private String carNom;
    private String carImage;
    private int carPrix;
    private String carCat;
    private int carDispo;
    private int carPromo;
    private int carAgemin;



    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            carNom = getArguments().getString(CAR_NOM);
            carImage = getArguments().getString(CAR_IMAGE);
            carPrix = getArguments().getInt(CAR_PRIX);
            carCat = getArguments().getString(CAR_CAT);
            carDispo = getArguments().getInt(CAR_DISPO);
            carPromo = getArguments().getInt(CAR_PROMO);
            carAgemin = getArguments().getInt(CAR_AGEMIN);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ImageView imageView = view.findViewById(R.id.image);
        TextView textNom = view.findViewById(R.id.nom);
        TextView textPrix = view.findViewById(R.id.prix);
        TextView textCat = view.findViewById(R.id.categorie);
        TextView textDispo = view.findViewById(R.id.disponibilite);
        TextView textPromo = view.findViewById(R.id.promotion);
        TextView textAge = view.findViewById(R.id.agemin);

        Picasso.with(view.getContext()).load("http://s519716619.onlinehome.fr/exchange/madrental/images/"+carImage).fit().centerCrop().into(imageView);
        textNom.setText(carNom);
        textPrix.setText(carPrix+"€ / jour");
        textCat.setText(carCat);
        if (carDispo == 1){
            textDispo.setText("Véhicule disponible");
        }else{
            textDispo.setText("Ce véhicule n'est pas disponible");
        }
        if (carPromo > 0){
            textPromo.setText(carPromo+"€");
        }else{
            textPromo.setText("Pas de promotion sur ce véhicule");
        }
        textAge.setText("Vous devez avoir "+carAgemin+" ans pour louer ce véhicule");

        return view;
    }


}