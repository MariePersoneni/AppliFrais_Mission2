package fr.cned.emdsgil.suividevosfrais.Controleur;

import android.content.Context;

import fr.cned.emdsgil.suividevosfrais.AccesConnexion.IntermediaireArrierePlan;
import fr.cned.emdsgil.suividevosfrais.MainActivity;

public class Controle {
    /**
     * Constantes
     */
    public final static String GET_ID_VISITEUR = "getIdVisiteur";
    public final static String GET_LIGNE_FRAIS_FORFAIT = "getLignesFraisForfait";
    public final  static String GET_FICHES_FRAIS = "getFichesDeFrais";
    public final static String MAJ_LIGNE_FRAIS_FORFAIT = "MAJligneFraisForfait";
    public final static String CREER_FICHE_FRAIS = "creeFicheFrais";
    public final static String GET_LIGNE_FRAIS_HF = "getLignesFraisHF";

    /**
     * Propriétés
     */
    private static Controle instance = null;
    private static Context context;
    private static IntermediaireArrierePlan intermediaireArrierePlan;

    private Controle(){
        super();
    }

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
            intermediaireArrierePlan = new IntermediaireArrierePlan();
        }
        return Controle.instance;
    }

    /**
     * Méthode quie demande à l'intermédiaire de lancer la connexion
     * au serveur en lui envoyer le profil et le mdp
     * récupéré sur l'activity de connexion
     *
     * @param login
     * @param mdp
     */
    public void lanceRequete(String login, String mdp){
        intermediaireArrierePlan.envoiDemandeConnexion(login, mdp);
    }

    /**
     * Méthode qui récupère le résultat de la requête de la part de
     * l'intermédiaire et qui l'envoiDemandeConnexion à la page de connexion (MainActivity)
     * @param s
     */
    public void RetourRequete_getIdVisiteur(String s){
        ((MainActivity)context).afficheResultat(s);
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
}
