package easii.com.br.iscoolapp.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.adapters.DisciplinaAdapter;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.main.TelaDeProvasDoAluno;
import easii.com.br.iscoolapp.main.TelaMensagens;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Disciplina;
import easii.com.br.iscoolapp.objetos.DisciplinaAluno;
import easii.com.br.iscoolapp.objetos.DisciplinaProfessor;
import easii.com.br.iscoolapp.objetos.Professor2;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

/**
 * Created by gustavo on 16/09/2016.
 */
public class OneFragmentAluno extends Fragment implements RecyclerViewOnClickListener {

    private List<Disciplina> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private View view;
    private DisciplinaAdapter mAdapter;

    public OneFragmentAluno() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_one, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new DisciplinaAdapter(list);
        mAdapter.setRecyclerViewOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        completaLista();

        return  view;
    }

    private void completaLista() {

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String alunoJson = sharedPref.getString("ALUNO", "NULL");
        Log.i("LOG -- ", alunoJson);
        Gson gson = new Gson();
        Aluno2 aluno = gson.fromJson(alunoJson, Aluno2.class);

        Disciplina d;

        mAdapter.limpalista();

        for (DisciplinaAluno dp : aluno.getDisciplinas()){
            d = new Disciplina(dp.getNomeProfessor(), dp.getNome());
            list.add(d);
            Log.i("LOG>", dp.getNome());
        }


        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListener(View view, int position) {
        SharedPreferences sharedPref = view.getContext().getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String alunoJson = sharedPref.getString("ALUNO", "NULL");

        Gson gson = new Gson();
        Aluno2 aluno2 = gson.fromJson(alunoJson, Aluno2.class);

        List<DisciplinaAluno> lista = aluno2.getDisciplinas();

        Intent intent = new Intent(view.getContext(), TelaMensagens.class);
        Bundle extras = new Bundle();
        extras.putLong("EXTRA_ID_DISCIPLINA", lista.get(position).getId());
        extras.putString("EXTRA_NOME_DISCIPLINA", lista.get(position).getNome());
        intent.putExtras(extras);
        startActivity(intent);
    }
}
