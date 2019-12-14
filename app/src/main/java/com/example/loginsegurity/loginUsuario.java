package com.example.loginsegurity;

import android.content.Context;
import android.content.Intent;
// import android.provider.ContactsContract;
import android.content.SharedPreferences;
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

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static com.example.loginsegurity.MainActivity.decrypt;
import static com.example.loginsegurity.MainActivity.encrypt;
import static com.example.loginsegurity.MainActivity.llaveEncriptacion;


public class loginUsuario extends AppCompatActivity {
    private static SecretKeySpec secret;
    MainActivity ob = new MainActivity();

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
                    String encriptacion = encrypt(password, llaveEncriptacion) ;
                    String desencriptacion = decrypt(encriptacion, llaveEncriptacion) ;
                    singIn(email, desencriptacion);

                    SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor=preferences.edit();
                    editor.putBoolean("isValid",true);
                    editor.putString("Correo", email);
                    editor.putString("contraseña", password);
                    editor.commit();
                    String mail = preferences.getString("correo","");
                    MainActivity ob = new MainActivity();

                    try{
                        ob.encriptacion = encryptMsg(email, generateKey());
                        SecretKey secret = generateKey();
                        ob.encriptacion = encryptMsg(password, generateKey());
                        desencriptacion = decrryptMsg(ob.encriptacion, generateKey());
                        singIn(email, desencriptacion);
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (InvalidKeyException e) {
                        e.printStackTrace();
                    } catch (NoSuchPaddingException e) {
                        e.printStackTrace();
                    } catch (BadPaddingException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (IllegalBlockSizeException e) {
                        e.printStackTrace();
                    }
                    }else{
                    Toast.makeText(loginUsuario.this, "Complete los campos", Toast.LENGTH_LONG).show();
                }
                }


        });


    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
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

    public static byte[] encryptMsg(String message, SecretKey secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;
    }
    public static String decrryptMsg(byte[] cipherText, SecretKey secret) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidParameterException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }

    public static SecretKey generateKey()throws NoSuchAlgorithmException, InvalidKeyException{
        return secret = new SecretKeySpec(llaveEncriptacion.getBytes(),"AES");
    }
}
