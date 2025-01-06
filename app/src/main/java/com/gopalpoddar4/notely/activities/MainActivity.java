package com.gopalpoddar4.notely.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;

import java.util.List;
public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_ADD_NOTE=1;
    public static final int REQUEST_CODE_UPDATE_NOTE=2;
    ImageView AddNoteBtn;
    private AddNoteViewModel addNoteViewModel;
    RecyclerView recyclerView;
    NoteAdapter noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        addNoteViewModel=new ViewModelProvider(this).get(AddNoteViewModel.class);
        AddNoteBtn=findViewById(R.id.addNoteButton);
        recyclerView=findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addNoteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(List<NoteEntity> noteEntities) {
               noteAdapter= new NoteAdapter(noteEntities,MainActivity.this);
                recyclerView.setAdapter(noteAdapter);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE_ADD_NOTE && resultCode==RESULT_OK){
            String title = data.getStringExtra("titleadd");
            String desc = data.getStringExtra("descadd");
            String datetime = data.getStringExtra("dateadd");
            NoteEntity noteEntity = new NoteEntity();
            noteEntity.setTitle(title);
            noteEntity.setNoteDescription(desc);
            noteEntity.setDateTime(datetime);
            addNoteViewModel.insert(noteEntity);
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        }

    }
}