package fr.cned.emdsgil.suividevosfrais.AccesConnexion;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;

public class IntermediaireArrierePlan implements AsyncResponse {

    private Controle controle;

    public IntermediaireArrierePlan(){
        controle = Controle.getInstance(null);
    };

    @Override
    public void processFinish(String output) {
        controle.finderecup(output);
    }

    public void envoi(String login, String mdp){
        TacheArrierePlan tacheArrierePlan = new TacheArrierePlan();
        tacheArrierePlan.delegate = this;
        tacheArrierePlan.execute(login, mdp);
    }
}
