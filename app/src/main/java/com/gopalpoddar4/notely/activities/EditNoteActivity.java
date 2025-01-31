package com.gopalpoddar4.notely.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditNoteActivity extends AppCompatActivity {
    ImageView back,save,changeBgColor;
    EditText etTitle1,etDescription1;
    TextView dateTime1;
    private EditNoteViewModel editNoteViewModel;
    private LiveData<NoteEntity> noteEntity;
    String editedColor;
    int num=0;
    CoordinatorLayout coordinatorLayout;
    NoteEntity entity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_note);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );
        coordinatorLayout=findViewById(R.id.editNoteActivity);
        back=findViewById(R.id.backBtnEdit);
        save=findViewById(R.id.saveEditedNoteBtn);
        etTitle1=findViewById(R.id.editNoteTitle);
        etDescription1=findViewById(R.id.editNoteDescription);
        dateTime1=findViewById(R.id.editDateTime);
        changeBgColor=findViewById(R.id.changeBgColor);
        changeBgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditNoteActivity.this, "This feature coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        int id = getIntent().getIntExtra("note_id",0);

        editNoteViewModel= new ViewModelProvider(this).get(EditNoteViewModel.class);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm a", Locale.getDefault());
        String currentDateAndTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        dateTime1.setText(currentDateAndTime);
        noteEntity = editNoteViewModel.getNote(id);
        noteEntity.observe(this, new Observer<NoteEntity>() {
            @Override
            public void onChanged(NoteEntity noteEntity) {
                etTitle1.setText(noteEntity.getTitle());
                etDescription1.setText(noteEntity.getNoteDescription());
                dateTime1.setText(noteEntity.getDateTime());
                if(noteEntity.getColour()!=null){
                    editedColor = noteEntity.getColour();

                }

                if (num==0){
                    if (noteEntity.getColour()!=null){
                        String color = noteEntity.getColour();
                        int colorInt = getDynamiccolor(color,EditNoteActivity.this);
                        coordinatorLayout.setBackgroundColor(colorInt);
                    }
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        editMislinious();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etTitle1.getText())){
                    Toast.makeText(EditNoteActivity.this, "Title can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(etDescription1.getText())) {
                    Toast.makeText(EditNoteActivity.this, "Note can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    NoteEntity note=new NoteEntity();
                    note.setId(id);
                    note.setTitle(etTitle1.getText().toString());
                    note.setNoteDescription(etDescription1.getText().toString());
                    note.setDateTime(currentDateAndTime);
                    note.setColour(editedColor);
                    editNoteViewModel.update(note);

                    finish();
                }
            }
        });
    }

    private int getDynamiccolor(String color, EditNoteActivity editNoteActivity) {
        int colorId =  editNoteActivity.getResources().getIdentifier(color,"color",editNoteActivity.getPackageName());
        return ContextCompat.getColor(editNoteActivity,colorId);
    }

    private void editMislinious(){
        final LinearLayout linearLayoutMis = findViewById(R.id.layout_mislinious);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutMis);

        linearLayoutMis.findViewById(R.id.textMislinious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState()!=bottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });
        linearLayoutMis.findViewById(R.id.viewMislinious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState()!=bottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        ImageView yellowColor = linearLayoutMis.findViewById(R.id.yellowColor);
        ImageView blueColor = linearLayoutMis.findViewById(R.id.blueColor);
        ImageView redColor = linearLayoutMis.findViewById(R.id.redColor);
        ImageView defaultColor = linearLayoutMis.findViewById(R.id.defaultColor);
        ImageView greenColor = linearLayoutMis.findViewById(R.id.greenColor);
        ImageView pinkColor = linearLayoutMis.findViewById(R.id.pinkColor);
        ImageView skyColor = linearLayoutMis.findViewById(R.id.skyColor);
        ImageView slateBlueColor = linearLayoutMis.findViewById(R.id.slateBlueColor);
        ImageView pastelYellowColor = linearLayoutMis.findViewById(R.id.pastelYellowColor);
        ImageView SandyBrownColor = linearLayoutMis.findViewById(R.id.SandyBrownColor);
        ImageView lavenderColor = linearLayoutMis.findViewById(R.id.lavenderColor);
        ImageView orangeColor = linearLayoutMis.findViewById(R.id.orangeColor);
        ImageView tealColor = linearLayoutMis.findViewById(R.id.tealColor);
        ImageView coralPinkColor = linearLayoutMis.findViewById(R.id.coralPinkColor);
        ImageView slateGrayColor = linearLayoutMis.findViewById(R.id.slateGrayColor);


        yellowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorYellow";
                yellowColor.setImageResource(R.drawable.savebtn);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        redColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorRed";
                yellowColor.setImageResource(0);
                redColor.setImageResource(R.drawable.savebtn);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        blueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorBlue";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(R.drawable.savebtn);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        defaultColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorDefaultNoteColor";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(R.drawable.savebtn);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        greenColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorGreen";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(R.drawable.savebtn);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        pinkColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorPink";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(R.drawable.savebtn);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        skyColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorPeachPuff";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(R.drawable.savebtn);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        slateBlueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorSlateBlue";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(R.drawable.savebtn);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        pastelYellowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorPastelYellow";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(R.drawable.savebtn);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        SandyBrownColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorSandyBrown";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(R.drawable.savebtn);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        lavenderColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorLavender";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(R.drawable.savebtn);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        orangeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorOrange";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(R.drawable.savebtn);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        tealColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorTeal";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(R.drawable.savebtn);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(0);
            }
        });
        coralPinkColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorCoralPink";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(R.drawable.savebtn);
                slateGrayColor.setImageResource(0);
            }
        });
        slateGrayColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editedColor="colorSlateGray";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
                greenColor.setImageResource(0);
                pinkColor.setImageResource(0);
                skyColor.setImageResource(0);
                slateBlueColor.setImageResource(0);
                pastelYellowColor.setImageResource(0);
                SandyBrownColor.setImageResource(0);
                lavenderColor.setImageResource(0);
                orangeColor.setImageResource(0);
                tealColor.setImageResource(0);
                coralPinkColor.setImageResource(0);
                slateGrayColor.setImageResource(R.drawable.savebtn);
            }
        });
    }

    private void storeColor(){

    }
}