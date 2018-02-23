package easii.com.br.iscoolapp.main;

import android.content.Context;
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
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Disciplina;
import easii.com.br.iscoolapp.objetos.DisciplinaAluno;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

public class DisciplinasFilho extends AppCompatActivity implements RecyclerViewOnClickListener {


    private Toolbar toolbar;
    private List<Disciplina> list = new ArrayList<>();
    private List<DisciplinaAluno> list2 = new ArrayList<>();
    private RecyclerView recyclerView;
    private DisciplinaAdapter mAdapter;
    private long idFilho = 0;
    private String nomeFilho = null;
    private String listaDeDisciplina = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas_filho);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idFilho = bundle.getLong("EXTRA_ID_FILHO");
        nomeFilho = bundle.getString("EXTRA_NOME_FILHO");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Disciplinas - " + nomeFilho);
        getSupportActionBar().setLogo(R.drawable.iscool);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Olar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                Intent intent = new Intent(DisciplinasFilho.this, PerfilVisualizado.class);
                Bundle extras = new Bundle();
                extras.putLong("EXTRA_Id", idFilho);
                extras.putString("EXTRA_Nome",nomeFilho );
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new DisciplinaAdapter(list);
        mAdapter.setRecyclerViewOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        completaLista();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        // or call onBackPressed()

        return true;
    }

    private void completaLista() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LISTAR_DISCIPLINA_FILHOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);
                        if (response.equals("0")) {
                            Toast.makeText(DisciplinasFilho.this, "Sem Disciplinas!", Toast.LENGTH_LONG).show();
                        } else {

                            listaDeDisciplina = response;

                            Gson gson = new Gson();
                            DisciplinaAluno[] dArray = gson.fromJson(response, DisciplinaAluno[].class);

                            Disciplina d;
                            for (DisciplinaAluno dp : dArray) {
                                d = new Disciplina(dp.getNomeProfessor(), dp.getNome());
                                list.add(d);
                                list2.add(new DisciplinaAluno(dp.getId(), dp.getNome(), dp.getTurmo(), dp.getSerie(), dp.getEscola(), dp.getNomeProfessor()));
                                Log.i("LOG", dp.getNome());
                            }

                            mAdapter.notifyDataSetChanged();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DisciplinasFilho.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idFilho", String.valueOf(idFilho));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClickListener(View view, int position) {

        Intent intent = new Intent(view.getContext(), NotasFilho.class);
        Bundle extras = new Bundle();
        extras.putLong("EXTRA_ID_FILHO", idFilho);
        extras.putLong("EXTRA_ID_DISCIPLINA", list2.get(position).getId());
        extras.putString("EXTRA_NOME_FILHO",nomeFilho);
        intent.putExtras(extras);
        startActivity(intent);

    }

}
