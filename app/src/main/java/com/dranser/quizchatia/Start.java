package com.dranser.quizchatia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dranser.quizchatia.Common.Common;
import com.dranser.quizchatia.Modelo.Pregunta;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class Start extends AppCompatActivity {

    Button btnJugar;

    FirebaseDatabase database;
    DatabaseReference preguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        database = FirebaseDatabase.getInstance();
        preguntas = database.getReference("Preguntas");

        cargarPregunta(Common.categoriaID);

        btnJugar = (Button)findViewById(R.id.btnPlay);

        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start.this,Jugando.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void cargarPregunta(String categoriaID) {

        //Limpiar lista si tiene viejas preguntas
        if (Common.preguntaList.size() > 0)
            Common.preguntaList.clear();

        preguntas.orderByChild("CategoriaID").equalTo(categoriaID)
        .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Pregunta preg = postSnapshot.getValue(Pregunta.class);
                    Common.preguntaList.add(preg);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //Lista Random
        Collections.shuffle(Common.preguntaList);
    }
}
