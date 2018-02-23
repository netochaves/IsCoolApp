package easii.com.br.iscoolapp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import easii.com.br.iscoolapp.adapters.ProvaAdapter;
import easii.com.br.iscoolapp.constantes.Constante;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.graficos.MediaDoAluno;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Professor2;
import easii.com.br.iscoolapp.objetos.Prova;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

public class TelaDeProvasDoAluno extends AppCompatActivity implements RecyclerViewOnClickListener {

    private Toolbar toolbar;
    private List<Prova> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProvaAdapter mAdapter;
    private Long idDisciplina = null;
    private String listaDeProvas = null;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_provas_do_aluno);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Minhas Provas");
        getSupportActionBar().setLogo(R.drawable.iscool);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ProvaAdapter(list);
        mAdapter.setRecyclerViewOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idDisciplina = bundle.getLong("EXTRA_ID_DISCIPLINA");

        listarProvas(idDisciplina);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MediaDoAluno.class);
                Bundle extras = new Bundle();
                extras.putLong("EXTRA_ID_DISCIPLINA", idDisciplina);
                extras.putLong("EXTRA_ID_ALUNO", acessaSharedPreferences());
                intent.putExtras(extras);
                startActivity(intent);
            }
        });
    }

    private long acessaSharedPreferences() {

        SharedPreferences sharedPref = this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String tipo = sharedPref.getString("TIPO", "NULL");

        if (tipo.equals("ALUNO")) {

            String alunoJson = sharedPref.getString("ALUNO", "NULL");
            Gson gson = new Gson();
            Aluno2 aluno2 = gson.fromJson(alunoJson, Aluno2.class);
            return aluno2.getId();

        } else {
            String profJson = sharedPref.getString("PROFESSOR", "NULL");
            Gson gson = new Gson();
            Professor2 professor2 = gson.fromJson(profJson, Professor2.class);
            return professor2.getId();

        }
    }

    private void listarProvas(final Long idDisciplina) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LISTAR_PROVAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);
                        listaDeProvas = response;
                        Gson gson = new Gson();
                        if (response.equals("0")) {
                           Toast.makeText(TelaDeProvasDoAluno.this, "Sem provas!", Toast.LENGTH_LONG).show();

                        } else {
                            Prova[] provaArray = gson.fromJson(response, Prova[].class);
                            Prova prova;

                            for (Prova p : provaArray) {
                                prova = new Prova(p.getId(), p.getNome(), p.getData());
                                list.add(prova);

                                Log.i("LOG>", prova.getNome());
                            }

                            mAdapter.notifyDataSetChanged();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TelaDeProvasDoAluno.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idDisciplina", String.valueOf(idDisciplina));

                return params;
            }

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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tela_de_provas_do_aluno, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
//            Intent intent = new Intent(this, RankingProva.class);
//            Bundle extras = new Bundle();
//            extras.putLong("EXTRA_ID",  idDisciplina);
//            extras.putBoolean("isNota",  false);
//            intent.putExtras(extras);
//            startActivity(intent);
        }
        if (id == R.id.action_ranking) {
            Intent intent = new Intent(this, RankingProva.class);
            Bundle extras = new Bundle();
            extras.putLong("EXTRA_ID", idDisciplina);
            extras.putBoolean("isNota", false);
            intent.putExtras(extras);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(View view, int position) {
        //     Toast.makeText(TelaDeProvasDoAluno.this, ""+position, Toast.LENGTH_LONG).show();
        Gson gson = new Gson();
        Prova[] provaArray = gson.fromJson(listaDeProvas, Prova[].class);
        Prova prova = new Prova(provaArray[position].getId(), provaArray[position].getNome(), provaArray[position].getData());


        SharedPreferences sharedPref = view.getContext().getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String alunoJson = sharedPref.getString("ALUNO", "NULL");

        Aluno2 aluno = gson.fromJson(alunoJson, Aluno2.class);

        Log.i("LOG>", "" + prova.getId());

        Intent intent = new Intent(view.getContext(), NotaDoAluno.class);
        Bundle extras = new Bundle();
        extras.putLong("EXTRA_ID_USUARIO", aluno.getId());
        extras.putLong("EXTRA_ID_PROVA", prova.getId());
        intent.putExtras(extras);
        startActivity(intent);
    }
}
