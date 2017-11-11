package com.dranser.quizchatia;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dranser.quizchatia.Common.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Jugando extends AppCompatActivity implements View.OnClickListener{

    final static long INTERVAL = 1000; // 1 seg
    final static long TIMEOUT = 7000; // 7 seg
    int progressValue = 0;

    CountDownTimer mCountDown;

    int index = 0, puntuacion = 0, thisPregunta = 0, totalPregunta, respuestaCorrecta;

    

    ProgressBar progressBar;
    ImageView pregunta_imagen;
    Button btnA, btnB, btnC, btnD;
    TextView txtPuntuacion, txtPreguntaNum, pregunta_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jugando);


        //Views

        txtPuntuacion = (TextView)findViewById(R.id.txtScore);
        txtPreguntaNum = (TextView)findViewById(R.id.txtTotalQuestion);
        pregunta_text = (TextView)findViewById(R.id.pregunta_texto);

        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        btnA = (Button)findViewById(R.id.btn_RespuestaA);
        btnB = (Button)findViewById(R.id.btn_RespuestaB);
        btnC = (Button)findViewById(R.id.btn_RespuestaC);
        btnD = (Button)findViewById(R.id.btn_RespuestaD);

        btnA.setOnClickListener(this);
        btnB.setOnClickListener(this);
        btnC.setOnClickListener(this);
        btnD.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        mCountDown.cancel();
        if(index < totalPregunta) //todavia hay preguntas en la lista
        {
            Button clickedButton = (Button)view;
            if (clickedButton.getText().equals(Common.preguntaList.get(index).getRespuestaCorrecta()))
            {
                // Elegir respuesta correcta
                puntuacion+=10;
                respuestaCorrecta++;
                mostrarPregunta(index++); //Siguiente pregunta
            }
            else
            {
                // Elegir respuesta incorrecta
                Intent intent = new Intent(this, Hecho.class);
                Bundle dataSend = new Bundle();
                dataSend.putInt("PUNTUACION",puntuacion);
                dataSend.putInt("TOTAL",totalPregunta);
                dataSend.putInt("CORRECTA",respuestaCorrecta);
                intent.putExtras(dataSend);
                startActivity(intent);
                finish();
            }

            txtPuntuacion.setText(String.format("%d",puntuacion));

        }

    }

    private void mostrarPregunta(int index) {
        if (index < totalPregunta)
        {
            thisPregunta++;
            txtPreguntaNum.setText(String.format("%d / %d", thisPregunta, totalPregunta));
            progressBar.setProgress(0);
            progressValue=0;

            if(Common.preguntaList.get(index).getIsImageQuestion().equals("true"))
            {
                //Si es imagen
                Picasso.with(getBaseContext())
                        .load(Common.preguntaList.get(index).getPregunta())
                        .into(pregunta_imagen);
                pregunta_imagen.setVisibility(View.VISIBLE);
                pregunta_text.setVisibility(View.INVISIBLE);
            }
            else
            {
                pregunta_text.setText(Common.preguntaList.get(index).getPregunta());

                pregunta_imagen.setVisibility(View.VISIBLE);
                pregunta_text.setVisibility(View.INVISIBLE);
            }

            btnA.setText(Common.preguntaList.get(index).getRespuestaA());
            btnB.setText(Common.preguntaList.get(index).getRespuestaB());
            btnC.setText(Common.preguntaList.get(index).getRespuestaC());
            btnD.setText(Common.preguntaList.get(index).getRespuestaD());

            mCountDown.start(); //Iniciar temporizador
        }
        else
        {
            //Si es la pregunta final

            Intent intent = new Intent(this, Hecho.class);
            Bundle dataSend = new Bundle();
            dataSend.putInt("PUNTUACION",puntuacion);
            dataSend.putInt("TOTAL",totalPregunta);
            dataSend.putInt("CORRECTA",respuestaCorrecta);
            intent.putExtras(dataSend);
            startActivity(intent);
            finish();

        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        totalPregunta = Common.preguntaList.size();

        mCountDown = new CountDownTimer(TIMEOUT, INTERVAL) {
            @Override
            public void onTick(long minisec) {
                progressBar.setProgress(progressValue);
                progressValue++;

            }

            @Override
            public void onFinish() {
                mCountDown.cancel();
                mostrarPregunta(++index);
            }
        };
        mostrarPregunta(++index);
    }
}
