package com.gopalpoddar4.notely.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddNoteActivity extends AppCompatActivity {
    ImageView backBtn,SaveNoteBtn;
    EditText etNoteTitle,etNoteDescription;
    TextView dateTime;
    String selectedColor;
    public static final String NOTE_ADDED="dataadded";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_note);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        );

        backBtn=findViewById(R.id.backBtn);
        SaveNoteBtn=findViewById(R.id.saveNoteBtn);
        etNoteTitle=findViewById(R.id.inputNoteTitle);
        etNoteDescription=findViewById(R.id.inputNoteDescription);
        dateTime=findViewById(R.id.textDateTime);

        initMislinious();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy, hh:mm a",Locale.getDefault());
        String currentDateAndTime = simpleDateFormat.format(Calendar.getInstance().getTime());
        dateTime.setText(currentDateAndTime);

        SaveNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNoteTitle.getText())){
                    Toast.makeText(AddNoteActivity.this, "Title can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(etNoteDescription.getText())) {
                    Toast.makeText(AddNoteActivity.this, "Note can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("titleadd",etNoteTitle.getText().toString());
                    intent.putExtra("descadd",etNoteDescription.getText().toString());
                    intent.putExtra("dateadd",dateTime.getText().toString());
                    intent.putExtra("color",selectedColor);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
    private void initMislinious(){
        final LinearLayout linearLayoutMis = findViewById(R.id.layout_mislinious);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(linearLayoutMis);

        linearLayoutMis.findViewById(R.id.textMislinious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
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
                if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED){
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
        yellowColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor="colorYellow";
                yellowColor.setImageResource(R.drawable.savebtn);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
            }
        });
        redColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor="colorRed";
                yellowColor.setImageResource(0);
                redColor.setImageResource(R.drawable.savebtn);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(0);
            }
        });
        blueColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedColor="colorBlue";
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(R.drawable.savebtn);
                defaultColor.setImageResource(0);
            }
        });
        defaultColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yellowColor.setImageResource(0);
                redColor.setImageResource(0);
                blueColor.setImageResource(0);
                defaultColor.setImageResource(R.drawable.savebtn);
            }
        });
    }
}