package com.dranser.quizchatia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dranser.quizchatia.Modelo.Pregunta;
import com.dranser.quizchatia.Modelo.PreguntaPuntuacion;
import com.dranser.quizchatia.ViewHolder.PuntuacionDetalleViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PuntuacionDetalles extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference pregunta_puntuacion;

    RecyclerView puntuacionLista;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<PreguntaPuntuacion,PuntuacionDetalleViewHolder> adapter;


    String viewUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntuacion_detalles);

        //Firebase
        database = FirebaseDatabase.getInstance();
        pregunta_puntuacion = database.getReference("Pregunta_Puntuacion");

        //View
        puntuacionLista = (RecyclerView)findViewById(R.id.puntuacionLista);
        puntuacionLista.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        puntuacionLista.setLayoutManager(layoutManager);





            if (getIntent() != null)
            viewUser = getIntent().getStringExtra("viewUser");
            if (!viewUser.isEmpty())
                cargarPuntuacionDetalle(viewUser);

    }

    private void cargarPuntuacionDetalle(String viewUser) {
        adapter = new FirebaseRecyclerAdapter<PreguntaPuntuacion, PuntuacionDetalleViewHolder>(
                PreguntaPuntuacion.class,
                R.layout.puntuacion_detalle_layout,
                PuntuacionDetalleViewHolder.class,
                pregunta_puntuacion.orderByChild("usuario").equalTo(viewUser)
                ) {
            @Override
            protected void populateViewHolder(PuntuacionDetalleViewHolder viewHolder, PreguntaPuntuacion model, int position) {
                viewHolder.txt_nombre.setText(model.getCategoriaNombre());
                viewHolder.txt_puntuacion.setText(model.getPuntuacion());
            }
        };
        adapter.notifyDataSetChanged();
        puntuacionLista.setAdapter(adapter);

    }
}
