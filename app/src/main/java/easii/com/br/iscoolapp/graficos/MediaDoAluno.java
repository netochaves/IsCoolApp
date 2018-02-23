package easii.com.br.iscoolapp.graficos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.objetos.Media;

public class MediaDoAluno extends AppCompatActivity {

    private Long idDisciplina = null;
    private Long idAluno = null;
    private LineChart lineChart;
    private Media[] md = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_do_aluno);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idDisciplina = bundle.getLong("EXTRA_ID_DISCIPLINA");
        idAluno = bundle.getLong("EXTRA_ID_ALUNO");

        lineChart = (LineChart) findViewById(R.id.chart);
        buscaMedia();
      //  buscaMedia2();
    }

    private void buscaMedia2() {
        Toast.makeText(MediaDoAluno.this, "aluno", Toast.LENGTH_SHORT).show();
        String response = "[\n" +
                "{\n" +
                "nome: \"Primeira Avaliação Mensal\",\n" +
                "media: 3.5\n" +
                "},\n" +
                "{\n" +
                "nome: \"Poo1\",\n" +
                "media: 7.5\n" +
                "},\n" +
                "{\n" +
                "nome: \"Terceira Avaliação\",\n" +
                "media: 10\n" +
                "}\n" +
                "]";
        Gson gson = new Gson();
        md = gson.fromJson(response, Media[].class);
        int i =0;

        ArrayList<Entry> entries = new ArrayList<>();
        for (Media m : md) {
            entries.add(new Entry(m.getMedia(), i));
            i++;
            Log.i("LOG", "i ="+i+" " );
        }
        LineDataSet dataset = new LineDataSet(entries, "Desempenho");
        ArrayList<String> labels = new ArrayList<String>();

        for (Media m : md) {
            labels.add(m.getNome());
        }


        LineData data = new LineData(labels, dataset);
        //  dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setDrawCubic(false);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(2000);
    }


    private void buscaMedia() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.SEND_MEDIA_ALUNO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", " -" + response);

                        if (response.equals("0")) {
                            Toast.makeText(MediaDoAluno.this, "Não disponível", Toast.LENGTH_SHORT).show();
                        } else {
                            Gson gson = new Gson();
                            md = gson.fromJson(response, Media[].class);
                            int i =0;

                            ArrayList<Entry> entries = new ArrayList<>();
                            for (Media m : md) {
                                entries.add(new Entry(m.getMedia(), i));
                                i++;
                                Log.i("LOG", "i ="+i+" " );
                            }
                            LineDataSet dataset = new LineDataSet(entries, "Desempenho");
                            ArrayList<String> labels = new ArrayList<String>();

                            for (Media m : md) {
                                labels.add(m.getNome());
                            }


                            LineData data = new LineData(labels, dataset);
                            //  dataset.setColors(ColorTemplate.COLORFUL_COLORS);
                            dataset.setDrawCubic(false);
                            dataset.setDrawFilled(true);

                            lineChart.setData(data);
                            lineChart.animateY(2000);

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MediaDoAluno.this, "ERRO ---Não disponível", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idDaDisciplina", String.valueOf(idDisciplina));
                params.put("idDoAluno", String.valueOf(idAluno));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_media_do_aluno, menu);
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

        return super.onOptionsItemSelected(item);
    }
}
