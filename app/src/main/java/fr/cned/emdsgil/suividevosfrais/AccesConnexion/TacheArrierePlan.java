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

public class TacheArrierePlan extends AsyncTask<String, Void, String> {

    public AsyncResponse delegate=null;

    @Override
    protected String doInBackground(String... strings) {
        String login = strings[0];
        String mdp = strings[1];
        StringBuffer chaine = new StringBuffer("");
        try{
            String urlParameters = "action=read&login=" + login + "&mdp=" + mdp; // paramètres à envoyer à service.php
            byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8); // paramètres formatés en UTF-8
            String request = "http://192.168.1.8/Suividevosfrais2/service.php"; // adresse du fichier PHP qui va executer la requête
            URL url = new URL(request); // création de l'URL
            HttpURLConnection connection = (HttpURLConnection)url.openConnection(); // création de la connexion
            connection.setDoOutput(true); // Flux de sortie de la connexion autorisée
            connection.setRequestMethod("POST"); // méthode de requête = POST
            connection.setRequestProperty("charset","utf-8"); // propriété de la requête : charset = UTF-8

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())){
                wr.write(postData); // envoi de la requête
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
     * Cette fonction doit être appelée depuis le thread principal
     * @param s = le résultat de la requête envoyée par doInBackground
     */
    @Override
    protected void onPostExecute(String s){
        delegate.processFinish(s);
    }
}