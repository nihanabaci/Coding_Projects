package com.nabaci.me.multinotes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NoteViewHolder> {

    private List<Note> noteList;
    private MainActivity mainAct;

    public NotesAdapter(List<Note> empList, MainActivity ma) {
        this.noteList = empList;
        mainAct = ma;
    }

    @Override
    public NoteViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_list_row, parent, false);

        itemView.setOnClickListener(mainAct);
        itemView.setOnLongClickListener(mainAct);

        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.tvTitle.setText(note.getTitle());
        holder.tvTime.setText(note.getTime());

        if (note.getText().length() >= 80) {
            holder.tvText.setText(note.getText().substring(0,80) + "...");
        } else {
            holder.tvText.setText(note.getText());
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

}
