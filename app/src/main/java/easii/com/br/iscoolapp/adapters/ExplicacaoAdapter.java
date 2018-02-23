package easii.com.br.iscoolapp.adapters;

/**
 * Created by gustavo on 27/07/2017.
 */

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Disciplina;
import easii.com.br.iscoolapp.objetos.Explicacao;

/**
 * Created by gustavo on 09/09/2016.
 */
public class ExplicacaoAdapter extends RecyclerView.Adapter<ExplicacaoAdapter.MyViewHolder> {

    private List<Explicacao> lista;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView descricaotv, explicacaotv, primeiraAl,segundaAl,terceiraAl,quartaAl,quintaAl,resposta,respAluno;

        public MyViewHolder(View view) {
            super(view);

            descricaotv = (TextView) view.findViewById(R.id.descricao);
            explicacaotv = (TextView) view.findViewById(R.id.explicacao);

            primeiraAl = (TextView) view.findViewById(R.id.primeiraAlternativa);
            segundaAl = (TextView) view.findViewById(R.id.segundaAlternativa);
            terceiraAl = (TextView) view.findViewById(R.id.terceiraAlternativa);
            quartaAl = (TextView) view.findViewById(R.id.quartaAlternativa);
            quintaAl = (TextView) view.findViewById(R.id.quintaAlternativa);

            resposta = (TextView) view.findViewById(R.id.respostaCorreta);
            respAluno = (TextView) view.findViewById(R.id.respostaAluno);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListener != null){
                mRecyclerViewOnClickListener.onClickListener(v,getPosition());
            }
        }
    }

    public ExplicacaoAdapter(List<Explicacao> lista) {
        this.lista = lista;
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r){
        this.mRecyclerViewOnClickListener = r;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_explicacao, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int posicao) {

        Explicacao e = lista.get(posicao);

        holder.descricaotv.setText(e.getDescricao());
        holder.explicacaotv.setText(e.getExplicacao());
        holder.primeiraAl.setText(e.getPrimeiraAlternativa());
        holder.segundaAl.setText(e.getSegundaAlternativa());
        holder.terceiraAl.setText(e.getTerceiraAlternativa());
        holder.quartaAl.setText(e.getQuartaAlternativa());
        holder.quintaAl.setText(e.getQuintaAlternativa());
        holder.resposta.setText(e.getRespostaCorreta());

        if (e.isAcertou()){
            holder.respAluno.setTextColor(Color.parseColor("#2E7D32"));
            holder.respAluno.setText(e.getRespostaAluno());
        }else{
            holder.respAluno.setTextColor(Color.parseColor("#e53935"));
            holder.respAluno.setText(e.getRespostaAluno());
        }

      //  holder.itemView.setBackgroundColor(Color.GRAY);

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void limpalista(){
        lista.clear();
    }


}
