package easii.com.br.iscoolapp.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

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

import de.greenrobot.event.EventBus;
import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.adapters.MessagesListAdapter;
import easii.com.br.iscoolapp.adapters.MsgAdapter;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.DatabaseHelper;
import easii.com.br.iscoolapp.objetos.Professor2;
import easii.com.br.iscoolapp.objetos.PushMessage;

public class TelaMensagens extends AppCompatActivity implements View.OnClickListener {

    private Long idDisciplina = null;

    private EditText etMessage;
    private ImageButton btSendMessage;
    private ListView list;
   // MsgAdapter adapter;
    MessagesListAdapter adapter;
    private List<PushMessage> listademensagens = new ArrayList<PushMessage>();
    private Toolbar toolbar;

    //private EventBus bus = EventBus.getDefault();
    private EventBus bus;
    private Aluno2 aluno2;
    private Professor2 professor2;
    private String idUser = "";
    private String nomeUser = "";
    private String nomeDisciplina;

    private DatabaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msg);

        helper = new DatabaseHelper(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idDisciplina = bundle.getLong("EXTRA_ID_DISCIPLINA");
        nomeDisciplina= bundle.getString("EXTRA_NOME_DISCIPLINA");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setTitle(" "+nomeDisciplina);
     //   getSupportActionBar().setTitle(" Matemática");
        getSupportActionBar().setLogo(R.drawable.textmsg);

        etMessage = (EditText) findViewById(R.id.et_message);
        btSendMessage = (ImageButton) findViewById(R.id.bt_send_message);
        btSendMessage.setOnClickListener(this);
        list = (ListView) findViewById(R.id.rv_list);

//        SharedPreferences sharedPref = this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
//        String alunoJson = sharedPref.getString("ALUNO", "NULL");

//        Gson gson = new Gson();
//        final Aluno2 aluno2 = gson.fromJson(alunoJson, Aluno2.class);
//
//
       listademensagens = new ArrayList<PushMessage>();
    //   listademensagens.add(new PushMessage("Pedro às 18:35", "Boa noite, vocês gostaram da aula de hoje? Ficou alguma dúvida referente ao assunto de trigonometria?", "0"));
//        listademensagens.add(new PushMessage("Alexandre Cristian às 18:37 ", "Ótima aula professor :),mas fiquei com algumas dúvidas principalmente sobre a 'Lei do Senos'", "0"));
//        listademensagens.add(new PushMessage("Gustavo Oliveira às 18:37 ", "Oi Alexandre!,É importante ressaltar que para aplicar a 'Lei dos Senos' precisamos do valor dos ângulos e o valor apenas de um lado.", ""+aluno2.getId()));
//        listademensagens.add(new PushMessage("Alexandre Cristian às 18:38 ", "Obrigado!", "0"));
       // adapter = new MsgAdapter(this, listademensagens);
        adapter = new MessagesListAdapter(this, listademensagens);

        list.setAdapter(adapter);
        list.setDivider(null);
        list.setDividerHeight(0);
        acessaSharedPreferences();
        bus = EventBus.getDefault();
        bus.register(this);

        carregaMsg();
    }

    public void carregaMsg() {

        String idUser;
        String nomeUSer;
        String idDis;
        String mensagem;
        String hora;


        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT iddouser,nomedouser,iddisciplina,mensagem,hora FROM mensagens WHERE iddisciplina =" + idDisciplina + ";", null);
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++) {

            idUser = cursor.getString(0);
            nomeUSer = cursor.getString(1);
            idDis = cursor.getString(2);
            mensagem = cursor.getString(3);
            hora = cursor.getString(4);


            Date date = new Date(Long.parseLong(hora));
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

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

            adapter.addItem(new PushMessage(nomeUSer + " às " + horaST + ":" + minutoST + " de " + df.format(date) + ":", mensagem, idUser));
            cursor.moveToNext();
        }
        cursor.close();

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tela_mensagens, menu);
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


    // LISTENER
    public void onEvent(final PushMessage pushMessage) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //    Log.i("LOG","on event " +pushMessage.getIdDoUser() + " "+pushMessage.toString() );

                Date date = new Date(Long.parseLong(pushMessage.getHora()));
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

                Log.i("LOG", "pm = " + pushMessage.getIdDisciplina() + " id tela " + idDisciplina + "id user - " + pushMessage.getIdDoUser());
                if (pushMessage.getIdDisciplina().equals("" + idDisciplina)) {
                    adapter.addItem(new PushMessage(pushMessage.getNomeDoUser() + " às " + horaST + ":" + minutoST + " de " + df.format(date) + ":", pushMessage.getMensagem(), pushMessage.getIdDoUser()));

                }


                // Log.i(LOG," "+ pushMessage.getMessage() + " "+ pushMessage.getTitle());

            }
        });
    }

    @Override
    public void onClick(View v) {
        etMessage.setEnabled(false);
        btSendMessage.setEnabled(false);
        Log.i("LOG", "aki");
        sendPush();
    }

    private void sendPush() {
        if (!etMessage.getText().toString().isEmpty() && !etMessage.getText().equals("")) {
            String msgVolley = etMessage.getText().toString();
            etMessage.setText("");
            enviaServidor(msgVolley);

        } else {
            etMessage.setEnabled(true);
        }
    }

    public void enviaServidor(final String msg) {

        final String idDis = "" + idDisciplina;
        final String idUserVolley = idUser;
        final String nomeUSerVolley = nomeUser;
        final String horaDaMsgVolley = horaMsg();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL.SEND_PUSH_MSG,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("LOG", "DATA :" + response);
                        etMessage.setEnabled(true);
                        btSendMessage.setEnabled(true);
                        etMessage.setText("");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        etMessage.setEnabled(true);
                        btSendMessage.setEnabled(true);
                        // etMessage.setText("");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("idUser", idUserVolley);
                params.put("mensagem", msg);
                params.put("idDisciplina", idDis);
                params.put("nomeUser", nomeUSerVolley);
                params.put("horaMsg", horaDaMsgVolley);

                return params;
            }


        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private String horaMsg() {
        Log.i("LOG", "hora" + System.currentTimeMillis());
        return "" + System.currentTimeMillis();
    }

    private void acessaSharedPreferences() {

        SharedPreferences sharedPref = TelaMensagens.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String tipo = sharedPref.getString("TIPO", "NULL");

        if (tipo.equals("ALUNO")) {

            String alunoJson = sharedPref.getString("ALUNO", "NULL");
            Gson gson = new Gson();
            aluno2 = gson.fromJson(alunoJson, Aluno2.class);
            idUser = "" + aluno2.getId();
            nomeUser = aluno2.getNome();
        } else {
            String profJson = sharedPref.getString("PROFESSOR", "NULL");
            Gson gson = new Gson();
            professor2 = gson.fromJson(profJson, Professor2.class);
            idUser = "" + professor2.getId();
            nomeUser = professor2.getNome();
        }
    }


}
