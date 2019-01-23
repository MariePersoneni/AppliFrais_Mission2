package fr.cned.emdsgil.suividevosfrais.Donnees;

import java.util.ArrayList;
import java.util.List;

public class Visiteur {
    /**
     * Propriétés
     */
    private static Visiteur instance = null;
    private static String id;
    private List lesLignesFraisForfait;
    private List lesFichesDeFrais;

    /**
     * Constructeur
     * @param idVisiteur
     */
    public Visiteur(String idVisiteur){
        id = idVisiteur;
        lesLignesFraisForfait = new ArrayList<LigneFraisForfait>();
        lesFichesDeFrais = new ArrayList<FicheFrais>();
    }

    /**
     * Retourne l'instance de Visiteur en cours, et la créée si
     * elle n'existe pas
     * @param idVisiteur
     * @return
     */
    public static Visiteur getInstance(String idVisiteur){
        if (Visiteur.instance == null) {
            Visiteur.instance = new Visiteur(idVisiteur);
        }
        return Visiteur.instance;
    }

    /**
     * Setters
     */
    public void setLesLignesFraisForfait(List<LigneFraisForfait> lesLignesFraisForfait) { this.lesLignesFraisForfait = lesLignesFraisForfait; }
    public List getLesFichesDeFrais() { return lesFichesDeFrais; }

    /**
     * Getters
     */
    public static String getId() {return id;}
    public List<LigneFraisForfait> getLesLignesFraisForfait() {return lesLignesFraisForfait;}
    public void setLesFichesDeFrais(List lesFichesDeFrais) { this.lesFichesDeFrais = lesFichesDeFrais; }
}
