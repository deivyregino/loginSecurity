package com.example.loginsegurity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class registroUsuario extends AppCompatActivity {

    Button btnRegistroRegistrar, btnLoginRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        btnLoginRegistrar = (Button) findViewById(R.id.btnLoginRegistrar);
        btnRegistroRegistrar = (Button) findViewById(R.id.btnRegistroRegistrar);

        // Boton Login de la clase Registrar.

        btnLoginRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(registroUsuario.this, loginUsuario.class);
                startActivity(i);
            }
        });
    }
}
