package com.gopalpoddar4.notely.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_NOTE=1;
    ImageView AddNoteBtn,setting,addNewCategory,emptyNoteImg;
    int noteFormatValue = 0;
    List<CategoryModel> categoryList;
    EditText addCategoryET;
    Button addCategoryBtn;
    int getNum;
    SearchView searchNoteET;
    private AddNoteViewModel addNoteViewModel;
    RecyclerView recyclerView,rcvCategory;
    NoteAdapter noteAdapter;
    LinearLayout gridLayout,linearLayout,newToOld,oldToNew;
    ImageView gridImage,linearImage,NTOImage,OTNImage;
    TextView gridText,linearText,NTOText,OTNText,cancelSetting,doneSetting,addNoteTxt;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        requestNotificationPermission();


        sharedPreferences = getSharedPreferences("shared_prefs",MODE_PRIVATE);

        addNoteViewModel=new ViewModelProvider(this).get(AddNoteViewModel.class);

        Boolean firstTime = sharedPreferences.getBoolean("firstTime",true);

        if (firstTime){
            CategoryModel categoryModel = new CategoryModel();

            categoryModel.setCategoryName("All");
            addNoteViewModel.insertCategory(categoryModel);
            sharedPreferences.edit().putBoolean("firstTime",false).apply();
        }

        //finding views of id
        AddNoteBtn=findViewById(R.id.addNoteButton);
        recyclerView=findViewById(R.id.recycleView);
        searchNoteET=findViewById(R.id.searchEditText);
        setting=findViewById(R.id.setting);
        rcvCategory=findViewById(R.id.categoryRcv);
        addNewCategory=findViewById(R.id.addNewCategory);
        emptyNoteImg=findViewById(R.id.emptyNoteImg);
        addNoteTxt=findViewById(R.id.addNewNoteTxt);

        addNewCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcategory();
            }
        });

        rcvCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        addNoteViewModel.getAllCategory().observe(MainActivity.this, new Observer<List<CategoryModel>>() {
            @Override
            public void onChanged(List<CategoryModel> categoryModels) {
                CategoryAdapter categoryAdapter = new CategoryAdapter(categoryModels, MainActivity.this, addNoteViewModel, new CategoryInterface() {
                    @Override
                    public void onItemClick(String categoryName) {
                        addNoteViewModel.categoryNote(categoryName).observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                            @Override
                            public void onChanged(List<NoteEntity> noteEntities) {
                                if (noteEntities.isEmpty()){
                                    emptyNoteImg.setVisibility(VISIBLE);
                                    addNoteTxt.setVisibility(VISIBLE);
                                    addNoteViewModel.getValue().observe(MainActivity.this, new Observer<Integer>() {
                                        @Override
                                        public void onChanged(Integer integer) {
                                            addNoteViewModel.categoryNote(categoryName).observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                                                @Override
                                                public void onChanged(List<NoteEntity> noteEntities) {
                                                    noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel,categoryModels);
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
                                }else {
                                    emptyNoteImg.setVisibility(GONE);
                                    addNoteTxt.setVisibility(GONE);

                                    addNoteViewModel.getValue().observe(MainActivity.this, new Observer<Integer>() {
                                        @Override
                                        public void onChanged(Integer integer) {
                                            addNoteViewModel.categoryNote(categoryName).observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                                                @Override
                                                public void onChanged(List<NoteEntity> noteEntities) {
                                                    noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel,categoryModels);
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

                            }
                        });
                    }
                });
                rcvCategory.setAdapter(categoryAdapter);
            }
        });


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
                addNoteViewModel.getAllCategory().observe(MainActivity.this, new Observer<List<CategoryModel>>() {
                    @Override
                    public void onChanged(List<CategoryModel> categoryModels) {
                        addNoteViewModel.getAllNotes().observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                            @Override
                            public void onChanged(List<NoteEntity> noteEntities) {
                                if (noteEntities.isEmpty()){
                                    emptyNoteImg.setVisibility(VISIBLE);
                                    addNoteTxt.setVisibility(VISIBLE);
                                    noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel,categoryModels);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setAdapter(null);
                                    recyclerView.setLayoutManager(null);
                                    if (integer==0){
                                        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                                    }else {
                                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                                    }
                                    recyclerView.setAdapter(noteAdapter);
                                }else {
                                    emptyNoteImg.setVisibility(GONE);
                                    addNoteTxt.setVisibility(GONE);
                                    noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel,categoryModels);
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
                            }
                        });
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
                 addNoteViewModel.getAllCategory().observe(MainActivity.this, new Observer<List<CategoryModel>>() {
                     @Override
                     public void onChanged(List<CategoryModel> categoryModels) {
                         addNoteViewModel.searchNote(query).observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                             @Override
                             public void onChanged(List<NoteEntity> noteEntities) {
                                 noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel,categoryModels);
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
                String Category = addCategoryET.getText().toString();
                if (Category.isEmpty()){
                    alertDialog.dismiss();
                    Toast.makeText(MainActivity.this,"PLease enter category name", Toast.LENGTH_SHORT).show();
                }else{
                    alertDialog.dismiss();
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.setCategoryName(Category);
                    addNoteViewModel.insertCategory(categoryModel);
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


    private void requestNotificationPermission(){
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.POST_NOTIFICATIONS},100);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode==100){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Thank you", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Allow notification for updates", Toast.LENGTH_SHORT).show();
            }
        }
    }
}