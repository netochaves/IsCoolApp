package easii.com.br.iscoolapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Aluno2;

/**
 * Created by gustavo on 28/09/2016.
 */
public class Aluno2Adapter extends RecyclerView.Adapter<Aluno2Adapter.MyViewHolder> {

    private List<Aluno2> lista;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nome, serie;

        public MyViewHolder(View view) {
            super(view);

            nome = (TextView) view.findViewById(R.id.nome);
            serie = (TextView) view.findViewById(R.id.serie);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListener != null) {
                mRecyclerViewOnClickListener.onClickListener(v, getPosition());
            }
        }
    }

    public Aluno2Adapter(List<Aluno2> lista) {
        this.lista = lista;
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r) {
        this.mRecyclerViewOnClickListener = r;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int posicao) {

        Aluno2 a = lista.get(posicao);

        holder.nome.setText(a.getNome());
        holder.serie.setText(a.getSerie());

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

}