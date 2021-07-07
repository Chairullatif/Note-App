package com.khoirullatif.mynoteappsjetpack.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.snackbar.Snackbar;
import com.khoirullatif.mynoteappsjetpack.R;
import com.khoirullatif.mynoteappsjetpack.database.Note;
import com.khoirullatif.mynoteappsjetpack.databinding.ActivityMainBinding;
import com.khoirullatif.mynoteappsjetpack.helper.SortUtils;
import com.khoirullatif.mynoteappsjetpack.helper.ViewModelFactory;
import com.khoirullatif.mynoteappsjetpack.ui.NotePagedListAdapter;
import com.khoirullatif.mynoteappsjetpack.ui.insert.NoteAddUpdateActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NotePagedListAdapter adapter;
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainViewModel = obtainViewModel(MainActivity.this);
        mainViewModel.getAllNotes(SortUtils.NEWEST).observe(this, noteObserver);

        adapter = new NotePagedListAdapter(MainActivity.this);

        binding.rvNotes.setLayoutManager(new LinearLayoutManager(this));
        binding.rvNotes.setHasFixedSize(true);
        binding.rvNotes.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(view -> {
            if (view.getId() == R.id.fab_add) {
                Intent intent = new Intent(MainActivity.this, NoteAddUpdateActivity.class);
                startActivityForResult(intent, NoteAddUpdateActivity.REQUEST_ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == NoteAddUpdateActivity.REQUEST_ADD) {
                if (requestCode == NoteAddUpdateActivity.REQUEST_ADD) {
                    showSnackbarMessage(getString(R.string.added));
                }
            } else if (requestCode == NoteAddUpdateActivity.REQUEST_UPDATE) {
                if (resultCode == NoteAddUpdateActivity.RESULT_UPDATE) {
                    showSnackbarMessage(getString(R.string.changed));
                } else if (resultCode == NoteAddUpdateActivity.RESULT_DELETE) {
                    showSnackbarMessage(getString(R.string.deleted));
                }
            }
        }
    }

    @NonNull
    private static MainViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return new ViewModelProvider(activity, factory).get(MainViewModel.class);
    }

    private final Observer<PagedList<Note>> noteObserver = new Observer<PagedList<Note>>() {
        @Override
        public void onChanged(@NonNull PagedList<Note> notes) {
            if (notes != null) {
                adapter.submitList(notes);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String sort = "";
        switch (item.getItemId()) {
            case R.id.action_newest:
                sort = SortUtils.NEWEST;
                break;
            case R.id.action_oldest:
                sort = SortUtils.OLDEST;
                break;
            case R.id.action_random:
                sort = SortUtils.RANDOM;
                break;
        }
        mainViewModel.getAllNotes(sort).observe(this, noteObserver);
        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_SHORT).show();
    }
}