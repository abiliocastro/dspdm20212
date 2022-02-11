package br.ufc.dspm.abilio.drapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import java.util.ArrayList;

import br.ufc.dspm.abilio.drapp.AppContainer;
import br.ufc.dspm.abilio.drapp.MyApplication;
import br.ufc.dspm.abilio.drapp.R;
import br.ufc.dspm.abilio.drapp.adapter.DoctorAdapter;
import br.ufc.dspm.abilio.drapp.model.Doctor;
import br.ufc.dspm.abilio.drapp.model.Patient;
import br.ufc.dspm.abilio.drapp.model.Users;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    ArrayList<Doctor> doctorArrayList = new ArrayList<>();
    DoctorAdapter doctorAdapter;
    ListView listView;
    TextView tvNoPatient;
    Users user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new HomeViewModel(getActivity().getApplication());
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        doctorAdapter = new DoctorAdapter(getActivity(), R.layout.fragment_home, doctorArrayList);
        listView = root.findViewById(R.id.lvDoctors);
        listView.setAdapter(doctorAdapter);

        tvNoPatient = root.findViewById(R.id.tvSemPaciente);

        user = homeViewModel.getUser();
        if(user != null && user instanceof Patient) {
            tvNoPatient.setVisibility(View.GONE);
            homeViewModel.getDoctors().observe(getViewLifecycleOwner(), doctors -> {
                doctorArrayList.addAll(doctors);
                doctorAdapter.notifyDataSetChanged();
            });
        } else if(user != null && user instanceof Doctor) {
            tvNoPatient.setVisibility(View.VISIBLE);
        }

        return root;
    }
}