package easii.com.br.iscoolapp.adapters;

/**
 * Created by gustavo on 03/05/2017.
 */
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.gson.Gson;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.Professor2;
import easii.com.br.iscoolapp.objetos.PushMessage;

public class MessagesListAdapter extends BaseAdapter {

    private Context context;
    private List<PushMessage> messagesItems;

    public MessagesListAdapter(Context context, List<PushMessage> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void addItem(final PushMessage item) {
        this.messagesItems.add(item);
        //Atualizar a lista caso seja adicionado algum item
        notifyDataSetChanged();
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /**
         * The following list not implemented reusable list items as list items
         * are showing incorrect data Add the solution if you have one
         * */

        PushMessage m = messagesItems.get(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);


        // Identifying the message owner


        String id = m.getIdDoUser();
        String idCell = acessaSharedPreferences();

        if (id.equals(idCell)) {
            // message belongs to you, so load the right aligned layout
            convertView = mInflater.inflate(R.layout.list_item_message_right,
                    null);
        } else {
            // message belongs to other person, load the left aligned layout
            convertView = mInflater.inflate(R.layout.list_item_message_left,
                    null);
        }

        TextView lblFrom = (TextView) convertView.findViewById(R.id.lblMsgFrom);
        TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);

        txtMsg.setText(m.getMensagem());
        lblFrom.setText(m.getNomeDoUser());

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
