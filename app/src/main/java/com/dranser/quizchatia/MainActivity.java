package com.dranser.quizchatia;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.dranser.quizchatia.Modelo.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
                            Toast.makeText(MainActivity.this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();
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
}
