package easii.com.br.iscoolapp.fragmentos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import de.hdodenhof.circleimageview.CircleImageView;
import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.adapters.DisciplinaAdapter;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.main.TelaDeProvasDoAluno;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Disciplina;
import easii.com.br.iscoolapp.objetos.DisciplinaAluno;
import easii.com.br.iscoolapp.objetos.Insignia;
import easii.com.br.iscoolapp.objetos.Prova;
import easii.com.br.iscoolapp.util.DividerItemDecoration;

/**
 * Created by gustavo on 13/02/2017.
 */

public class PerfilFragmentoAluno extends Fragment {


    private View view;
    private TextView nometx,infotx;
    public CircleImageView s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12;
    private String listaDeInsignias = null;

    public PerfilFragmentoAluno() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_perfil, container, false);

        s1 = (CircleImageView) view.findViewById(R.id.s1);
        s2 = (CircleImageView) view.findViewById(R.id.s2);
        s3 = (CircleImageView) view.findViewById(R.id.s3);
        s4 = (CircleImageView) view.findViewById(R.id.s4);
        s5 = (CircleImageView) view.findViewById(R.id.s5);
        s6 = (CircleImageView) view.findViewById(R.id.s6);
        s7 = (CircleImageView) view.findViewById(R.id.s7);
        s8 = (CircleImageView) view.findViewById(R.id.s8);
        s9 = (CircleImageView) view.findViewById(R.id.s9);
        s10 = (CircleImageView) view.findViewById(R.id.s10);
        s11 = (CircleImageView) view.findViewById(R.id.s11);
        s12 = (CircleImageView) view.findViewById(R.id.s12);
        nometx = (TextView) view.findViewById(R.id.nomedoperfil);
        infotx = (TextView) view.findViewById(R.id.info);

        setaInformacoes();
        procuraInsignea();
//
//        s1.setImageResource(R.mipmap.s1);
//        s2.setImageResource(R.mipmap.s2);
//        s3.setImageResource(R.mipmap.s3);
//        s4.setImageResource(R.mipmap.s4);
//        s5.setImageResource(R.mipmap.s5);
//        s6.setImageResource(R.mipmap.s6);
//        s7.setImageResource(R.mipmap.s7);
//        s8.setImageResource(R.mipmap.s8);
//        s9.setImageResource(R.mipmap.s9);
//        s10.setImageResource(R.mipmap.s10);
//        s11.setImageResource(R.mipmap.s11);
//        s12.setImageResource(R.mipmap.s12);


        return view;

    }

    private void setaInformacoes() {

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String alunoJson = sharedPref.getString("ALUNO", "NULL");

        Gson gson = new Gson();
        Aluno2 aluno2 = gson.fromJson(alunoJson, Aluno2.class);

        nometx.setText(aluno2.getNome());
        infotx.setText(aluno2.getSerie()+ "/"+aluno2.getTurno()+ "\n");
    }

    private void procuraInsignea() {

        SharedPreferences sharedPref = view.getContext().getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String alunoJson = sharedPref.getString("ALUNO", "NULL");
        Log.i("LOG -- ", alunoJson);
        Gson gson = new Gson();
        final Aluno2 aluno = gson.fromJson(alunoJson, Aluno2.class);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.GET_INSIGNIAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG", "DATA :" + response);

                        listaDeInsignias = response;

                        Gson gson = new Gson();
                        if (response.equals("0")) {
                        //    Toast.makeText(view.getContext(), "Você ainda não possui Insignias!", Toast.LENGTH_LONG).show();

                        } else {
                            Log.i("LOG", "entrou no else q nao deveria:" + listaDeInsignias);

                            Insignia[] insigniaArray = gson.fromJson(listaDeInsignias, Insignia[].class);
                            Insignia insigia;

                            for (Insignia i : insigniaArray) {

                                insigia = new Insignia(i.getId(), i.getDescricao(), i.getCodigo(), i.getNome(), i.getQuantidade());


                                if (insigia.getCodigo().equals("s1")) {
                                    final String texto = insigia.getDescricao();
                                    s1.setImageResource(R.mipmap.s1);
                                    s1.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s1)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s2")) {

                                    final String texto = insigia.getDescricao();
                                    s2.setImageResource(R.mipmap.s2);
                                    s2.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s2)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s3")) {

                                    final String texto = insigia.getDescricao();
                                    s3.setImageResource(R.mipmap.s3);
                                    s3.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s3)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s4")) {

                                    final String texto = insigia.getDescricao();
                                    s4.setImageResource(R.mipmap.s4);
                                    s4.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s4)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s5")) {

                                    final String texto = insigia.getDescricao();
                                    s5.setImageResource(R.mipmap.s5);
                                    s5.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s5)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s6")) {

                                    final String texto = insigia.getDescricao();
                                    s6.setImageResource(R.mipmap.s6);
                                    s6.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s6)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s7")) {

                                    final String texto = insigia.getDescricao();
                                    s7.setImageResource(R.mipmap.s7);
                                    s7.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s7)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s8")) {

                                    final String texto = insigia.getDescricao();
                                    s8.setImageResource(R.mipmap.s8);
                                    s8.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s8)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s9")) {

                                    final String texto = insigia.getDescricao();
                                    s9.setImageResource(R.mipmap.s9);
                                    s9.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s9)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s10")) {

                                    final String texto = insigia.getDescricao();
                                    s10.setImageResource(R.mipmap.s10);
                                    s10.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s10)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s11")) {

                                    final String texto = insigia.getDescricao();
                                    s11.setImageResource(R.mipmap.s11);
                                    s11.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s11)
                                                    .show();


                                        }
                                    });

                                }
                                if (insigia.getCodigo().equals("s12")) {

                                    final String texto = insigia.getDescricao();
                                    s12.setImageResource(R.mipmap.s12);
                                    s12.setOnClickListener(new View.OnClickListener() {

                                        @Override
                                        public void onClick(View v) {

                                            new AlertDialog.Builder(view.getContext())
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.s12)
                                                    .show();


                                        }
                                    });

                                }

                            }


                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("LOG", "erro :" + error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idAluno", "" + aluno.getId());
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(stringRequest);
    }


}