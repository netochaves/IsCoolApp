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
import easii.com.br.iscoolapp.main.TelaDeAlunosDoProfessor;
import easii.com.br.iscoolapp.objetos.Disciplina;
import easii.com.br.iscoolapp.objetos.DisciplinaProfessor;
import easii.com.br.iscoolapp.objetos.Professor;
import easii.com.br.iscoolapp.objetos.Professor2;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

/**
 * Created by gustavo on 16/08/2016.
 */
public class TwoFragment extends Fragment implements RecyclerViewOnClickListener {

    private List<Disciplina> list = new ArrayList<>();
    private RecyclerView recyclerView;
    private DisciplinaAdapter mAdapter;
    private View view;

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_two, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mAdapter = new DisciplinaAdapter(list);
        mAdapter.setRecyclerViewOnClickListener(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        completaLista();
        return view;
    }

    private void completaLista() {

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String profJson = sharedPref.getString("PROFESSOR", "NULL");

        Gson gson = new Gson();
        Professor2 professor = gson.fromJson(profJson, Professor2.class);
        Disciplina d;

        for (DisciplinaProfessor dp : professor.getDisciplinas()) {
            d = new Disciplina(dp.getEscola().toString(), dp.getNome().toString());
            list.add(d);
            Log.i("LOG>", dp.getNome());
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClickListener(View view, int position) {

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String profJson = sharedPref.getString("PROFESSOR", "NULL");

        Gson gson = new Gson();
        Professor2 professor = gson.fromJson(profJson, Professor2.class);

        List<DisciplinaProfessor> lista = professor.getDisciplinas();

        Intent intent = new Intent(view.getContext(), TelaDeAlunosDoProfessor.class);
        Bundle extras = new Bundle();
        extras.putLong("EXTRA_ID_DISCIPLINA", lista.get(position).getId());
        intent.putExtras(extras);
        startActivity(intent);
    }

}
