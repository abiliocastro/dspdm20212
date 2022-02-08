package br.ufc.dspm.abilio.drapp.parser;

import java.io.InputStream;
import java.io.InputStreamReader;

import br.ufc.dspm.abilio.drapp.model.Doctor;
import br.ufc.dspm.abilio.drapp.model.Patient;
import br.ufc.dspm.abilio.drapp.model.Users;

public class UsersParser extends Parser {

    public UsersParser() {
        super();
    }

    public Users parseUser(InputStream inputStream) {
        InputStreamReader isr = new InputStreamReader(inputStream);
        Users user = gson.fromJson(isr, Users.class);
        return user;
    }

    public Patient parsePatient(InputStream inputStream) {
        InputStreamReader isr = new InputStreamReader(inputStream);
        Patient patient = gson.fromJson(isr, Patient.class);
        return patient;
    }

    public Doctor parseDoctor(InputStream inputStream) {
        InputStreamReader isr = new InputStreamReader(inputStream);
        Doctor doctor = gson.fromJson(isr, Doctor.class);
        return doctor;
    }
}
