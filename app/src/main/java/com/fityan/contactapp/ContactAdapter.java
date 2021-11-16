package com.fityan.contactapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactListViewHolder> {
    private final ArrayList<Contact> contacts;

    public ContactAdapter(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }


    @NonNull
    @Override
    public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactListViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ContactListViewHolder holder, int position) {
        Contact contact = contacts.get(position);

        /* Set display of contact name & phone */
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());

        /* When a contact item is clicked */
        holder.itemView.setOnClickListener(view -> {
            /* TODO: Go to contact details page */
            Toast.makeText(view.getContext(), "Contact id: " + String.valueOf(contact.getId()), Toast.LENGTH_SHORT).show();
        });

        /* When Edit Button is clicked */
        holder.btnEdit.setOnClickListener(view -> {
            /* TODO: Go to edit contact page */
        });

        /* When Delete Button is clicked */
        holder.btnDelete.setOnClickListener(view -> {
            /* Delete contact */
            boolean success = new DBHelper(view.getContext())
                    .deleteContact(contact.getId());

            if (success) {
                /* TODO: Refresh the contact list view */
            } else {
                Toast.makeText(view.getContext(),
                        "Failed to delete contact", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
