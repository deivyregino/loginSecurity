package com.example.loginsegurity;

import android.content.Intent;
// import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginUsuario extends AppCompatActivity {

    Button btnIniciarLogin, btnRegistrarLogin;
    EditText emailLogin, claveLogin;

    private String email = "", password = "";

    private FirebaseAuth myAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        myAuth = FirebaseAuth.getInstance();

        btnIniciarLogin = (Button) findViewById(R.id.btnIniciarLogin);
        btnRegistrarLogin = (Button) findViewById(R.id.btnRegistrarLogin);
        emailLogin = (EditText) findViewById(R.id.emailLogin);
        claveLogin = (EditText) findViewById(R.id.claveLogin);


        // Boton Registrar de la interfaz "loginUsuario"
        btnRegistrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginUsuario.this, registroUsuario.class);
                startActivity(i);
            }
        });

        btnIniciarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailLogin.getText().toString();
                password = claveLogin.getText().toString();

                if ((!email.isEmpty()) && (!password.isEmpty())){

                    loginUsuario(email, password);

                }else{
                    Toast.makeText(loginUsuario.this, "Complete los campos", Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    private void loginUsuario(String email, String password){

        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(loginUsuario.this, "Inicio sesion", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(loginUsuario.this, homeUsuario.class));
                    finish();
                }else {
                    Toast.makeText(loginUsuario.this, "No se pudo iniciar sesion", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
