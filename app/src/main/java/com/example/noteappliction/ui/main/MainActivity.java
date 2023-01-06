package com.example.noteappliction.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.example.noteappliction.R;
import com.example.noteappliction.data.databases.AppDatabase;
import com.example.noteappliction.data.model.Note;
import com.example.noteappliction.databinding.ActivityMainBinding;
import com.example.noteappliction.ui.add.AddFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity  implements MainContract.View {
    ActivityMainBinding binding;
    MainContract.Presenter presenter;
    AdapterNote adapterNote;
    int selectedItem=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        presenter = new MainPresenter(AppDatabase.getAppDatabase(MainActivity.this).getNoteDao(),this);
        binding.addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AddFragment().show(getSupportFragmentManager(),new AddFragment().getTag());
            }
        });
        presenter.onAttach();
    }


    @Override
    public void showNotes(List<Note> noteList) {
        binding.emptyLayout.setVisibility(View.GONE);
        binding.noteList.setVisibility(View.VISIBLE);
        adapterNote = new AdapterNote(MainActivity.this, noteList, new AdapterNote.OnMenuItemClick() {
            @Override
            public void OnDeleteItemClick(Note note) {
                presenter.onDeletePopUpItemClick(note);
            }

            @Override
            public void OnEditItemClick(Note note) {
                Bundle bundle = new Bundle();
                bundle.putInt("NoteId",note.getId());
                AddFragment addFragment = new AddFragment();
                addFragment.setArguments(bundle);
                addFragment.show(getSupportFragmentManager(),addFragment.getTag());

            }
        });
        binding.noteList.setAdapter(adapterNote);
        binding.noteList.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

    }

    @Override
    public void showEmpty() {
        binding.emptyLayout.setVisibility(View.VISIBLE);
        binding.noteList.setVisibility(View.GONE);
    }

    @Override
    public void showMessageDelete() {
        Snackbar.make(binding.root,"delete successfully",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select Priority");
        String[] priorityList = new String[]{"All","High","Normal","Low"};
        builder.setSingleChoiceItems(priorityList, selectedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i){
                    case 0 :{
                        presenter.onAttach();
                        break;
                    }
                    case 1:
                    case 2:
                    case 3: {
                        presenter.filterNote(priorityList[i]);
                        break;
                    }
                }
                dialogInterface.dismiss();
                selectedItem=i;

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.filter :
            {
                presenter.onFilterBtnClick();
            }
            case R.id.search:
            {
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("search ...");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        presenter.searchNotes(newText);
                        return true;
                    }
                });
            }

        }
        return true;
    }
}