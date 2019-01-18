package fr.cned.emdsgil.suividevosfrais.AccesConnexion;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;

public class IntermediaireArrierePlan implements AsyncResponse {

    private Controle controle;

    public IntermediaireArrierePlan(){
        controle = Controle.getInstance(null);
    };

    /**
     * Méthode qui permet de récupérer le résultat de la
     * requête gérée par TacheArrièrePlan et de la transmettre
     * au controleur
     *
     * @param output résultat de la requête
     */
    @Override
    public void processFinish(String output) {
        controle.RetourRequete(output);
    }

    /**
     * Méthode qui créé un objet TacheArrierePlan et lui demande
     * d'executer la requete de connexion.
     *
     * @param login : récupéré dans txtLogin de MainActivity
     * @param mdp : récupéré dans txtMdp de MainActivity
     */
    public void envoi(String login, String mdp){
        TacheArrierePlan tacheArrierePlan = new TacheArrierePlan();
        tacheArrierePlan.delegate = this;
        tacheArrierePlan.execute(login, mdp);
    }
}
