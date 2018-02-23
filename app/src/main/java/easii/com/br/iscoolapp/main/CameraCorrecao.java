package easii.com.br.iscoolapp.main;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.constantes.Constante;
import easii.com.br.iscoolapp.constantes.URL;
import easii.com.br.iscoolapp.services.ServiceUploadFoto;


public class CameraCorrecao extends AppCompatActivity {

    //VIEW
   // private Button btCrop;
    //EXTRAS
    private String idProva = null;
    private Long idDoAluno = -1L;
 //   private CropImageView mCropImageView;
    private String listaDeAlunos = null;
    private Long idDisciplina = null;
    private int posicao = -1;
    private ProgressBar progressBar;

    private Toolbar toolbar;
 //   private ScrollView sv;

 //   private RadioGroup radioGroup1;
 //   private RadioGroup radioGroup2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_correcao);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setTitle(" Aguarde Correção");
        getSupportActionBar().setLogo(R.drawable.provabranca);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        idDoAluno = bundle.getLong("EXTRA_ID_USUARIO");
        idProva = bundle.getString("EXTRA_ID_PROVAS");
        idDisciplina = bundle.getLong("EXTRA_ID_DISCIPLINA");
        listaDeAlunos = bundle.getString("EXTRA_LISTA_DE_ALUNOS");
        posicao = bundle.getInt("EXTRA_POSICAO");

        Log.i("LOG", idDoAluno + " " + idProva);
 //       sv = (ScrollView) findViewById(R.id.sv);
 //       sv.setVisibility(View.INVISIBLE);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

     //   radioGroup1 = (RadioGroup) findViewById(R.id.radioQ1);
    //    radioGroup2 = (RadioGroup) findViewById(R.id.radioQ2);

        Log.i("LOG", "id dis " + idDisciplina);
        corrige();
    }

    private void corrige() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File file = new File(Environment.getExternalStorageDirectory() + "/image.png");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, 0);
    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d("LOG", "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
    }

    private File getOutputMediaFile() {

        File mediaFile;

        //  mediaFile = new File(String.valueOf(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM))+ "fotodaultimacorrecao.png");
        mediaFile = new File(Environment.getExternalStorageDirectory() + "/image.png");
        // mediaFile = new File(CameraCorrecao.this.getFilesDir(), "/imagecorrecao.png");
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_CANCELED) {
            finish();
        }
        switch (requestCode) {

            case 0:

                Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.png");

                storeImage(getResizedBitmap(bitmap, (bitmap.getWidth()) / 3, (bitmap.getHeight()) / 3));
                enviafoto();


            case 1:
                break;
        }


    }

    private void enviafoto() {

        Toast.makeText(this, "Corrigindo... ", Toast.LENGTH_SHORT).show();

        File file = new File(Environment.getExternalStorageDirectory() + "/image.png");
        AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("aluno_id", idDoAluno);
        params.put("prova_id", idProva);
        try {
            params.put("gabarito", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        client.post(URL.SEND_URL_FOTO, params, new TextHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String res) {
                        Log.i("LOG", "OK requisicao" + res);
                        progressBar.setVisibility(View.INVISIBLE);
                    //    sv.setVisibility(View.VISIBLE);
                        Toast.makeText(CameraCorrecao.this,"Corrigida "+ res,Toast.LENGTH_LONG).show();

                   //     radioGroup1.check(R.id.a1);


                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, String res, Throwable t) {
                        Toast.makeText(CameraCorrecao.this,"Houve algum problema com a conexão!",Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.INVISIBLE);
                    //    sv.setVisibility(View.VISIBLE);
                        Log.i("LOG", "Bad requiicao" + res);
                     //   radioGroup1.check(R.id.b1);
                   //     radioGroup2.check(R.id.b2);
                        finish();
                    }
                }
        );

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_camera_correcao, menu);
//        return true;
//    }

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
