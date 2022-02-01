package br.ufc.dspm.abilio.drapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.ufc.dspm.abilio.drapp.model.Patient;
import br.ufc.dspm.abilio.drapp.model.Result;
import br.ufc.dspm.abilio.drapp.repository.UsersRepository;

public class PatientSignup extends AppCompatActivity {

    Button btnCadastrar;
    EditText etNome;
    EditText etEmail;
    EditText etSenha;
    EditText etConfirmarSenha;

    UsersRepository usersRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_signup);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
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
                Toast.makeText(PatientSignup.this, "senha e confirmação de senha\n não conferem!", Toast.LENGTH_SHORT).show();
                return;
            }
            insertPatientRequest(new Patient(nome, email, password));
        });
    }

    private void insertPatientRequest(Patient patient) {
        usersRepository.makeInsertPatientRequest(patient, result -> {
            if(result instanceof Result.Success) {
                Toast.makeText(PatientSignup.this, "usuário adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(PatientSignup.this, "erro ao adicionar usuário", Toast.LENGTH_SHORT).show();
            Log.e("erro:", ((Result.Error<List<Patient>>) result).exception.getMessage());
        });
    }
}