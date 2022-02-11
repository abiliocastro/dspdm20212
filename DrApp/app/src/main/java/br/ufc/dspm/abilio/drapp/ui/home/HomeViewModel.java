package br.ufc.dspm.abilio.drapp.ui.home;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import br.ufc.dspm.abilio.drapp.HomeActivity;
import br.ufc.dspm.abilio.drapp.MyApplication;
import br.ufc.dspm.abilio.drapp.model.Doctor;
import br.ufc.dspm.abilio.drapp.model.Patient;
import br.ufc.dspm.abilio.drapp.model.Result;
import br.ufc.dspm.abilio.drapp.model.Users;
import br.ufc.dspm.abilio.drapp.repository.DoctorRepository;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<List<Doctor>> doctors;
    private MutableLiveData<List<Patient>> patients;
    private DoctorRepository doctorRepository;
    private Users user;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        doctorRepository = ((MyApplication) application).appContainer.doctorRepository;
        user = ((MyApplication) application).appContainer.user;
    }

    public LiveData<List<Doctor>> getDoctors() {
        if(doctors == null) {
            doctors = new MutableLiveData<>();
            doctorRepository.makeGetAllDoctors(result -> {
                if(result instanceof Result.Success) {
                    List<Doctor> doctorsRetrieved = ((Result.Success<List<Doctor>>) result).data;
                    if(!doctorsRetrieved.isEmpty()) {
                        doctors.setValue(doctorsRetrieved);
                    }
                }
            });
        }
        return doctors;
    }

    public Users getUser() {
        return user;
    }

    public LiveData<List<Patient>> getDoctorsPatient() {
        if(patients == null) {
            patients = new MutableLiveData<>();
            doctorRepository.makeGetAllDoctors(result -> {
                if(result instanceof Result.Success) {
                    List<Patient> doctorsRetrieved = new ArrayList<>();
                    if(!doctorsRetrieved.isEmpty()) {
                        patients.setValue(doctorsRetrieved);
                    }
                }
            });
        }
        return patients;
    }
}