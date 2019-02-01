package fr.cned.emdsgil.suividevosfrais.Outils;

import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import fr.cned.emdsgil.suividevosfrais.R;

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

    /**
     * Modification de l'affichage de la date (juste le mois et l'année, sans le jour)
     */
    public static void changeAfficheDate(DatePicker datePicker, boolean afficheJours) {
        try {
            Field f[] = datePicker.getClass().getDeclaredFields();
            for (Field field : f) {
                int daySpinnerId = Resources.getSystem().getIdentifier("day", "id", "android");
                datePicker.init(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), null);
                if (daySpinnerId != 0)
                {
                    View daySpinner = datePicker.findViewById(daySpinnerId);
                    if (!afficheJours)
                    {
                        daySpinner.setVisibility(View.GONE);
                    }
                }
            }
            // date max = date du jour
            datePicker.setMaxDate(System.currentTimeMillis());
        } catch (SecurityException | IllegalArgumentException e) {
            Log.d("ERROR", e.getMessage());
        }
    }

    /**
     * Définit la date minimum autorisée d'un DatePicker d'un an en arrière
     * @param datePicker
     */
    public static void setMinDate(DatePicker datePicker){
        Calendar dateMin = Calendar.getInstance();
        dateMin.add(Calendar.DAY_OF_YEAR,-365);
        datePicker.setMinDate(dateMin.getTimeInMillis());
    }
}
