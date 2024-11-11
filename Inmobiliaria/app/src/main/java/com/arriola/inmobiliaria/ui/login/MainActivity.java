package com.arriola.inmobiliaria.ui.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private ActivityMainBinding bind;
    private MainActivityViewModel vm;


    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final int SHAKE_THRESHOLD = 1000;
    private long lastUpdate;
    private float lastX, lastY, lastZ;
    private static final String PHONE_NUMBER = "123456789";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(bind.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainActivityViewModel.class);



        bind.ivLogoLogin.setImageResource(R.drawable.icono_inmobiliaria);
        bind.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.entrar(bind.etUsuarioLogin.getText().toString(), bind.etClaveLogin.getText().toString());
            }
        });

        bind.tvCambioClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.EnviarMail(bind.etUsuarioLogin.getText().toString());
            }
        });
        Intent intent = getIntent();
        vm.mensaje(intent);
        vm.UsuarioLogeado();


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (accelerometer != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastUpdate) > 100) {
            long timeDifference = (currentTime - lastUpdate);
            lastUpdate = currentTime;

            // Obtener las lecturas de los ejes
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            // Calcular el movimiento
            float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / timeDifference * 10000;

            if (speed > SHAKE_THRESHOLD) {
                makePhoneCall(); // Llama cuando se detecte una agitación
            }

            lastX = x;
            lastY = y;
            lastZ = z;
        }
    }

    private void makePhoneCall() {
        // Verifica el permiso antes de realizar la llamada
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + PHONE_NUMBER));
            startActivity(callIntent);
        } else {
            Toast.makeText(this, "Permiso de llamada no concedido", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de llamada concedido", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permiso de llamada denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }
}