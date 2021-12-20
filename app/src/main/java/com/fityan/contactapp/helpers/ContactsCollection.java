package com.fityan.contactapp.helpers;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ContactsCollection {
    public static final String FIND_ALL = "findAll";
    public static final String COLLECTION_PATH = "contacts";
    private final CollectionReference collection = FirebaseFirestore.getInstance().collection(COLLECTION_PATH);

    public ContactsCollection() {}

    public Task<QuerySnapshot> findAll() {
        return this.collection.get()
            .addOnFailureListener(e -> Log.w(FIND_ALL, "Error writing document", e));
    }
}
