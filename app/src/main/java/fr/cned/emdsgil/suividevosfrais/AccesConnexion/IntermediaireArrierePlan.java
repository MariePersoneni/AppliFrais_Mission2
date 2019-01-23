package fr.cned.emdsgil.suividevosfrais.AccesConnexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Donnees.LigneFraisForfait;
import fr.cned.emdsgil.suividevosfrais.Donnees.Visiteur;

public class IntermediaireArrierePlan implements AsyncResponse {

    private Controle controle;
    private TacheArrierePlan tacheArrierePlan;

    public IntermediaireArrierePlan(){
        controle = Controle.getInstance(null);
    };

    /**
     * Méthode qui permet de récupérer le résultat de la
     * requête gérée par TacheArrièrePlan et de la transmettre
     * au controleur.
     *
     * @param output résultat de la requête
     */
    @Override
    public void processFinish(String output) {
        String action = tacheArrierePlan.getAction();
        switch (action){
            case "getIdVisiteur" :
                /**                 *
                 * Si le résultat correspond aux données du visiteur, on renvoi
                 * l'id de ce visiteur, sinon on renvoi "1" ce qui qui signifie
                 * mdp incorrect                 *
                 */
                if (!output.equals("1")){
                    try {
                        JSONObject outputJSON = new JSONObject(output);
                        // récupération de l'ID du visiteur
                        String id = outputJSON.getString("id");
                        controle.RetourRequete_getIdVisiteur(id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // si output = 1 : envoi "1"
                    controle.RetourRequete_getIdVisiteur("1");
                }
                break;
            case "getLignesFraisForfait" :
                if (output != null){
                    try{
                        JSONArray outputJSON = new JSONArray(output);
                        /**
                         * récupère le visiteur et la liste de ses frais forfait
                         */
                        String idVisiteur = Visiteur.getId();
                        Visiteur leVisiteur = Visiteur.getInstance(idVisiteur);
                        List lesLignesFraisForfait = new ArrayList();
                        /**
                         * Parcours le tableau JSON chiffré : [0[a;b;c];1[a;b;c]]
                         * i = 0 ; i = 1 ; i = 3...
                         */
                        for (int i = 0 ; i < outputJSON.length() ; i++){
                            JSONArray ligneFraisForfaitJSON = new JSONArray();
                            ligneFraisForfaitJSON = outputJSON.getJSONArray(i);
                            String[] ligneFraisForfait = new String[6];
                            /**
                             * Parcours le tableau JSON lettré : [0[a;b;c]...]
                             * j = a ; j = b ; j = c...
                             */
                            for (int j = 0 ; j < 6 ; j++){
                                ligneFraisForfait[j] = ligneFraisForfaitJSON.getString(j);
                            }
                            /**
                             * création de l'objet lignedefraisforfait
                             */
                            String id = ligneFraisForfait[0];
                            String mois = ligneFraisForfait[1];
                            String idFraisForfait = ligneFraisForfait[2];
                            String idFraisKm = ligneFraisForfait[3];
                            int quantite = Integer.parseInt(ligneFraisForfait[4]);
                            int numero = Integer.parseInt(ligneFraisForfait[5]);
                            LigneFraisForfait ligne = new LigneFraisForfait(id,mois,idFraisForfait,idFraisKm,quantite,numero);
                            /**
                             * Ajout de cet objet lignedefraisforfait dans la collection
                             * lesLignesFraisForfait
                             */
                            lesLignesFraisForfait.add(ligne);
                        }
                        /**
                         * valorisation de la collection du visiteur avec la
                         * collection créée alimentée ci-dessus
                         */
                        leVisiteur.setLesLignesFraisForfait(lesLignesFraisForfait);
                    } catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                break;
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
        tacheArrierePlan = new TacheArrierePlan();
        tacheArrierePlan.delegate = this;
        tacheArrierePlan.execute("getIdVisiteur",login, mdp);
    }

    public void envoiDemandeFraisForfait(String idVisiteur) {
        tacheArrierePlan = new TacheArrierePlan();
        tacheArrierePlan.delegate = this;
        tacheArrierePlan.execute("getLignesFraisForfait", idVisiteur);
    }
}
