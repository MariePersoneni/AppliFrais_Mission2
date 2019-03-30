package fr.cned.emdsgil.suividevosfrais;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker.OnDateChangedListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;
import fr.cned.emdsgil.suividevosfrais.Donnees.FicheFrais;
import fr.cned.emdsgil.suividevosfrais.Donnees.LigneFraisForfait;
import fr.cned.emdsgil.suividevosfrais.Donnees.Visiteur;
import fr.cned.emdsgil.suividevosfrais.Outils.Fonctions;

public class KmActivity extends AppCompatActivity {

	/**
	 * propriétés
	 */
	private Integer qte;
	private Controle controle;
	private String idVisiteur;
	private Visiteur leVisiteur;
	private List lesFraisDuVisiteur;
	private LigneFraisForfait ligneEnCours;
	private List lesFichesDeFraisDuVisiteur;
	private FicheFrais ficheEnCours;
	private String anneeMois;
	private String idFraisKm = "D4";
	private RadioButton btnFraisKm;
	private static String numero;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_km);
		controle = Controle.getInstance(this);
		// récupération  du visiteur et de ses fiches de frais
		initVisiteur();
		// modification de l'affichage du DatePicker
		Fonctions.changeAfficheDate((DatePicker) findViewById(R.id.datKm), false) ;
		// initialisation des propriétés
		valoriseProprietes();
		// chargement des méthodes événementielles
		imgReturn_clic() ;
		cmdValider_clic() ;
		cmdPlus_clic() ;
		cmdMoins_clic() ;
		dat_clic() ;
		radioGroup_change();
	}

	/**
	 * Valorise les propriétés liées au visiteur
	 */
	private void initVisiteur() {
		idVisiteur = Visiteur.getId();
		leVisiteur = Visiteur.getInstance(idVisiteur);
		lesFraisDuVisiteur = leVisiteur.getLesLignesFraisForfait();
		lesFichesDeFraisDuVisiteur = leVisiteur.getLesFichesDeFrais();
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
		Integer annee = ((DatePicker)findViewById(R.id.datKm)).getYear() ;
		Integer mois = ((DatePicker)findViewById(R.id.datKm)).getMonth() + 1 ;
		String numMois = mois.toString();
		if (mois < 10) {
			numMois = "0" + mois;
		}
		anneeMois = annee.toString() + numMois;
		getLaFicheEnCours();
		getLaligneEnCours();
		qte = ligneEnCours.getQuantite();
		((EditText)findViewById(R.id.txtKm)).setText(qte.toString());
	}


	/**
	 * Sur la selection de l'image : retour au menu principal
	 */
	private void imgReturn_clic() {
		findViewById(R.id.imgKmReturn).setOnClickListener(new ImageView.OnClickListener() {
			public void onClick(View v) {
				retourActivityPrincipale() ;
			}
		}) ;
	}

	/**
	 * Sur le clic du bouton valider : MAJ base de données
	 */
	private void cmdValider_clic() {
		findViewById(R.id.cmdKmValider).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// vérifie si une fiche existe pour ce mois
				String anneeMoisDerniereFiche = ((FicheFrais)lesFichesDeFraisDuVisiteur.get(lesFichesDeFraisDuVisiteur.size()-1)).getMois();
				if (Fonctions.estMoisActuel(anneeMois) & !anneeMois.equals(anneeMoisDerniereFiche)){
					controle.creerFicheFrais(idVisiteur, anneeMois, Fonctions.getMoisPrecedent(anneeMois));
					actualiseFraisVisiteur(idVisiteur);
				}
				// récuperation du numéro qui fait partie de la clé primaire et envoi de la quantité
				String mois = ligneEnCours.getMois();
				controle.MAJligneFraisForfait(idVisiteur, mois, numero, qte.toString() );
				retourActivityPrincipale() ;
			}
		}) ;
	}

	/**
	 * Sur le clic du bouton plus : ajout de 1 dans la quantité
	 */
	private void cmdPlus_clic() {
		findViewById(R.id.cmdKmPlus).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (ficheEnCours.getEtat().equals("CR") | ficheEnCours.getEtat().equals("")){
					qte += 10;
					((EditText) findViewById(R.id.txtKm)).setText(qte.toString());
				}else {
					Toast.makeText(KmActivity.this, "Saisie impossible : fiche clôturée", Toast.LENGTH_SHORT).show();
				}
			}
		}) ;
	}

	/**
	 * Sur le clic du bouton moins : enlève 1 dans la quantité si c'est possible
	 */
	private void cmdMoins_clic() {
		findViewById(R.id.cmdKmMoins).setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if (ficheEnCours.getEtat().equals("CR") | ficheEnCours.getEtat().equals("")){
					qte = Math.max(0, qte - 10); // suppression de 10 si possible
					((EditText) findViewById(R.id.txtKm)).setText(qte.toString());
				}else {
					Toast.makeText(KmActivity.this, "Saisie impossible : fiche clôturée", Toast.LENGTH_SHORT).show();
				}
			}
		}) ;
	}

	/**
	 * Sur le changement de date : mise à jour de l'affichage de la qte
	 */
	private void dat_clic() {
		final DatePicker uneDate = (DatePicker) findViewById(R.id.datKm);
		uneDate.init(uneDate.getYear(), uneDate.getMonth(), uneDate.getDayOfMonth(), new DatePicker.OnDateChangedListener(){
			@Override
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				valoriseProprietes() ;
				// on/off bouton valider selon l'état de la fiche en cours
				((Button)findViewById(R.id.cmdKmValider)).setEnabled(ficheEnCours.getEtat().equals("CR")|ficheEnCours.getEtat().equals(""));
			}
		});
	}

    /**
     * evenement sur changement de l'id de Frais km
     */
	private void radioGroup_change(){
		final RadioGroup rdGroup = (RadioGroup)findViewById(R.id.rdgIdFraisKm);
		rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
			    // récupération de l'idFraisKm selectionné
				btnFraisKm = (RadioButton)findViewById(checkedId);
				idFraisKm = (btnFraisKm.getText()).toString();
				btnFraisKm.setChecked(true);
				valoriseProprietes();
			}
		});
	}

	/**
	 * Retour à l'activité principale (le menu)
	 */
	private void retourActivityPrincipale() {
		Intent intent = new Intent(KmActivity.this, MenuActivity.class) ;
		startActivity(intent) ;
	}

	/**
	 * Actualise les collections du visiteur
	 * @param idVisiteur
	 */
	private void actualiseFraisVisiteur(String idVisiteur){
		controle.getLesFichesDeFrais(idVisiteur);
		controle.getLesLignesFraisForfait(idVisiteur);
	}

	/**
	 * Retourne la fiche de frais qui correspond à la date affichée
	 */
	private void getLaFicheEnCours(){
		ficheEnCours = new FicheFrais(anneeMois,"");
		if (lesFichesDeFraisDuVisiteur.contains(ficheEnCours)){
			int index = lesFichesDeFraisDuVisiteur.indexOf(ficheEnCours);
			ficheEnCours = (FicheFrais) lesFichesDeFraisDuVisiteur.get(index);
		}
	}

	/**
	 * Retourne la ligne de frais qui correspond à la date affichée et au type de frais selectionné
	 */
	private void getLaligneEnCours(){
		ligneEnCours = new LigneFraisForfait(anneeMois,"null",idFraisKm,0,numero);
		if (lesFraisDuVisiteur.contains(ligneEnCours)){
			int index = lesFraisDuVisiteur.indexOf(ligneEnCours);
			ligneEnCours = (LigneFraisForfait) lesFraisDuVisiteur.get(index);
			idFraisKm = ligneEnCours.getIdFraisKm();
			numero = ligneEnCours.getNumero();
		}
	}
}

