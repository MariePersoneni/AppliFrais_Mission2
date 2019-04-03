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
    public LigneFraisForfait(String mois, String idFraisForfait, String idFraisKm, int quantite, String numero){
        this.idFraisForfait = idFraisForfait;
        this.idFraisKm = idFraisKm;
        this.idvisiteur = Visiteur.getId();
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
    public String getIdFraisKm() { return idFraisKm; }

    /**
     * Redéfinition de la méthode equals() de la classe Object
     * pour pouvoir filtrer les liste uniquement les infos dont
     * on a besoin cad : idVisiteur, mois, type de frais
     * @param o : élément à comparer
     * @return true s'il y a correspondance
     *          sinon false
     */
    @Override
    public boolean equals(Object o) {
        return Objects.equals(mois, ((LigneFraisForfait) o).mois) && Objects.equals(idFraisForfait, ((LigneFraisForfait) o).idFraisForfait ) && Objects.equals(idFraisKm, ((LigneFraisForfait) o).idFraisKm );
    }
}
