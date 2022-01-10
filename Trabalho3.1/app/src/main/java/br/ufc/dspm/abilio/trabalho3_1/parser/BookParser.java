package br.ufc.dspm.abilio.trabalho3_1.parser;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.ufc.dspm.abilio.trabalho3_1.model.Book;

public class BookParser {

    private Gson gson;

    public BookParser() {
        gson = new Gson();
    }

    public List<Book> parseAllBooks(InputStream inputStream) {
        InputStreamReader isr = new InputStreamReader(inputStream);
        Book[] booksArray = gson.fromJson(isr, Book[].class);
        return new ArrayList<>(Arrays.asList(booksArray));
    }
}
