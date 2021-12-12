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

    /**
     * Closure to handle actions of contact item.
     */
    final ContactAdapter.OnItemListener onItemListener;


    public ContactListViewHolder(@NonNull View itemView, ContactAdapter.OnItemListener onItemListener) {
        super(itemView);

        /* Initialize view elements. */
        tvName = itemView.findViewById(R.id.tvName);
        tvPhone = itemView.findViewById(R.id.tvPhone);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDelete = itemView.findViewById(R.id.btnDelete);

        /* Initialize the itemListener closure. */
        this.onItemListener = onItemListener;

        /* Set actions when item is clicked */
        itemView.setOnClickListener(view ->
                onItemListener.onItemClick(getAdapterPosition()));

        /* Set delete actions when Delete Button is clicked */
        btnDelete.setOnClickListener(view ->
                onItemListener.onDeleteItem(getAdapterPosition()));

        /* Set edit actions when Edit Button is clicked */
        btnEdit.setOnClickListener(view ->
                onItemListener.onEditItem(getAdapterPosition()));
    }
}
