package br.ufc.dspm.abilio.trabalho3_1.repository;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import br.ufc.dspm.abilio.trabalho3_1.model.Book;
import br.ufc.dspm.abilio.trabalho3_1.model.Result;
import br.ufc.dspm.abilio.trabalho3_1.parser.BookParser;

public class BookRepository {

    private final String host = "http://10.0.0.103:8080";
    private final BookParser responseParser;
    private final Executor executor;
    private final Handler resultHandler;
    private final Gson gson;

    public BookRepository(BookParser responseParser, Executor executor, Handler handler) {
        this.responseParser = responseParser;
        this.executor = executor;
        this.resultHandler = handler;
        this.gson = new Gson();
    }

    public void makeGetAllBooksRequest(final RepositoryCallback<List<Book>> callback) {
        executor.execute(() -> {
            try {
                Result<List<Book>> result = doAllBooksRequest();
                notifyResult(result, callback);
            } catch (Exception e) {
                Result<List<Book>> errorResult = new Result.Error<>(e);
                notifyResult(errorResult, callback);
            }
        });
    }

    public Result<List<Book>> doAllBooksRequest() {
        try {
            String endpoint = "/book/all/books";
            URL url = new URL(host + endpoint);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");

            List<Book> booksResponse = responseParser.parseAllBooks(httpConnection.getInputStream());
            return new Result.Success<>(booksResponse);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

    public void makeInsertBookRequest(final RepositoryCallback<List<Book>> callback, Book book) {
        executor.execute(() -> {
            try {
                doInsertBookRequest(book);
                notifyResult(new Result.Success<>(new ArrayList<>()), callback);
            } catch (Exception e) {
                Result<List<Book>> errorResult = new Result.Error<>(e);
                notifyResult(errorResult, callback);
            }
        });
    }

    public void doInsertBookRequest(Book book) throws IOException {
        try {
            String endpoint = "/book";
            URL url = new URL(host + endpoint);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setDoOutput(true);
            httpConnection.getOutputStream().write(gson.toJson(book).getBytes("utf-8"));
        } catch (Exception e) {
            throw e;
        }
    }

    private void notifyResult(
            final Result<List<Book>> result,
            final RepositoryCallback<List<Book>> callback) {
        resultHandler.post(() -> callback.onComplete(result));
    }

}