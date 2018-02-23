package easii.com.br.iscoolapp.adapters;

import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Disciplina;

/**
 * Created by gustavo on 09/09/2016.
 */
public class DisciplinaAdapter extends RecyclerView.Adapter<DisciplinaAdapter.MyViewHolder> {

    private List<Disciplina> lista;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nome, professor;

        public MyViewHolder(View view) {
            super(view);

            nome = (TextView) view.findViewById(R.id.nome);
            professor = (TextView) view.findViewById(R.id.professor);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListener != null){
                mRecyclerViewOnClickListener.onClickListener(v,getPosition());
            }
        }
    }

    public DisciplinaAdapter(List<Disciplina> lista) {
        this.lista = lista;
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r){
     this.mRecyclerViewOnClickListener = r;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disciplina, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int posicao) {

        Disciplina d = lista.get(posicao);

        holder.nome.setText(d.getNome());
        holder.professor.setText("Pedro de Alcântara ");

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void limpalista(){
        lista.clear();
    }


}
