package fr.cned.emdsgil.suividevosfrais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;

public class MainActivity extends AppCompatActivity {
    private Controle controle;

    /**
     * Méthode de création de l'Activity
     * Instancie le controle
     * Place un listener sur le bouton connexion
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // récupération de l'instance de contrôle
        controle = Controle.getInstance(this);
        ecoute_btnConnexion();
    }

    /**
     * Méthode qui place un listener sur le bouton de connexion     *
     */
    private void ecoute_btnConnexion() {
        ((Button)findViewById(R.id.btnConnexion)).setOnClickListener(new Button.OnClickListener(){
            /**
             * Méthode qui définit l'action à réaliser quand on clique sur le bouton de connexion
             * Ici elle lance la requête de demande de connexion au serveur et envoiDemandeConnexion en paramètres
             * le login et le mot de passe saisis
             *
             * @param v = btnConnexion
             */
            @Override
            public void onClick(View v) {
                /* vérification que les champs ont été saisis*/
                String login = ((EditText)findViewById(R.id.txtLogin)).getText().toString();
                String mdp = ((EditText)findViewById(R.id.txtMdp)).getText().toString();
                if (login.equals("") | mdp.equals("")){
                    Toast.makeText(MainActivity.this, "Veuillez saisir tous les champs",Toast.LENGTH_SHORT).show();
                } else {
                    // Envoi de la requête de connexion
                    controle.getIdVisiteur(login, mdp);
                }
            }
        });
    }

    /**
     * Fonction qui affiche un message d'erreur si l'identification à échouée
     * Elle est appelée par le controleur après réception du résultat de la
     * requête getIdVisiteur
     */
    public void mdpIncorrect() {
        Toast.makeText(MainActivity.this, "Login / mdp incorrect", Toast.LENGTH_SHORT).show();
    }

    /**
     * Fonction appelée par le contrôleur si l'identification à réussie
     * Elle appelle l'activité du menu
     */
    public void connexionMenu() {
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}
