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
import easii.com.br.iscoolapp.graficos.MediaDaTurma;
import easii.com.br.iscoolapp.graficos.MediaDoAluno;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Nota;
import easii.com.br.iscoolapp.objetos.Prova;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

public class TelaPerfilAlunoDoProfessor extends AppCompatActivity implements RecyclerViewOnClickListener {
    //VIEW
    private RecyclerView recyclerView;
    private ProvaAdapter mAdapter;
    private List<Prova> list = new ArrayList<>();
    private Toolbar toolbar;
    //EXTRAS E VARIAVEIS
    private Long idDisciplina, idProva = null;
    private String listaDeAlunos = null;
    private int posicao = -1;
    private Long idAluno = -1L;
    private String listaDeProvas = null;
    private FloatingActionButton fab;

    private ImageView imageSemProva;
    private TextView textSemProva;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_perfil_aluno_do_professor);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idDisciplina = bundle.getLong("EXTRA_ID_DISCIPLINA");
        listaDeAlunos = bundle.getString("EXTRA_LISTA_DE_ALUNOS");
        posicao = bundle.getInt("EXTRA_POSICAO");
        idAluno = bundle.getLong("EXTRA_ID_ALUNO");

        Gson gson = new Gson();
        Aluno2[] alunoArray = gson.fromJson(listaDeAlunos, Aluno2[].class);
        Aluno2 aluno = new Aluno2(alunoArray[posicao].getId(), alunoArray[posicao].getDisciplinas(), alunoArray[posicao].isAluno(), alunoArray[posicao].getEscola(), alunoArray[posicao].getTurno(), alunoArray[posicao].getDescricaoDaTurma(), alunoArray[posicao].getSerie(), alunoArray[posicao].getNome());


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(primeiroNomeDoAluno(aluno.getNome()));
        getSupportActionBar().setLogo(R.drawable.iscool);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new ProvaAdapter(list);
        mAdapter.setRecyclerViewOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        imageSemProva = (ImageView) findViewById(R.id.imageSemProvas);
        textSemProva = (TextView) findViewById(R.id.textoSemProvas);
        linearLayout = (LinearLayout)findViewById(R.id.ll);
        imageSemProva.setVisibility(View.VISIBLE);
        textSemProva.setVisibility(View.VISIBLE);

        Log.i("LOG", "id disciplina" + idDisciplina);

        listarProvas(idDisciplina);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), MediaDoAluno.class);
                Bundle extras = new Bundle();
                extras.putLong("EXTRA_ID_DISCIPLINA", idDisciplina);
                extras.putLong("EXTRA_ID_ALUNO", idAluno);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });


    }

    private String primeiroNomeDoAluno(String nomeCompleto) {


        int idx = nomeCompleto.indexOf(" ");

        if (idx == -1) {
            return nomeCompleto;
        } else {
            return (nomeCompleto.substring(0, nomeCompleto.indexOf(" ")));
        }

    }

    private void listarProvas(final Long idDisciplina) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LISTAR_PROVAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);

                        if(response.equals("0")){
                          //  Toast.makeText(TelaPerfilAlunoDoProfessor.this,"Provas não cadastradas",Toast.LENGTH_SHORT).show();
                            linearLayout.setVisibility(View.VISIBLE);
                            imageSemProva.setVisibility(View.VISIBLE);
                            imageSemProva.setVisibility(View.VISIBLE);
                        }else{
                            listaDeProvas = response;
                            Gson gson = new Gson();
                            Prova[] provaArray = gson.fromJson(response, Prova[].class);
                            Prova prova;

                            for (Prova p : provaArray) {

                                if ((p.getNome()).length() >= 30) {
                                    prova = new Prova(p.getId(), p.getNome(), p.getData());
                                    list.add(prova);

                                } else {
                                    prova = new Prova(p.getId(), (p.getNome()), p.getData());
                                    list.add(prova);
                                }

                                Log.i("LOG>", prova.getNome());
                            }

                            mAdapter.notifyDataSetChanged();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // Toast.makeText(TelaPerfilAlunoDoProfessor.this, "Provas não Cadastradas", Toast.LENGTH_LONG).show();

                        imageSemProva.setImageResource(R.drawable.semnetpreto);
                        imageSemProva.setVisibility(View.VISIBLE);
                        textSemProva.setText("Verifique sua Conexão!");
                        textSemProva.setVisibility(View.VISIBLE);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tela_perfil_aluno_do_professor, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListener(View view, int position) {

        Gson gson = new Gson();
        Prova[] provaArray = gson.fromJson(listaDeProvas, Prova[].class);
        Prova prova = new Prova(provaArray[position].getId(), provaArray[position].getNome(), provaArray[position].getData());

        Intent intent = new Intent(TelaPerfilAlunoDoProfessor.this, CameraCorrecao.class);

        Bundle extras = new Bundle();
        extras.putLong("EXTRA_ID_USUARIO", idAluno);
        extras.putString("EXTRA_ID_PROVAS", String.valueOf(prova.getId()));
        extras.putString("EXTRA_LISTA_DE_ALUNOS", listaDeAlunos);
        extras.putLong("EXTRA_ID_DISCIPLINA", idDisciplina);
        extras.putInt("EXTRA_POSICAO", posicao);

        intent.putExtras(extras);
        startActivity(intent);

    }
}
