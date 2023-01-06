package com.example.noteappliction.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteappliction.R;
import com.example.noteappliction.data.model.Note;
import com.example.noteappliction.databinding.RcRowBinding;

import java.util.List;

public class AdapterNote extends RecyclerView.Adapter<AdapterNote.AdapterHolder> {
    RcRowBinding binding;
    Context context;
    List<Note> noteList;
    OnMenuItemClick onMenuItemClick;
    public AdapterNote(Context context,List<Note> noteList,OnMenuItemClick onMenuItemClick){
        this.context = context;
        this.noteList = noteList;
        this.onMenuItemClick=onMenuItemClick;

    }
    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = RcRowBinding.inflate(layoutInflater,parent,false);
        return new AdapterHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
        Note note = noteList.get(position);
        binding.txtTitle.setText(note.getTitle());
        binding.txtdesc.setText(note.getDesc());
        switch (note.getPriority()){
            case "High" :
                {
                binding.priorityColor.setBackgroundColor(ContextCompat.getColor(context,R.color.red));
                break;
            }
            case "Normal" :
                {
                    binding.priorityColor.setBackgroundColor(ContextCompat.getColor(context,R.color.yellow));
                  break;
                }
            case "Low" : {
                binding.priorityColor.setBackgroundColor(ContextCompat.getColor(context,R.color.aqua));
                break;
                 }
        }
        switch (note.getCategory()){
            case "Work" : {
                binding.imgCategory.setImageResource(R.drawable.work);
                break;
            }
            case "Education" : {
                binding.imgCategory.setImageResource(R.drawable.education);
                break;
            }
            case "Health" : {
                binding.imgCategory.setImageResource(R.drawable.healthcare);
                break;
            }
            case "Home" : {
                binding.imgCategory.setImageResource(R.drawable.home);
                break;
            }
        }
        binding.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete :
                            {
                                onMenuItemClick.OnDeleteItemClick(note);
                                break;
                            }
                            case R.id.edit:
                            {
                                onMenuItemClick.OnEditItemClick(note);
                                break;
                            }
                        }
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{
        TextView txtTitle,txtdesc;
        View view_priority;
        ImageView img_category;

        public AdapterHolder(@NonNull RcRowBinding itemView) {
            super(binding.getRoot());
//            txtTitle =  binding.txtTitle;
//            txtdesc = binding.txtdesc;
//            view_priority = binding.priorityColor;
//            img_category = binding.imgCategory;


        }
    }

    public interface OnMenuItemClick{
        void OnDeleteItemClick(Note note);
        void OnEditItemClick(Note note);

    }
}
