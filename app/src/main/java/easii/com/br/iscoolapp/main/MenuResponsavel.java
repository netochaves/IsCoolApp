package easii.com.br.iscoolapp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.DisciplinaAluno;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

public class MenuResponsavel extends AppCompatActivity implements RecyclerViewOnClickListener {

    private Toolbar toolbar;
    private List<Aluno2> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Aluno2Adapter mAdapter;
    private String listaDeAlunos = null;
    Context context = this;
    String cpf = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_responsavel);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(" Meus Filhos");
        getSupportActionBar().setLogo(R.drawable.iscool);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new Aluno2Adapter(list);
        mAdapter.setRecyclerViewOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        SharedPreferences sharedPreferences = context.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        cpf = sharedPreferences.getString("CPF", "NULL");

        completaAlunos();
    }

    private void completaAlunos() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LISTAR_FILHOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);
                        if (response.equals("0")) {
                            Toast.makeText(MenuResponsavel.this, "Você nao possui filhos cadastrados ainda!", Toast.LENGTH_LONG).show();
                        } else {

                            listaDeAlunos = response;
                            Gson gson = new Gson();
                            Aluno2[] alunoArray = gson.fromJson(response, Aluno2[].class);
                            Aluno2 aluno2;

                            for (Aluno2 p : alunoArray) {
                                aluno2 = new Aluno2(p.getId(), p.getDisciplinas(), p.isAluno(), p.getEscola(), p.getTurno(), p.getDescricaoDaTurma(), p.getSerie(), p.getNome());
                                list.add(aluno2);
                                Log.i("LOG", aluno2.getNome());
                            }

                            mAdapter.notifyDataSetChanged();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenuResponsavel.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cpf", cpf);

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClickListener(View view, int position) {

        Intent intent = new Intent(view.getContext(), DisciplinasFilho.class);
        Bundle extras = new Bundle();
        extras.putLong("EXTRA_ID_FILHO", list.get(position).getId());
        extras.putString("EXTRA_NOME_FILHO", list.get(position).getNome());
        intent.putExtras(extras);
        startActivity(intent);

    }
}
