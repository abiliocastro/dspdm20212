package br.ufc.dspm.abilio.provafinal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import br.ufc.dspm.abilio.provafinal.R;
import br.ufc.dspm.abilio.provafinal.model.Livro;

public class LivroAdapter extends ArrayAdapter<Livro> {
    public LivroAdapter(@NonNull Context context, int resource, @NonNull List<Livro> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Livro livro = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.livro_item, parent, false);
        }
        // Lookup view for data population
        TextView idTextView = convertView.findViewById(R.id.idTextView);
        TextView tituloTextView = convertView.findViewById(R.id.tituloTextView);
        TextView autorTextView = convertView.findViewById(R.id.autorTextView);
        TextView editoraTextView = convertView.findViewById(R.id.editoraTextView);
        TextView anoPublicacaoTextView = convertView.findViewById(R.id.anoPublicacaoTextView);
        TextView isbnTextView = convertView.findViewById(R.id.isbnTextView);
        // Populate the data into the template view using the data object
        idTextView.setText(livro.getId());
        tituloTextView.setText(livro.getTitulo());
        autorTextView.setText(livro.getAutor());
        editoraTextView.setText(livro.getEditora());
        anoPublicacaoTextView.setText(livro.getAnoPublicacao());
        isbnTextView.setText(livro.getIsbn());
        // Return the completed view to render on screen
        return convertView;
    }
}
