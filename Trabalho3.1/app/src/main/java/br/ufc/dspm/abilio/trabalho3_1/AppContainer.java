package br.ufc.dspm.abilio.trabalho3_1;

import android.os.Handler;

import java.util.concurrent.ExecutorService;

import br.ufc.dspm.abilio.trabalho3_1.parser.BookParser;
import br.ufc.dspm.abilio.trabalho3_1.repository.BookRepository;

public class AppContainer {

    private final ExecutorService executorService;
    private final Handler handler;
    private final BookParser bookParser;

    public BookRepository bookRepository;

    public AppContainer(ExecutorService executorService, Handler handler, BookParser bookParser) {
        this.executorService = executorService;
        this.handler = handler;
        this.bookParser = bookParser;
        this.bookRepository = new BookRepository(bookParser, executorService, handler);
    }
}
