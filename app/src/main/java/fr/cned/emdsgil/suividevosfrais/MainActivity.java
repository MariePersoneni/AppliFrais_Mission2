package fr.cned.emdsgil.suividevosfrais;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    private Context context = this;
    private EditText id, mdp;
    private Button bouton;

    public Context getContext() {
        return context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_change((EditText)findViewById(R.id.txtLogin));
        txt_change((EditText)findViewById(R.id.txtMdp));
        focus_change((EditText)findViewById(R.id.txtMdp));
        btnConnexion_clic();
    }

    /**
     * Fonction executée à la création de la page de connexion
     * Elle permet d'écouter les modifications sur un objet Edit Text
     * Elle modifie la oouleur du texte en cas de modification
     * Elle vide le champ Login pour une saisie plus rapide
     *
     * @param champ -> Edit Text à modifier
     */
    private void txt_change(final EditText champ){
        champ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String texte = (champ.getText()).toString();
                // changement couleur à la modification
                if ( texte != "Login" & texte != "****") champ.setTextColor(Color.BLACK);
                // Efface le login
                if (texte.equals("Logi")) champ.setText("");
            }
        });
    }

    /**
     * Fonction executée à la création de la page de connexion
     * Elle permet d'écouter lorsqu'un champ récupère le focus
     * Elle vide le champ de mot de passe lorsque celui si à le
     * focus et qu'il contient la valeur initiale ****
     *
     * @param champ Edit Text à modifier
     */
    private void focus_change(final EditText champ){
        champ.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String texte = (champ.getText()).toString();
                if(hasFocus & texte.equals("****")){
                    champ.setText("");
                }
            }
        });
    }

    private void btnConnexion_clic() {
        ((Button)findViewById(R.id.btnConnexion)).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Envoi de la requête
                new Connexion().execute("http://192.168.1.8/Suividevosfrais2/service.php");
            }
        });
    }

    public void afficheResultat(String reponse){
        if(reponse.equals("1")){
            // mdp incorrect
            Toast.makeText(MainActivity.this, "Mot de passe incorrect", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
        }
    }
}
