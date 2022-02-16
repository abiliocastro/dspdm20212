package br.ufc.dspm.abilio.provafinal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.ufc.dspm.abilio.provafinal.model.Livro;

public class SecondActivity extends AppCompatActivity {

    TextView infoTextView;
    EditText idEditText;
    EditText tituloEditText;
    EditText autorEditText;
    EditText editoraEditText;
    EditText anoPublicacaoEditText;
    EditText isbnEditText;
    Button actionButton;
    Button cancelarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        infoTextView = findViewById(R.id.infoTextView);
        idEditText = findViewById(R.id.idEditText);
        idEditText.setEnabled(false);
        tituloEditText = findViewById(R.id.tituloEditText);
        autorEditText = findViewById(R.id.autorEditText);
        editoraEditText = findViewById(R.id.editoraEditText);
        anoPublicacaoEditText = findViewById(R.id.anoPublicacaoEditText);
        isbnEditText = findViewById(R.id.isbnEditText);
        actionButton = findViewById(R.id.actionButton);
        cancelarButton = findViewById(R.id.cancelarButton);

        Intent intent = getIntent();
        String action = (String) intent.getSerializableExtra("action");
        if(action.equals("edit")) {
            Livro livro = (Livro) intent.getSerializableExtra("livro");
            infoTextView.setText(R.string.infoEditarTextView);
            idEditText.setText(livro.getId());
            tituloEditText.setText(livro.getTitulo());
            autorEditText.setText(livro.getAutor());
            editoraEditText.setText(livro.getEditora());
            anoPublicacaoEditText.setText(livro.getAnoPublicacao());
            isbnEditText.setText(livro.getIsbn());
            actionButton.setText(R.string.editarButton);
            setEditButton();
        } else {
            int id = (int) intent.getSerializableExtra("id");
            idEditText.setText(String.valueOf(id));
            infoTextView.setText(R.string.infoAdicionarTextView);
            actionButton.setText(R.string.actionAdicionarButton);
            setAddButton();
        }

        cancelarButton.setOnClickListener(v -> {
            Intent intentCancel = new Intent(this, MainActivity.class);
            intentCancel.putExtra("action", action);
            setResult(Activity.RESULT_CANCELED, intentCancel);
            finish();
        });


    }

    private void setEditButton() {
        actionButton.setOnClickListener(v -> {
            String id = idEditText.getText().toString();
            String titulo = tituloEditText.getText().toString();
            String autor = autorEditText.getText().toString();
            String editora = editoraEditText.getText().toString();
            String anoPublicacao = anoPublicacaoEditText.getText().toString();
            String isbn = isbnEditText.getText().toString();
            if(titulo.isEmpty() || autor.isEmpty() || editora.isEmpty() || anoPublicacao.isEmpty() || isbn.isEmpty()) {
                Toast.makeText(SecondActivity.this, "por favor\n preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            Livro livro = new Livro(id, titulo, autor, editora, anoPublicacao, isbn);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("action", "edit");
            intent.putExtra("livro", livro);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }

    private void setAddButton() {
        actionButton.setOnClickListener(v -> {
            String id = idEditText.getText().toString();
            String titulo = tituloEditText.getText().toString();
            String autor = autorEditText.getText().toString();
            String editora = editoraEditText.getText().toString();
            String anoPublicacao = anoPublicacaoEditText.getText().toString();
            String isbn = isbnEditText.getText().toString();
            if(titulo.isEmpty() || autor.isEmpty() || editora.isEmpty() || anoPublicacao.isEmpty() || isbn.isEmpty()) {
                Toast.makeText(SecondActivity.this, "por favor\n preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }
            Livro livro = new Livro(id, titulo, autor, editora, anoPublicacao, isbn);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("action", "add");
            intent.putExtra("livro", livro);
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
    }
}