package com.dranser.quizchatia;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dranser.quizchatia.Common.Common;
import com.dranser.quizchatia.Interface.ItemClickListener;
import com.dranser.quizchatia.Interface.RankingCallBack;
import com.dranser.quizchatia.Modelo.Pregunta;
import com.dranser.quizchatia.Modelo.PreguntaPuntuacion;
import com.dranser.quizchatia.Modelo.Ranking;
import com.dranser.quizchatia.ViewHolder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RankingFragment extends Fragment {
    View myFragment;

    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking,RankingViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference preguntaPuntuacion, rankingTbl;

    int sum = 0;

    public static RankingFragment newInstance(){
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();
        preguntaPuntuacion = database.getReference("Pregunta_Puntuacion");
        rankingTbl = database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_ranking,container,false);

        //Vista de inicio
        rankingList = (RecyclerView)myFragment.findViewById(R.id.rankingLista);
        layoutManager = new LinearLayoutManager(getActivity());
        rankingList.setHasFixedSize(true);

        //Ordenamos la lista ascendentemente
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankingList.setLayoutManager(layoutManager);

        actualizarPuntuacion(Common.usuarioActual.getNombreUsuario(), new RankingCallBack<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {
                //Actualizar a la tabla de ranking
                rankingTbl.child(ranking.getNombreUsuario())
                        .setValue(ranking);
                //showRanking(); //Ordenamos la tabla de ranking y mostramos el resultado
            }
        });

        //Set Adapter
        adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.layout_ranking,
                RankingViewHolder.class,
                rankingTbl.orderByChild("puntuacion")
        ) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, Ranking model, int position) {

                viewHolder.txt_nombre.setText(model.getNombreUsuario());
                viewHolder.txt_puntos.setText(String.valueOf(model.getPuntuacion()));

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int posicion, boolean isLongClick) {

                    }
                });

            }
        };

        adapter.notifyDataSetChanged();
        rankingList.setAdapter(adapter);

        return myFragment;
    }

    private void actualizarPuntuacion(final String nombreUsuario, final RankingCallBack<Ranking> callback) {
        preguntaPuntuacion.orderByChild("usuario").equalTo(nombreUsuario)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data:dataSnapshot.getChildren())
                        {
                            PreguntaPuntuacion preg = data.getValue(PreguntaPuntuacion.class);
                            sum+=Integer.parseInt(preg.getPuntuacion());
                        }
                        //Luego de sumar la puntuacion, sumamos variable aqui
                        //porque firebase es db asincrónico
                        Ranking ranking = new Ranking(nombreUsuario, sum);
                        callback.callBack(ranking);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
