package br.ufc.dspm.abilio.drapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import br.ufc.dspm.abilio.drapp.model.Doctor;
import br.ufc.dspm.abilio.drapp.model.Result;
import br.ufc.dspm.abilio.drapp.model.Users;
import br.ufc.dspm.abilio.drapp.repository.UsersRepository;

public class DoctorSignup extends AppCompatActivity {

    Button btnCadastrar;
    EditText etNome;
    EditText etEspecialidade;
    EditText etEmail;
    EditText etSenha;
    EditText etConfirmarSenha;

    UsersRepository usersRepository;
    AppContainer appContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_singup);

        appContainer = ((MyApplication) getApplication()).appContainer;
        usersRepository = appContainer.usersRepository;

        setCadastrarAction();
    }

    private void setCadastrarAction() {
        btnCadastrar = findViewById(R.id.btnCadastrarMedico);
        etNome = findViewById(R.id.etNomeMedico);
        etEspecialidade = findViewById(R.id.etEspecialidadeMedico);
        etEmail = findViewById(R.id.etEmailMedico);
        etSenha = findViewById(R.id.etSenhaCadastroMedico);
        etConfirmarSenha = findViewById(R.id.etConfirmarSenhaCadastroMedico);

        btnCadastrar.setOnClickListener(v -> {
            String nome = etNome.getText().toString();
            String especialidade = etEspecialidade.getText().toString();
            String email = etEmail.getText().toString();
            String password = etSenha.getText().toString();
            String passwordConfirmation = etConfirmarSenha.getText().toString();
            if(nome.isEmpty() || especialidade.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
                Toast.makeText(DoctorSignup.this, "por favor\n preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            } else if(!password.equals(passwordConfirmation)) {
                Toast.makeText(DoctorSignup.this, "senha e confirmação de senha\n não conferem!", Toast.LENGTH_SHORT).show();
                return;
            }
            insertDoctorRequest(new Doctor(nome, especialidade, email, password));
        });
    }

    private void insertDoctorRequest(Doctor doctor) {
        usersRepository.makeInsertDoctorRequest(doctor, result -> {
            if(result instanceof Result.Success) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("user", doctor);
                intent.putExtra("userType", "doctor");
                appContainer.user = doctor;
                startActivity(intent);
                finishAffinity();
                return;
            }
            Toast.makeText(DoctorSignup.this, "erro ao adicionar usuário", Toast.LENGTH_SHORT).show();
            Log.e("erro:", ((Result.Error<Users>) result).exception.getMessage());
        });
    }
}