package br.ufc.dspm.abilio.trabalho1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {

    Button btnBackToMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        // 12 - Navegação entre activities
        btnBackToMain = findViewById(R.id.btnBackSecondScreen);
        btnBackToMain.setOnClickListener(v -> {
            finish();
        });

    }

}