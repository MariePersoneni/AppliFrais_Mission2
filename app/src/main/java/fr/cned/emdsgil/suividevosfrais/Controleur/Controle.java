package fr.cned.emdsgil.suividevosfrais.Controleur;

import android.content.Context;

import fr.cned.emdsgil.suividevosfrais.AccesConnexion.IntermediaireArrierePlan;
import fr.cned.emdsgil.suividevosfrais.Donnees.LigneFraisHorsForfait;
import fr.cned.emdsgil.suividevosfrais.Donnees.Visiteur;
import fr.cned.emdsgil.suividevosfrais.MainActivity;

public class Controle {
    /**
     * Constantes
     */
    public final static String GET_ID_VISITEUR          = "getIdVisiteur";
    public final static String GET_LIGNES_FRAIS_FORFAIT = "getLignesFraisForfait";
    public final static String GET_FICHES_FRAIS         = "getFichesDeFrais";
    public final static String UPD_LIGNE_FRAIS_FORFAIT  = "MAJligneFraisForfait";
    public final static String CRE_FICHE_FRAIS          = "creeFicheFrais";
    public final static String GET_LIGNES_FRAIS_HF      = "getLignesFraisHF";
    public final static String DEL_LIGNE_FRAIS_HF       = "suppLigneFraisHF";
    public final static String CRE_LIGNE_FRAIS_HF       = "creerLigneFraisHF";

    /**
     * Propriétés
     */
    private static Controle instance = null;
    private static Context context;
    private static IntermediaireArrierePlan intermediaireArrierePlan;
    private static Visiteur visiteur;

    private Controle(){super(); }

    /**
     * Fonction qui retourne l'instance de Controle en cours
     * ou qui en créé une si elle n'existe pas déjà
     *
     * @param context
     * @return l'instance de controle
     */
    public static final Controle getInstance(Context context){
        if (context != null){
            Controle.context = context;
        }
        if(Controle.instance == null){
            Controle.instance = new Controle();
            /* initialisation de l'intermédiaire qui dialoguera
            *  avec la tâche d'arrière plan */
            intermediaireArrierePlan = new IntermediaireArrierePlan();
        }
        return Controle.instance;
    }


    /************************************************************************
     * METHODES DE CONNEXION
     * Ces méthodes demandent à l'intermédiaire de lancer la connexion et
     * d'envoyer des requêtes spécifiques.
     ************************************************************************/
    public void getIdVisiteur(String login, String mdp){
        intermediaireArrierePlan.envoiDemandeConnexion(login, mdp);
    }

    public void getLesLignesFraisForfait(String idVisiteur) {
        intermediaireArrierePlan.envoiDemandeFraisForfait(idVisiteur);
    }

    public void MAJligneFraisForfait(String idVisiteur, String mois, String numero, String qte) {
        intermediaireArrierePlan.envoiDemandeMAJligneFraisForfait(idVisiteur,mois,numero, qte);
    }

    public void getLesFichesDeFrais(String idVisiteur) {
        intermediaireArrierePlan.envoiDemandeFicheFrais(idVisiteur);
    }

    public void creerFicheFrais(String idVisiteur, String anneeMois, String moisPrecedent) {
        intermediaireArrierePlan.envoiDemandeCreerFicheFrais(idVisiteur,anneeMois, moisPrecedent);
    }

    public void getLesLignesFraisHF(String idVisiteur) {
        intermediaireArrierePlan.envoiDemandeFraisHF(idVisiteur);
    }

    public void suppLigneHorsForfait(Integer id) {
        intermediaireArrierePlan.envoiDemandeSuppLigneHF(id);
    }

    public void creerLigneFraisHF(String id, String anneeMois, String motif, String date, Float montant) {
        intermediaireArrierePlan.envoiDemandeCreerLigneHF(id, anneeMois, motif, date, montant);
    }

    public void suppLigneHF(LigneFraisHorsForfait ligneFraisHF) {
        visiteur.SuppLigneFraisHF(ligneFraisHF);
    }
    /************************************************************************
     ************************************************************************/

    /**
     * Méthode qui récupère le résultat de la requête getIdVisiteur et qui vérifie
     * si le login et le mot de passe sont corrects.
     * @param idVisiteur
     * @param nom
     * @param prenom
     */
    public void RetourRequete_getIdVisiteur(String idVisiteur, String nom, String prenom){
        if (idVisiteur.equals("1")){
            ((MainActivity)context).mdpIncorrect();
        } else {
            // création du visiteur
            Visiteur leVisiteur = new Visiteur(idVisiteur, nom, prenom);
            visiteur = Visiteur.getInstance(Visiteur.getId());
            ((MainActivity)context).connexionMenu();
        }
    }
}
