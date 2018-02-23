package easii.com.br.iscoolapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.objetos.Desafio;

/**
 * Created by gustavo on 01/05/2017.
 */

public class DesafioAdapter extends RecyclerView.Adapter<DesafioAdapter.MyViewHolder> {

    private List<Desafio> list;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nome,participantes;

        public MyViewHolder(View view) {
            super(view);
            nome = (TextView) view.findViewById(R.id.nome);
            participantes = (TextView) view.findViewById(R.id.participantes);

        }


    }


    public DesafioAdapter(List<Desafio> list) {
        this.list = list;
    }

    public void addItem(final Desafio item) {
        this.list.add(item);
        //Atualizar a lista caso seja adicionado algum item
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        this.list.remove(pos);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_desafio, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Desafio d = list.get(position);
        holder.nome.setText(d.getNome());
        holder.participantes.setText(d.getParticipantes());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}