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
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private List<RetourWS> retourWSs;
    private FrameLayout frameLayoutConteneurDetail = null;
    CarAdapter carAdapter = null;
    private RecyclerView recyclerView = null;
    private Switch mySwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayoutConteneurDetail = findViewById(R.id.conteneur_detail);
        mySwitch  = (Switch)findViewById(R.id.favoris_switch);
        if (mySwitch != null){
        }

        recyclerView = findViewById(R.id.liste_cars);


        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();
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

    public void onClicItem(int position)
    {
        CarAdapter carAdapter = new CarAdapter(this, retourWSs);
        RetourWS retourWS = carAdapter.getItemParPosition(position);

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

            fragment.setArguments(bundle);

            // le conteneur de la partie détail est disponible, on est donc en mode "tablette" :
            getSupportFragmentManager().beginTransaction().replace(R.id.conteneur_detail, fragment).commit();
        }
        else
        {
            // le conteneur de la partie détail n'est pas disponible, on est donc en mode "smartphone" :
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.CAR, Parcels.wrap(retourWS));
            startActivity(intent);
        }
    }

    public void updateRecyclerView(List<RetourWS> listeCars){
        carAdapter = new CarAdapter(this, listeCars);
        recyclerView.setAdapter(carAdapter);
    }

    public void onCheckedChanged(Switch mySwitch, boolean isChecked) {
        Toast.makeText(this, "The Switch is " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
        if(isChecked) {
            //do stuff when Switch is ON
        } else {
            //do stuff when Switch if OFF
        }
    }


}