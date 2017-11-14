package com.dranser.quizchatia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dranser.quizchatia.Common.Common;
import com.dranser.quizchatia.Modelo.Pregunta;
import com.dranser.quizchatia.Modelo.PreguntaPuntuacion;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Hecho extends AppCompatActivity {

    Button btnIntentarNuevamente;
    TextView txtResultadoPuntuacion, getTxtResultadoPregunta;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference pregunta_puntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hecho);

        database = FirebaseDatabase.getInstance();
        pregunta_puntuacion = database.getReference("Pregunta_Puntuacion");

        txtResultadoPuntuacion = (TextView)findViewById(R.id.txtTotalPuntuacion);
        getTxtResultadoPregunta = (TextView)findViewById(R.id.txtTotalPregunta);
        progressBar = (ProgressBar)findViewById(R.id.doneProgressBar);
        btnIntentarNuevamente = (Button)findViewById(R.id.btnIntentarNuevamente);

        btnIntentarNuevamente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hecho.this,Inicio.class);
                startActivity(intent);
                finish();
            }
        });

        //Obtener datos del bundle y oonfigurarlos para ver
        Bundle extra = getIntent().getExtras();
        if(extra != null)
        {
            int puntuacion = extra.getInt("PUNTUACION");
            int totalPregunta = extra.getInt("TOTAL");
            int respuestaCorrecta = extra.getInt("CORRECTAS");

            txtResultadoPuntuacion.setText(String.format("PUNTUACION : %d",puntuacion));
            getTxtResultadoPregunta.setText(String.format("SUPERADO : %d  / %d",respuestaCorrecta,totalPregunta));

            progressBar.setMax(totalPregunta);
            progressBar.setProgress(respuestaCorrecta);

            //Subir puntos a la BD
            pregunta_puntuacion.child(String.format("%s_%s", Common.usuarioActual.getNombreUsuario(),Common.categoriaID))
                    .setValue(new PreguntaPuntuacion(String.format("%s_%s", Common.usuarioActual.getNombreUsuario(),Common.categoriaID),
                            Common.usuarioActual.getNombreUsuario(),
                            String.valueOf(puntuacion),
                            Common.categoriaID,
                            Common.categoriaNombre));
        }
    }
}
