package com.arriola.inmobiliaria.ui.menu;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.model.Propietario;
import com.arriola.inmobiliaria.model.Token;
import com.arriola.inmobiliaria.request.ApiClient;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.arriola.inmobiliaria.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;
    private MenuActivityViewModel vm;
    private TextView nombreCompleto;
    private TextView email;
    private ImageView fotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MenuActivityViewModel.class);

        setSupportActionBar(binding.appBarMenu.toolbar);
        binding.appBarMenu.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        View headerView = navigationView.getHeaderView(0);
        nombreCompleto = headerView.findViewById(R.id.tbNombreApellidoPropietario);
        email = headerView.findViewById(R.id.tvEmailPropietario);
        fotoPerfil =headerView.findViewById(R.id.ivFotoPerfilPropoietario);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.contratoFragment, R.id.inicioFragment, R.id.perfilFragment, R.id.inquilinosFragment, R.id.inmueblesFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        vm.getmToken().observe(this, new Observer<Token>() {
            @Override
            public void onChanged(Token token) {
                nombreCompleto.setText(token.getUsuario().getNombre() + " " + token.getUsuario().getApellido());
                email.setText(token.getUsuario().getEmail());
                Log.d("SALIDA URL", token.getUsuario().getAvatar_Url());
                Glide.with(getApplicationContext())
                        .load(ApiClient.URLBASE + token.getUsuario().getAvatar_Url())
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round)
                        .into(fotoPerfil);
            }
        });

        vm.datosPropietario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}