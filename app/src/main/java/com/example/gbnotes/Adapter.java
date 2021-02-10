package com.example.gbnotes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final static String TAG = "Adapter";
    private NotesSource dataSource;
    private ClickListener clickListener;

    public Adapter(NotesSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setOnItemClickListener(ClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        Log.d(TAG, "onCreateViewHolder");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(dataSource.getNote(position));
        Log.d(TAG, "onBindViewHolder");
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface ClickListener {
        void onItemClick(int position, Note note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);

            itemView.setOnClickListener(view -> {
                if (clickListener != null) {
                    int position = getAdapterPosition();
                    clickListener.onItemClick(position, dataSource.getNote(position));
                }
            });
        }

        public void onBind(Note note) {
            title.setText(note.getHeadLine());
            date.setText(note.getDate());
        }
    }
}
