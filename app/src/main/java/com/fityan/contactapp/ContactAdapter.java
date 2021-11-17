package com.fityan.contactapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactListViewHolder> {
    /**
     * List of contact.
     */
    private final ArrayList<Contact> contacts;

    /**
     * Closure to handle actions of contact item.
     */
    private final OnItemListener onItemListener;


    public ContactAdapter(ArrayList<Contact> contacts, OnItemListener onItemListener) {
        this.contacts = contacts;
        this.onItemListener = onItemListener;
    }


    @NonNull
    @Override
    public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactListViewHolder(itemView, onItemListener);
    }


    @Override
    public void onBindViewHolder(@NonNull ContactListViewHolder holder, int position) {
        Contact contact = contacts.get(position);

        /* Set display of contact name & phone */
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }


    /**
     * Action listener for contact item.
     */
    interface OnItemListener {
        /**
         * Set actions when item is clicked.
         * @param position The position of item.
         */
        void onItemClick(int position);

        /**
         * Set delete actions when Delete Button on item is clicked.
         * @param position The position of item.
         */
        void onDeleteItem(int position);

        /**
         * Set edit actions when Edit Button on item is clicked.
         * @param position The position of item.
         */
        void onEditItem(int position);
    }
}
