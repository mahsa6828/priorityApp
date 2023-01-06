package com.example.noteappliction.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NOTE_TABLE")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String desc;
    private String category;
    private String priority;

    public Note(){

    }

    public Note(String title,String desc,String category,String priority){
        this.title=title;
        this.desc=desc;
        this.category=category;
        this.priority=priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


}
