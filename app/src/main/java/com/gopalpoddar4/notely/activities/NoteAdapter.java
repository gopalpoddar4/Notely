package com.gopalpoddar4.notely.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;
import java.util.List;
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.myVH> {
    List<NoteEntity> notes;
    Context context;
    private AddNoteViewModel addNoteViewModel;
    public static final int REQUEST_CODE_UPDATE_NOTE=2;
    public NoteAdapter(List<NoteEntity> notes, Context context,AddNoteViewModel addNoteViewModel) {
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

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               AlertDialog.Builder builder = new AlertDialog.Builder(context);
               builder.setTitle("Delete note");
               builder.setMessage("Are you sure you want to delete");
               builder.setIcon(R.drawable.delete);
               builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       addNoteViewModel.delete(temp);
                       dialog.dismiss();
                       Toast.makeText(context, "Note deleted", Toast.LENGTH_SHORT).show();
                   }
               });
               builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.dismiss();
                   }
               });
               AlertDialog dialog = builder.create();
               dialog.show();
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
        TextView rcvTitle,rcvDescription,rcvTime ;
        public myVH(@NonNull View itemView) {
            super(itemView);
            rcvTitle=itemView.findViewById(R.id.rcvTitle);
            rcvDescription=itemView.findViewById(R.id.rcvDescription);
            linearLayout=itemView.findViewById(R.id.sampleLayout);
            rcvTime=itemView.findViewById(R.id.rcvTime);
            ll=itemView.findViewById(R.id.rcvLinear);
        }

    }

}
