package fr.cned.emdsgil.suividevosfrais.Donnees;

import java.util.List;

public class Visiteur {
    private String id;
    private List<String[]> lesLignesFraisForfait;

    public Visiteur(String idVisiteur){
        id = idVisiteur;
    }
}
