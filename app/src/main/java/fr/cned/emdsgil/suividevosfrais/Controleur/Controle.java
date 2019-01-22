package fr.cned.emdsgil.suividevosfrais.Controleur;

import android.content.Context;

import java.util.List;

import fr.cned.emdsgil.suividevosfrais.AccesConnexion.IntermediaireArrierePlan;
import fr.cned.emdsgil.suividevosfrais.MainActivity;
import fr.cned.emdsgil.suividevosfrais.MenuActivity;

public class Controle {
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
        intermediaireArrierePlan.envoi(login, mdp);
    }

    /**
     * Méthode qui récupère le résultat de la requête de la part de
     * l'intermédiaire et qui l'envoi à la page de connexion (MainActivity)
     * @param s
     */
    public void RetourRequete_getIdVisiteur(String s){
        ((MainActivity)context).afficheResultat(s);
    }

    public void getLesLignesFraisForfait(String idVisiteur) {
        intermediaireArrierePlan.envoiDemandeFraisForfait(idVisiteur);
    }

    public void RetourRequete_getLesLignesFraisForfait(List<String[]> lesLignesFraisForfait) {
        ((MenuActivity)context).valoriseLesLignesFraisForfait(lesLignesFraisForfait);
    }
}
