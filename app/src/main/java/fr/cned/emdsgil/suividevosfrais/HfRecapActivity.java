package fr.cned.emdsgil.suividevosfrais;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Donnees.LigneFraisHorsForfait;
import fr.cned.emdsgil.suividevosfrais.Donnees.Visiteur;

public class HfRecapActivity extends AppCompatActivity {
	private String anneeMois;
	private Visiteur leVisiteur;
	private List lesFraisHFduVisiteur;
	private List lesFraisDuMois;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hf_recap);
        setTitle("GSB : Récap Frais HF");
		// modification de l'affichage du DatePicker
		Global.changeAfficheDate((DatePicker) findViewById(R.id.datHfRecap), false) ;
		// récupération infos du visiteur
		initVisiteur();
		// valorisation des propriétés
		afficheListe() ;
        // chargement des méthodes événementielles
		imgReturn_clic() ;
		dat_clic() ;
	}

	/**
	 * Valorise les propriétés liées au visiteur
	 */
	private void initVisiteur() {
		String idVisiteur = Visiteur.getId();
		leVisiteur = Visiteur.getInstance(idVisiteur);
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
	 * Affiche la liste des frais hors forfaits de la date sélectionnée
	 */
	private void afficheListe() {
		// actualise les infos du visiteur
		leVisiteur = Visiteur.getInstance(Visiteur.getId());
		lesFraisHFduVisiteur = leVisiteur.getLesLignesFraisHF();
		Integer annee = ((DatePicker)findViewById(R.id.datHfRecap)).getYear() ;
		Integer mois = ((DatePicker)findViewById(R.id.datHfRecap)).getMonth() + 1 ;
		String numMois = mois.toString();
		if (mois < 10) {
			numMois = "0" + mois;
		}
		anneeMois = annee.toString() + numMois;
		// récupération des frais HF pour cette date
		List lignesMoisEnCours = new ArrayList<LigneFraisHorsForfait>();
		LigneFraisHorsForfait ligneModele = new LigneFraisHorsForfait(0, anneeMois,"",null,0 );
		for (Object uneLigne : lesFraisHFduVisiteur ){
			if(uneLigne.equals(ligneModele)){
				lignesMoisEnCours.add(uneLigne);
			}
		}
		lignesMoisEnCours = lignesMoisEnCours;
		// Tri croissant des lignes HF du mois en cours par jour
		Collections.sort(lignesMoisEnCours, new Comparator<LigneFraisHorsForfait>() {
			@Override
			public int compare(LigneFraisHorsForfait o1, LigneFraisHorsForfait o2) {
				return (Integer.valueOf(o1.getJour()).compareTo(Integer.valueOf(o2.getJour())));
			}
		});
		ListView listView = (ListView) findViewById(R.id.lstHfRecap);
		FraisHfAdapter adapter = new FraisHfAdapter(HfRecapActivity.this, lignesMoisEnCours) ;
		listView.setAdapter(adapter) ;
	}


	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
    private void imgReturn_clic() {
    	findViewById(R.id.imgHfRecapReturn).setOnClickListener(new ImageView.OnClickListener() {
    		public void onClick(View v) {
    			retourActivityPrincipale() ;    		
    		}
    	}) ;
    }

    /**
     * Sur le changement de date : mise à jour de l'affichage de la qte
     */
    private void dat_clic() {   	
    	final DatePicker uneDate = (DatePicker) findViewById(R.id.datHfRecap);
    	uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				afficheListe() ;				
			}
    	});       	
    }

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(HfRecapActivity.this, MenuActivity.class) ;
		startActivity(intent) ;   					
	}
}
