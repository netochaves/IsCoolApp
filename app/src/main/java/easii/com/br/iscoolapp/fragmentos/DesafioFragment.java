package easii.com.br.iscoolapp.fragmentos;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.adapters.DesafioAdapter;
import easii.com.br.iscoolapp.graficos.MediaDoAluno;
import easii.com.br.iscoolapp.objetos.Desafio;
import easii.com.br.iscoolapp.util.DividerItemDecoration;
import easii.com.br.iscoolapp.util.RecyclerItemClickListener;

/**
 * Created by gustavo on 01/05/2017.
 */

public class DesafioFragment extends Fragment {

    private List<Desafio> desafios = new ArrayList<>();
    private View view;
    private RecyclerView recyclerView;
    private DesafioAdapter mAdapter;

    public DesafioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_desafio, container, false);

        desafios.add(new Desafio("Maior nota", 2, "Gustavo e joao"));
        desafios.add(new Desafio("Maior nota da sala", 2, "Gustavo , pedro , mao santa"));
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mAdapter = new DesafioAdapter(desafios);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(view.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }
                })
        );


        mAdapter.notifyDataSetChanged();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        return view;

    }

}
