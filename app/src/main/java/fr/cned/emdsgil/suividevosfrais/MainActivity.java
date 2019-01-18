package fr.cned.emdsgil.suividevosfrais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
             * Ici elle lance la requête de demande de connexion au serveur et envoi en paramètres
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
                    controle.lanceRequete(login, mdp);
                }
            }
        });
    }

    /**
     * Méthode qui récupère le résultat de la demande de connexion
     * Si le login et le mot de passe sont correct, la méthode appelle
     * l'activity de menu
     *
     * @param resultat : vaut 1 si la récupération des données à echoué
     *                 sinon resultat contient toutes les infos du visiteur
     *
     */
    public void afficheResultat(String resultat){
        if (resultat.equals("1")){
            Toast.makeText(MainActivity.this, "Login / mdp incorrect", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(MainActivity.this, "Connexion réussie :",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(intent);
        }
    }
}
