package br.ufc.dspm.abilio.trabalho1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    ToggleButton toggleButton;
    TextView textViewToggleBtn;
    Button btnSeeInputTxt;
    EditText editText1;
    AutoCompleteTextView autoCompleteTextView;
    Spinner spinner;
    RadioGroup radioGroup;
    Button btnTry;
    RadioButton radioButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToggleButton();
        setEditText();
        setAutocompleteTextView();
        setSpinner();
        setRadioButton();

    }

    // 1 - Toggle Button
    private void setToggleButton() {
        toggleButton = findViewById(R.id.toggleButton);
        textViewToggleBtn = findViewById(R.id.txtViewToggleBtn);

        setTextViewToggleBtn();

        toggleButton.setOnClickListener(v -> setTextViewToggleBtn());
    }

    private void setTextViewToggleBtn() {
        if(toggleButton.isChecked()) {
            textViewToggleBtn.setText("Ligou o negoço");
        } else {
            textViewToggleBtn.setText("Desligou o coiso");
        }
    }

    // 2 - EditText
    private void setEditText() {
        btnSeeInputTxt = findViewById(R.id.btnSeeInputTxt);
        editText1 = findViewById(R.id.editText1);

        btnSeeInputTxt.setOnClickListener(v -> {
            String textInput = editText1.getText().toString();
            StringBuilder sb = new StringBuilder();
            if(textInput.isEmpty()){
                sb.append("Macho! Tu não digitou nada, oh");
            } else {
                sb.append("Eu digo que tu digitou: \n").append(textInput);
            }
            Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    // 4 - Autocomplete Text View
    private void setAutocompleteTextView() {
        String str[]={"Arrocha","Amigo","Adaptável","Autocomplete","Abóbora (Jirimum)",
                "Abestado","Amor I Love You","Acutis","Aeogon","Aff","Ageu", "Antoin"};

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);

        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, str);

        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(adp);
    }

    // 5 - Spinner
    private void setSpinner() {
        String[] celebrities = {"", "Chicó", "João Grilo", "Rosinha", "Dora", "Major Antonio Moraes", "Vicentão", "Sivirino de Aracajú", "Pade João", "Cabo Setenta" };

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, celebrities);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,
                                               int arg2, long arg3) {
                        int position = spinner.getSelectedItemPosition();
                        if(position > 0) {
                            Toast.makeText(getApplicationContext(),"Escolhestes: "+celebrities[+position],Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {

                    }

                }
        );
    }

    // 6 - Radio Button
    private void setRadioButton() {
        radioGroup = findViewById(R.id.radioGroup);
        btnTry = (Button) findViewById(R.id.btnTentar);

        btnTry.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = (RadioButton) findViewById(selectedId);
            if(radioButton.getText().equals("Pamonha")) {
                Toast.makeText(MainActivity.this, "Pamonha é bom demais mah!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(MainActivity.this, "Errou!", Toast.LENGTH_SHORT).show();
        });
    }
}