package com.gopalpoddar4.notely.activities;

import static android.view.View.VISIBLE;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;
import java.util.List;
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.myVH> {
    List<NoteEntity> notes;
    Context context;
    private AddNoteViewModel addNoteViewModel;
    public static final int REQUEST_CODE_UPDATE_NOTE=2;
    public NoteAdapter(List<NoteEntity> notes, Context context,AddNoteViewModel addNoteViewModel  ) {
        this.notes = notes;
        this.context = context;
        this.addNoteViewModel=addNoteViewModel;
    }
    @NonNull
    @Override
    public myVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()) ;
        View view = inflater.inflate(R.layout.note_sample_layout,parent,false);
        return new myVH(view);
    }
    @Override
    public void onBindViewHolder(@NonNull myVH holder, int position) {

        final NoteEntity temp = notes.get(position);
        holder.rcvTitle.setText(notes.get(position).getTitle());
        holder.rcvTime.setText(notes.get(position).getDateTime());
        holder.rcvDescription.setText(notes.get(position).getNoteDescription());
        if(notes.get(position).isPinned() ){
            holder.pin.setVisibility(VISIBLE);
        }

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               showDaiolog(notes.get(position),v);
                return true;
           }
       });
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra("note_id",temp.getId());
                intent.putExtra("title",temp.getTitle());
                intent.putExtra("desc",temp.getNoteDescription());
                intent.putExtra("time",temp.getDateTime());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Activity)context).startActivityForResult(intent,REQUEST_CODE_UPDATE_NOTE);
            }
        });

        if (notes.get(position).getColour() != null){
            String colorName = notes.get(position).getColour();
            int colorInt = getDynamicColor(colorName,context);
            holder.ll.setBackgroundColor(colorInt);
            holder.linearLayout.setCardBackgroundColor(colorInt);

        }
    }

    public void showDaiolog(NoteEntity note,View v){
        LinearLayout linearLayout1 =  v.findViewById(R.id.pin_delete_layout);
        View view = LayoutInflater.from(context).inflate(R.layout.pin_delete_lock_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, com.google.android.material.R.style.ThemeOverlay_Material3_Dialog);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();

        LinearLayout pinLayout = view.findViewById(R.id.pin_layout);
        ImageView img_pin = view.findViewById(R.id.pin_img);
        TextView txt_pin = view.findViewById(R.id.pin_txt);
        if (note.isPinned()){
            img_pin.setImageResource(R.drawable.notpin);
            txt_pin.setText("Unpin Note");
            pinLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note.setPinned(false);
                    addNoteViewModel.update(note);
                    alertDialog.dismiss();
                }
            });
        } else {
            pinLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note.setPinned(true);
                    addNoteViewModel.update(note);
                    alertDialog.dismiss();
                }
            });
        }
        LinearLayout deleteLayout = view.findViewById(R.id.delete_layout);
        LinearLayout lockLayout = view.findViewById(R.id.lock_layout);

        lockLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                Toast.makeText(context, "Feature coming soon", Toast.LENGTH_SHORT).show();
            }
        });

        //Here we perform delete note operation
        deleteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout deletelayout = v.findViewById(R.id.delete_view);
                View view1 = LayoutInflater.from(context).inflate(R.layout.delete_layout,null);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(context, com.google.android.material.R.style.ThemeOverlay_Material3_Dialog);
                builder1.setView(view1);
                AlertDialog alertDialog1=builder1.create();

                TextView cancelBtn = view1.findViewById(R.id.cancel_delete);
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog1.dismiss();
                    }
                });
                TextView deleteBtn = view1.findViewById(R.id.btn_delete);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addNoteViewModel.delete(note);
                        alertDialog1.dismiss();
                    }
                });

                alertDialog1.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog1.show();
                alertDialog.dismiss();

            }

        });


        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        alertDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private int getDynamicColor(String colorName,Context context) {
        int colorId =  context.getResources().getIdentifier(colorName,"color",context.getPackageName());
        return ContextCompat.getColor(context,colorId);
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
    public class myVH extends RecyclerView.ViewHolder {

        CardView linearLayout;
        LinearLayout ll;
        ImageView pin;
        TextView rcvTitle,rcvDescription,rcvTime ;
        public myVH(@NonNull View itemView) {
            super(itemView);
            rcvTitle=itemView.findViewById(R.id.rcvTitle);
            rcvDescription=itemView.findViewById(R.id.rcvDescription);
            linearLayout=itemView.findViewById(R.id.sampleLayout);
            rcvTime=itemView.findViewById(R.id.rcvTime);
            ll=itemView.findViewById(R.id.rcvLinear);
            pin=itemView.findViewById(R.id.rcvIsPinned);
        }

    }

}
