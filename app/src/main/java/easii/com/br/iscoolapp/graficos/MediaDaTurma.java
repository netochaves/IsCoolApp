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
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.objetos.Media;


public class MediaDaTurma extends AppCompatActivity {

    private Long idDisciplina = null;
    private Media[] md = null;
    private LineChart lineChart = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_da_turma);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idDisciplina = bundle.getLong("EXTRA_ID_DISCIPLINA");
        lineChart = (LineChart) findViewById(R.id.chart);
        buscaMedia();
       // buscaMedia2();
       // buscaMedia3();
    }

    private void buscaMedia3() {
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4, 0));
        entries.add(new Entry(8, 1));
        entries.add(new Entry(6, 2));
        entries.add(new Entry(2, 3));
        entries.add(new Entry(180, 4));
        entries.add(new Entry(9, 5));

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        lineChart.setData(data);
        lineChart.animateY(5000);

    }

    private void buscaMedia2() {
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
        lineChart.animateY(1000);

    }

    private void buscaMedia() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.SEND_MEDIA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.i("LOG", " -" + response);

                        if (response.equals("0")) {
                            Toast.makeText(MediaDaTurma.this, "Não disponível", Toast.LENGTH_SHORT).show();
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
                    //    Log.i("LOG", " erro -" + error.getMessage().toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idDaDisciplina", String.valueOf(idDisciplina));

                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_media_da_turma, menu);
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
