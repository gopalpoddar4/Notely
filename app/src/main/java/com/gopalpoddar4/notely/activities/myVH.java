package com.gopalpoddar4.notely.activities;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gopalpoddar4.notely.R;

public class myVH extends RecyclerView.ViewHolder {

    LinearLayout linearLayout;
    TextView rcvTitle,rcvDescription,rcvTime ;
    public myVH(@NonNull View itemView) {
        super(itemView);
        rcvTitle=itemView.findViewById(R.id.rcvTitle);
        rcvDescription=itemView.findViewById(R.id.rcvDescription);
        linearLayout=itemView.findViewById(R.id.sampleLayout);
        rcvTime=itemView.findViewById(R.id.rcvTime);

    }
}
