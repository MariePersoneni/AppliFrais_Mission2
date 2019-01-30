package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.cned.emdsgil.suividevosfrais.Donnees.LigneFraisHorsForfait;
import fr.cned.emdsgil.suividevosfrais.Donnees.Visiteur;

class FraisHfAdapter extends BaseAdapter {

	//private final ArrayList<FraisHf> lesFrais ; // liste des frais du mois
	private final List lesFraisHFduVisiteur;
	private final LayoutInflater inflater ;

    /**
	 * Constructeur de l'adapter pour valoriser les propriétés
     * @param context Accès au contexte de l'application
     * @param lesLignesFraisHF Liste des frais hors forfait
     */
	public FraisHfAdapter(Context context,List lesLignesFraisHF) {
		inflater = LayoutInflater.from(context) ;
		lesFraisHFduVisiteur = lesLignesFraisHF;
		//this.lesFrais = lesFrais ;
    }
	
	/**
	 * retourne le nombre d'éléments de la listview
	 */
	@Override
	public int getCount() {
		return lesFraisHFduVisiteur.size() ;
	}

	/**
	 * retourne l'item de la listview à un index précis
	 */
	@Override
	public Object getItem(int index) {
		return lesFraisHFduVisiteur.get(index) ;
	}

	/**
	 * retourne l'index de l'élément actuel
	 */
	@Override
	public long getItemId(int index) {
		return index;
	}

	/**
	 * structure contenant les éléments d'une ligne
	 */
	private class ViewHolder {
		TextView txtListJour ;
		TextView txtListMontant ;
		TextView txtListMotif ;
		ImageButton cmdSuppHf;

		/**
		 * Evenement sur le clic du bouton cmdSuppHF :
		 * Suppression dans la base de données de cette ligne
		 * puis mise à jour de l'adapter
		 */
		public void cmdSuppHf_onClick(){
			cmdSuppHf.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					txtListMotif.setText("test");
				}
			});
		}
	}

	/**
	 * Affichage dans la liste
	 */
	@Override
	public View getView(int index, View convertView, ViewGroup parent) {
		ViewHolder holder ;
		// Si la vue n'est pas recyclée (= elle est toujours affichée à l'écran)
		if (convertView == null) {
			// création du holder qui va contenir les éléments d'une même ligne
			holder = new ViewHolder() ;
			// Récupère le layout
			convertView = inflater.inflate(R.layout.layout_liste, parent, false) ;
			// Ajout des éléments dans le holder
			holder.txtListJour = convertView.findViewById(R.id.txtListJour);
			holder.txtListMontant = convertView.findViewById(R.id.txtListMontant);
			holder.txtListMotif = convertView.findViewById(R.id.txtListMotif);
			holder.cmdSuppHf = convertView.findViewById(R.id.cmdSuppHf);
			holder.cmdSuppHf_onClick();
			// Insertion du holder dans le layout
			convertView.setTag(holder) ;
		}else{
			// si la vue est recyclée, on récupère le holder précédemment créé
			holder = (ViewHolder)convertView.getTag();
		}
		// Alimentation des éléments du holder avec les informations de la ligne de frais HF
        LigneFraisHorsForfait ligneEnCours = (LigneFraisHorsForfait) lesFraisHFduVisiteur.get(index);
		holder.txtListJour.setText(String.format(Locale.FRANCE, "%d", ligneEnCours.getJour()));
		holder.txtListMontant.setText(String.format(Locale.FRANCE, "%.2f", ligneEnCours.getMontant())) ;
		holder.txtListMotif.setText(ligneEnCours.getLibelle()) ;
		return convertView ;
	}
	
}
