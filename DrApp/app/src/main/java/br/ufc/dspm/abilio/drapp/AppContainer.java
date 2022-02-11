package br.ufc.dspm.abilio.drapp;

import android.os.Handler;

import java.util.concurrent.ExecutorService;

import br.ufc.dspm.abilio.drapp.model.Users;
import br.ufc.dspm.abilio.drapp.parser.Parser;
import br.ufc.dspm.abilio.drapp.parser.UsersParser;
import br.ufc.dspm.abilio.drapp.repository.DoctorRepository;
import br.ufc.dspm.abilio.drapp.repository.UsersRepository;

public class AppContainer {

    private final ExecutorService executorService;
    private final Handler handler;
    public UsersRepository usersRepository;
    public DoctorRepository doctorRepository;
    public Users user;

    public AppContainer(ExecutorService executorService, Handler handler) {
        this.executorService = executorService;
        this.handler = handler;
        UsersParser usersParser = new UsersParser();
        this.usersRepository = new UsersRepository(usersParser, executorService, handler);
        this.doctorRepository = new DoctorRepository(usersParser, executorService, handler);
    }
}
