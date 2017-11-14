package com.dranser.quizchatia.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dranser.quizchatia.R;

/**
 * Created by Dranser on 14/11/2017.
 */

public class PuntuacionDetalleViewHolder extends RecyclerView.ViewHolder {

    public TextView txt_nombre, txt_puntuacion;

    public PuntuacionDetalleViewHolder(View itemView) {
        super(itemView);

        txt_nombre = (TextView)itemView.findViewById(R.id.txt_nombre);
        txt_puntuacion = (TextView)itemView.findViewById(R.id.txt_puntuacion);


    }
}
