package com.example.noteappliction.ui.add;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.noteappliction.R;
import com.example.noteappliction.data.databases.AppDatabase;
import com.example.noteappliction.data.databases.AppDatabase_Impl;
import com.example.noteappliction.data.model.Note;
import com.example.noteappliction.databinding.FragmentAddBinding;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class AddFragment extends BottomSheetDialogFragment implements AddContract.View {

    AddContract.presenter presenter;
    List<String> categoryList;
    List<String> priorityList;
    String category;
    String priority;
    int noteId=0;
    String type;
    FragmentAddBinding binding;
    public AddFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddBinding.inflate(getLayoutInflater());
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null){
            noteId = getArguments().getInt("NoteId",0);
        }
        if (noteId>0){
            type="edit";
        }
        else
        {
            type="new";
        }

        presenter = new AddPresenter(AppDatabase.getAppDatabase(getContext()).getNoteDao(),this);
        binding.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddFragment.this.dismiss();
            }
        });
        categoriesSpinnerItem();
        prioritySpinnerItem();

        if (type.equals("edit")){
            presenter.getDetailNote(noteId);
        }

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = binding.titleEdt.getText().toString();
                String desc = binding.edtDesc.getText().toString();
                Note note = new Note();
                note.setId(noteId);
                note.setTitle(title);
                note.setDesc(desc);
                note.setCategory(category);
                note.setPriority(priority);
                if (type.equals("edit")){
                    presenter.updateNote(note);
                }
                else {
                    presenter.onSaveBtnClick(note);
                }

            }
        });
    }



    private void categoriesSpinnerItem(){
        categoryList = new ArrayList();
        categoryList.add("Work");
        categoryList.add("Home");
        categoryList.add("Education");
        categoryList.add("Health");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,categoryList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.categoriesSpinner.setAdapter(adapter);
        binding.categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = categoryList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void prioritySpinnerItem(){
        priorityList = new ArrayList();
        priorityList.add("High");
        priorityList.add("Normal");
        priorityList.add("Low");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item,priorityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.prioritySpinner.setAdapter(adapter);
        binding.prioritySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priority = priorityList.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void close() {
        AddFragment.this.dismiss();
        Toast.makeText(getContext(),"save",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showDetailNote(Note note) {
        if (this.isAdded()){
            requireActivity().runOnUiThread(() -> {
                binding.titleEdt.setText(note.getTitle());
                binding.edtDesc.setText(note.getDesc());
                binding.categoriesSpinner.setSelection(getIndex(categoryList,note.getCategory()));
                binding.prioritySpinner.setSelection(getIndex(priorityList,note.getPriority()));

            });
        }
    }

    private int getIndex(List<String> list,String item){
        int index=0;
        for (int i=0;i<list.size();i++){
            if (list.get(i).equals(item)){
                index = i;
                break;
            }
        }
        return index;
    }


}