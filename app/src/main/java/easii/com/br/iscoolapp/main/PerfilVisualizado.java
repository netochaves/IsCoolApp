package easii.com.br.iscoolapp.main;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.objetos.Insignia;

public class PerfilVisualizado extends AppCompatActivity {

    private Toolbar toolbar;
    private long id;
    private String nome;
    public CircleImageView s1, s2, s3, s4, s5, s6, s7, s8, s9, s10, s11, s12;
    private String listaDeInsignias = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_visualizado);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getLong("EXTRA_Id");
        nome = bundle.getString("EXTRA_Nome");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Insíginias de " +primeiroNomeDoAluno(nome));
        getSupportActionBar().setLogo(R.drawable.perfil);

        s1 = (CircleImageView) findViewById(R.id.s1);
        s2 = (CircleImageView) findViewById(R.id.s2);
        s3 = (CircleImageView) findViewById(R.id.s3);
        s4 = (CircleImageView) findViewById(R.id.s4);
        s5 = (CircleImageView) findViewById(R.id.s5);
        s6 = (CircleImageView) findViewById(R.id.s6);
        s7 = (CircleImageView) findViewById(R.id.s7);
        s8 = (CircleImageView) findViewById(R.id.s8);
        s9 = (CircleImageView) findViewById(R.id.s9);
        s10 = (CircleImageView) findViewById(R.id.s10);
        s11 = (CircleImageView) findViewById(R.id.s11);
        s12 = (CircleImageView) findViewById(R.id.s12);

        procuraInsignea();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        // or call onBackPressed()

        return true;
    }

    private String primeiroNomeDoAluno(String nomeCompleto) {


        int idx = nomeCompleto.indexOf(" ");

        if (idx == -1) {
            return nomeCompleto;
        } else {
            return (nomeCompleto.substring(0, nomeCompleto.indexOf(" ")));
        }

    }

    private void procuraInsignea() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.GET_INSIGNIAS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG", "DATA :" + response);

                        listaDeInsignias = response;

                        Gson gson = new Gson();
                        if (response.equals("0")) {
                            Toast.makeText(PerfilVisualizado.this, nome +" ainda não possui Insignias!", Toast.LENGTH_LONG).show();

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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage("João ganhou a insignia devido sua primeira nota 10")
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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

                                            new AlertDialog.Builder(PerfilVisualizado.this)
                                                    .setTitle("Insignia Conquistada")
                                                    .setMessage(texto)
                                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            // continue with delete

                                                        }
                                                    })

                                                    .setIcon(R.mipmap.trofeu2)
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
                params.put("idAluno", "" +id);
                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(PerfilVisualizado.this);
        requestQueue.add(stringRequest);
    }


}
