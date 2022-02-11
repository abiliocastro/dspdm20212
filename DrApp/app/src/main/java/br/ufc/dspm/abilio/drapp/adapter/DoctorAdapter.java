package br.ufc.dspm.abilio.drapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import br.ufc.dspm.abilio.drapp.R;
import br.ufc.dspm.abilio.drapp.model.Doctor;

public class DoctorAdapter extends ArrayAdapter<Doctor> {


    public DoctorAdapter(@NonNull Context context, int resource, @NonNull List<Doctor> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Doctor doctor = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.doctor_item, parent, false);
        }

        TextView tvNome = convertView.findViewById(R.id.tvItemNomeDoctor);
        TextView tvEspecialidade = convertView.findViewById(R.id.tvItemEspecialidadeDoctor);

        tvNome.setText(doctor.getNome());
        tvEspecialidade.setText(doctor.getEspecialidade());

        return convertView;
    }
}
