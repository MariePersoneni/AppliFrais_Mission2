package fr.cned.emdsgil.suividevosfrais.Donnees;

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
}
