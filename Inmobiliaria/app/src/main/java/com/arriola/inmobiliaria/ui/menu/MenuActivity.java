package com.arriola.inmobiliaria.ui.menu;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arriola.inmobiliaria.R;
import com.arriola.inmobiliaria.Util;
import com.arriola.inmobiliaria.model.Propietario;
import com.arriola.inmobiliaria.model.Token;
import com.arriola.inmobiliaria.request.ApiClient;
import com.arriola.inmobiliaria.ui.login.MainActivity;
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
                Util.cargarImagen(getApplicationContext(),token.getUsuario().getAvatar_Url(), R.mipmap.ic_launcher_round, fotoPerfil);
            }
        });

        vm.datosPropietario();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem itemSalir = menu.getItem(0);

        itemSalir.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                ApiClient.Borrar(getApplicationContext());
                //Toast.makeText(context, "Sesión Expirada!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("FLAGSESSION", false);
                getApplicationContext().startActivity(intent);

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}