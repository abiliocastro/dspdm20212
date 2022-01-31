package br.ufc.dspm.abilio.drapp.repository;

import android.os.Handler;

import com.google.gson.Gson;

import java.util.concurrent.Executor;

import br.ufc.dspm.abilio.drapp.parser.UsersParser;

public class UsersRepository {
    private final String host = "http://10.0.0.103:8080";
    private final UsersParser responseParser;
    private final Executor executor;
    private final Handler resultHandler;
    private final Gson gson;

    public UsersRepository(UsersParser responseParser, Executor executor, Handler resultHandler) {
        this.responseParser = responseParser;
        this.executor = executor;
        this.resultHandler = resultHandler;
        this.gson = new Gson();
    }
}
