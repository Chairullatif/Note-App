package com.khoirullatif.mynoteappsjetpack.ui.main;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.khoirullatif.mynoteappsjetpack.database.Note;
import com.khoirullatif.mynoteappsjetpack.repository.NoteRepository;

public class MainViewModel extends ViewModel {
    private NoteRepository mNoteRepository;

    public MainViewModel(Application application) {
        mNoteRepository = new NoteRepository(application);
    }

    LiveData<PagedList<Note>> getAllNotes(String sort) {
//        return mNoteRepository.getAllNotes();
        return new LivePagedListBuilder<>(mNoteRepository.getAllNotes(sort), 20).build();
    }
}
