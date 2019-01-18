package fr.cned.emdsgil.suividevosfrais;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import fr.cned.emdsgil.suividevosfrais.Controleur.Controle;

public class MainActivity extends AppCompatActivity {
    private Context context = this;
    private EditText id, mdp;
    private Button bouton;
    public static String reponse = null;
    private Controle controle;

    public static void setReponse(String reponse) {
        MainActivity.reponse = reponse;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controle = Controle.getInstance(this);
        btnConnexion_clic();
    }

    private void btnConnexion_clic() {
        ((Button)findViewById(R.id.btnConnexion)).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Envoi de la requête
                String login = ((EditText)findViewById(R.id.txtLogin)).getText().toString();
                String mdp = ((EditText)findViewById(R.id.txtMdp)).getText().toString();
                controle.lanceRequete(login, mdp);
            }
        });
    }

    public void afficheResultat(String resultat){
        if(resultat.equals("1")){
            Toast.makeText(MainActivity.this, "mdp incorrect", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "connexion réussie",Toast.LENGTH_SHORT).show();
        }
    }
}
