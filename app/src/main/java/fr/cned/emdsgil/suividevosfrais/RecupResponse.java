package fr.cned.emdsgil.suividevosfrais;

public class RecupResponse implements AsyncResponse {

    private Controle controle;

    public RecupResponse (){
        controle = Controle.getInstance(null);
    };

    @Override
    public void processFinish(String output) {
        controle.finderecup(output);
    }

    public void envoi(){
        Connexion connexion = new Connexion();
        connexion.delegate = this;
        connexion.execute("http://192.168.1.8/Suividevosfrais2/service.php");
    }
}
