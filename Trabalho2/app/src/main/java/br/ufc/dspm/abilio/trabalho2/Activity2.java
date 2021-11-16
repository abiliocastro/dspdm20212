package br.ufc.dspm.abilio.trabalho2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.dspm.abilio.trabalho2.model.Book;

public class Activity2 extends AppCompatActivity {

    EditText etId;
    EditText etTitle;
    EditText etPages;
    TextView tvInfo;
    Button btnAction;
    Button btnCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        etId = findViewById(R.id.etIdActivity2);
        etId.setEnabled(false);
        etTitle = findViewById(R.id.etTitleActivity2);
        etPages = findViewById(R.id.etNumOfPagesActivity2);
        tvInfo = findViewById(R.id.tvInfo);
        btnAction = findViewById(R.id.btnAction);
        btnCancelar = findViewById(R.id.btnCancelar);

        Intent intent = getIntent();
        String action = (String) intent.getSerializableExtra("action");
        if(action.equals("edit")) {
            Book book = (Book) intent.getSerializableExtra("book");
            etId.setText(book.getId());
            etTitle.setText(book.getTitle());
            tvInfo.setText(R.string.tvInfoEdit);
            etPages.setText(book.getNumberOfPages());
            btnAction.setText(R.string.btnActionEdit);
            setEditButton();
        } else {
            int id = (int) intent.getSerializableExtra("id");
            etId.setText(String.valueOf(id));
            tvInfo.setText(R.string.defaultTvInfo);
            btnAction.setText(R.string.btnActionDefault);
            setAddButton();
        }

        btnCancelar.setOnClickListener(v -> {
            Intent intentCancel = new Intent(this, MainActivity.class);
            intentCancel.putExtra("action", action);
            setResult(Activity.RESULT_CANCELED, intentCancel);
            finish();
        });


    }

    private void setEditButton() {
        btnAction.setOnClickListener(v -> {
            String id = etId.getText().toString();
            String title = etTitle.getText().toString();
            String numOfPages = etPages.getText().toString();
            if(title.isEmpty() || numOfPages.isEmpty()) {
                Toast.makeText(Activity2.this, "por favor\n preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            Book book = new Book(id, title, numOfPages);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("action", "edit");
            intent.putExtra("book", book);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }

    private void setAddButton() {
        btnAction.setOnClickListener(v -> {
            String id = etId.getText().toString();
            String title = etTitle.getText().toString();
            String numOfPages = etPages.getText().toString();
            if(title.isEmpty() || numOfPages.isEmpty()) {
                Toast.makeText(Activity2.this, "por favor\n preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            Book book = new Book(id, title, numOfPages);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("action", "add");
            intent.putExtra("book", book);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}