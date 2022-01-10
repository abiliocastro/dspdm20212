package br.ufc.dspm.abilio.trabalho3_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import br.ufc.dspm.abilio.trabalho3_1.R;
import br.ufc.dspm.abilio.trabalho3_1.model.Book;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, ArrayList<Book> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Book book = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_item, parent, false);
        }
        // Lookup view for data population
        TextView tvId = convertView.findViewById(R.id.tvId);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvNumOfPages = convertView.findViewById(R.id.tvNumOfPages);
        // Populate the data into the template view using the data object
        tvId.setText(book.getScreenId());
        tvTitle.setText(book.getTitle());
        tvNumOfPages.setText(book.getNumberOfPages());
        // Return the completed view to render on screen
        return convertView;
    }
}