package easii.com.br.iscoolapp.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.adapters.ExplicacaoAdapter;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.objetos.Explicacao;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

/**
 * Created by gustavo on 27/07/2017.
 */

public class TelaExplicacao extends AppCompatActivity {

    private Long idAluno = -1L, idProva = -1L;
    private List<Explicacao> listE = new ArrayList<>();
    private RecyclerView recyclerView;
    private ExplicacaoAdapter mAdapter;
    private Context context =TelaExplicacao.this;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_explicacao);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idProva = bundle.getLong("EXTRA_ID_PROVA");
        idAluno = bundle.getLong("EXTRA_ID_USUARIO");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Explicações");
        getSupportActionBar().setLogo(R.drawable.textmsg);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new ExplicacaoAdapter(listE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(context, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        completaLista();

    }



    private void completaLista() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL.URL_EXIBIR_EXPLICACAO_ALUNO+idAluno+"/"+idProva,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG","explicacao -- s" +response);

                        Gson gson = new Gson();
                        if (response.equals("0")) {
                            Toast.makeText(TelaExplicacao.this, "Sem explicações", Toast.LENGTH_LONG).show();
                        } else {
                            Explicacao[] explicacaoArray = gson.fromJson(response, Explicacao[].class);
                            Explicacao explicacao;
                            int i = 1;
                            for (Explicacao e : explicacaoArray) {

                                explicacao = new Explicacao(getText(R.string.explicacao) + e.getExplicacao(),e.isAcertou(),i+") "+e.getDescricao(),getText(R.string.a)+e.getPrimeiraAlternativa(),getText(R.string.b)+e.getSegundaAlternativa(),getText(R.string.c)+e.getTerceiraAlternativa(),getText(R.string.d)+e.getQuartaAlternativa(),getText(R.string.e)+e.getQuintaAlternativa(),getText(R.string.resposta_correta)+e.getRespostaCorreta(),getText(R.string.resposta_aluno)+e.getRespostaAluno());
                                listE.add(explicacao);
                                Log.i("LOG>", explicacao.getDescricao());
                                i++;
                            }


                            mAdapter.notifyDataSetChanged();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TelaExplicacao.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                    }
                }) {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        // or call onBackPressed()

        return true;
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tela_de_provas_do_aluno, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
////            Intent intent = new Intent(this, RankingProva.class);
////            Bundle extras = new Bundle();
////            extras.putLong("EXTRA_ID",  idDisciplina);
////            extras.putBoolean("isNota",  false);
////            intent.putExtras(extras);
////            startActivity(intent);
//        }
//
//
//        return super.onOptionsItemSelected(item);
//    }

}
