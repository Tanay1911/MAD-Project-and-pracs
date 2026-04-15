package com.example.prac15;

import android.content.*;
import android.view.*;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.*;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    List<NoteModel> list;
    Context context;

    public NotesAdapter(Context context, List<NoteModel> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        public ViewHolder(View v) {
            super(v);
            text = v.findViewById(android.R.id.text1);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NoteModel note = list.get(position);
        holder.text.setText(note.title);

        // DELETE on long press
        holder.itemView.setOnLongClickListener(v -> {
            context.getContentResolver().delete(
                    NotesProvider.URI,
                    "id=?",
                    new String[]{String.valueOf(note.id)}
            );
            list.remove(position);
            notifyDataSetChanged();
            return true;
        });

        // EDIT on click
        holder.itemView.setOnClickListener(v -> {
            android.app.AlertDialog.Builder builder =
                    new android.app.AlertDialog.Builder(context);

            android.widget.EditText input = new android.widget.EditText(context);
            input.setText(note.title);

            builder.setTitle("Edit Note");
            builder.setView(input);

            builder.setPositiveButton("Update", (d, w) -> {
                ContentValues cv = new ContentValues();
                cv.put("title", input.getText().toString());

                context.getContentResolver().update(
                        NotesProvider.URI,
                        cv,
                        "id=?",
                        new String[]{String.valueOf(note.id)}
                );

                list.get(position).title = input.getText().toString();
                notifyDataSetChanged();
            });

            builder.setNegativeButton("Cancel", null);
            builder.show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
