package fr.cned.emdsgil.suividevosfrais.Controleur;

import android.content.Context;

import fr.cned.emdsgil.suividevosfrais.AccesConnexion.IntermediaireArrierePlan;
import fr.cned.emdsgil.suividevosfrais.MainActivity;

public class Controle {
    private static Controle instance = null;
    private static Context context;
    private static IntermediaireArrierePlan intermediaireArrierePlan;

    private Controle(){
        super();
    }

    public static final Controle getInstance(Context context){
        if (context != null){
            Controle.context = context;
        }
        if(Controle.instance == null){
            Controle.instance = new Controle();
            intermediaireArrierePlan = new IntermediaireArrierePlan();
        }
        return Controle.instance;
    }

    public void lanceRequete(String login, String mdp){
        intermediaireArrierePlan.envoi(login, mdp);
    }

    public void finderecup(String s){
        ((MainActivity)context).afficheResultat(s);
    }
}
