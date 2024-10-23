package com.arriola.inmobiliaria.ui.restablecerclave;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.ActivityMainBinding;
import com.arriola.inmobiliaria.databinding.ActivityRestablecerBinding;
import com.arriola.inmobiliaria.ui.login.MainActivityViewModel;

public class RestablecerActivity extends AppCompatActivity {
    private ActivityRestablecerBinding bind;
    private RestablecerActivityViewModel vm;
    private String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityRestablecerBinding.inflate(getLayoutInflater());

        setContentView(bind.getRoot());
        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RestablecerActivityViewModel.class);

        Intent intent = getIntent();
        Uri data = intent.getData();

        token = data.getQueryParameter("access_token");

        bind.btGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vm.cambiarClave(token, bind.etContrasenia.getText().toString());
            }
        });
    }
}