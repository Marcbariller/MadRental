package com.example.madrental;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.example.madrental.BDD.AppDataBase;
import com.example.madrental.BDD.AppDatabaseHelper;
import com.example.madrental.BDD.CarDTO;
import com.example.madrental.WS.RetourWS;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // Vues
    private FrameLayout frameLayoutConteneurDetail = null;
    private RecyclerView recyclerView = null;
    private Switch mySwitch;

    // liste des voitures
    private List<RetourWS> retourWSs;
    private List<RetourWS> favorisCarsListe = null;

    // Adapter
    CarAdapter carAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // init
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // vues
        frameLayoutConteneurDetail = findViewById(R.id.conteneur_detail);
        recyclerView = findViewById(R.id.liste_cars);
        mySwitch  = findViewById(R.id.favoris_switch);

        // layout manager
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // récupération du contenu WS à la création
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://s519716619.onlinehome.fr/exchange/madrental/get-vehicules.php", new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                String retour = new String(response);
                Gson gson = new Gson();
                retourWSs = gson.fromJson(retour, new TypeToken<List<RetourWS>>(){}.getType());

                // Maj du recyclerview
                updateRecyclerView(retourWSs);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });

        // Si le switch est activé on liste les favoris de la BDD sinon on récupère les infos du WS
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Switch activé >> récupération des voitures favorites de la BDD
                if (isChecked) {
                    List<CarDTO> listeCars = AppDatabaseHelper.getDatabase(buttonView.getContext()).carDAO().getListeCars();
                    favorisCarsListe = new ArrayList<RetourWS>();
                    for (int i = 0; i < listeCars.size(); i++){
                        for (CarDTO car : listeCars){
                            RetourWS newCar = new RetourWS(car.nom, car.image, car.prixjournalierbase, car.categorieco2, car.disponible, car.promotion, car.agemin);
                            Boolean inFavorite;
                            if (favorisCarsListe != null){
                                inFavorite = favorisCarsListe.contains(newCar);
                                Log.i("bool",""+inFavorite );
                                if (inFavorite == false){
                                    favorisCarsListe.add(newCar);
                                }
                            }else{
                                favorisCarsListe.add(newCar);
                            }

                        }
                    }
                    // Maj du recyclerview
                    updateRecyclerView(favorisCarsListe);
                // Switch non activé >> récupération des voitures du WS
                } else {
                    AsyncHttpClient client = new AsyncHttpClient();
                    client.get("http://s519716619.onlinehome.fr/exchange/madrental/get-vehicules.php", new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                            String retour = new String(response);
                            Gson gson = new Gson();
                            retourWSs = gson.fromJson(retour, new TypeToken<List<RetourWS>>(){}.getType());
                            updateRecyclerView(retourWSs);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        }
                    });
                }
            }
        });




    }

    // quand une voiture du recycler view est cliquée
    public void onClicItem(int position)
    {
        // récupération de la voiture correspondant à cette position
        CarAdapter carAdapter = new CarAdapter(this, retourWSs);
        RetourWS retourWS = carAdapter.getItemParPosition(position);

        // le conteneur de la partie détail est disponible, on est donc en mode "tablette" :
        if (frameLayoutConteneurDetail != null)
        {
            DetailFragment fragment = new DetailFragment();
            Bundle bundle = new Bundle();
            bundle.putString(DetailFragment.CAR_NOM, retourWS.nom);
            bundle.putString(DetailFragment.CAR_IMAGE, retourWS.image);
            bundle.putInt(DetailFragment.CAR_PRIX, retourWS.prixjournalierbase);
            bundle.putString(DetailFragment.CAR_CAT, retourWS.categorieco2);
            bundle.putInt(DetailFragment.CAR_DISPO, retourWS.disponible);
            bundle.putInt(DetailFragment.CAR_PROMO, retourWS.promotion);
            bundle.putInt(DetailFragment.CAR_AGEMIN, retourWS.agemin);
            // envoi des détails de la voiture au fragment
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.conteneur_detail, fragment).commit();
        }

        // le conteneur de la partie détail n'est pas disponible, on est donc en mode "smartphone" :
        else
        {
            // envoi des détails de la voiture à la nouvelle activité
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.CAR, Parcels.wrap(retourWS));
            startActivity(intent);
        }
    }

    // Maj du recyclerview
    public void updateRecyclerView(List<RetourWS> listeCars){
        List<RetourWS> retourWSList = new ArrayList<RetourWS>();
        carAdapter = new CarAdapter(this, retourWSList);
        recyclerView.setAdapter(carAdapter);
        carAdapter.notifyDataSetChanged();

        carAdapter = new CarAdapter(this, listeCars);
        recyclerView.setAdapter(carAdapter);
        carAdapter.notifyDataSetChanged();
    }


}