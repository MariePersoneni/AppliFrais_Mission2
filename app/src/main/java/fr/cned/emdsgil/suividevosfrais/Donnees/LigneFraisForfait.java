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
    private int numero;

    /**
     * Constructeur qui valorise les propriétés privées
     */
    public LigneFraisForfait(String idvisiteur, String mois, String idFraisForfait, String idFraisKm, int quantite, int numero){
        this.idFraisForfait = idFraisForfait;
        this.idFraisKm = idFraisKm;
        this.idvisiteur = idvisiteur;
        this.mois = mois;
        this.numero = numero;
        this.quantite = quantite;
    }

    public String getMois() {
        return mois;
    }

    public int getQuantite() {
        return quantite;
    }

    @Override
    public boolean equals(Object o) {
        LigneFraisForfait ligne = (LigneFraisForfait)o;
//        if (((LigneFraisForfait) o).getMois().equals("201901")){
//            Boolean ok = true;
//            ok = ok;
//        }
        return Objects.equals(mois, ((LigneFraisForfait) o).mois) && Objects.equals(idFraisForfait, ((LigneFraisForfait) o).idFraisForfait);
    }
}
