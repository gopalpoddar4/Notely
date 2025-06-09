package com.gopalpoddar4.notely.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.CategoryFiles.CategoryAdapter;
import com.gopalpoddar4.notely.activities.CategoryFiles.CategoryInterface;
import com.gopalpoddar4.notely.activities.CategoryFiles.CategoryModel;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_NOTE=1;
    ImageView AddNoteBtn,setting,addNewCategory;
    int noteFormatValue = 0;
    List<CategoryModel> categoryList;
    EditText addCategoryET;
    Button addCategoryBtn;
    String Category;
    int getNum;
    SearchView searchNoteET;
    private AddNoteViewModel addNoteViewModel;
    RecyclerView recyclerView,rcvCategory;
    NoteAdapter noteAdapter;
    LinearLayout gridLayout,linearLayout,newToOld,oldToNew;
    ImageView gridImage,linearImage,NTOImage,OTNImage;
    TextView gridText,linearText,NTOText,OTNText,cancelSetting,doneSetting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        categoryList=new ArrayList<>();
        categoryList.add(new CategoryModel("All"));
        categoryList.add(new CategoryModel("Shayri"));
        categoryList.add(new CategoryModel("College Task"));
        categoryList.add(new CategoryModel("Contect"));
        categoryList.add(new CategoryModel("Youtube"));
        categoryList.add(new CategoryModel("Instagram"));
        categoryList.add(new CategoryModel("Coding"));



        addNoteViewModel=new ViewModelProvider(this).get(AddNoteViewModel.class);

        //finding views of id
        AddNoteBtn=findViewById(R.id.addNoteButton);
        recyclerView=findViewById(R.id.recycleView);
        searchNoteET=findViewById(R.id.searchEditText);
        setting=findViewById(R.id.setting);
        rcvCategory=findViewById(R.id.categoryRcv);
        addNewCategory=findViewById(R.id.addNewCategory);

        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcategory();
            }
        });

        rcvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList, this, addNoteViewModel, new CategoryInterface() {
            @Override
            public void onItemClick(String categoryName) {
                addNoteViewModel.categoryNote(categoryName).observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                    @Override
                    public void onChanged(List<NoteEntity> noteEntities) {
                        addNoteViewModel.getValue().observe(MainActivity.this, new Observer<Integer>() {
                            @Override
                            public void onChanged(Integer integer) {
                                addNoteViewModel.categoryNote(categoryName).observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                                    @Override
                                    public void onChanged(List<NoteEntity> noteEntities) {
                                        noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel,categoryList);
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setAdapter(null);
                                        recyclerView.setLayoutManager(null);
                                        if (integer==0){
                                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                                        }else {
                                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                        }
                                        recyclerView.setAdapter(noteAdapter);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        rcvCategory.setAdapter(categoryAdapter);
        searchNoteET.setQueryHint("Search Notes");

        //Setting button function
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingDailog();
            }
        });

        SharedPreferences preferences =getSharedPreferences("note_format",MODE_PRIVATE);
        getNum=preferences.getInt("format",0);
        if (getNum==0){
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        }else {
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        }
        addNoteViewModel.setValue(getNum);
        addNoteViewModel.getValue().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                addNoteViewModel.getAllNotes().observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                    @Override
                    public void onChanged(List<NoteEntity> noteEntities) {
                        noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel,categoryList);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setAdapter(null);
                        recyclerView.setLayoutManager(null);
                        if (integer==0){
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                        }else {
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                        }
                        recyclerView.setAdapter(noteAdapter);
                    }
                });
            }
        });

        //Search activity function
         searchNoteET.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String query) {
                 return false;
             }

             @Override
             public boolean onQueryTextChange(String query) {
                 addNoteViewModel.searchNote(query).observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                     @Override
                     public void onChanged(List<NoteEntity> noteEntities) {
                         noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel,categoryList);
                         recyclerView.setHasFixedSize(true);
                         recyclerView.setAdapter(null);
                         recyclerView.setLayoutManager(null);

                         addNoteViewModel.getValue().observe(MainActivity.this, new Observer<Integer>() {
                             @Override
                             public void onChanged(Integer integer) {
                                 if (integer==0){
                                     recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                                 }else {
                                     recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                 }
                             }
                         });

                         recyclerView.setAdapter(noteAdapter);
                     }
                 });
                 return true;
             }
         });

        // Add button function
        AddNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ADD_NOTE);
            }
        });

    }
    //Showing the alert dialog for setting
    private void showSettingDailog(){
        LinearLayout linearLayout1 = findViewById(R.id.settingLayout);
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.setting_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, com.google.android.material.R.style.ThemeOverlay_Material3_Dialog);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();

        gridLayout=view.findViewById(R.id.gridLayout);
        gridImage=view.findViewById(R.id.gridImg);
        gridText=view.findViewById(R.id.gridText);


        gridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridLayout.setBackgroundResource(R.drawable.format_bg);
                linearLayout.setBackgroundResource(android.R.color.transparent);
                noteFormatValue=0;
            }
        });
        linearLayout=view.findViewById(R.id.linearLayout);
        linearImage=view.findViewById(R.id.linearImg);
        linearText=view.findViewById(R.id.linearText);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setBackgroundResource(R.drawable.format_bg);
                gridLayout.setBackgroundResource(android.R.color.transparent);
                noteFormatValue=1;
            }
        });

        addNoteViewModel.getValue().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==0){
                    gridLayout.setBackgroundResource(R.drawable.format_bg);
                    linearLayout.setBackgroundResource(android.R.color.transparent);
                }else {
                    linearLayout.setBackgroundResource(R.drawable.format_bg);
                    gridLayout.setBackgroundResource(android.R.color.transparent);
                }
            }
        });



        oldToNew=view.findViewById(R.id.oldToNew);
        OTNImage=view.findViewById(R.id.OTNImg);
        OTNText=view.findViewById(R.id.OTNText);

        newToOld=view.findViewById(R.id.newToOld);
        NTOImage=view.findViewById(R.id.NTOImg);
        NTOText=view.findViewById(R.id.NTOText);

        cancelSetting=view.findViewById(R.id.cancelSetting);
        cancelSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        doneSetting=view.findViewById(R.id.doneSetting);
        doneSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = getSharedPreferences("note_format",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("format",noteFormatValue);
                editor.apply();
                addNoteViewModel.setValue(noteFormatValue);
                alertDialog.dismiss();
            }
        });

        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    //Add new category function
    private void addcategory(){

        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_category_dialog,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, com.google.android.material.R.style.ThemeOverlay_Material3_Dialog);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();

        addCategoryET=view.findViewById(R.id.newCategoryET);
        addCategoryBtn=view.findViewById(R.id.addCategory);

        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category = addCategoryET.getText().toString();
                if (Category.isEmpty()){
                    alertDialog.dismiss();
                    Toast.makeText(MainActivity.this, R.string.soon, Toast.LENGTH_SHORT).show();
                }else{
                    alertDialog.dismiss();
                    Toast.makeText(MainActivity.this, R.string.soon, Toast.LENGTH_SHORT).show();
                }
            }
        });


        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ADD_NOTE && resultCode==RESULT_OK){
            String title = data.getStringExtra("titleadd");
            String desc = data.getStringExtra("descadd");
            String datetime = data.getStringExtra("dateadd");
            String color = data.getStringExtra("color");
            Boolean pinned = data.getBooleanExtra("pin", false);
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setTitle(title);
            noteEntity.setNoteDescription(desc);
            noteEntity.setDateTime(datetime);
            noteEntity.setColour(color);
            noteEntity.setPinned(pinned);
            noteEntity.setCategory("All");
            noteEntity.setAllCategory("All");

            addNoteViewModel.insert(noteEntity);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }

    }
}