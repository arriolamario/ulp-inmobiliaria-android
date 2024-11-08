package com.arriola.inmobiliaria.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding bind;
    private MainActivityViewModel vm;
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

    }
}