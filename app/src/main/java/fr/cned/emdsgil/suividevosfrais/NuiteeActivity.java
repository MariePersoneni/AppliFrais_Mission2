package fr.cned.emdsgil.suividevosfrais;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Donnees.FicheFrais;
import fr.cned.emdsgil.suividevosfrais.Donnees.LigneFraisForfait;
import fr.cned.emdsgil.suividevosfrais.Donnees.Visiteur;

public class NuiteeActivity extends AppCompatActivity {

    private Integer annee;
    private Integer mois;
    private Integer qte;
    private Controle controle;
    private String idVisiteur;
    private Visiteur leVisiteur;
    private List lesFraisDuVisiteur;
    private LigneFraisForfait ligneEnCours;
    private List lesFichesDeFraisDuVisiteur;
    private FicheFrais ficheEnCours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuitee);
        controle = Controle.getInstance(this);
        // récupération  du visiteur et de ses fiches de frais
        idVisiteur = Visiteur.getId();
        leVisiteur = Visiteur.getInstance(idVisiteur);
        lesFraisDuVisiteur = leVisiteur.getLesLignesFraisForfait();
        lesFichesDeFraisDuVisiteur = leVisiteur.getLesFichesDeFrais();
        // modification de l'affichage du DatePicker
        Global.changeAfficheDate((DatePicker) findViewById(R.id.datNuitee), false) ;
        // initialisation des propriétés
        valoriseProprietes();
        // chargement des méthodes événementielles
        imgReturn_clic() ;
        cmdValider_clic() ;
        cmdPlus_clic() ;
        cmdMoins_clic() ;
        dat_clic() ;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals(getString(R.string.retour_accueil))) {
            retourActivityPrincipale() ;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Valorisation des propriétés avec les informations affichées
     */
    private void valoriseProprietes() {
        annee = ((DatePicker)findViewById(R.id.datNuitee)).getYear() ;
        mois = ((DatePicker)findViewById(R.id.datNuitee)).getMonth() + 1 ;
        String numMois = mois.toString();
        if (mois < 10) {
            numMois = "0" + mois;
        }
        String anneMois = annee.toString() + numMois;
        // Recherche d'une fiche de frais pour ce mois
        ficheEnCours = new FicheFrais(anneMois,"");
        if (lesFichesDeFraisDuVisiteur.contains(ficheEnCours)){
            int index = lesFichesDeFraisDuVisiteur.indexOf(ficheEnCours);
            ficheEnCours = (FicheFrais) lesFichesDeFraisDuVisiteur.get(index);
        } else {
            // TO DO
        }
        /* Recherche d'une ligne de frais existante pour cette periode
         * Si une ligne existe on la place dans la variable ligneEnCours */
        ligneEnCours = new LigneFraisForfait(idVisiteur,anneMois,"NUI","",0,0);
        if (lesFraisDuVisiteur.contains(ligneEnCours)){
            int index = lesFraisDuVisiteur.indexOf(ligneEnCours);
            ligneEnCours = (LigneFraisForfait) lesFraisDuVisiteur.get(index);
        }
        /* Récupération de la quantité de la ligne en cours
         * puis affichage*/
        qte = ligneEnCours.getQuantite();
        ((EditText)findViewById(R.id.txtNuitee)).setText(qte.toString());
    }

    /**
     * Sur la selection de l'image : retour au menu principal
     */
    private void imgReturn_clic() {
        findViewById(R.id.imgNuiteeReturn).setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                retourActivityPrincipale() ;
            }
        }) ;
    }

    /**
     * Sur le clic du bouton valider : MAJ base de données
     */
    private void cmdValider_clic() {
        findViewById(R.id.cmdNuiteeValider).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Serializer.serialize(Global.listFraisMois, NuiteeActivity.this) ;
                retourActivityPrincipale() ;

                // récuperation du numéro qui fait partie de la clé primaire et envoiDemandeConnexion de la quantité
                Integer numero = ligneEnCours.getNumero();
                String mois = ligneEnCours.getMois();
                controle.MAJligneFraisForfait(idVisiteur, mois, numero.toString(), qte.toString() );
            }
        }) ;
    }

    /**
     * Sur le clic du bouton plus : ajout de 1 dans la quantité
     */
    private void cmdPlus_clic() {
        findViewById(R.id.cmdNuiteePlus).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (!ficheEnCours.getEtat().equals("CR")){
                    Toast.makeText(NuiteeActivity.this, "Saisie impossible : fiche clôturée", Toast.LENGTH_SHORT).show();
                }else {
                    qte += 1;
                    ((EditText) findViewById(R.id.txtNuitee)).setText(qte.toString());
                }
            }
        }) ;
    }

    /**
     * Sur le clic du bouton moins : enlève 1 dans la quantité si c'est possible
     */
    private void cmdMoins_clic() {
        findViewById(R.id.cmdNuiteeMoins).setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                if (!ficheEnCours.getEtat().equals("CR")){
                    Toast.makeText(NuiteeActivity.this, "Saisie impossible : fiche clôturée", Toast.LENGTH_SHORT).show();
                }else {
                    qte = Math.max(0, qte - 1); // suppression de 10 si possible
                    ((EditText) findViewById(R.id.txtNuitee)).setText(qte.toString());
                }
            }
        }) ;
    }

    /**
     * Sur le changement de date : mise à jour de l'affichage de la qte
     */
    private void dat_clic() {
        final DatePicker uneDate = (DatePicker) findViewById(R.id.datNuitee);
        uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                valoriseProprietes() ;
                // on/off bouton valider selon l'état de la fiche en cours
                if (!ficheEnCours.getEtat().equals("CR")) ((Button)findViewById(R.id.cmdNuiteeValider)).setEnabled(false);
                else ((Button)findViewById(R.id.cmdNuiteeValider)).setEnabled(true);
            }
        });
    }

    /**
     * Enregistrement dans la zone de texte et dans la liste de la nouvelle qte, à la date choisie
     */
    private void enregNewQte() {
        // enregistrement dans la zone de texte
        ((EditText)findViewById(R.id.txtNuitee)).setText(String.format(Locale.FRANCE, "%d", qte)) ;
        // enregistrement dans la liste
        Integer key = annee*100+mois ;
        if (!Global.listFraisMois.containsKey(key)) {
            // creation du mois et de l'annee s'ils n'existent pas déjà
            Global.listFraisMois.put(key, new FraisMois(annee, mois)) ;
        }
        Global.listFraisMois.get(key).setKm(qte) ;
    }

    /**
     * Retour à l'activité principale (le menu)
     */
    private void retourActivityPrincipale() {
        Intent intent = new Intent(NuiteeActivity.this, MenuActivity.class) ;
        startActivity(intent) ;
    }
}
