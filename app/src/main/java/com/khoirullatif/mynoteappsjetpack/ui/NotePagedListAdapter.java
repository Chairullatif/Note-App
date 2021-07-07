package com.khoirullatif.mynoteappsjetpack.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.khoirullatif.mynoteappsjetpack.database.Note;
import com.khoirullatif.mynoteappsjetpack.databinding.ItemNoteBinding;
import com.khoirullatif.mynoteappsjetpack.ui.insert.NoteAddUpdateActivity;

public class NotePagedListAdapter extends PagedListAdapter<Note, NotePagedListAdapter.NoteViewHolder> {

    private final Activity activity;

    public NotePagedListAdapter(Activity activity) {
        super(DIFF_CALLBACK);
        this.activity = activity;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotePagedListAdapter.NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        final ItemNoteBinding binding;

        NoteViewHolder(ItemNoteBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Note note) {
            binding.tvItemTitle.setText(note.getTitle());
            binding.tvItemDate.setText(note.getDate());
            binding.tvItemDescription.setText(note.getDescription());

            binding.cvItemNote.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), NoteAddUpdateActivity.class);
                intent.putExtra(NoteAddUpdateActivity.EXTRA_POSITION, getAdapterPosition());
                intent.putExtra(NoteAddUpdateActivity.EXTRA_NOTE, note);
                activity.startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_UPDATE);
            });
        }
    }

    private static DiffUtil.ItemCallback<Note> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Note>() {
                @Override
                public boolean areItemsTheSame(@NonNull Note odlNote, @NonNull Note newNote) {
                    return odlNote.getTitle().equals(newNote.getTitle()) && odlNote.getDescription().equals(newNote.getDescription());
                }
                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull Note oldNote, @NonNull Note newNote) {
                    return oldNote.equals(newNote);
                }
            };

}
