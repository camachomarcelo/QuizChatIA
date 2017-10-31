package com.dranser.quizchatia.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dranser.quizchatia.Interface.ItemClickListener;
import com.dranser.quizchatia.R;

/**
 * Created by Dranser on 30/10/2017.
 */

public class CategoriaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView categoria_nombre;
    public ImageView categoria_imagen;

    private ItemClickListener itemClickListener;

    public CategoriaViewHolder(View itemView) {
        super(itemView);
        categoria_imagen = (ImageView)itemView.findViewById(R.id.categoria_imagen);
        categoria_nombre = (TextView)itemView.findViewById(R.id.categoria_nombre);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);

    }
}
