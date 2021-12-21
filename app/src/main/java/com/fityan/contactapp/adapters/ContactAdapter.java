package com.fityan.contactapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fityan.contactapp.R;
import com.fityan.contactapp.models.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactListViewHolder> {
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
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_contact, parent, false);
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
    public interface OnItemListener {
        /**
         * Set actions when item is clicked.
         *
         * @param position The position of item.
         */
        void onItemClick(int position);

        /**
         * Set delete actions when Delete Button on item is clicked.
         *
         * @param position The position of item.
         */
        void onDeleteItem(int position);

        /**
         * Set edit actions when Edit Button on item is clicked.
         *
         * @param position The position of item.
         */
        void onEditItem(int position);
    }


    /**
     * View Holder for contact list.
     */
    static class ContactListViewHolder extends RecyclerView.ViewHolder {
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
            itemView.setOnClickListener(
                view -> onItemListener.onItemClick(getAdapterPosition()));

            /* Set delete actions when Delete Button is clicked */
            btnDelete.setOnClickListener(
                view -> onItemListener.onDeleteItem(getAdapterPosition()));

            /* Set edit actions when Edit Button is clicked */
            btnEdit.setOnClickListener(
                view -> onItemListener.onEditItem(getAdapterPosition()));
        }
    }
}
