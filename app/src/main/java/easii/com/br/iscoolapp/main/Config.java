package easii.com.br.iscoolapp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.Switch;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.objetos.Aluno2;


public class Config extends AppCompatActivity {

    private Toolbar toolbar;
    private Switch silenciarSwitch;
    private RadioGroup radioGroup;
    private RadioButton visivel, oculto, invisivel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Configurações");
        getSupportActionBar().setLogo(R.drawable.config);

        silenciarSwitch =  (Switch) findViewById(R.id.switch1);

        SharedPreferences sharedPreferences = Config.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        boolean silenciado = sharedPreferences.getBoolean("SILENCIAR",false);
        if(silenciado){
            silenciarSwitch.setChecked(true);
        }else{
            silenciarSwitch.setChecked(false);
        }

        silenciarSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean bChecked) {
                if (bChecked) {
                    SharedPreferences sharedPref = Config.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("SILENCIAR", true);
                    editor.commit();

                } else {
                    SharedPreferences sharedPref = Config.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putBoolean("SILENCIAR", false);
                    editor.commit();
                }
            }
        });


        radioGroup= (RadioGroup) findViewById(R.id.myRadioGroup);
        visivel = (RadioButton) findViewById(R.id.visivel);
        oculto = (RadioButton) findViewById(R.id.oculto);
        invisivel = (RadioButton) findViewById(R.id.invisivel);

        SharedPreferences sharedPreferences2 = Config.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String privacidade = sharedPreferences2.getString("PRIVACIDADE","NULL");

        if(privacidade.equals("NULL")){
            radioGroup.check(R.id.visivel);
        }else if (privacidade.equals("V")){
            radioGroup.check(R.id.visivel);
        }else if(privacidade.equals("O")){
            radioGroup.check(R.id.oculto);
        }else{
            radioGroup.check(R.id.invisivel);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId == R.id.visivel) {
                    SharedPreferences sharedPref = Config.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("PRIVACIDADE", "V");
                    editor.commit();
                    mudaPrivacidade("V");
                } else if(checkedId == R.id.oculto) {
                    SharedPreferences sharedPref = Config.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("PRIVACIDADE", "O");
                    editor.commit();
                    mudaPrivacidade("O");
                } else {
                    SharedPreferences sharedPref = Config.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("PRIVACIDADE", "I");
                    editor.commit();
                    mudaPrivacidade("I");
                }
            }
        });

    }


    public void sairDaAplicacao(View view) {

        SharedPreferences settings = this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        SharedPreferences settings2 = this.getSharedPreferences("androidhive-welcome", Context.MODE_PRIVATE);
        settings.edit().clear().commit();

        Intent i  = new Intent(this,MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void mudaPrivacidade(String p) {

        final String enviaPrivacidade = p;

        SharedPreferences sharedPref = this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String alunoJson = sharedPref.getString("ALUNO", "NULL");

        Gson gson = new Gson();
        final Aluno2 aluno2 = gson.fromJson(alunoJson, Aluno2.class);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_MUDA_PRIVACIDADE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG","muda privacidade" + response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Config.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idAluno", ""+aluno2.getId());
                params.put("privacidade",enviaPrivacidade);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
