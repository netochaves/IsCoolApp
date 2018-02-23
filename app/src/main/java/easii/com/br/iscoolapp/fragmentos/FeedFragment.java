package easii.com.br.iscoolapp.fragmentos;

import android.content.Context;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.adapters.FeedAdapter;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Feed;

/**
 * Created by gustavo on 16/09/2016.
 */
public class FeedFragment extends Fragment  {

    private List<Feed> listF = new ArrayList<>();
    private RecyclerView recyclerView;
    private View view;
    private FeedAdapter mAdapter;
    private Long idAluno;

    public FeedFragment() {
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
        mAdapter = new FeedAdapter(listF,view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
     //   recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        completaInfo();
        completaLista();

        return  view;
    }

    private void completaInfo() {
        SharedPreferences sharedPref = view.getContext().getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String alunoJson = sharedPref.getString("ALUNO", "NULL");

        Gson gson = new Gson();
        Aluno2 aluno2 = gson.fromJson(alunoJson, Aluno2.class);

        idAluno = aluno2.getId();
    }

    private void completaLista() {

        mAdapter.limpalista();
        Feed feed = new Feed("Avaliação de Tópicos em Engenharia de Software ","Hoje(6/10) será feita nossa primeira avaliação.Boa Sorte!","Pedro de Alcântara ","http://lms.tenhoprovaamanha.com.br/_arquivos/biblioteca/05835_prova-alternativa.jpg","6/10/2017");
        listF.add(feed);
  //      feed = new Feed("Festas juninas ","Começa hj tbm","voce","http://androidcss.com/wp-content/uploads/2016/05/android-navigation-drawer-menu-on-top-of-actionbar.jpg","1/12/2205");
  //      listF.add(feed);
//        feed = new Feed("quero q vcs se ....","vai ambrazendoo","zoobomafú","http://192.168.1.11:8080/teste/feed/fotofeed-1501207053178.png","1/12/2205");
//        listF.add(feed);
        mAdapter.notifyDataSetChanged();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_EXIBIR_FEED_ALUNO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG","explicacao -- s" +response);

                        Gson gson = new Gson();
                        if (response.equals("0")) {
                            Toast.makeText(view.getContext(), "Sem Notícias", Toast.LENGTH_LONG).show();
                        } else {
                            Feed[] feedArray = gson.fromJson(response, Feed[].class);
                            Feed feed1;

                            for (Feed f : feedArray) {
                                Log.i("LOG",">"+ f.getImageUrl());

                                Date date = new Date(Long.parseLong(f.getData()));
                                // DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);
                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                                //new SimpleDateFormat("dd/MM/yyyy");
                                int horaINT = date.getHours();
                                int minutoINT = date.getMinutes();

                                String minutoST = "" + minutoINT;
                                String horaST = "" + horaINT;

                                if (minutoINT < 10) {
                                    minutoST = "0" + minutoINT;
                                }
                                if (horaINT < 10) {
                                    horaST = "0" + horaINT;
                                }


                                feed1 = new Feed(f.getTitulo(),f.getDescricao(),f.getAutor(),f.getImageUrl()," às " + horaST + ":" + minutoST + " de " + df.format(date));
                                listF.add(feed1);
                            }

                            mAdapter.notifyDataSetChanged();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(view.getContext(), "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idAluno", String.valueOf(idAluno));
                //   params.put("idProva", String.valueOf(idProva));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);
    }


}
