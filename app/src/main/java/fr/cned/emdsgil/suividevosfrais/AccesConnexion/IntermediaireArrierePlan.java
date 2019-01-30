package fr.cned.emdsgil.suividevosfrais.AccesConnexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Donnees.FicheFrais;
import fr.cned.emdsgil.suividevosfrais.Donnees.LigneFraisForfait;
import fr.cned.emdsgil.suividevosfrais.Donnees.LigneFraisHorsForfait;
import fr.cned.emdsgil.suividevosfrais.Donnees.Visiteur;

public class IntermediaireArrierePlan implements AsyncResponse {

    /**
     * Propriétés
     */
    private Controle controle;
    private TacheArrierePlan tacheArrierePlan;

    /**
     * Constructeur qui récupère le contrôle
     */
    public IntermediaireArrierePlan(){ controle = Controle.getInstance(null); }

    /**
     * Méthode qui permet de récupérer et de traiter le résultat
     * de la requête gérée par TacheArrièrePlan selon l'action en
     * cours, récuperée en paramètre
     *
     * @param output = résultat de la requête
     * @param action = action en cours
     */
    @Override
    public void processFinish(String output, String action) {
        // init variables réccurrentes
        String idVisiteur = null;
        Visiteur leVisiteur;
        switch (action){
            /**
             * Réception de l'id du Visiteur
             */
            case Controle.GET_ID_VISITEUR :
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
            case Controle.GET_LIGNE_FRAIS_FORFAIT :
                // récupération des fiches
                List lesLignesFraisForfait = new ArrayList<LigneFraisForfait>();
                lesLignesFraisForfait = receptionTableauJSON(output,"LigneFraisForfait", 6);
                // alimentation de la liste du visiteur avec cette collection
                leVisiteur = getLeVisiteur();
                leVisiteur.setLesLignesFraisForfait(lesLignesFraisForfait);
                break;
            /**
             * Réception des lignes de frais hors forfait
             */
            case Controle.GET_LIGNE_FRAIS_HF :
                // récupération des fiches
                List lesLignesFraisHF = new ArrayList<LigneFraisHorsForfait>();
                lesLignesFraisHF = receptionTableauJSON(output, "LigneFraisHF", 6);
                // alimentation de la liste du visiteur avec cette collection
                leVisiteur = getLeVisiteur();
                leVisiteur.setLesLignesFraisHF(lesLignesFraisHF);
                break;
            /**
             * Récuperation des fiches de frais du visiteur
             */
            case Controle.GET_FICHES_FRAIS :
                // récupération des fiches
                List lesFiches = new ArrayList<FicheFrais>();
                lesFiches = receptionTableauJSON(output,"FicheFrais", 2);
                // alimentation de la liste du visiteur avec cette collection
                leVisiteur = getLeVisiteur();
                leVisiteur.setLesFichesDeFrais(lesFiches);
                lesFiches = lesFiches;
                break;
        }
    }

    /**
     * Récupère l'instance du visiteur et la retourne
     * @return
     */
    private Visiteur getLeVisiteur() {
        String idVisiteur  = Visiteur.getId();
        Visiteur leVisiteur = Visiteur.getInstance(idVisiteur);
        return leVisiteur;
    }

    /**
     * Fonction qui transforme un tableau JSON au format List<>
     * @param tableauJSON = le tableau à transformer
     * @param typeCollection = type de la liste (FicheFrais, LigneFraisForfait)
     * @param tailleColection = nombre de champ qui composent les lignes de la liste
     * @return une liste d'objets du type passé en paramètre
     */
    private List receptionTableauJSON(String tableauJSON, String typeCollection, int tailleColection){
        if (tableauJSON != null){
            try{
                JSONArray maitreJSON = new JSONArray(tableauJSON);
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
                    String mois = null;
                    String idVisiteur = null;
                    Object ligne = null;
                    switch (typeCollection){
                        case "LigneFraisForfait":
                            idVisiteur = sousTableau[0];
                            mois = sousTableau[1];
                            String idFraisForfait = sousTableau[2];
                            String idFraisKm = sousTableau[3];
                            int quantite = Integer.parseInt(sousTableau[4]);
                            String numero = sousTableau[5];
                            //LigneFraisForfait ligneFrais = new LigneFraisForfait(mois,idFraisForfait,idFraisKm,quantite,numero);
                            ligne = new LigneFraisForfait(mois,idFraisForfait,idFraisKm,quantite,numero);
                            break;
                        case "LigneFraisHF" :
                            int id = Integer.parseInt(sousTableau[0]);
                            idVisiteur = sousTableau[1];
                            mois = sousTableau[2];
                            String libelle = sousTableau[3];
                            Integer jour = Integer.parseInt(sousTableau[4].substring(8));
                            Float montant = Float.parseFloat(sousTableau[5]);
                            //LigneFraisHorsForfait ligneFraisHF = new LigneFraisHorsForfait(id,mois,libelle,jour, montant);
                            ligne = new LigneFraisHorsForfait(id,mois,libelle,jour, montant);
                            break;
                        case "FicheFrais" :
                            mois = sousTableau[0];
                            String etat = sousTableau[1];
                            //FicheFrais ligneFiche = new FicheFrais(mois, etat);
                            ligne = new FicheFrais(mois, etat);
                            break;
                    }
                     //Ajout de cet objet lignedefraisforfait dans la collection tableauMaitre
                     tableauMaitre.add(ligne);
                }
                return tableauMaitre;
            } catch (JSONException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    /************************************************************************
     * METHODES DE CONNEXION
     * Ces méthodes créent un objet TacheArrierePlan et
     * lui demande d'executer la connexion
     ************************************************************************/
    public void envoiDemandeConnexion(String login, String mdp){
        creerDelegate().execute(Controle.GET_ID_VISITEUR,login, mdp);
    }

    public void envoiDemandeFraisForfait(String idVisiteur) {
        creerDelegate().execute(Controle.GET_LIGNE_FRAIS_FORFAIT, idVisiteur);
    }

    public void envoiDemandeMAJligneFraisForfait(String idVisiteur, String mois, String numero, String qte) {
        String num = numero.toString();
        creerDelegate().execute(Controle.MAJ_LIGNE_FRAIS_FORFAIT, idVisiteur, mois,numero, qte);
    }

    public void envoiDemandeFicheFrais(String idVisiteur) {
        creerDelegate().execute(Controle.GET_FICHES_FRAIS, idVisiteur);
    }

    public void envoiDemandeCreerFicheFrais(String idVisiteur, String anneeMois, String moisPrecedent) {
        creerDelegate().execute(Controle.CREER_FICHE_FRAIS, idVisiteur, anneeMois, moisPrecedent);
    }

    public void envoiDemandeFraisHF(String idVisiteur) {
        creerDelegate().execute(Controle.GET_LIGNE_FRAIS_HF, idVisiteur);
    }

    /************************************************************************
     ************************************************************************/

    /**
     * Fonction qui créer une nouvelle tache et retourne un nouveau
     * delegate. Utilisée dans toutes les méthodes de connexion
     * @return
     */
    private TacheArrierePlan creerDelegate(){
        tacheArrierePlan = new TacheArrierePlan();
        tacheArrierePlan.delegate = this;
        return tacheArrierePlan;
    }


}
