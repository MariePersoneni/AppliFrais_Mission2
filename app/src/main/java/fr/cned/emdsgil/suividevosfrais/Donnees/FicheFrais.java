package fr.cned.emdsgil.suividevosfrais.Donnees;

import java.util.Objects;

public class FicheFrais {
    /**
     * Propriétés
     */
    private String idVisiteur;
    private String mois;
    private String etat;

    /**
     * Constructeur
     */
    public FicheFrais(String mois, String etat) {
        this.mois = mois;
        this.etat = etat;
        this.idVisiteur = Visiteur.getId();
    }

    /**
     * Redéfinition de la méthode equals() de la classe Object
     * Filtre uniquement sur le mois
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        FicheFrais ligne = (FicheFrais) o;
        return Objects.equals(mois, ((FicheFrais) o).mois);
    }

    /**
     * Getters
     */
    public String getEtat() { return etat; }
    public String getMois() { return mois; }
}
