package fr.cned.emdsgil.suividevosfrais.AccesConnexion;

import org.json.JSONException;
import org.json.JSONObject;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;

public class IntermediaireArrierePlan implements AsyncResponse {

    private Controle controle;

    public IntermediaireArrierePlan(){
        controle = Controle.getInstance(null);
    };

    /**
     * Méthode qui permet de récupérer le résultat de la
     * requête gérée par TacheArrièrePlan et de la transmettre
     * au controleur.
     * Si le résultat correspond aux données du visiteur, on renvoi
     * l'id de ce visiteur, sinon on renvoi "1" ce qui qui signifie
     * mdp incorrect
     *
     * @param output résultat de la requête
     */
    @Override
    public void processFinish(String output) {
        // vérification contenu des données : 1 = mdp incorrect
        if (!output.equals("1")){
            // si output != 1 on envoi les données décodées
            try {
                JSONObject outputJSON = new JSONObject(output);
                // récupération de l'ID du visiteur
                String id = outputJSON.getString("id");
                controle.RetourRequete(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // si output = 1 : envoi "1"
            controle.RetourRequete("1");
        }
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
