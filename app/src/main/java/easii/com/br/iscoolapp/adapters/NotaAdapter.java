package easii.com.br.iscoolapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Disciplina;
import easii.com.br.iscoolapp.objetos.Nota;

/**
 * Created by gustavo on 11/04/2017.
 */

public class NotaAdapter extends RecyclerView.Adapter<NotaAdapter.MyViewHolder> {

    private List<Nota> lista;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nome, nota;

        public MyViewHolder(View view) {
            super(view);

            nome = (TextView) view.findViewById(R.id.nome);
            nota = (TextView) view.findViewById(R.id.nota);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListener != null){
                mRecyclerViewOnClickListener.onClickListener(v,getPosition());
            }
        }
    }

    public NotaAdapter(List<Nota> lista) {
        this.lista = lista;
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r){
        this.mRecyclerViewOnClickListener = r;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota, parent, false);
        return new NotaAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotaAdapter.MyViewHolder holder, int posicao) {

        Nota n = lista.get(posicao);

        holder.nome.setText(n.getNomeDaProva());
        holder.nota.setText("" +(n.getPorcentagemDeAcertos()/10));

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void limpalista(){
        lista.clear();
    }


}