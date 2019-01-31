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
    private List lesLignesFraisHF;

    /**
     * Constructeur
     * @param idVisiteur
     */
    public Visiteur(String idVisiteur){
        id = idVisiteur;
        lesLignesFraisForfait = new ArrayList<LigneFraisForfait>();
        lesFichesDeFrais = new ArrayList<FicheFrais>();
        lesLignesFraisHF = new ArrayList<LigneFraisHorsForfait>();
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
    public void setLesFichesDeFrais(List lesFichesDeFrais) { this.lesFichesDeFrais = lesFichesDeFrais; }
    public void setLesLignesFraisHF(List lesLignesFraisHF) { this.lesLignesFraisHF = lesLignesFraisHF; }

    /**
     * Getters
     */
    public static String getId() {return id;}
    public List<LigneFraisForfait> getLesLignesFraisForfait() {return lesLignesFraisForfait;}
    public List getLesFichesDeFrais() { return lesFichesDeFrais; }
    public List getLesLignesFraisHF() { return lesLignesFraisHF; }

    public void SuppLigneFraisHF(LigneFraisHorsForfait ligneFraisHF) {
        this.lesLignesFraisHF.remove(ligneFraisHF);
    }
}
