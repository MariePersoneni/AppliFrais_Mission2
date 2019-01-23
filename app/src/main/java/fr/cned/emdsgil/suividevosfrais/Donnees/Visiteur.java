package fr.cned.emdsgil.suividevosfrais.Donnees;

import java.util.ArrayList;
import java.util.List;

public class Visiteur {
    private static Visiteur instance = null;
    private static String id;
    private List lesLignesFraisForfait;

    public Visiteur(String idVisiteur){
        id = idVisiteur;
        lesLignesFraisForfait = new ArrayList<LigneFraisForfait>();
    }

    public static Visiteur getInstance(String idVisiteur){
        if (Visiteur.instance == null) {
            Visiteur.instance = new Visiteur(idVisiteur);
        }
        return Visiteur.instance;
    }

    public static String getId() {
        return id;
    }

    public List<LigneFraisForfait> getLesLignesFraisForfait() {
        return lesLignesFraisForfait;
    }

    public void setLesLignesFraisForfait(List<LigneFraisForfait> lesLignesFraisForfait) {
        this.lesLignesFraisForfait = lesLignesFraisForfait;
    }
}
