package fr.cned.emdsgil.suividevosfrais.Donnees;

import java.util.Objects;

public class LigneFraisHorsForfait {
    /**
     * Propriétés
     */
    private int id;
    private String idVisiteur;
    private String  mois;
    private String libelle;
    private Integer jour;
    private Float montant;

    /**
     * Constructeur
     */
    public LigneFraisHorsForfait(int id, String mois, String libelle, Integer jour, float montant) {
        this.id = id;
        idVisiteur = Visiteur.getId();
        this.mois = mois;
        this.libelle = libelle;
        this.jour = jour;
        this.montant = montant;
    }

    /**
     * Getters
     */
    public String getLibelle() { return libelle; }
    public Integer getJour() { return jour; }
    public float getMontant() { return montant; }

    /**
     * Redéfinition de la méthode equals() de la classe Object
     * pour pouvoir filtrer les liste uniquement les infos dont
     * on a besoin cad : idVisiteur, mois, type de frais
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        LigneFraisHorsForfait ligne = (LigneFraisHorsForfait) o;
        return Objects.equals(mois, ((LigneFraisHorsForfait) o).mois);
    }
}
