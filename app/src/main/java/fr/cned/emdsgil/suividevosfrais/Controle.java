package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;

public class Controle {
    private static Controle instance = null;
    private static Context context;
    private static RecupResponse recupResponse;

    private Controle(){
        super();
    }

    public static final Controle getInstance(Context context){
        if (context != null){
            Controle.context = context;
        }
        if(Controle.instance == null){
            Controle.instance = new Controle();
            recupResponse = new RecupResponse();
        }
        return Controle.instance;
    }

    public void lanceRequete(){
        recupResponse.envoi();
    }

    public void finderecup(String s){
        ((MainActivity)context).afficheResultat(s);
    }
}
