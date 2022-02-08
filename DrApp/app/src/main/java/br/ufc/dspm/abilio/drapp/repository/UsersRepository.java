package br.ufc.dspm.abilio.drapp.repository;

import android.os.Handler;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import br.ufc.dspm.abilio.drapp.model.Doctor;
import br.ufc.dspm.abilio.drapp.model.Patient;
import br.ufc.dspm.abilio.drapp.model.Result;
import br.ufc.dspm.abilio.drapp.model.Users;
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

    public void makeLoginRequest(Users user, final RepositoryCallback<Users> callback) {
        executor.execute(() -> {
            try {
                notifyResult(doLoginRequest(user), callback);
            } catch (Exception e) {
                Result<Users> errorResult = new Result.Error<>(e);
                notifyResult(errorResult, callback);
            }
        });
    }

    private Result<Users> doLoginRequest(Users user) throws IOException {
        try {
            String endpoint = "/users/login";
            URL url = new URL(host + endpoint);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Authorization", setAdminAuth());
            httpConnection.setDoOutput(true);
            httpConnection.getOutputStream().write(gson.toJson(user).getBytes("utf-8"));
            int resultCode = httpConnection.getResponseCode();
            if(resultCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Not 200 ok resultCode");
            }
            Users userReturned = responseParser.parseUser(httpConnection.getInputStream());
            return new Result.Success<>(userReturned);
        } catch (Exception e) {
            throw e;
        }
    }

    public void makeGetPatient(Users user, final RepositoryCallback<Users> callback) {
        executor.execute(() -> {
            try {
                notifyResult(doGetPatientRequest(user), callback);
            } catch (Exception e) {
                Result<Users> errorResult = new Result.Error<>(e);
                notifyResult(errorResult, callback);
            }
        });
    }

    private Result<Users> doGetPatientRequest(Users user) throws IOException {
        try {
            String endpoint = "/users/patient/" + user.getUsername();
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
            Patient patientReturned = responseParser.parsePatient(httpConnection.getInputStream());
            return new Result.Success<>(patientReturned);
        } catch (Exception e) {
            throw e;
        }
    }

    public void makeGetDoctor(Users user, final RepositoryCallback<Users> callback) {
        executor.execute(() -> {
            try {
                notifyResult(doGetDoctorRequest(user), callback);
            } catch (Exception e) {
                Result<Users> errorResult = new Result.Error<>(e);
                notifyResult(errorResult, callback);
            }
        });
    }

    private Result<Users> doGetDoctorRequest(Users user) throws IOException {
        try {
            String endpoint = "/users/doctor/" + user.getUsername();
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
            Doctor doctorReturned = responseParser.parseDoctor(httpConnection.getInputStream());
            return new Result.Success<>(doctorReturned);
        } catch (Exception e) {
            throw e;
        }
    }

    public void makeInsertPatientRequest(Patient patient, final RepositoryCallback<Users> callback) {
        executor.execute(() -> {
            try {
                doInsertPatientRequest(patient);
                notifyResult(new Result.Success<>(null), callback);
            } catch (Exception e) {
                Result<Users> errorResult = new Result.Error<>(e);
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

    public void makeInsertDoctorRequest(Doctor doctor, final RepositoryCallback<Users> callback) {
        executor.execute(() -> {
            try {
                doInsertDoctorRequest(doctor);
                notifyResult(new Result.Success<>(null), callback);
            } catch (Exception e) {
                Result<Users> errorResult = new Result.Error<>(e);
                notifyResult(errorResult, callback);
            }
        });
    }

    private void doInsertDoctorRequest(Doctor doctor) throws IOException {
        try {
            String endpoint = "/users/doctor/signup";
            URL url = new URL(host + endpoint);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Authorization", setAdminAuth());
            httpConnection.setDoOutput(true);
            httpConnection.getOutputStream().write(gson.toJson(doctor).getBytes("utf-8"));
            int resultCode = httpConnection.getResponseCode();
            if(resultCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("Not 200 ok resultCode");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private void notifyResult(
            final Result<Users> result,
            final RepositoryCallback<Users> callback) {
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
