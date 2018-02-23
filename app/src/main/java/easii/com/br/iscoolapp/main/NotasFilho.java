package easii.com.br.iscoolapp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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
import easii.com.br.iscoolapp.adapters.DisciplinaAdapter;
import easii.com.br.iscoolapp.adapters.NotaAdapter;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.graficos.MediaDoAluno;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Disciplina;
import easii.com.br.iscoolapp.objetos.DisciplinaAluno;
import easii.com.br.iscoolapp.objetos.Nota;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

public class NotasFilho extends AppCompatActivity implements RecyclerViewOnClickListener {

    private Toolbar toolbar;
    private List<Nota> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private NotaAdapter mAdapter;
    private String listaDeNotas = null;
    private long  idFilho =0;
    private long idDisciplina = 0;
    private String nomeFilho = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_filho);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idFilho = bundle.getLong("EXTRA_ID_FILHO");
        idDisciplina = bundle.getLong("EXTRA_ID_DISCIPLINA");
        nomeFilho = bundle.getString("EXTRA_NOME_FILHO");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(view.getContext(), MediaDoAluno.class);
                Bundle extras = new Bundle();
                extras.putLong("EXTRA_ID_DISCIPLINA", idDisciplina);
                extras.putLong("EXTRA_ID_ALUNO", idFilho);
                intent.putExtras(extras);
                startActivity(intent);



               // Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();


            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Notas - "+ nomeFilho);
        getSupportActionBar().setLogo(R.drawable.iscool);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new NotaAdapter(list);
        mAdapter.setRecyclerViewOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        completaNota();
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }


    private void completaNota() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LISTAR_NOTA_FILHOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);
                        if (response.equals("0")) {
                            Toast.makeText(NotasFilho.this, "Sem Notas!", Toast.LENGTH_LONG).show();
                        } else {

                            listaDeNotas = response;

                            Gson gson = new Gson();
                            Nota[] nArray = gson.fromJson(response, Nota[].class);
                            Nota n;
                            for (Nota na : nArray) {
                                n = new Nota(na.getNomeDaProva(),na.getId(),na.getIdProva(),na.getPorcentagemDeAcertos());
                                list.add(n);

                            }

                            mAdapter.notifyDataSetChanged();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NotasFilho.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idFilho", String.valueOf(idFilho));
                params.put("idDisciplina", String.valueOf(idDisciplina));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClickListener(View view, int position) {

        Intent intent = new Intent(view.getContext(), NotaDoAluno.class);
        Bundle extras = new Bundle();
        Log.i("LOG","nota filho - "+idFilho + "   " + idDisciplina);
        extras.putLong("EXTRA_ID_USUARIO", idFilho);
        extras.putLong("EXTRA_ID_PROVA", list.get(position).getIdProva());
        intent.putExtras(extras);
        startActivity(intent);

    }
}
