package easii.com.br.iscoolapp.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;
import java.util.Random;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Professor2;
import easii.com.br.iscoolapp.objetos.PushMessage;
import easii.com.br.iscoolapp.util.MaterialColorPalette;

import static easii.com.br.iscoolapp.R.id.singleMessageContainer;

/**
 * Created by gustavo on 26/10/2016.
 */
public class MsgAdapter extends BaseAdapter {
    private List<PushMessage> lista;
    private LayoutInflater inflater;
    private Context context;
    private LinearLayout singleMessageContainer;

    public MsgAdapter(Context context, List<PushMessage> plista) {
        this.lista = plista;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void addItem(final PushMessage item) {
        this.lista.add(item);
        //Atualizar a lista caso seja adicionado algum item
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Random rand = new Random();
        PushMessage pm = lista.get(position);
        convertView = inflater.inflate(R.layout.item_msg, null);

        View row = convertView;
        if (row == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.item_msg, parent, false);
        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);


        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvMsg = (TextView) convertView.findViewById(R.id.tvMsg);


        String id = pm.getIdDoUser();
        String idCell = acessaSharedPreferences();

        MaterialColorPalette myCustomPalette = new MaterialColorPalette(Color.YELLOW);
        int my200Color = myCustomPalette.getColor("200");

        if(id.equals(idCell)){

            tvTitle.setText(pm.getNomeDoUser());
          //  tvTitle.setPadding(64, 16, 32, 0);
            tvMsg.setText(pm.getMensagem());
        //    tvMsg.setPadding(64, 16, 32, 16);
         //   convertView = inflater.inflate(R.layout.list_item_message_right, null);

        }else{

            tvTitle.setText(pm.getNomeDoUser());
       //     tvTitle.setPadding(32, 16, 64, 0);
            tvMsg.setText(pm.getMensagem());
       //     tvMsg.setPadding(32, 16, 64, 16);
            tvMsg.setBackgroundColor(my200Color);
            //tvMsg.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
      //      convertView = inflater.inflate(R.layout.list_item_message_left, null);
        }

        //   tvMsg.setBackgroundResource( R.drawable.bolha);
        singleMessageContainer.setGravity(id.equals(idCell) ? Gravity.RIGHT : Gravity.LEFT);


        // singleMessageContainer.setBackgroundResource(R.drawable.bolha);
        return convertView;
    }

    private String acessaSharedPreferences() {

        SharedPreferences sharedPref = context.getSharedPreferences("CONSTANTES", Context.MODE_PRIVATE);
        String tipo = sharedPref.getString("TIPO", "NULL");

        if (tipo.equals("ALUNO")) {

            String alunoJson = sharedPref.getString("ALUNO", "NULL");
            Gson gson = new Gson();
            Aluno2 aluno2 = gson.fromJson(alunoJson, Aluno2.class);
            return "" + aluno2.getId();

        } else {
            String profJson = sharedPref.getString("PROFESSOR", "NULL");
            Gson gson = new Gson();
            Professor2 professor2 = gson.fromJson(profJson, Professor2.class);
            return "" + professor2.getId();

        }
    }
}

