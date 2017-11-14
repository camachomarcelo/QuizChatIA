package com.dranser.quizchatia;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.dranser.quizchatia.BroadcastReceiver.AlarmReceiver;
import com.dranser.quizchatia.Common.Common;
import com.dranser.quizchatia.Modelo.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    MaterialEditText edtNuevoUsuario, edtNuevaContraseña, edtNuevoEmail; //Para el registro
    MaterialEditText edtUsuario, edtContraseña; //Para iniciar sesión

    Button btnRegistro, btnIniciarSesion;

    FirebaseDatabase database;
    DatabaseReference usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registrarAlarma();

        //Firebase
        database = FirebaseDatabase.getInstance();
        usuarios = database.getReference("Usuarios");

        edtUsuario = (MaterialEditText)findViewById(R.id.edtUsuario);
        edtContraseña = (MaterialEditText)findViewById(R.id.edtContraseña);

        btnRegistro = (Button)findViewById(R.id.btn_registro);
        btnIniciarSesion = (Button)findViewById(R.id.btn_iniciar_sesion);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarRegistroDialog();
            }
        });

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion(edtUsuario.getText().toString(),edtContraseña.getText().toString());
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

     /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }); */
    }

    private void registrarAlarma() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,12); //hora 9
        calendar.set(Calendar.MINUTE,12); //minuto 28
        calendar.set(Calendar.SECOND,0); //segundo 0

        Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }

    private void iniciarSesion(final String user, final String pwd) {
        usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(user).exists())
                {
                    if (!user.isEmpty())
                    {
                        Usuario login = dataSnapshot.child(user).getValue(Usuario.class);
                        if (login.getContraseña().equals(pwd))
                        {
                            Intent inicioActivity = new Intent(MainActivity.this, Inicio.class);
                            Common.usuarioActual = login;
                            startActivity(inicioActivity);
                            finish();
                        }
                        else
                            Toast.makeText(MainActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Ingresa tus datos completos", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(MainActivity.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void mostrarRegistroDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Regístrate");
        alertDialog.setMessage("Rellena todos los campos");

        LayoutInflater inflater = this.getLayoutInflater();
        View registro_layout = inflater.inflate(R.layout.registro_layout, null);

        edtNuevoUsuario = (MaterialEditText)registro_layout.findViewById(R.id.edtNuevoNombreUsuario);
        edtNuevoEmail = (MaterialEditText)registro_layout.findViewById(R.id.edtNuevoEmail);
        edtNuevaContraseña = (MaterialEditText)registro_layout.findViewById(R.id.edtNuevaContraseña);

        alertDialog.setView(registro_layout);
        alertDialog.setIcon(R.drawable.ic_account_circle_black_24dp);


        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                 final Usuario usuario = new Usuario(edtNuevoUsuario.getText().toString(),
                        edtNuevaContraseña.getText().toString(),
                        edtNuevoEmail.getText().toString());

                usuarios.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(usuario.getNombreUsuario()).exists())
                            Toast.makeText(MainActivity.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();
                        else
                        {
                            usuarios.child(usuario.getNombreUsuario())
                                    .setValue(usuario);
                            Toast.makeText(MainActivity.this, "Registro correcto", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void abrirChat(View view){
        Intent abrirChatap = getPackageManager().getLaunchIntentForPackage("com.dranser.androidsimsimi");
        startActivity(abrirChatap);
    }
}
