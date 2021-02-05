package com.example.gbnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private Note[] dataSource;
    private ClickListener ClickListener;

    public Adapter(Note[] dataSource) {
        this.dataSource = dataSource;
    }

    public void setOnItemClickListener(ClickListener itemClickListener) {
        ClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(dataSource[position]);
    }

    @Override
    public int getItemCount() {
        return dataSource.length;
    }

    public interface ClickListener {
        void onItemClick(int position, Note note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                ClickListener.onItemClick(position, dataSource[position]);
            });
        }

        public void onBind(Note note) {
            textView.setText(note.getHeadLine());
        }
    }
}
