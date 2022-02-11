package br.ufc.dspm.abilio.drapp.repository;

import android.os.Handler;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.Executor;

import br.ufc.dspm.abilio.drapp.model.Doctor;
import br.ufc.dspm.abilio.drapp.model.Result;
import br.ufc.dspm.abilio.drapp.parser.UsersParser;

public class DoctorRepository {
    private final String host = "http://10.0.0.109:8080";
    private final UsersParser responseParser;
    private final Executor executor;
    private final Handler resultHandler;
    private final Gson gson;

    public DoctorRepository(UsersParser responseParser, Executor executor, Handler resultHandler) {
        this.responseParser = responseParser;
        this.executor = executor;
        this.resultHandler = resultHandler;
        this.gson = new Gson();
    }

    public void makeGetAllDoctors(final RepositoryCallback<List<Doctor>> callback) {
        executor.execute(() -> {
            try {
                notifyResult(doGetAllDoctorRequest(), callback);
            } catch (Exception e) {
                Result<List<Doctor>> errorResult = new Result.Error<>(e);
                notifyResult(errorResult, callback);
            }
        });
    }

    private Result<List<Doctor>> doGetAllDoctorRequest() throws IOException {
        try {
            String endpoint = "/users/allDoctors";
            URL url = new URL(host + endpoint);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Authorization", setAdminAuth());
            int resultCode = httpConnection.getResponseCode();
            if(resultCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Not 200 ok resultCode");
            }
            List<Doctor> doctorsReturned = responseParser.parseAllDoctors(httpConnection.getInputStream());
            return new Result.Success<>(doctorsReturned);
        } catch (Exception e) {
            throw e;
        }
    }

    private void notifyResult(
            final Result<List<Doctor>> result,
            final RepositoryCallback<List<Doctor>> callback) {
        resultHandler.post(() -> callback.onComplete(result));
    }

    private String setAdminAuth() {
        String user = "admin";
        String password = "admin";
        String auth = user + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes());
        String authHeaderValue = "Basic " + new String(encodedAuth);
        return authHeaderValue;
    }
}
