package com.dranser.quizchatia.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dranser.quizchatia.Interface.ItemClickListener;
import com.dranser.quizchatia.R;

/**
 * Created by Dranser on 11/11/2017.
 */

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView txt_nombre,txt_puntuacion;

    private ItemClickListener itemClickListener;

    public RankingViewHolder(View itemView) {
        super(itemView);
        txt_nombre = (TextView)itemView.findViewById(R.id.txt_nombre);
        txt_puntuacion = (TextView)itemView.findViewById(R.id.txt_puntuacion);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
