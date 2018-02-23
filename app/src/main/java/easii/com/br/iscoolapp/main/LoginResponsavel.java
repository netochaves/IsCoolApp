package easii.com.br.iscoolapp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Professor2;

public class LoginResponsavel extends AppCompatActivity {

    private EditText usuario;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_responsavel);

        usuario = (EditText) findViewById(R.id.etLogin);

    }

    public void fazerLogin(View view) {

        final String usuarioInformado = usuario.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LOGAR_RESPONSAVEL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);
                        if (!response.equals("0")) {

                            SharedPreferences sharedPref = context.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("CPF", usuarioInformado);
                            editor.putString("TIPO", "PAI");
                            editor.putString("LOGADO", "SIM");
                            editor.commit();

                            Intent intent = new Intent(LoginResponsavel.this, MenuResponsavel.class);
                            startActivity(intent);

                        }else{
                            Toast.makeText(LoginResponsavel.this, "USUÁRIO NÃO ENCONTRADO OU NAO POSSUI FILHOS!", Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginResponsavel.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cpf", usuarioInformado);


                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
