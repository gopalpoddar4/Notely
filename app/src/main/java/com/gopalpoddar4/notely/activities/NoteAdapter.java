package com.gopalpoddar4.notely.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;
import java.util.List;
public class NoteAdapter extends RecyclerView.Adapter<myVH> {
    List<NoteEntity> notes;
    Context context;

    public NoteAdapter(List<NoteEntity> notes, Context context) {
        this.notes = notes;
        this.context = context;
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
        holder.rcvDescription.setText(notes.get(position).getNoteDescription());
        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

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

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ((Activity)context).startActivityForResult(intent,MainActivity.REQUEST_CODE_UPDATE_NOTE);
            }
        });
    }
    @Override
    public int getItemCount() {
        return notes.size();
    }
}
