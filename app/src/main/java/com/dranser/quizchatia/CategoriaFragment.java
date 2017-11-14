package com.dranser.quizchatia;

import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dranser.quizchatia.Common.Common;
import com.dranser.quizchatia.Interface.ItemClickListener;
import com.dranser.quizchatia.Modelo.Categoria;
import com.dranser.quizchatia.ViewHolder.CategoriaViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class CategoriaFragment extends Fragment {

    View myFragment;

    RecyclerView listaCategoria;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Categoria, CategoriaViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference categorias;



    public static CategoriaFragment newInstance(){
        CategoriaFragment categoriaFragment = new CategoriaFragment();
        return categoriaFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        categorias = database.getReference("Categoria");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_categoria,container,false);

        listaCategoria = (RecyclerView)myFragment.findViewById(R.id.listCategoria);
        listaCategoria.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listaCategoria.setLayoutManager(layoutManager);

        cargarCategorias();
        
        return myFragment;
    }

    private void cargarCategorias() {
        adapter = new FirebaseRecyclerAdapter<Categoria, CategoriaViewHolder>(
                Categoria.class,
                R.layout.categoria_layout,
                CategoriaViewHolder.class,
                categorias
        ) {
            @Override
            protected void populateViewHolder(CategoriaViewHolder viewHolder, final Categoria model, final int position) {
                viewHolder.categoria_nombre.setText(model.getNombre());
                Picasso.with(getActivity())
                        .load(model.getImagen())
                        .into(viewHolder.categoria_imagen);

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int posicion, boolean isLongClick) {
                        //Toast.makeText(getActivity(), String.format("%s|%s", adapter.getRef(posicion).getKey(), model.getNombre()), Toast.LENGTH_SHORT).show();
                        Intent startGame = new Intent(getActivity(),Start.class);
                        Common.categoriaID = adapter.getRef(position).getKey();
                        Common.categoriaNombre = model.getNombre();
                        startActivity(startGame);

                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        listaCategoria.setAdapter(adapter);
    }
}
