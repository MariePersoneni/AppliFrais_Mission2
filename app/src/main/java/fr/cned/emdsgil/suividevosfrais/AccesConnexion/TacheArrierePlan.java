package fr.cned.emdsgil.suividevosfrais.AccesConnexion;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import fr.cned.emdsgil.suividevosfrais.AccesConnexion.AsyncResponse;
import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;

public class TacheArrierePlan extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate=null;
    private String action = null;

    /**
     * Fonction qui s'execute en arrière plan, elle lance une requête
     * de connexion avec des paramètres différents selon l'action
     *
     * @param parametres = le 1er paramètre contient toujours le
     *                   nom de l'action à réaliser
     * @return le résultat de la requete sous forme d'objet ou de tableau JSON
     */
    @Override
    protected String doInBackground(String... parametres) {
        action = parametres[0];
        String param = "";
        String idVisiteur;
        switch (action){
            case Controle.GET_ID_VISITEUR :
                String login = parametres[1];
                String mdp = parametres[2];
                param = "action=readvisiteur&login=" + login + "&mdp=" + mdp;
                break;
            case Controle.GET_LIGNE_FRAIS_FORFAIT :
                idVisiteur = parametres[1];
                param = "action=getLignesFraisForfait&idVisiteur=" + idVisiteur;
                break;
            case Controle.MAJ_LIGNE_FRAIS_FORFAIT :
                idVisiteur = parametres[1];
                String mois = parametres[2];
                String numero = parametres[3];
                String qte = parametres[4];
                param = "action=MAJligneFraisForfait&idVisiteur=" + idVisiteur + "&mois=" + mois + "&numero=" + numero + "&qte=" + qte;
                break;
            case Controle.GET_FICHES_FRAIS :
                idVisiteur = parametres[1];
                param = "action=getFichesDeFrais&idVisiteur=" + idVisiteur;
                break;
        }
        StringBuffer chaine = new StringBuffer("");
        try{
            String urlParameters = param; // paramètres à envoyer à service.php
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8); // paramètres formatés en UTF-8
            String request = "http://192.168.1.8/Suividevosfrais2/service.php"; // adresse du fichier PHP qui va executer la requête
            URL url = new URL(request); // création de l'URL
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(); // création de la connexion
            connection.setDoOutput(true); // Flux de sortie de la connexion autorisée
            connection.setRequestMethod("POST"); // méthode de requête = POST
            connection.setRequestProperty("charset","utf-8"); // propriété de la requête : charset = UTF-8

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())){
                wr.write(postData); // envoiDemandeConnexion de la requête
            }

            connection.connect(); // connexion

            InputStream inputStream = connection.getInputStream(); // récupération de la réponse

            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream)); // lecteur qui va lire la requête
            String line = "";
            while((line = rd.readLine()) != null){
                chaine.append(line); // alimentation de chaine avec les réponses
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        if (chaine != null){
            return new String(chaine);
        } else {
            return null;
        }
    }

    /**
     * Méthode qui reçoit le résultat de la méthode doIBackground
     * ci-dessus.
     * Le delegate a été instancié par la classe IntermediaireArrierePlan
     * dans sa méthode envoiDemandeConnexion utilisée auparavant. Il appelle la méthode
     * processFinish de la classe IntermediaireArrierePlan et lui transmet l'action en cours.
     *
     * @param resulatRequête = le résultat de la requête envoyée par doInBackground, il est
     *                       au  format JSON.
     */
    @Override
    protected void onPostExecute(String resulatRequête){
        delegate.processFinish(resulatRequête, action);
    }
}