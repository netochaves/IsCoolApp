package easii.com.br.iscoolapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.net.PortUnreachableException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.main.MainActivity;
import easii.com.br.iscoolapp.main.MenuAluno;
import easii.com.br.iscoolapp.main.RankingProva;
import easii.com.br.iscoolapp.objetos.Prova;

/**
 * Created by gustavo on 14/09/2016.
 */
public class ProvaAdapter extends RecyclerView.Adapter<ProvaAdapter.MyViewHolder> {

    private List<Prova> lista;

    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;
    private Context context = null;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nome, data,nota;
      //  public ImageButton nota;

        public MyViewHolder(View view) {
            super(view);

            nome = (TextView) view.findViewById(R.id.nome);
            data = (TextView) view.findViewById(R.id.data);
            nota = (TextView) view.findViewById(R.id.nota);
          //  nota.setOnClickListener(this);
            context = view.getContext();
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
//            if (v.getId() == nota.getId()){
//                Prova p = lista.get(getAdapterPosition());
//                Toast.makeText(v.getContext(), "prova = " + p.getNome(), Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, RankingProva.class);
//                context.startActivity(intent);
//            }
            if (mRecyclerViewOnClickListener != null) {
                mRecyclerViewOnClickListener.onClickListener(v, getPosition());
            }
        }
    }

    public ProvaAdapter(List<Prova> lista) {
        this.lista = lista;


    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r) {
        this.mRecyclerViewOnClickListener = r;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prova, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int posicao) {

        Prova p = lista.get(posicao);

        Date date = new Date(p.getData());
        DateFormat df = DateFormat.getDateInstance(DateFormat.FULL);


        holder.nome.setText(p.getNome());
        holder.data.setText("" + df.format(date));
        holder.nota.setText("-");

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

}
