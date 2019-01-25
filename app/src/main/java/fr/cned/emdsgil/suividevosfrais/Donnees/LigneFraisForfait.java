package fr.cned.emdsgil.suividevosfrais.Donnees;

import java.util.Objects;

public class LigneFraisForfait{

    /**
     * Propriétés privées
     */
    private String idvisiteur;
    private String mois;
    private String idFraisForfait;
    private String idFraisKm;
    private int quantite;
    private String numero;

    /**
     * Constructeur qui valorise les propriétés privées
     */
    public LigneFraisForfait(String idvisiteur, String mois, String idFraisForfait, String idFraisKm, int quantite, String numero){
        this.idFraisForfait = idFraisForfait;
        this.idFraisKm = idFraisKm;
        this.idvisiteur = idvisiteur;
        this.mois = mois;
        this.numero = numero;
        this.quantite = quantite;
    }

    /**
     * Getters
     */
    public String getMois() { return mois; }
    public int getQuantite() { return quantite; }
    public String getNumero() { return numero; }

    /**
     * Redéfinition de la méthode equals() de la classe Object
     * pour pouvoir filtrer les liste uniquement les infos dont
     * on a besoin cad : idVisiteur, mois, type de frais
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        LigneFraisForfait ligne = (LigneFraisForfait)o;
        return Objects.equals(mois, ((LigneFraisForfait) o).mois) && Objects.equals(idFraisForfait, ((LigneFraisForfait) o).idFraisForfait);
    }
}
