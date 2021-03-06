package br.ufc.dspm.abilio.drapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.ufc.dspm.abilio.drapp.model.Patient;
import br.ufc.dspm.abilio.drapp.model.Result;
import br.ufc.dspm.abilio.drapp.model.Users;
import br.ufc.dspm.abilio.drapp.repository.UsersRepository;

public class PatientSignup extends AppCompatActivity {

    Button btnCadastrar;
    EditText etNome;
    EditText etEmail;
    EditText etSenha;
    EditText etConfirmarSenha;

    UsersRepository usersRepository;
    AppContainer appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup);

        appContainer = ((MyApplication) getApplication()).appContainer;
        usersRepository = appContainer.usersRepository;

        setCadastrarAction();
    }

    private void setCadastrarAction() {
        btnCadastrar = findViewById(R.id.btnCadastrarPaciente);
        etNome = findViewById(R.id.etNomePaciente);
        etEmail = findViewById(R.id.etEmailPaciente);
        etSenha = findViewById(R.id.etSenhaCadastroPaciente);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenhaCadastroPaciente);

        btnCadastrar.setOnClickListener(v -> {
            String nome = etNome.getText().toString();
            String email = etEmail.getText().toString();
            String password = etSenha.getText().toString();
            String passwordConfirmation = etConfirmarSenha.getText().toString();
            if(nome.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
                Toast.makeText(PatientSignup.this, "por favor\n preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            } else if(!password.equals(passwordConfirmation)) {
                Toast.makeText(PatientSignup.this, "senha e confirma????o de senha\n n??o conferem!", Toast.LENGTH_SHORT).show();
                return;
            }
            insertPatientRequest(new Patient(nome, email, password));
        });
    }

    private void insertPatientRequest(Patient patient) {
        usersRepository.makeInsertPatientRequest(patient, result -> {
            if(result instanceof Result.Success) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("user", patient);
                intent.putExtra("userType", "patient");
                appContainer.user = patient;
                startActivity(intent);
                finishAffinity();
                return;
            }
            Toast.makeText(PatientSignup.this, "erro ao adicionar usu??rio", Toast.LENGTH_SHORT).show();
            Log.e("erro:", ((Result.Error<Users>) result).exception.getMessage());
        });
    }
}