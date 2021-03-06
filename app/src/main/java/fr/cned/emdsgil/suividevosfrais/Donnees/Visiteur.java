package fr.cned.emdsgil.suividevosfrais.Donnees;

import java.util.ArrayList;
import java.util.List;

public class Visiteur {
    /**
     * Propriétés
     */
    private static Visiteur instance = null;
    private static String id;
    private static String nom;
    private static String prenom;
    private static List lesLignesFraisForfait;
    private static List lesFichesDeFrais;
    private static List lesLignesFraisHF;

    /**
     * Constructeur
     * @param idVisiteur
     * @param nom
     * @param prenom
     */
    public Visiteur(String idVisiteur, String nom, String prenom){
        id = idVisiteur;
        this.nom = nom;
        this.prenom = prenom;
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
            Visiteur.instance = new Visiteur(idVisiteur, nom, prenom);
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
    public static List getLesLignesFraisForfait() {return lesLignesFraisForfait;}
    public static List getLesFichesDeFrais() { return lesFichesDeFrais; }
    public static List getLesLignesFraisHF() { return lesLignesFraisHF; }
    public static String getNom() { return nom; }
    public static String getPrenom() { return prenom; }

    /**
     * Supprime la ligne de frais HF passée en paramètre
     * de la liste des lignes de frais HF du visiteur
     * @param ligneFraisHF
     */
    public void SuppLigneFraisHF(LigneFraisHorsForfait ligneFraisHF) {
        this.lesLignesFraisHF.remove(ligneFraisHF);
    }
}
