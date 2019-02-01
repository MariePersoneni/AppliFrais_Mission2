package fr.cned.emdsgil.suividevosfrais;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.nio.DoubleBuffer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Donnees.FicheFrais;
import fr.cned.emdsgil.suividevosfrais.Donnees.Visiteur;
import fr.cned.emdsgil.suividevosfrais.Outils.Fonctions;

public class HfActivity extends AppCompatActivity {
	private Controle controle;
	private List lesFichesDeFraisDuVisiteur;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hf);
        setTitle("GSB : Frais HF");
        controle = Controle.getInstance(this);
        lesFichesDeFraisDuVisiteur = Visiteur.getLesFichesDeFrais();
        // Configuration de la date
        Fonctions.changeAfficheDate((DatePicker) findViewById(R.id.datHf), true) ;
		Fonctions.setMinDate((DatePicker)findViewById(R.id.datHf));
		// mise à 0 du montant
		((EditText)findViewById(R.id.txtHf)).setText("0") ;
        // chargement des méthodes événementielles
		imgReturn_clic() ;
		cmdAjouter_clic() ;
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
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	findViewById(R.id.imgHfReturn).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;    		
    		}
    	}) ;
    }

    /**
     * Sur le clic du bouton ajouter : enregistrement dans la liste et sérialisation
     */
    private void cmdAjouter_clic() {
    	findViewById(R.id.cmdHfAjouter).setOnClickListener(new Button.OnClickListener() {
    		public void onClick(View v) {
    			enregListe() ;
    		}
    	}) ;    	
    }
    
	/**
	 * Enregistrement dans la liste du nouveau frais hors forfait
	 */
	private void enregListe() {
		// récupération des informations saisies
		Integer annee = ((DatePicker)findViewById(R.id.datHf)).getYear() ;
		Integer mois = ((DatePicker)findViewById(R.id.datHf)).getMonth() + 1 ;
		Integer jour = ((DatePicker)findViewById(R.id.datHf)).getDayOfMonth() ;
		Float montant = Float.valueOf((((EditText)findViewById(R.id.txtHf)).getText().toString()));
		String motif = ((EditText)findViewById(R.id.txtHfMotif)).getText().toString() ;
		// motif obligatoire
		if (motif.equals("")){
			Toast.makeText(HfActivity.this, "Motif obligatoire", Toast.LENGTH_SHORT).show();
		}else{
			String anneeMois = Fonctions.getFormatMois(annee, mois);
			// vérification fiche existe pour ce mois
			String anneeMoisDerniereFiche = ((FicheFrais)lesFichesDeFraisDuVisiteur.get(lesFichesDeFraisDuVisiteur.size()-1)).getMois();
			if (Fonctions.estMoisActuel(anneeMois) & !anneeMois.equals(anneeMoisDerniereFiche)){
				controle.creerFicheFrais(Visiteur.getId(), anneeMois, Fonctions.getMoisPrecedent(anneeMois));
			}
			String date = annee + "-" + mois + "-" + jour;
			// enregistrement dans la BDD
			controle.creerLigneFraisHF(Visiteur.getId(), anneeMois, motif, date, montant);
			retourActivityPrincipale() ;
		}
	}

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfActivity.this, MenuActivity.class) ;
		startActivity(intent) ;   					
	}
}
