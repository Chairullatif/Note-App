package com.khoirullatif.mynoteappsjetpack;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.khoirullatif.mynoteappsjetpack.database.Note;
import com.khoirullatif.mynoteappsjetpack.databinding.ItemNoteBinding;
import com.khoirullatif.mynoteappsjetpack.helper.NoteDiffCallback;
import com.khoirullatif.mynoteappsjetpack.ui.insert.NoteAddUpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private final ArrayList<Note> listNotes = new ArrayList<>();
    private final Activity activity;

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListNotes(List<Note> listNotes) {
        final NoteDiffCallback diffCallback = new NoteDiffCallback(this.listNotes, listNotes);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.listNotes.clear();
        this.listNotes.addAll(listNotes);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(listNotes.get(position));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        final ItemNoteBinding binding;

        NoteViewHolder(@NonNull ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Note note) {
            binding.tvItemTitle.setText(listNotes.get(getAdapterPosition()).getTitle());
            binding.tvItemDate.setText(listNotes.get(getAdapterPosition()).getDate());
            binding.tvItemDescription.setText(listNotes.get(getAdapterPosition()).getDescription());

            binding.cvItemNote.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), NoteAddUpdateActivity.class);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, getAdapterPosition());
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note);
                activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE);
            });
        }
    }
}
