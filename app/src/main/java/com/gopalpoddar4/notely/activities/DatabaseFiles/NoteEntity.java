package com.gopalpoddar4.notely.activities.DatabaseFiles;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="note_table")
public class NoteEntity {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "title")
    String title;
    @ColumnInfo(name = "date_time")
    String dateTime;
    @ColumnInfo(name = "note_description")
    String noteDescription;
    @ColumnInfo(name = "image_path")
    String imagePath;
    @ColumnInfo(name = "colour")
    String colour;
    @ColumnInfo(name = "link")
    String link;
    @ColumnInfo(name = "pinned")
    boolean isPinned;



    @NonNull
    @ColumnInfo(name = "category",defaultValue = "All")
    String category;

    @NonNull
    @ColumnInfo(name = "allcategory",defaultValue = "All")
    String allCategory;

    public boolean isPinned() {
        return isPinned;
    }


    public void setPinned(boolean pinned) {
        isPinned = pinned;
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @NonNull
    public String getAllCategory() {
        return allCategory;
    }

    public void setAllCategory(@NonNull String allCategory) {
        this.allCategory = allCategory;
    }



}
