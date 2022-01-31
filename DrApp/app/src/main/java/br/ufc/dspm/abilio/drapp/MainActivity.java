package br.ufc.dspm.abilio.drapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView linkPatientSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callPatientSignUpScreen();
    }

    private void callPatientSignUpScreen() {
        linkPatientSignUp = findViewById(R.id.pacienteCadastroTv);
        linkPatientSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(this, PatientSignup.class);
            startActivity(intent);
        });
    }
}