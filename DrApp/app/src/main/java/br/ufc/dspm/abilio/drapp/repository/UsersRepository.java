package br.ufc.dspm.abilio.drapp.repository;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import br.ufc.dspm.abilio.drapp.model.Patient;
import br.ufc.dspm.abilio.drapp.model.Result;
import br.ufc.dspm.abilio.drapp.parser.UsersParser;

import org.apache.commons.codec.binary.Base64;

public class UsersRepository {
    private final String host = "http://10.0.0.109:8080";
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

    public void makeInsertPatientRequest(Patient patient, final RepositoryCallback<List<Patient>> callback) {
        executor.execute(() -> {
            try {
                doInsertPatientRequest(patient);
                notifyResult(new Result.Success<>(new ArrayList<>()), callback);
            } catch (Exception e) {
                Result<List<Patient>> errorResult = new Result.Error<>(e);
                notifyResult(errorResult, callback);
            }
        });
    }

    private void doInsertPatientRequest(Patient patient) throws IOException {
        try {
            String endpoint = "/users/patient/signup";
            URL url = new URL(host + endpoint);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Authorization", setAdminAuth());
            httpConnection.setDoOutput(true);
            httpConnection.getOutputStream().write(gson.toJson(patient).getBytes("utf-8"));
            int resultCode = httpConnection.getResponseCode();
            if(resultCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Not 200 ok resultCode");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void notifyResult(
            final Result<List<Patient>> result,
            final RepositoryCallback<List<Patient>> callback) {
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
