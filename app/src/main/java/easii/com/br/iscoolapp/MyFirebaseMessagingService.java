package easii.com.br.iscoolapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import de.greenrobot.event.EventBus;
import easii.com.br.iscoolapp.main.MainActivity;
import easii.com.br.iscoolapp.main.TelaMensagens;
import easii.com.br.iscoolapp.objetos.DatabaseHelper;
import easii.com.br.iscoolapp.objetos.PushMessage;
import easii.com.br.iscoolapp.util.Util;

/**
 * Created by gustavo on 27/10/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    String TAG = "log";
    private EventBus bus = EventBus.getDefault();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){

        SharedPreferences sharedPreferences = this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String logado = sharedPreferences.getString("LOGADO", "NULL");

        Log.i(TAG,"from :" + remoteMessage.getFrom()+ " data " + remoteMessage.getData());
        Log.i(TAG,"from :" + remoteMessage.getFrom()+ " CHAVE -" + remoteMessage.getData().get("chave"));

        if(remoteMessage.getData().get("chave").equals("1")){

            String titulo = ""+remoteMessage.getData().get("titulo");
            String mensagem = ""+remoteMessage.getData().get("mensagem");
            String codigo   = ""+remoteMessage.getData().get("codigo");

            Log.i("LOG","LOG TESTE PIKA DAS GALAXIAS " + titulo + " "  + mensagem + " "+ codigo) ;

            setNotoficationNota(remoteMessage);
        }else{

            String nomeDoUser = ""+remoteMessage.getData().get("nomeDoUser");
            String mensagem = ""+remoteMessage.getData().get("mensagem");
            String hora   = ""+remoteMessage.getData().get("hora");
            String idDisciplina = ""+remoteMessage.getData().get("idDisciplina");
            String idDoUser = ""+remoteMessage.getData().get("idDoUser");

            DatabaseHelper helper = new DatabaseHelper(this);
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("iddouser", idDoUser);
            values.put("nomedouser", nomeDoUser);
            values.put("iddisciplina", idDisciplina);
            values.put("mensagem", mensagem);
            values.put("hora", hora);

            long resultado = db.insert("mensagens", null, values);
            if (resultado != -1) {
                Log.i(TAG, "SALVO");
            } else {
                Log.i(TAG, "NAAAAAO SALVO");
            }

            //bus.post(new PushMessage(idDoUser,nomeDoUser,idDisciplina,mensagem,hora));

            if ((!Util.isMyApplicationTaskOnTop(this)) && (logado.equals("SIM"))) {
                setNotoficationMsg(remoteMessage);
            } else {
                bus.post(new PushMessage(idDoUser,nomeDoUser,idDisciplina,mensagem,hora));
            }

        }

    }

    public int getIcon(String cod){

        int image = R.drawable.trofeu2;

        if(cod.equals("m1")){
            image = R.drawable.m1;
        }
        if(cod.equals("m2")){
            image = R.drawable.m2;
        }
        if(cod.equals("m3")){
            image = R.drawable.m3;
        }
        if(cod.equals("c")){
            image = R.drawable.c;
        }
        if(cod.equals("d")){
            image = R.drawable.d;
        }

        return  image;
    }




    public void setNotoficationMsg(RemoteMessage remoteMessage) {

        int id = 6666;

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setTicker(remoteMessage.getData().get("mensagem"))
                .setSmallIcon(R.drawable.iscoolazul)
                .setContentTitle(remoteMessage.getData().get("nomeDoUser"))
                .setContentText(remoteMessage.getData().get("mensagem"))
                .setAutoCancel(true);
        Intent intent = new Intent(this, TelaMensagens.class);
        Bundle extras = new Bundle();
        extras.putLong("EXTRA_ID_DISCIPLINA",Long.parseLong(remoteMessage.getData().get("idDisciplina")) );
        extras.putString("EXTRA_NOME_DISCIPLINA", remoteMessage.getData().get("nomeDisciplina"));
        intent.putExtras(extras);
        PendingIntent pi  = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(id,builder.build());
    }

    public void setNotoficationNota(RemoteMessage remoteMessage) {

        int id = 6666;

        String cod = remoteMessage.getData().get("codigo");
        int image = getIcon(cod);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setTicker(remoteMessage.getData().get("mensagem"))
                .setSmallIcon(image)
                .setContentTitle(remoteMessage.getData().get("titulo"))
                .setContentText(remoteMessage.getData().get("mensagem"))
                .setAutoCancel(true);
        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pi  = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(uri);

        NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(id,builder.build());
    }
}
