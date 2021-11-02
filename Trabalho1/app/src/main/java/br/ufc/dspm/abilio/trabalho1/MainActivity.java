package br.ufc.dspm.abilio.trabalho1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;

import br.ufc.dspm.abilio.trabalho1.models.User;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ToggleButton toggleButton;
    TextView textViewToggleBtn;
    Button btnSeeInputTxt;
    EditText editText1;
    AutoCompleteTextView autoCompleteTextView;
    Spinner spinner;
    RadioGroup radioGroup;
    Button btnTry;
    RadioButton radioButton;
    Button btnPopupMenu;
    Button btnClickLongo;
    TextView textViewImgFundo;
    Button btnGoSecondScreen;
    Button btnGoMultiTabs;
    ListView listView;
    ListView listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToggleButton();
        setEditText();
        setAutocompleteTextView();
        setSpinner();
        setRadioButton();
        setPopUpMenu();
        setLongClickButton();
        setTextViewImgFundo();
        setGoSecondScreen();
        setGoMultiTabs();
        setListView();
        setListViewAdapter();
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
        btnTry = findViewById(R.id.btnTentar);

        btnTry.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(selectedId);
            if(radioButton != null && radioButton.getText().equals("Pamonha")) {
                Toast.makeText(MainActivity.this, "Pamonha é bom demais mah!", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(MainActivity.this, "Errou!", Toast.LENGTH_SHORT).show();
        });
    }

    // 7 - Options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        LinearLayout activity_main = findViewById(R.id.activity_main);
        switch (item.getItemId()) {
            case R.id.menu_red:
                item.setChecked(!item.isChecked());
                activity_main.setBackgroundColor(Color.RED);
                return true;
            case R.id.menu_green:
                item.setChecked(!item.isChecked());
                activity_main.setBackgroundColor(Color.GREEN);
                return true;
            case R.id.menu_blue:
                item.setChecked(!item.isChecked());
                activity_main.setBackgroundColor(Color.BLUE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // 8 e 9 - Popup/Dropdown Menu
    private void setPopUpMenu() {
        btnPopupMenu = findViewById(R.id.btnPopupMenu);
        btnPopupMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(MainActivity.this, btnPopupMenu);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(item -> {
                    Toast.makeText(
                            MainActivity.this,
                            "Escolhestes: " + item.getTitle(),
                            Toast.LENGTH_SHORT
                    ).show();
                    return true;
                });

                popup.show(); //showing popup menu
            }
        }); //closing the setOnClickListener method
    }

    // 10 - Clique Longo
    private void setLongClickButton() {
        btnClickLongo = findViewById(R.id.btnLongClick);
        btnClickLongo.setOnLongClickListener(v -> {
            Toast.makeText(
                    MainActivity.this,
                    "Olha ai o clique longo mah!",
                    Toast.LENGTH_SHORT
            ).show();
            return true;
        });
    }

    // 11 - Imagem de Fundo
    private void setTextViewImgFundo() {
        textViewImgFundo = findViewById(R.id.textViewImgFundo);
        textViewImgFundo.setBackgroundResource(R.drawable.rick);
    }

    // 12 - Navegação entre activities
    private void setGoSecondScreen() {
        btnGoSecondScreen = findViewById(R.id.btnGoSecondScreen);
        btnGoSecondScreen.setOnClickListener(v -> {
            goToSecondScreen();
        });
    }

    private void goToSecondScreen() {
        Intent secondScreenIntent = new Intent(this, SecondActivity.class);
        startActivity(secondScreenIntent);
    }

    // 13 - Activity com Múltiplas Tabs
    private void setGoMultiTabs() {
        btnGoMultiTabs = findViewById(R.id.btnGoMultiTabs);
        btnGoMultiTabs.setOnClickListener(v -> {
            goToMultiTabs();
        });
    }

    private void goToMultiTabs() {
        Intent multiTabsIntent = new Intent(this, TabedActivity.class);
        startActivity(multiTabsIntent);
    }

    // 14 - List View
    private void setListView() {
        listView = findViewById(R.id.sportsList);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                Toast.LENGTH_SHORT).show();
    }

    // 15 - List View Adapter
    private void setListViewAdapter() {
        // Construct the data source
        ArrayList<User> arrayOfUsers = new ArrayList<>();
        // Create the adapter to convert the array to views
        UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = findViewById(R.id.usersList);
        listView.setAdapter(adapter);

        // Add itens to adapter
        User newUser = new User("Nathan", "San Diego");
        User newUser1 = new User("Chico", "Fortaleza");
        User newUser2 = new User("Kit Harrington", "Londres");
        User newUser3 = new User("Maicão", "Bed Stuy");
        User newUser4 = new User("Untoin", "Quixadá");

        adapter.addAll(Arrays.asList(newUser, newUser1, newUser2, newUser3, newUser4));
    }


}