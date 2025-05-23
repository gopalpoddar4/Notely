package com.gopalpoddar4.notely.activities.CategoryFiles;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void addCategory(CategoryModel categoryModel);

    @Update
    void updateCategory(CategoryModel categoryModel);

    @Delete
    void deleteCategory(CategoryModel categoryModel);

    @Query("SELECT * FROM category_table")
    LiveData<List<CategoryModel>> showCategory();
}
