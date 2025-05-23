package com.gopalpoddar4.notely.activities.CategoryFiles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gopalpoddar4.notely.R;
import com.gopalpoddar4.notely.activities.AddNoteViewModel;
import com.gopalpoddar4.notely.activities.NoteAdapter;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.vh> {

    List<CategoryModel> categoryList;
    Context context;
    private AddNoteViewModel addNoteViewModel;
    private CategoryInterface listner;
    private int selectedPosition = RecyclerView.NO_POSITION;


    public CategoryAdapter(List<CategoryModel> categoryList, Context context, AddNoteViewModel addNoteViewModel,CategoryInterface listner) {
        this.categoryList = categoryList;
        this.context = context;
        this.addNoteViewModel = addNoteViewModel;
        this.listner = listner;
    }

    @NonNull
    @Override
    public vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext()) ;
        View view = inflater.inflate(R.layout.category_sample_layout,parent,false);
        return new CategoryAdapter.vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull vh holder, int position) {
        CategoryModel model = categoryList.get(position);
        int id = model.getId();
        holder.categoryname.setText(categoryList.get(position).getCategoryName());
        holder.category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int previousPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(previousPosition);
                notifyItemChanged(selectedPosition);

                listner.onItemClick(model.getCategoryName());
            }
        });
        if (selectedPosition == position){
            holder.category.setBackgroundResource(R.drawable.background_category);
        }else{
            holder.category.setBackgroundResource(R.drawable.bg_category);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class vh extends RecyclerView.ViewHolder{

        TextView categoryname;
        LinearLayout category;
        public vh(@NonNull View itemView) {
            super(itemView);

            category=itemView.findViewById(R.id.categoryLayout);
            categoryname=itemView.findViewById(R.id.categoryname);
        }
    }
}
