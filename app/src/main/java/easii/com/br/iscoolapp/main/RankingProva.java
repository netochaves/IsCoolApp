package easii.com.br.iscoolapp.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import easii.com.br.iscoolapp.adapters.Aluno2Adapter;
import easii.com.br.iscoolapp.adapters.RankingAdapter;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.AlunoRanking;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

public class RankingProva extends AppCompatActivity implements RecyclerViewOnClickListener {

    private Toolbar toolbar;
    private List<AlunoRanking> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private RankingAdapter mAdapter;
    private Long id = -1L;
    private String listaDeAlunos = null;
    private boolean isNota = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ranking_prova);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getLong("EXTRA_ID");
        isNota = bundle.getBoolean("isNota");
        if(isNota){
            getSupportActionBar().setTitle(" Ranking da Prova");
        }else{
            getSupportActionBar().setTitle(" Ranking da Disciplina");
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setLogo(R.drawable.trofeu);



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new RankingAdapter(list);
        mAdapter.setRecyclerViewOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        completaAlunos();
    }
    private void completaAlunos() {
        String url;
        if(isNota){
            url = URL.URL_LISTAR_USUARIOS_RANK;
        }else{
            url = URL.URL_LISTAR_USUARIOS_RANK_DISCIPLINA;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);
                        if (response.equals("0")) {
                            Toast.makeText(RankingProva.this, "Erro :/", Toast.LENGTH_LONG).show();
                        } else {

                            listaDeAlunos = response;
                            Gson gson = new Gson();
                            AlunoRanking[] alunoArray = gson.fromJson(response, AlunoRanking[].class);
                            AlunoRanking alunoRanking;

                            for (AlunoRanking p : alunoArray) {
                                alunoRanking = new AlunoRanking(p.getPosicao(), p.getNome(), p.getXp(),p.getId());
                                list.add(alunoRanking);
                                Log.i("LOG", alunoRanking.getNome());
                            }

                            mAdapter.notifyDataSetChanged();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(RankingProva.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(id));

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
    public void onClickListener(View view, int position) {
        Gson gson = new Gson();
        AlunoRanking[] alunoArray = gson.fromJson(listaDeAlunos, AlunoRanking[].class);
        //Toast.makeText(RankingProva.this, "vai pra tela de perfil" + alunoArray[position].getNome(), Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, PerfilVisualizado.class);
        Bundle extras = new Bundle();
        extras.putLong("EXTRA_Id", alunoArray[position].getId());
        extras.putString("EXTRA_Nome",alunoArray[position].getNome() );
        intent.putExtras(extras);
        startActivity(intent);
    }
}
