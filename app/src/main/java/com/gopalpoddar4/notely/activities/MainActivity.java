package com.gopalpoddar4.notely.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;
import java.util.List;
public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_NOTE=1;
    public static final int REQUEST_CODE_UPDATE_NOTE=2;
    ImageView AddNoteBtn,setting;
    int noteFormatValue = 0;
    int getNum;
    EditText searchNoteET;
    private AddNoteViewModel addNoteViewModel;
    RecyclerView recyclerView;
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

        addNoteViewModel=new ViewModelProvider(this).get(AddNoteViewModel.class);

        //finding views of id
        AddNoteBtn=findViewById(R.id.addNoteButton);
        recyclerView=findViewById(R.id.recycleView);
        searchNoteET=findViewById(R.id.searchEditText);
        setting=findViewById(R.id.setting);
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
                        noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel);
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

        addNoteViewModel.getValue().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                searchNoteET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        String query = s.toString();
                        addNoteViewModel.searchNote(query).observe(MainActivity.this, new Observer<List<NoteEntity>>() {
                            @Override
                            public void onChanged(List<NoteEntity> noteEntities) {
                                noteAdapter= new NoteAdapter(noteEntities,MainActivity.this,addNoteViewModel);
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
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
            }
        });

        AddNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddNoteActivity.class);
                startActivityForResult(intent,REQUEST_CODE_ADD_NOTE);
            }
        });

    }
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
                linearLayout.setBackgroundResource(R.color.colorPrimary);
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
                gridLayout.setBackgroundResource(R.color.colorPrimary);
                noteFormatValue=1;
            }
        });

        addNoteViewModel.getValue().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer==0){
                    gridLayout.setBackgroundResource(R.drawable.format_bg);
                    linearLayout.setBackgroundResource(R.color.colorPrimary);
                }else {
                    linearLayout.setBackgroundResource(R.drawable.format_bg);
                    gridLayout.setBackgroundResource(R.color.colorPrimary);
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
            addNoteViewModel.insert(noteEntity);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }

    }
}