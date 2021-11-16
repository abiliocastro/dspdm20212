package br.ufc.dspm.abilio.trabalho2;

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
import java.util.Arrays;

import br.ufc.dspm.abilio.trabalho2.model.Book;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button editButton;
    Button addButton;
    EditText etId;

    BookAdapter adapter;

    int nextId = 3;

    // Construct the data source
    ArrayList<Book> arrayOfBooks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setListViewAdapter();
        setEditButton();
        setAddButton();

    }

    @Override
    protected void onResume() {
        super.onResume();
        etId.setText("");
        etId.clearFocus();
    }

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent intent = result.getData();
                String action = (String) intent.getSerializableExtra("action");
                Book book = (Book) intent.getSerializableExtra("book");
                if(action != null && action.equals("edit")){
                    Book bookFromArray = getBookById(book.getId());
                    bookFromArray.setTitle(book.getTitle());
                    bookFromArray.setNumberOfPages(book.getNumberOfPages());
                }
                else if(action != null && action.equals("add")){
                    arrayOfBooks.add(book);
                }
                adapter.notifyDataSetChanged();
            } else if(result.getResultCode() == Activity.RESULT_CANCELED){
                Intent intent = result.getData();
                String action = (String) intent.getSerializableExtra("action");
                if(action != null && action.equals("add")){
                    nextId--;
                }
            }
        });

    private void setEditButton() {
        editButton = findViewById(R.id.btnEdit);
        etId = findViewById(R.id.etId);
        editButton.setOnClickListener(v -> {
            String idInput = etId.getText().toString();
            if(idInput.isEmpty()){
                Toast.makeText(MainActivity.this, "Por favor digite algum id", Toast.LENGTH_SHORT).show();
                return;
            } else if(!arrayOfBooks.isEmpty()) {
                Book book = getBookById(idInput);
                if(book != null) {
                    Intent intent = new Intent(this, Activity2.class);
                    intent.putExtra("book", book);
                    intent.putExtra("action", "edit");
                    activityResultLauncher.launch(intent);
                    return;
                }
            }
            Toast.makeText(MainActivity.this, "id não encontrado", Toast.LENGTH_SHORT).show();
        });
    }

    private void setAddButton() {
        addButton = findViewById(R.id.btnAdd);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, Activity2.class);
            intent.putExtra("action", "add");
            intent.putExtra("id", getNextId());
            activityResultLauncher.launch(intent);
        });
    }

    private Book getBookById(String id) {
        for (Book book: arrayOfBooks) {
            if(book.getId().equals(id)) return book;
        }
        return null;
    }

    private int getNextId() {
        nextId++;
        return nextId;
    }

    // List View Adapter
    private void setListViewAdapter() {
        // Create the adapter to convert the array to views
        adapter = new BookAdapter(this, arrayOfBooks);
        // Attach the adapter to a ListView
        listView = findViewById(R.id.lvBooks);
        listView.setAdapter(adapter);

        // Add itens to adapter
        Book book1 = new Book("1", "Pálido Ponto Azul", "400");
        Book book2 = new Book("2", "O Poeta do Absurdo", "200");
        Book book3 = new Book("3", "Engenharia de Software - Sommervile", "300");

        arrayOfBooks.addAll(Arrays.asList(book1, book2, book3));
//        adapter.addAll(Arrays.asList(book1, book2, book3));
    }
}