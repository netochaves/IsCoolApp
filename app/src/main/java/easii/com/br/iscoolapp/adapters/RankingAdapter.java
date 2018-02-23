package easii.com.br.iscoolapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Aluno2;
import easii.com.br.iscoolapp.objetos.AlunoRanking;



/**
 * Created by gustavo on 09/02/2017.
 */

public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.MyViewHolder> {

    private List<AlunoRanking> lista;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView nome, xp;
        public CircleImageView circleImageView;

        public MyViewHolder(View view) {
            super(view);
            circleImageView = (CircleImageView) view.findViewById(R.id.circle);
            nome = (TextView) view.findViewById(R.id.nome);
            xp = (TextView) view.findViewById(R.id.xp);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListener != null) {
                mRecyclerViewOnClickListener.onClickListener(v, getPosition());
            }
        }
    }

    public RankingAdapter(List<AlunoRanking> lista) {
        this.lista = lista;
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r) {
        this.mRecyclerViewOnClickListener = r;
    }


    @Override
    public RankingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ranking, parent, false);
        return new RankingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RankingAdapter.MyViewHolder holder, int posicao) {

        AlunoRanking a = lista.get(posicao);

        holder.nome.setText("" + a.getXp() + " XP");
        holder.xp.setText("" + a.getNome());

        if (a.getPosicao() == 1) {
            holder.circleImageView.setImageResource(R.mipmap.m1);
        } else if (a.getPosicao() == 2) {
            holder.circleImageView.setImageResource(R.mipmap.m2);
        } else if (a.getPosicao() == 3) {
            holder.circleImageView.setImageResource(R.mipmap.m3);
        } else if(a.getPosicao() <= 10){
            holder.circleImageView.setImageResource(R.mipmap.d);
        }else{
            holder.circleImageView.setImageResource(R.mipmap.c);
        }


    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}