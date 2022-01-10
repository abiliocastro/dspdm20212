package br.ufc.dspm.abilio.trabalho3_1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.ufc.dspm.abilio.trabalho3_1.adapter.BookAdapter;
import br.ufc.dspm.abilio.trabalho3_1.model.Book;
import br.ufc.dspm.abilio.trabalho3_1.model.Result;
import br.ufc.dspm.abilio.trabalho3_1.parser.BookParser;
import br.ufc.dspm.abilio.trabalho3_1.repository.BookRepository;
import br.ufc.dspm.abilio.trabalho3_1.repository.RepositoryCallback;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button editButton;
    Button addButton;
    EditText etId;

    BookAdapter adapter;

    int nextId = 4;

    // Construct the data source
    ArrayList<Book> arrayOfBooks = new ArrayList<>();

    BookRepository bookRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;
        bookRepository = appContainer.bookRepository;

//        requestPermissionLauncher.launch(Manifest.permission.INTERNET);
        setListViewAdapter();
        getAllBoooksRequest();
        setEditButton();
        setAddButton();

    }

    @Override
    protected void onResume() {
        super.onResume();
        etId.setText("");
        etId.clearFocus();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        getAllBoooksRequest();
//    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            });

    // You can do the assignment inside onAttach or onCreate, i.e, before the activity is displayed
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
        new ActivityResultContracts.StartActivityForResult(),
        result -> {
            if (result != null && result.getResultCode() == Activity.RESULT_OK) {
                // There are no request codes
                Intent intent = result.getData();
                String action = (String) intent.getSerializableExtra("action");
                Book book = (Book) intent.getSerializableExtra("book");
                if(action != null && action.equals("edit")){
                    Book bookFromArray = getBookByScreenId(book.getScreenId());
                    bookFromArray.setTitle(book.getTitle());
                    bookFromArray.setNumberOfPages(book.getNumberOfPages());
                }
                else if(action != null && action.equals("add")){
                    arrayOfBooks.add(book);
                }
                adapter.notifyDataSetChanged();
            } else if(result != null && result.getResultCode() == Activity.RESULT_CANCELED){
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
                Book book = getBookByScreenId(idInput);
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

    private Book getBookByScreenId(String id) {
        for (Book book: arrayOfBooks) {
            if(book.getScreenId().equals(id)) return book;
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
//        // Add itens to adapter
//        getAllBoooksRequest();
        // Attach the adapter to a ListView
        listView = findViewById(R.id.lvBooks);
        listView.setAdapter(adapter);

//        adapter.addAll(Arrays.asList(book1, book2, book3));
    }

    private void getAllBoooksRequest() {
        bookRepository.makeGetAllBooksRequest(result -> {
            if (result instanceof Result.Success) {
                List<Book> booksRetrieved = ((Result.Success<List<Book>>) result).data;
                arrayOfBooks.addAll(booksRetrieved);
                adapter.notifyDataSetChanged();
            } else {
                Log.e("erro:", ((Result.Error<List<Book>>) result).exception.getMessage());
            }
        });
    }
}