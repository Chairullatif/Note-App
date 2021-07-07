package com.khoirullatif.mynoteappsjetpack.ui.insert;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.khoirullatif.mynoteappsjetpack.database.Note;
import com.khoirullatif.mynoteappsjetpack.repository.NoteRepository;

public class NoteAddUpdateViewModel extends ViewModel {

    // KELAS INI DIGUNAKAN UNTUK MENGHUBUNGKAN ACTIVITY DENGAN REPOSITORY

    private NoteRepository mNoteRepository;

    public NoteAddUpdateViewModel(Application application) {
        mNoteRepository = new NoteRepository(application);
    }

    public void insert(Note note) {
        mNoteRepository.insert(note);
    }

    public void update(Note note) {
        mNoteRepository.update(note);
    }

    public void delete(Note note) {
        mNoteRepository.delete(note);
    }
}
