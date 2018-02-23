package easii.com.br.iscoolapp.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.objetos.Nota;

public class NotaDoAluno extends AppCompatActivity {

    private Toolbar toolbar;
    private Long idAluno = -1L, idProva = -1L;
    private TextView acertos, erros, porcentagem, emBranco;
    private Context context = NotaDoAluno.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_do_aluno);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idProva = bundle.getLong("EXTRA_ID_PROVA");
        idAluno = bundle.getLong("EXTRA_ID_USUARIO");

        acertos = (TextView) findViewById(R.id.acertosDaProva);
        erros = (TextView) findViewById(R.id.errosDaProva);
        porcentagem = (TextView) findViewById(R.id.porcetagemDaProva);
        emBranco = (TextView) findViewById(R.id.brancosDaProva);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Detalhes da Nota");
        getSupportActionBar().setLogo(R.drawable.iscool);

        completaNotas();

    }

    public void verExplicacao(View v) {

        Intent intent = new Intent(NotaDoAluno.this, TelaExplicacao.class);
        Bundle extras = new Bundle();
        extras.putLong("EXTRA_ID_USUARIO", idAluno);
        extras.putLong("EXTRA_ID_PROVA", idProva);
        intent.putExtras(extras);
        startActivity(intent);


    }

    private void completaNotas() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.URL_EXIBIR_NOTA_ALUNO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", response);

                        if (response.equals("0")) {
                            Toast.makeText(NotaDoAluno.this, "Prova não corrigida", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            Nota nota = gson.fromJson(response, Nota.class);


                            Log.i("LOG", " " + nota.getAcertos() + "  " + nota.getErros());

                            acertos.setText("" + nota.getAcertos());
                            erros.setText("" + nota.getErros());
                            porcentagem.setText("" + nota.getPorcentagemDeAcertos());
                            emBranco.setText("" + nota.getQuestoesSemResposta());

                            PieChart pieChart = (PieChart) findViewById(R.id.chart);

                            float ac = (float) nota.getAcertos();
                            float er = (float) nota.getErros();
                            float eb = (float) nota.getQuestoesSemResposta();
                            ArrayList<Entry> entries = new ArrayList<>();
                            entries.add(new Entry(ac, 0));
                            entries.add(new Entry(er, 1));
                            entries.add(new Entry(eb, 2));


                            PieDataSet dataset = new PieDataSet(entries, "#Legenda");

                            ArrayList<String> labels = new ArrayList<String>();
                            labels.add("Acertos");
                            labels.add("Erros");
                            labels.add("Em branco");


                            PieData data = new PieData(labels, dataset);
                            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
                            pieChart.setDescription("Estatísticas");
                            pieChart.setData(data);

                            pieChart.animateY(2000);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(NotaDoAluno.this, "Verifique sua conexão!", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idAluno", String.valueOf(idAluno));
                params.put("idProva", String.valueOf(idProva));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nota_do_aluno, menu);

        return true;
    }

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
        if (id == R.id.action_ranking) {
            Intent intent = new Intent(this, RankingProva.class);
            Bundle extras = new Bundle();
            extras.putLong("EXTRA_ID", idProva);
            extras.putBoolean("isNota", true);
            intent.putExtras(extras);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
