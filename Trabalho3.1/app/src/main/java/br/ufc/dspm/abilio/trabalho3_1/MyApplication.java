package br.ufc.dspm.abilio.trabalho3_1;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.ufc.dspm.abilio.trabalho3_1.parser.BookParser;

public class MyApplication extends Application {
    ExecutorService executorService = Executors.newFixedThreadPool(4);
    Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    public AppContainer appContainer = new AppContainer(executorService, mainThreadHandler, new BookParser());
}
