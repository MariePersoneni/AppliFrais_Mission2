package fr.cned.emdsgil.suividevosfrais.AccesConnexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Donnees.FicheFrais;
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
        // init variables réccurrentes
        String idVisiteur;
        Visiteur leVisiteur;
        switch (action){
            /**
             * Réception de l'id du Visiteur
             */
            case "getIdVisiteur" :
                if (!output.equals("1")){
                    // output = idVisiteur
                    try {
                        JSONObject outputJSON = new JSONObject(output);
                        // récupération de l'ID du visiteur
                        String id = outputJSON.getString("id");
                        controle.RetourRequete_getIdVisiteur(id);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    // output = "1" = mdp incorrect
                    controle.RetourRequete_getIdVisiteur("1");
                }
                break;
            /**
             * Reception des lignes de frais forfait
             */
            case "getLignesFraisForfait" :
//                if (output != null){
//                    try{
//                        JSONArray outputJSON = new JSONArray(output);
//                        //récupère le visiteur et la liste de ses frais forfait
//                        String idVisiteur = Visiteur.getId();
//                        Visiteur leVisiteur = Visiteur.getInstance(idVisiteur);
//                        List lesLignesFraisForfait = new ArrayList();
//                         // Parcours le tableau JSON chiffré : [0[a;b;c];1[a;b;c]]
//                         // i = 0 ; i = 1 ; i = 3...
//                        for (int i = 0 ; i < outputJSON.length() ; i++){
//                            JSONArray ligneFraisForfaitJSON = new JSONArray();
//                            ligneFraisForfaitJSON = outputJSON.getJSONArray(i);
//                            String[] ligneFraisForfait = new String[6];
//                             // Parcours le tableau JSON lettré : [0[a;b;c]...]
//                             // j = a ; j = b ; j = c...
//                            for (int j = 0 ; j < 6 ; j++){
//                                ligneFraisForfait[j] = ligneFraisForfaitJSON.getString(j);
//                            }
//                             // création de l'objet lignedefraisforfait
//                            String id = ligneFraisForfait[0];
//                            String mois = ligneFraisForfait[1];
//                            String idFraisForfait = ligneFraisForfait[2];
//                            String idFraisKm = ligneFraisForfait[3];
//                            int quantite = Integer.parseInt(ligneFraisForfait[4]);
//                            int numero = Integer.parseInt(ligneFraisForfait[5]);
//                            LigneFraisForfait ligne = new LigneFraisForfait(id,mois,idFraisForfait,idFraisKm,quantite,numero);
//                            // Ajout de cet objet lignedefraisforfait dans la collection lesLignesFraisForfait
//                            lesLignesFraisForfait.add(ligne);
//                        }
//                         // valorisation de la collection du visiteur avec la collection créée alimentée ci-dessus
//                        leVisiteur.setLesLignesFraisForfait(lesLignesFraisForfait);
//                    } catch (JSONException e){
//                        e.printStackTrace();
//                    }
//                }
                // récupération des fiches
                List lesLignesFraisForfait = new ArrayList<LigneFraisForfait>();
                lesLignesFraisForfait = receptionTableauJSON(output,"LigneFraisForfait", 6);
                // alimentation de la liste du visiteur avec cette collection
                idVisiteur = Visiteur.getId();
                leVisiteur = Visiteur.getInstance(idVisiteur);
                leVisiteur.setLesLignesFraisForfait(lesLignesFraisForfait);
                break;
            /**
             * Récuperation des fiches de frais du visiteur
             */
            case "getFichesDeFrais":
                // récupération des fiches
                List lesFiches = new ArrayList<FicheFrais>();
                lesFiches = receptionTableauJSON(output,"FicheFrais", 2);
                // alimentation de la liste du visiteur avec cette collection
                idVisiteur  = Visiteur.getId();
                leVisiteur = Visiteur.getInstance(idVisiteur);
                leVisiteur.setLesFichesDeFrais(lesFiches);
                break;
        }
    }

    private List receptionTableauJSON(String output, String typeCollection, int tailleColection){
        if (output != null){
            try{
                JSONArray maitreJSON = new JSONArray(output);
                //récupère le visiteur et la liste de ses frais forfait
//                String idVisiteur = Visiteur.getId();
//                Visiteur leVisiteur = Visiteur.getInstance(idVisiteur);
                List tableauMaitre = new ArrayList();
                // Parcours le tableau JSON chiffré : [0[a;b;c];1[a;b;c]]
                // i = 0 ; i = 1 ; i = 3...
                for (int i = 0 ; i < maitreJSON.length() ; i++){
                    JSONArray sousTableauJSON = new JSONArray();
                    sousTableauJSON = maitreJSON.getJSONArray(i);
                    String[] sousTableau = new String[tailleColection];
                    // Parcours le tableau JSON lettré : [0[a;b;c]...]
                    // j = a ; j = b ; j = c...
                    for (int j = 0 ; j < tailleColection ; j++){
                        sousTableau[j] = sousTableauJSON.getString(j);
                    }
                    // création de l'objet selon le type de Frais
                    Object ligne = null;
                    String mois = null;
                    switch (typeCollection){
                        case "LigneFraisForfait":
                            String id = sousTableau[0];
                            mois = sousTableau[1];
                            String idFraisForfait = sousTableau[2];
                            String idFraisKm = sousTableau[3];
                            int quantite = Integer.parseInt(sousTableau[4]);
                            int numero = Integer.parseInt(sousTableau[5]);
                            ligne = new LigneFraisForfait(id,mois,idFraisForfait,idFraisKm,quantite,numero);
                            break;
                        case "FicheFrais" :
                            mois = sousTableau[0];
                            String etat = sousTableau[1];
                            ligne = new FicheFrais(mois, etat);
                            break;
                    }
                    
                    // Ajout de cet objet lignedefraisforfait dans la collection tableauMaitre
                    tableauMaitre.add(ligne);
                }
                // valorisation de la collection du visiteur avec la collection créée alimentée ci-dessus
                //leVisiteur.setLesLignesFraisForfait(tableauMaitre);
                // retourne le tableau maitre
                return tableauMaitre;
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return null;
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

    public void envoiDemandeMAJligneFraisForfait(String idVisiteur, String mois, String numero, String qte) {
        tacheArrierePlan = new TacheArrierePlan();
        tacheArrierePlan.delegate = this;
        String num = numero.toString();
        tacheArrierePlan.execute("MAJligneFraisForfait", idVisiteur, mois,numero, qte);
    }

    public void envoiDemandeFicheFrais(String idVisiteur) {
        tacheArrierePlan = new TacheArrierePlan();
        tacheArrierePlan.delegate = this;
        tacheArrierePlan.execute("getFichesDeFrais", idVisiteur);
    }
}
