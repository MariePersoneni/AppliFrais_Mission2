package fr.cned.emdsgil.suividevosfrais.Donnees;

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
}
