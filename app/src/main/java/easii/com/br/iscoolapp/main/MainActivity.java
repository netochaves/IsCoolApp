package easii.com.br.iscoolapp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;


import java.util.HashMap;
import java.util.Map;

import easii.com.br.iscoolapp.MyFirebaseInstanceIDService;
import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.constantes.URL;

import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Professor2;

public class MainActivity extends AppCompatActivity {

    //VIEW
    private EditText usuario;
    private EditText senha;
    private ProgressBar progressBar;
    //Constantes
    Context context = this;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        usuario = (EditText) findViewById(R.id.etLogin);
        senha = (EditText) findViewById(R.id.etSenha);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);



//        Intent it = new Intent(MainActivity.this, MyFirebaseInstanceIDService.class);
//        startService(it);

        SharedPreferences sharedPreferences = context.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String tipo = sharedPreferences.getString("TIPO", "NULL");
        String logado = sharedPreferences.getString("LOGADO", "NULL");
        if (logado.equals("SIM")) {

            if (tipo.equals("ALUNO")) {
                Intent intent = new Intent(MainActivity.this, MenuAluno.class);
                startActivity(intent);

            } else if(tipo.equals("PROFESSOR")){
                Intent intent = new Intent(MainActivity.this, MenuProfessor.class);
                startActivity(intent);
            }else{
                Intent intent = new Intent(MainActivity.this, MenuResponsavel.class);
                startActivity(intent);
            }


        }
    }

    public void fazerLogin(View view) {

        final String senhaInformada = senha.getText().toString();
        final String usuarioInformado = usuario.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LOGAR,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);

                        Log.i("LOG", response);
                        if (!response.equals("0")) {

                            Gson gson = new Gson();
                            Professor2 professor = gson.fromJson(response, Professor2.class);
                            Aluno2 aluno = gson.fromJson(response, Aluno2.class);

                            if ((String.valueOf(professor.getId())) == null && String.valueOf(aluno.getId()) == null) {
                                Toast.makeText(MainActivity.this, "USUÁRIO NÃO ENCONTRADO!", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                            if (professor.isAluno() == false) {

                                SharedPreferences sharedPref = context.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.putString("PROFESSOR", response);
                                editor.putString("TIPO", "PROFESSOR");
                                editor.putString("LOGADO", "SIM");
                                editor.putString("ID_USER", "" + professor.getId());
                                editor.commit();

                                Intent it = new Intent(MainActivity.this, MyFirebaseInstanceIDService.class);
                                startService(it);

                                SharedPreferences sharedPref2 = MainActivity.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                                String token = sharedPref2.getString("TOKEN", "NULL");
                                Log.i("LOG", "token volley teste 1 " + token);
                                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                Log.i("LOG", "token volley teste 2 " + refreshedToken);
                                sendRegistrationId(refreshedToken, "" + professor.getId());

                                Intent intent = new Intent(MainActivity.this, MenuProfessor.class);
                                startActivity(intent);


                            } else {
                                if (aluno.isAluno() == true) {

                                    SharedPreferences sharedPref = context.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPref.edit();
                                    editor.putString("ALUNO", response);
                                    editor.putString("TIPO", "ALUNO");
                                    editor.putString("LOGADO", "SIM");
                                    editor.putString("ID_USER", "" + aluno.getId());
                                    editor.commit();

                                    Intent it = new Intent(MainActivity.this, MyFirebaseInstanceIDService.class);
                                    startService(it);

                                    SharedPreferences sharedPref2 = MainActivity.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
                                    String token = sharedPref2.getString("TOKEN", "NULL");

                                    String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                    Log.i("LOG", "token volley teste 2 " + refreshedToken);

                                    sendRegistrationId(refreshedToken, "" + aluno.getId());



                                    Intent intent = new Intent(MainActivity.this, MenuAluno.class);
                                    startActivity(intent);


                                } else {
                                    Toast.makeText(MainActivity.this, "USUÁRIO NÃO ENCONTRADO!", Toast.LENGTH_LONG).show();
                                }
                            }

                        } else {
                            Toast.makeText(MainActivity.this, "USUÁRIO NÃO ENCONTRADO!", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Toast.makeText(MainActivity.this, "Verifique sua conexão! 2", Toast.LENGTH_LONG).show();
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "Verifique sua conexão", Snackbar.LENGTH_LONG)
                                .setAction("OK", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                });
                        snackbar.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("login", usuarioInformado);
                params.put("senha", senhaInformada);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void sendRegistrationId(final String token, final String id) {

        Log.i("LOG", "token volley-- " + token);
        Log.i("LOG", "id usr volley --" + id);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG", "CADASTRADO" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("LOG", " NAO CADASTRADO");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUser", id);
                params.put("token", token);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void onClickEsqueceu(View v) {

        Uri uri = Uri.parse(URL.URL_ESQUECEU);

        Intent intent = new Intent(Intent.ACTION_VIEW, uri);

        startActivity(intent);

    }

    public void onClickResponsavel(View v) {

        Intent intent = new Intent(MainActivity.this, LoginResponsavel.class);
        startActivity(intent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
