package br.ufc.dspm.abilio.drapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.dspm.abilio.drapp.model.Result;
import br.ufc.dspm.abilio.drapp.model.Users;
import br.ufc.dspm.abilio.drapp.repository.UsersRepository;

public class MainActivity extends AppCompatActivity {

    TextView linkPatientSignUp;
    TextView linkDoctorSingUp;
    EditText etUsername;
    EditText etPassword;
    Button loginBtn;

    UsersRepository usersRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        usersRepository = appContainer.usersRepository;

        setLoginAction();
        callPatientSignUpScreen();
        callDoctorSignUpScreen();
    }

    private void setLoginAction() {
        loginBtn = findViewById(R.id.btnLogin);
        etUsername = findViewById(R.id.etEmailLogin);
        etPassword = findViewById(R.id.etPasswordLogin);
        loginBtn.setOnClickListener(v -> {
            String username = etUsername.getText().toString();
            String password = etPassword.getText().toString();
            if(username.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "por favor\n preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            usersRepository.makeLoginRequest(new Users(username, password), result -> {
                if(result instanceof Result.Success) {
                    Users user = ((Result.Success<Users>) result).data;
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.putExtra("user", user);
                    if(user.get_class().equals("com.drapp.drappbackend.model.Doctor")) {
                        intent.putExtra("userType", "doctor");
                    } else {
                        intent.putExtra("userType", "patient");
                    }
                    startActivity(intent);
                    finish();
                    return;
                }
                Toast.makeText(MainActivity.this, "erro ao fazer login", Toast.LENGTH_SHORT).show();
                Log.e("erro:", ((Result.Error<Users>) result).exception.getMessage());
            });
        });
    }

    private void callPatientSignUpScreen() {
        linkPatientSignUp = findViewById(R.id.pacienteCadastroTv);
        linkPatientSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, PatientSignup.class);
            startActivity(intent);
        });
    }

    private void callDoctorSignUpScreen() {
        linkDoctorSingUp = findViewById(R.id.medicoCadastroTv);
        linkDoctorSingUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, DoctorSignup.class);
            startActivity(intent);
        });
    }
}