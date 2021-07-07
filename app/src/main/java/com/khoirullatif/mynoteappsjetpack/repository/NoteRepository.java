package com.khoirullatif.mynoteappsjetpack.repository;

import android.app.Application;

import androidx.paging.DataSource;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.khoirullatif.mynoteappsjetpack.database.Note;
import com.khoirullatif.mynoteappsjetpack.database.NoteDao;
import com.khoirullatif.mynoteappsjetpack.database.NoteRoomDatabase;
import com.khoirullatif.mynoteappsjetpack.helper.SortUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteRepository {
    private NoteDao mNotesDao;
    private ExecutorService executorService;

    public NoteRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        NoteRoomDatabase database = NoteRoomDatabase.getDatabase(application);
        mNotesDao = database.noteDao();
    }

//    public LiveData<List<Note>> getAllNotes() {
//        return mNotesDao.getAllNotes();
//    }

    public DataSource.Factory<Integer, Note> getAllNotes(String sort) {
        SimpleSQLiteQuery query = SortUtils.getSortedQuery(sort);
        return mNotesDao.getAllNotes(query);
    }

    public void insert(final Note note) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNotesDao.insert(note);
            }
        });
    }

    public void delete(final Note note) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNotesDao.delete(note);
            }
        });
    }

    public void update(final Note note) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                mNotesDao.update(note);
            }
        });
    }
}
