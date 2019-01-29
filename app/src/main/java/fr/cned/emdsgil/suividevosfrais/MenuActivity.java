package fr.cned.emdsgil.suividevosfrais;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

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
        // récupère l'id du visiteur
        String idVisiteur = Visiteur.getId();
        leVisiteur = Visiteur.getInstance(idVisiteur);
        // récupération des frais du visiteur
        //controle.getLesFichesDeFrais(idVisiteur);
        controle.getLesLignesFraisForfait(idVisiteur);
        controle.getLesFichesDeFrais(idVisiteur);
        controle.getLesLignesFraisHF(idVisiteur);
        // chargement des méthodes événementielles
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdKm)), KmActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdHf)), HfActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdRepas)), RepasActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdNuitee)), NuiteeActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdEtape)), EtapeActivity.class, idVisiteur);
        cmdMenu_clic(((ImageButton) findViewById(R.id.cmdHfRecap)), HfRecapActivity.class, idVisiteur);
        cmdTransfert_clic();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Récupère la sérialisation si elle existe
     */
    private void recupSerialize() {
        /* Pour éviter le warning "Unchecked cast from Object to Hash" produit par un casting direct :
         * Global.listFraisMois = (Hashtable<Integer, FraisMois>) Serializer.deSerialize(Global.filename, MenuActivity.this);
         * On créé un Hashtable générique <?,?> dans lequel on récupère l'Object retourné par la méthode deSerialize, puis
         * on cast chaque valeur dans le type attendu.
         * Seulement ensuite on affecte cet Hastable à Global.listFraisMois.
        */
        Hashtable<?, ?> monHash = (Hashtable<?, ?>) Serializer.deSerialize(MenuActivity.this);
        if (monHash != null) {
            Hashtable<Integer, FraisMois> monHashCast = new Hashtable<>();
            for (Hashtable.Entry<?, ?> entry : monHash.entrySet()) {
                monHashCast.put((Integer) entry.getKey(), (FraisMois) entry.getValue());
            }
            Global.listFraisMois = monHashCast;
        }
        // si rien n'a été récupéré, il faut créer la liste
        if (Global.listFraisMois == null) {
            Global.listFraisMois = new Hashtable<>();
            /* Retrait du type de l'HashTable (Optimisation Android Studio)
			 * Original : Typage explicit =
			 * Global.listFraisMois = new Hashtable<Integer, FraisMois>();
			*/

        }
    }

    /**
     * Sur la sélection d'un bouton dans l'activité principale ouverture de l'activité correspondante
     */
    private void cmdMenu_clic(ImageButton button, final Class classe, final String idVisiteur) {
        button.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // ouvre l'activité et lui transmet l'id du visiteur
                Intent intent = new Intent(MenuActivity.this, classe);
                intent.putExtra("idVisiteur",idVisiteur);
                startActivity(intent);
            }
        });
    }

    /**
     * Cas particulier du bouton pour le transfert d'informations vers le serveur
     */
    private void cmdTransfert_clic() {
        findViewById(R.id.cmdTransfert).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                // envoiDemandeConnexion les informations sérialisées vers le serveur
                // en construction
            }
        });
    }
}
