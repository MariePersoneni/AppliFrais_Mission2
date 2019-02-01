package fr.cned.emdsgil.suividevosfrais;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Donnees.Visiteur;

public class MenuActivity extends AppCompatActivity {

    private Controle controle;
    private Visiteur leVisiteur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        setTitle("GSB : Suivi des frais");
        // récupère le controle
        controle = Controle.getInstance(this);
        // récupère les infos du visiteur
        String idVisiteur = Visiteur.getId();
        leVisiteur = Visiteur.getInstance(idVisiteur);
        controle.getLesLignesFraisForfait(idVisiteur);
        controle.getLesFichesDeFrais(idVisiteur);
        controle.getLesLignesFraisHF(idVisiteur);
        String profil = leVisiteur.getPrenom() + " " + leVisiteur.getNom();
        // Affiche le profil du visiteur
        ((TextView)findViewById(R.id.txtProfil)).setText(profil);
        // chargement des méthodes événementielles
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdKm)), KmActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdHf)), HfActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdRepas)), RepasActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdNuitee)), NuiteeActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdEtape)), EtapeActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdHfRecap)), HfRecapActivity.class, idVisiteur);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Sur la sélection d'un bouton dans l'activité principale ouverture de l'activité correspondante
     */
    private void cmdMenu_clic(ImageButton button, final Class classe, final String idVisiteur) {
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // ouvre l'activité et lui transmet l'id du visiteur
                Intent intent = new Intent(MenuActivity.this, classe);
                startActivity(intent);
            }
        });
    }
}
