package com.example.loginsegurity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class homeUsuario extends AppCompatActivity {

    Button btnObtenerUbicacion;
    EditText txtLatitud, txtLogintud;

    private LocationManager ubicacion;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_usuario);

        btnObtenerUbicacion = (Button) findViewById(R.id.btnObtenerUbicacion);
        txtLatitud = (EditText) findViewById(R.id.txtLatitud);
        txtLogintud = (EditText) findViewById(R.id.txtLongitud);

        obtenerUbicacion();
    }

    private void obtenerUbicacion() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1000);
        }
        ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        if (ubicacion != null) {
            txtLatitud.setText("".valueOf(loc.getLatitude()));
            txtLogintud.setText("".valueOf(loc.getLongitude()));
        }
    }
}
