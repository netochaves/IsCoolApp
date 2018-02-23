package easii.com.br.iscoolapp.main;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.adapters.Aluno2Adapter;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.graficos.MediaDaTurma;
import easii.com.br.iscoolapp.graficos.MediaDoAluno;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Prova;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

public class TelaDeAlunosDoProfessor extends AppCompatActivity implements RecyclerViewOnClickListener {

    //VIEW
    private Toolbar toolbar;
    private FloatingActionButton fab;
    //ADAPTER
    private List<Aluno2> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private Aluno2Adapter mAdapter;
    //EXTRAS
    private Long idDisciplina = null;
    private String listaDeAlunos = null;

    private String listaDeProvas = null;
    private List<Prova> listdeProvas = new ArrayList<>();

    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    private ImageView imageSemRede;
    private TextView textSemRede;

    private LinearLayout linearLayout;


    private FloatingActionMenu materialDesignFAM;
    com.github.clans.fab.FloatingActionButton floatingActionButton1, floatingActionButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_de_alunos_do_professor);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Minha Turma");
        getSupportActionBar().setLogo(R.drawable.iscool);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new Aluno2Adapter(list);
        mAdapter.setRecyclerViewOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        imageSemRede = (ImageView) findViewById(R.id.imageSemRede);
        textSemRede = (TextView) findViewById(R.id.textoSem);
        linearLayout = (LinearLayout) findViewById(R.id.ll);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idDisciplina = bundle.getLong("EXTRA_ID_DISCIPLINA");

        listarProvas(idDisciplina);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TelaDeAlunosDoProfessor.this, MediaDaTurma.class);
                Bundle extras = new Bundle();
                extras.putLong("EXTRA_ID_DISCIPLINA", idDisciplina);
                intent.putExtras(extras);
                startActivity(intent);
            }
        });

//        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.fab);
//        floatingActionButton1 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
//        floatingActionButton2 = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
//
//        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                AlertDialog.Builder builderSingle = new AlertDialog.Builder(TelaDeAlunosDoProfessor.this);
//                builderSingle.setIcon(R.drawable.correcaoazul);
//                builderSingle.setTitle("Selecione a Prova:");
//
//                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(TelaDeAlunosDoProfessor.this, android.R.layout.select_dialog_singlechoice);
//              //  arrayAdapter.add("Hardik");
//                for (int i = 0 ;i <listdeProvas.size();i++){
//
//                    arrayAdapter.add(listdeProvas.get(i).getNome());
//                }
//
//                builderSingle.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//
//                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which22) {
//                        String strName = arrayAdapter.getItem(which22);
//                        AlertDialog.Builder builderInner = new AlertDialog.Builder(TelaDeAlunosDoProfessor.this);
//                        builderInner.setMessage(strName);
//                        builderInner.setTitle("Confirmar envio da explicação para a turma abaixo?");
//                        Log.i("LOG","wchich " + which22);
//                        final int aux = which22;
//                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//
//                            @Override
//                            public void onClick(DialogInterface dialog,int which) {
//                                AsyncHttpClient client = new AsyncHttpClient();
//                                String url = URL.URL_ENVIA_EXPLICACAO+""+listdeProvas.get(aux).getId();
//                                Log.i("LOG", ""+ url);
//                                client.get(url, new AsyncHttpResponseHandler() {
//
//
//                                    @Override
//                                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
//                                        Log.i("LOG","deu bom cusao");
//                                    }
//
//                                    @Override
//                                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
//                                        Log.i("LOG","deu ruim clã");
//                                    }
//                                });
//                            }
//
//
//                        });
//                        builderInner.show();
//                    }
//                });
//                builderSingle.show();
//
//
//            }
//        });
//        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//
//                Intent intent = new Intent(TelaDeAlunosDoProfessor.this, MediaDaTurma.class);
//                Bundle extras = new Bundle();
//                extras.putLong("EXTRA_ID_DISCIPLINA", idDisciplina);
//                intent.putExtras(extras);
//                startActivity(intent);
//            }
//        });

        completaAlunos();
    }


    private void completaAlunos() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LISTAR_USUARIOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);
                        if (response.equals("0")) {
                            Toast.makeText(TelaDeAlunosDoProfessor.this, "Essa disciplina não possui alunos", Toast.LENGTH_LONG).show();
                            linearLayout.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.VISIBLE);
                            textSemRede.setVisibility(View.VISIBLE);
                            textSemRede.setText("Sem alunos nessa disciplina!");
                        } else {

                            progressBar.setVisibility(View.INVISIBLE);

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
                        //  Toast.makeText(TelaDeAlunosDoProfessor.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                        linearLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        textSemRede.setVisibility(View.VISIBLE);
                        imageSemRede.setVisibility(View.VISIBLE);

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
                params.put("idDisciplina", String.valueOf(idDisciplina));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void listarProvas(final Long idDisciplina) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_LISTAR_PROVAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);

                        if (response.equals("0")) {
                            //   Toast.makeText(TelaDeAlunosDoProfessor.this,"Prova não cadastradas",Toast.LENGTH_SHORT).show();

                        } else {
                            listaDeProvas = response;
                            Gson gson = new Gson();
                            Prova[] provaArray = gson.fromJson(response, Prova[].class);
                            Prova prova;

                            for (Prova p : provaArray) {

                                if ((p.getNome()).length() >= 30) {
                                    prova = new Prova(p.getId(), p.getNome(), p.getData());
                                    listdeProvas.add(prova);

                                } else {
                                    prova = new Prova(p.getId(), (p.getNome()), p.getData());
                                    listdeProvas.add(prova);
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
                        Log.i("LOG", "Fudeu");
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
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_tela_de_alunos_do_professor, menu);
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

        Aluno2[] alunoArray = gson.fromJson(listaDeAlunos, Aluno2[].class);
        Aluno2 aluno = new Aluno2(alunoArray[position].getId(), alunoArray[position].getDisciplinas(), alunoArray[position].isAluno(), alunoArray[position].getEscola(), alunoArray[position].getTurno(), alunoArray[position].getDescricaoDaTurma(), alunoArray[position].getSerie(), alunoArray[position].getNome());

        Log.i("LOG", "" + aluno.getId());

        Intent intent = new Intent(view.getContext(), TelaPerfilAlunoDoProfessor.class);

        Bundle extras = new Bundle();
        extras.putLong("EXTRA_ID_DISCIPLINA", idDisciplina);
        extras.putString("EXTRA_LISTA_DE_ALUNOS", listaDeAlunos);
        extras.putInt("EXTRA_POSICAO", position);
        extras.putLong("EXTRA_ID_ALUNO", aluno.getId());
        intent.putExtras(extras);

        startActivity(intent);
    }
}
