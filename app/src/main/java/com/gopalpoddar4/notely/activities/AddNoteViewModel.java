package com.gopalpoddar4.notely.activities;

import android.app.Application;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gopalpoddar4.notely.activities.CategoryFiles.CategoryDao;
import com.gopalpoddar4.notely.activities.CategoryFiles.CategoryModel;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteDao;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteDatabase;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;

import java.util.List;

public class AddNoteViewModel extends AndroidViewModel {
    private NoteDao noteDao;
    private CategoryDao categoryDao;
    private NoteDatabase noteDatabase;
    LiveData<List<NoteEntity>> allNotes;
    LiveData<List<CategoryModel>> allCategory;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        noteDatabase=NoteDatabase.noteDatabase(application);
        noteDao=noteDatabase.noteDao();
        allNotes=noteDao.getallnotes();

        categoryDao=noteDatabase.categoryDao();
        allCategory=categoryDao.showCategory();
    }

    private MutableLiveData<Integer> value = new MutableLiveData<>();
    public LiveData<Integer> getValue(){
        return value;
    }
    public void setValue(int value1){
        value.setValue(value1);
    }
    public void insert(NoteEntity noteEntity){
        new InsertAsyncTask(noteDao).execute(noteEntity);
    }

    public void insertCategory(CategoryModel categoryModel){
        new InsertCategory(categoryDao).execute(categoryModel);
    }
    public void delete(NoteEntity noteEntity){
        new DeleteAsuncTask(noteDao).execute(noteEntity);
    }

    public void deleteCategory(CategoryModel categoryModel){
        new DeleteCategoryAsuncTask(categoryDao).execute(categoryModel);
    }

    private class DeleteCategoryAsuncTask extends AsyncTask<CategoryModel,Void,Void>{
        CategoryDao categoryDao;
        public DeleteCategoryAsuncTask(CategoryDao categoryDao) {
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(CategoryModel... categoryModels) {
            categoryDao.deleteCategory(categoryModels[0]);
            return null;
        }
    }


    LiveData<List<NoteEntity>> getAllNotes(){
        return allNotes;
    }
    LiveData<List<CategoryModel>> getAllCategory(){
        return allCategory;
    }
    LiveData<List<NoteEntity>> searchNote(String query){
        return noteDao.searchNote("%" + query + "%");
    }
    LiveData<List<NoteEntity>> categoryNote(String categoryQuery){
        return noteDao.categoryNote(categoryQuery);
    }
    public void update(NoteEntity noteEntity){
        new  UpdateAsyncTask(noteDao).execute(noteEntity);
    }
    private class UpdateAsyncTask extends AsyncTask<NoteEntity,Void,Void> {
        NoteDao mNoteDao;
        public UpdateAsyncTask(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.updateNote(noteEntities[0]);
            return null;
        }
    }
    private class DeleteAsuncTask extends AsyncTask<NoteEntity,Void,Void>{
        NoteDao mNoteDao;
        public DeleteAsuncTask(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.deleteNote(noteEntities[0]);
            return null;
        }
    }

    private class InsertAsyncTask extends AsyncTask<NoteEntity,Void,Void>{
        NoteDao mNoteDao;
        public InsertAsyncTask(NoteDao noteDao) {
            this.mNoteDao=noteDao;
        }
        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            noteDao.insertNote(noteEntities[0]);
            return null;
        }
    }

    private class InsertCategory extends AsyncTask<CategoryModel,Void,Void>{
        CategoryDao categoryDao;
        public InsertCategory(CategoryDao categoryDao){
            this.categoryDao = categoryDao;
        }

        @Override
        protected Void doInBackground(CategoryModel... categoryModels) {
            categoryDao.addCategory(categoryModels[0]);
            return null;
        }
    }

}
