package easii.com.br.iscoolapp.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;


import cz.msebera.android.httpclient.Header;
import easii.com.br.iscoolapp.constantes.Constante;
import easii.com.br.iscoolapp.constantes.URL;

/**
 * Created by gustavo on 21/10/2016.
 */
public class ServiceUploadFoto extends Service {


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String idDoAluno = intent.getStringExtra("idDoAluno");
        String idProva = intent.getStringExtra("idProva");

        Toast.makeText(this, "Corrigindo... ", Toast.LENGTH_SHORT).show();
       // File file = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM))+ "fotodaultimacorrecao.png");
        File file = new File(Environment.getExternalStorageDirectory() + "/image.png");
     //   File file = new File(ServiceUploadFoto.this.getFilesDir(), "/imagecorrecao.png");
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("aluno_id", 10);
        params.put("prova_id", 10);
        try {
            params.put("gabarito", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        client.post(URL.SEND_URL_FOTO, params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        Log.i("LOG", "OK requisicao" + res);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {

                        Log.i("LOG", "Bad requiicao" + res);
                    }
                }
        );

       // stopSelf();
        onDestroy();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Fim da Correção!", Toast.LENGTH_SHORT).show();
    }

}
