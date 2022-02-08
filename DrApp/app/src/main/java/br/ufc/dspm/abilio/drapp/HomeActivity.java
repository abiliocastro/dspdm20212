package br.ufc.dspm.abilio.drapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import br.ufc.dspm.abilio.drapp.model.Doctor;
import br.ufc.dspm.abilio.drapp.model.Patient;
import br.ufc.dspm.abilio.drapp.model.Result;
import br.ufc.dspm.abilio.drapp.model.Users;
import br.ufc.dspm.abilio.drapp.repository.UsersRepository;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private UsersRepository usersRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        usersRepository = appContainer.usersRepository;

        Intent intent = getIntent();
        String userType = (String) intent.getSerializableExtra("userType");
        Users user = (Users) intent.getSerializableExtra("user");
        if(userType.equals("patient")) {
            usersRepository.makeGetPatient(user, result -> {
                if(result instanceof Result.Success) {
                    Patient patient = (Patient) ((Result.Success<Users>) result).data;
                    TextView tvHeaderTitle = navigationView.findViewById(R.id.tvHeaderMainTitle);
                    TextView tvHeadetSubtitle = navigationView.findViewById(R.id.tvHeaderMainSubtitle);
                    tvHeaderTitle.setText(patient.getNome());
                    tvHeadetSubtitle.setText(patient.getUsername());
                } else {
                    Toast.makeText(HomeActivity.this, "erro ao carregar informações do usuário", Toast.LENGTH_SHORT).show();
                    Log.e("erro:", ((Result.Error<Users>) result).exception.getMessage());
                }
            });
        } else {
            usersRepository.makeGetDoctor(user, result -> {
                if(result instanceof Result.Success) {
                    Doctor doctor = (Doctor) ((Result.Success<Users>) result).data;
                    TextView tvHeaderTitle = navigationView.findViewById(R.id.tvHeaderMainTitle);
                    TextView tvHeadetSubtitle = navigationView.findViewById(R.id.tvHeaderMainSubtitle);
                    tvHeaderTitle.setText(doctor.getNome());
                    tvHeadetSubtitle.setText(doctor.getEspecialidade());
                } else {
                    Toast.makeText(HomeActivity.this, "erro ao carregar informações do usuário", Toast.LENGTH_SHORT).show();
                    Log.e("erro:", ((Result.Error<Users>) result).exception.getMessage());
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}