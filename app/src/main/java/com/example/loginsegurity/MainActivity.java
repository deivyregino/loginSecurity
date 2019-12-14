package com.example.loginsegurity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    static String clave = "deivy";
    static String llaveEncriptacion = "DeivyJoseReginoTorres";
    private static String complemento = "ReginoTorres";
    byte[] encriptacion = new byte[0];
    private static SecretKeySpec secret;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // Metodo para generar clave
    public static SecretKey generateKey() throws NoSuchAlgorithmException, InvalidKeyException{
    return secret = new SecretKeySpec(clave.getBytes(), "AES");
    }

    // Metodo para encriptar
    public static byte[] encryptMsg(String message, SecretKey secret) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidParameterException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        byte[] cipherText = cipher.doFinal(message.getBytes("UTF-8"));
        return cipherText;
    }

    // Metodo para desencriptar
    public static String decryptMsg(byte[] cipherText , SecretKey secret) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, InvalidParameterException,
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        Cipher cipher = null;
        cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret);
        String  decryptString = new String(cipher.doFinal(cipherText), "UTF-8");
        return decryptString;
    }

    // Metodo Encriptacion
    public static String encrypt(String password, String secret)
    {
        try
        {
            byte[] iv =  new byte[0];
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(llaveEncriptacion);
            KeySpec spec = new PBEKeySpec(secret.toCharArray(), complemento.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
            return Base64.getEncoder().encodeToString(cipher.doFinal(password.getBytes("UTF-8")));
        }
        catch (Exception e)
        {
            System.out.println("Error mientras se encripta: " + e.toString());
        }
        return null;
    }

    // Metodo Desencriptacion

    public static String decrypt(String password, String secret){
        try
        {
            byte[] iv = new byte[0];
            IvParameterSpec ivspec = new IvParameterSpec(iv);

            SecretKeyFactory factory = SecretKeyFactory.getInstance(llaveEncriptacion);
            KeySpec spec = new PBEKeySpec(secret.toCharArray(), complemento.getBytes(), 65536, 256);
            SecretKey tmp = factory.generateSecret(spec);
            SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
            return new String(cipher.doFinal(Base64.getDecoder().decode(password)));
        }
        catch (Exception e) {
            System.out.println("Error mientras se desecncripta: " + e.toString());
        }
        return null;
    }
}
