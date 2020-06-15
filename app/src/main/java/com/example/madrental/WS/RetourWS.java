package com.example.madrental.WS;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class RetourWS {
    public String nom;
    public String image;
    public int prixjournalierbase;
    public String categorieco2;
    public int disponible;
    public int promotion;
    public int agemin;

    public RetourWS(String nom, String image, int prixjournalierbase, String categorieco2, int disponible, int promotion, int agemin) {
        this.nom = nom;
        this.image = image;
        this.prixjournalierbase = prixjournalierbase;
        this.categorieco2 = categorieco2;
        this.disponible = disponible;
        this.promotion = promotion;
        this.agemin = agemin;
    }

    public RetourWS(){

    }

    public String toString(){
        String retour;
        String promotionText = "";
        String Newligne =System.getProperty("line.separator");
        if (promotion > 0){
            promotionText =  Newligne+"Actuellement ce véhicule est en promotion, économisez "+promotion+"€ dès maintenant!";
        }
        if (disponible == 1){
            retour = nom+ " est disponible. Si vous avez au moins "+agemin+" ans et que vous pouvez payer "+prixjournalierbase+"€ par jour, alors vous serez accueilli correctement."+promotionText;
        }else{
            retour = "Désolé cette voiture n'est plus disponible actuellement. Retentez votre chance prochainement.";
        }

        return retour;
    }
}
