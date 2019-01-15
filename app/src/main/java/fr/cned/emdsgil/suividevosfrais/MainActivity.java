package fr.cned.emdsgil.suividevosfrais;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_change((EditText)findViewById(R.id.txtLogin));
        txt_change((EditText)findViewById(R.id.txtMdp));
        focus_change((EditText)findViewById(R.id.txtMdp));

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
}
