package com.example.loginsegurity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class loginUsuario extends AppCompatActivity {

    Button btnIniciarLogin, btnRegistrarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);

        btnIniciarLogin = (Button) findViewById(R.id.btnIniciarLogin);
        btnRegistrarLogin = (Button) findViewById(R.id.btnRegistrarLogin);


        // Boton Registrar de la interfaz "loginUsuario"
        btnRegistrarLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(loginUsuario.this, registroUsuario.class);
                startActivity(i);
            }
        });


    }
}
