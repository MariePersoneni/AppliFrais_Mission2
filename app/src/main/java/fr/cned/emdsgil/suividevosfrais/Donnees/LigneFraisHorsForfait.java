package fr.cned.emdsgil.suividevosfrais.Donnees;

import java.util.Objects;

public class LigneFraisHorsForfait {
    /**
     * Propriétés
     */
    private Integer id;
    private String idVisiteur;
    private String  mois;
    private String motif;
    private Integer jour;
    private Float montant;

    /**
     * Constructeur
     */
    public LigneFraisHorsForfait(int id, String mois, String motif, Integer jour, float montant) {
        this.id = id;
        idVisiteur = Visiteur.getId();
        this.mois = mois;
        this.motif = motif;
        this.jour = jour;
        this.montant = montant;
    }

    /**
     * Getters
     */
    public String getMotif() { return motif; }
    public Integer getJour() { return jour; }
    public float getMontant() { return montant; }
    public Integer getId() { return id; }

    /**
     * Redéfinition de la méthode equals() de la classe Object
     * Nous avons besoin de 2 filtres différents
     * 1- sur le mois pour créer la liste de frais HF par mois
     * 2- sur l'id pour mettre à jour la liste de frais HF du visiteur
     * @param ligne : élément à comparer
     * @return true s'il y a correspondance
     *          sinon false
     */
    @Override
    public boolean equals(Object ligne) {
        // si la ligne est non fictive (id différent de 0)
        if (((LigneFraisHorsForfait) ligne).id != 0){
            // on compare l'id
            return Objects.equals(id, ((LigneFraisHorsForfait) ligne).id);
        } else {
            // si elle est fictive on compare le mois
            return Objects.equals(mois, ((LigneFraisHorsForfait) ligne).mois);
        }
    }
}
