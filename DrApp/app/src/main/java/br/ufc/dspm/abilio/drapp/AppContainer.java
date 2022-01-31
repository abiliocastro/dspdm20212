package br.ufc.dspm.abilio.drapp;

import android.os.Handler;

import java.util.concurrent.ExecutorService;

import br.ufc.dspm.abilio.drapp.parser.Parser;
import br.ufc.dspm.abilio.drapp.parser.UsersParser;
import br.ufc.dspm.abilio.drapp.repository.UsersRepository;

public class AppContainer {

    private final ExecutorService executorService;
    private final Handler handler;
    public UsersRepository usersRepository;

    public AppContainer(ExecutorService executorService, Handler handler) {
        this.executorService = executorService;
        this.handler = handler;
        this.usersRepository = new UsersRepository(new UsersParser(), executorService, handler);
    }
}
