package com.gopalpoddar4.notely.activities;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.CategoryFiles.CategoryModel;
import com.gopalpoddar4.notely.activities.DatabaseFiles.NoteEntity;
import java.util.List;
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.myVH> {
    List<NoteEntity> notes;
    Context context;
    private AddNoteViewModel addNoteViewModel;
    List<CategoryModel> categoryList;
    public static final int REQUEST_CODE_UPDATE_NOTE=2;
    public NoteAdapter(List<NoteEntity> notes, Context context,AddNoteViewModel addNoteViewModel,List<CategoryModel> categoryList ) {
        this.notes = notes;
        this.context = context;
        this.addNoteViewModel=addNoteViewModel;
        this.categoryList=categoryList;
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

        holder.rcvDescription.setAutoLinkMask(Linkify.WEB_URLS);
        holder.rcvDescription.setMovementMethod(LinkMovementMethod.getInstance());

        if(notes.get(position).isPinned() ){
            holder.pin.setVisibility(VISIBLE);
        }else{
            holder.pin.setVisibility(GONE);
        }
        holder.rcvDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra("note_id",notes.get(position).getId());
                ((Activity)context).startActivityForResult(intent,REQUEST_CODE_UPDATE_NOTE);
            }
        });
        holder.rcvDescription.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDaiolog(notes.get(position),v,categoryList);
                return true;
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDaiolog(notes.get(position),v,categoryList);
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNoteActivity.class);
                intent.putExtra("note_id",notes.get(position).getId());
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

    //Option window
    public void showDaiolog(NoteEntity note,View v,List<CategoryModel> categoryList){
        LinearLayout linearLayout1 =  v.findViewById(R.id.pin_delete_layout);
        View view = LayoutInflater.from(context).inflate(R.layout.pin_delete_lock_layout,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context, com.google.android.material.R.style.ThemeOverlay_Material3_Dialog);
        builder.setView(view);
        AlertDialog alertDialog=builder.create();

        LinearLayout pinLayout = view.findViewById(R.id.pin_layout);
        ImageView img_pin = view.findViewById(R.id.pin_img);
        TextView txt_pin = view.findViewById(R.id.pin_txt);
        TextView lock_txt = view.findViewById(R.id.lock_txt);

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
        LinearLayout categorylayout = view.findViewById(R.id.category_layout);

        lockLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, R.string.soon, Toast.LENGTH_SHORT).show();
              /*  View view3 = LayoutInflater.from(context).inflate(R.layout.lock_dialog,null);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(context, com.google.android.material.R.style.ThemeOverlay_Material3_Dialog);
                builder3.setView(view3);
                AlertDialog alertDialog3=builder3.create();

                TextView locktxt = view3.findViewById(R.id.lock_txt);
                EditText locket = view3.findViewById(R.id.locket);
                Button lockBtn = view3.findViewById(R.id.lockBtn);



                alertDialog3.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialog3.show();

                alertDialog3.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
 */
                alertDialog.dismiss();

            }
        });

        //Here we perform select category operation
        categorylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                View viewc = LayoutInflater.from(context).inflate(R.layout.category_spinner,null);
                AlertDialog.Builder builderc = new AlertDialog.Builder(context, com.google.android.material.R.style.ThemeOverlay_Material3_Dialog);
                builderc.setView(viewc);
                AlertDialog alertDialogc =builderc.create();

                Spinner spinner = viewc.findViewById(R.id.categorySpinner);
                Button ok = viewc.findViewById(R.id.okcategory);

                ArrayAdapter<CategoryModel> spinnerAdapter = new ArrayAdapter<>(context,android.R.layout.simple_spinner_dropdown_item,categoryList);
                spinner.setAdapter(spinnerAdapter);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String selectedCategort = spinner.getSelectedItem().toString();
                        note.setCategory(selectedCategort);
                        addNoteViewModel.update(note);
                        alertDialogc.dismiss();
                        Toast.makeText(context, selectedCategort + " Category Selected", Toast.LENGTH_SHORT).show();

                    }
                });

                alertDialogc.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                alertDialogc.show();
                alertDialogc.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
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

                        LinearLayout delete_anim = v.findViewById(R.id.delete_anim);
                        View view2 = LayoutInflater.from(context).inflate(R.layout.delete_animation_layout,null);
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(context, com.google.android.material.R.style.ThemeOverlay_Material3_Dialog);
                        builder2.setView(view2);
                        AlertDialog alertDialog2 = builder2.create();
                        alertDialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        alertDialog2.show();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                alertDialog2.dismiss();
                            }
                        },5000);

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
        FrameLayout lockOverlay;
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