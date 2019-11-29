package com.example.loginsegurity;

import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.UnsupportedEncodingException;
import java.net.PasswordAuthentication;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class registroUsuario extends AppCompatActivity {

    Button btnRegistroRegistrar, btnLoginRegistrar;
    EditText editEmail;

    private EditText claveUno, claveDos, email;


    private String emailRegistro = "" , guardarClaveUno = "", guardarClaveDos = "";


    FirebaseAuth myAuth;
    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        myAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        email = (EditText) findViewById(R.id.emailRegistro);
        claveUno = (EditText) findViewById(R.id.claveRegistro);
        claveDos = (EditText) findViewById(R.id.claveRegistroConfirmar);
        btnLoginRegistrar = (Button) findViewById(R.id.btnLoginRegistrar);
        btnRegistroRegistrar = (Button) findViewById(R.id.btnRegistroRegistrar);

        btnRegistroRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                emailRegistro = email.getText().toString();

                guardarClaveUno = claveUno.getText().toString();

                guardarClaveDos = claveDos.getText().toString();

                if (guardarClaveUno == guardarClaveDos) {


                        MainActivity ob = new MainActivity();
                        try {

                            ob.encryptMsg(guardarClaveUno, ob.secret);
                            ob.encryptMsg(emailRegistro, ob.secret);

                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (NoSuchPaddingException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                } else {
                    Toast.makeText(registroUsuario.this, "Por favor, llene todos los campos o verifique que las claves sean iguales", Toast.LENGTH_LONG).show();
                }
                registrarUsuario();



            }
        });

        btnLoginRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registroUsuario.this, loginUsuario.class));
                finish();
            }
        });
    }

    private void registrarUsuario(){

        myAuth.createUserWithEmailAndPassword(emailRegistro, guardarClaveUno).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    Map<String, Object> map = new HashMap<>();

                    map.put("Email", email);
                    map.put("Password", guardarClaveUno);

                    String id = myAuth.getCurrentUser().getUid();

                    database.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()){
                                startActivity(new Intent(registroUsuario.this, loginUsuario.class));
                                finish();
                            }else{
                                Toast.makeText(registroUsuario.this, "No se pudieron crear los datos correctamente", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(registroUsuario.this, "No se pudo registrar este usuario", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
