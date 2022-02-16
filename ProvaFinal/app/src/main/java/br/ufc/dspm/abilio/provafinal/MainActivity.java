package br.ufc.dspm.abilio.provafinal;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.ufc.dspm.abilio.provafinal.adapter.LivroAdapter;
import br.ufc.dspm.abilio.provafinal.model.Livro;

public class MainActivity extends AppCompatActivity {

    ListView livrosListView;
    Button editarButton;
    Button adicionarButton;
    EditText idEditText;

    LivroAdapter livroAdapter;

    int nextId = 0;

    ArrayList<Livro> livros = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListView();
        setEditButton();
        setAddButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        idEditText.setText("");
        idEditText.clearFocus();
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent intent = result.getData();
                    String action = (String) intent.getSerializableExtra("action");
                    Livro livro = (Livro) intent.getSerializableExtra("livro");
                    if(action != null && action.equals("edit")){
                        Livro livroFromArray = getLivroById(livro.getId());
                        livroFromArray.setTitulo(livro.getTitulo());
                        livroFromArray.setAutor(livro.getAutor());
                        livroFromArray.setEditora(livro.getEditora());
                        livroFromArray.setAnoPublicacao(livro.getAnoPublicacao());
                        livroFromArray.setIsbn(livro.getIsbn());
                    }
                    else if(action != null && action.equals("add")){
                        livros.add(livro);
                    }
                    livroAdapter.notifyDataSetChanged();
                } else if(result.getResultCode() == Activity.RESULT_CANCELED){
                    Intent intent = result.getData();
                    String action = (String) intent.getSerializableExtra("action");
                    if(action != null && action.equals("add")){
                        nextId--;
                    }
                }
            });

    private void setListView() {
        livroAdapter = new LivroAdapter(this, R.id.livrosListView, livros);
        livrosListView = findViewById(R.id.livrosListView);
        livrosListView.setAdapter(livroAdapter);
    }

    private void setEditButton() {
        editarButton = findViewById(R.id.editarButton);
        idEditText = findViewById(R.id.idEditText);
        editarButton.setOnClickListener(v -> {
            String idInput = idEditText.getText().toString();
            if(idInput.isEmpty()){
                Toast.makeText(MainActivity.this, "Por favor digite algum id", Toast.LENGTH_SHORT).show();
                return;
            } else if(!livros.isEmpty()) {
                Livro livro = getLivroById(idInput);
                if(livro != null) {
                    Intent intent = new Intent(this, SecondActivity.class);
                    intent.putExtra("livro", livro);
                    intent.putExtra("action", "edit");
                    activityResultLauncher.launch(intent);
                    return;
                }
            }
            Toast.makeText(MainActivity.this, "id não encontrado", Toast.LENGTH_SHORT).show();
        });
    }

    private void setAddButton() {
        adicionarButton = findViewById(R.id.adicionarButton);
        adicionarButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra("action", "add");
            intent.putExtra("id", getNextId());
            activityResultLauncher.launch(intent);
        });
    }

    private Livro getLivroById(String id) {
        for (Livro livro: livros) {
            if(livro.getId().equals(id)) return livro;
        }
        return null;
    }

    private int getNextId() {
        nextId++;
        return nextId;
    }
}