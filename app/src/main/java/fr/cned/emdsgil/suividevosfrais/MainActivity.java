package fr.cned.emdsgil.suividevosfrais;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
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
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
                controle.lanceRequete();
                //new Connexion().execute("http://192.168.1.8/Suividevosfrais2/service.php");
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
