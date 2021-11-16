package com.fityan.contactapp;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class ContactListViewHolder extends RecyclerView.ViewHolder {
    /* View elements */
    protected final TextView tvName;
    protected final TextView tvPhone;
    protected final ImageButton btnEdit;
    protected final ImageButton btnDelete;


    public ContactListViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvName);
        tvPhone = itemView.findViewById(R.id.tvPhone);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }
}