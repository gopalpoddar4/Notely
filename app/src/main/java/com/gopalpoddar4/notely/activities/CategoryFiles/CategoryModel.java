package com.gopalpoddar4.notely.activities.CategoryFiles;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "category_table")
public class CategoryModel {
    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "category_name")
    String categoryName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryModel(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public CategoryModel(String categoryName) {
        this.categoryName = categoryName;
    }


    public String toString(){
        return categoryName;
    }
}
