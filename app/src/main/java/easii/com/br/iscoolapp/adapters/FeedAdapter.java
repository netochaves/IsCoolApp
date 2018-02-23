package easii.com.br.iscoolapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import easii.com.br.iscoolapp.R;
import easii.com.br.iscoolapp.interfaces.RecyclerViewOnClickListener;
import easii.com.br.iscoolapp.objetos.Feed;

/**
 * Created by gustavo on 27/07/2017.
 */

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder> {

    private List<Feed> lista;
    private View view;
    private RecyclerViewOnClickListener mRecyclerViewOnClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView titulotv, descricaotv, datatv;
        public ImageView imagetv;
        public MyViewHolder(View view) {
            super(view);

            titulotv = (TextView) view.findViewById(R.id.titulofeed);
            descricaotv = (TextView) view.findViewById(R.id.descricaofeed);
            datatv = (TextView) view.findViewById(R.id.datafeed);
            imagetv = (ImageView) view.findViewById(R.id.imageViewfeed);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListener != null){
                mRecyclerViewOnClickListener.onClickListener(v,getPosition());
            }
        }
    }

    public FeedAdapter(List<Feed> lista,View view) {
        this.lista = lista;
        this.view = view;
    }

    public void setRecyclerViewOnClickListener(RecyclerViewOnClickListener r){
        this.mRecyclerViewOnClickListener = r;
    }



    @Override
    public FeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new FeedAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Feed f = lista.get(position);

        holder.titulotv.setText(f.getTitulo());
        holder.descricaotv.setText(f.getDescricao());
        holder.datatv.setText(f.getAutor()+ " às "+f.getData());
        Picasso.with(view.getContext())
                .load(f.getImageUrl())
                .resize(300,450)
                .into(holder.imagetv);

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public void limpalista(){
        lista.clear();
    }


}
