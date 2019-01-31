package fr.cned.emdsgil.suividevosfrais.Outils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fonctions {
    /**
     * Fonction qui retourne vrai si le mois passé en paramètre
     * correspond au mois actuel (format AAAAMM)
     * @param mois
     * @return
     */
    public static boolean estMoisActuel(String mois){
        Date dateJ = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        String moisActuel = format.format(dateJ);
        return (mois.equals(moisActuel));
    }

    /**
     * Fonction qui retourne le mois précédent au mois
     * passé en paramètres (format AAAAMM)
     * @param mois
     * @return
     */
    public static String getMoisPrecedent(String mois){
        String numMois = mois.substring(4,6);
        Integer moisInt = Integer.parseInt(numMois);
        String numAnnee = mois.substring(0,4);
        Integer anneeInt = Integer.parseInt(numAnnee);
        if (moisInt == 1){
            numMois = "12";
            anneeInt -=1;
            numAnnee = anneeInt.toString();
        } else if(moisInt < 10){
            numMois = "0" + (moisInt-1);
        }
        else {
            moisInt -= 1;
            numMois = moisInt.toString();
        }
        return numAnnee + numMois;
    }

    /**
     * Retourne le mois sous format AAAAMM
     * @param annee
     * @param mois
     * @return
     */
    public static String getFormatMois(Integer annee, Integer mois ){
        String numMois = mois.toString();
        if (mois < 10) {
            numMois = "0" + mois;
        }
        return annee.toString() + numMois;
    }
}
