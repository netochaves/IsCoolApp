package easii.com.br.iscoolapp;

/**
 * Created by gustavo on 27/10/2016.
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i("LOG", "Refreshed token: " + refreshedToken);
        SharedPreferences sharedPref = MyFirebaseInstanceIDService.this.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("TOKEN",refreshedToken);
        editor.commit();
      //
    }


}

