package com.fityan.contactapp.helpers;

import android.util.Log;

import com.fityan.contactapp.models.Contact;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ContactsCollection {
    public static final String FIND_ALL = "findAll";
    public static final String COLLECTION_PATH = "contacts";
    private final CollectionReference collection = FirebaseFirestore.getInstance().collection(COLLECTION_PATH);

    public ContactsCollection() {}

    public Task<QuerySnapshot> findAll() {
        return this.collection.get()
            .addOnFailureListener(e -> Log.w(FIND_ALL, "Error writing document", e));
    }


    public Task<DocumentSnapshot> findOne(String id) {
        return this.collection.document(id).get()
            .addOnFailureListener(e -> Log.w(FIND_ALL, "Error getting document", e));
    }


    public Task<DocumentReference> insert(String name, String phone, String email, String address) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("phone", phone);
        data.put("email", email);
        data.put("address", address);

        return this.collection.add(data);
    }


    public Task<Void> update(Contact contact) {
        return this.collection.document(contact.getId())
            .update(
                "name", contact.getName(),
                "phone", contact.getPhone(),
                "email", contact.getEmail(),
                "address", contact.getAddress()
            );
    }


    public Task<Void> delete(String contactId) {
        return this.collection.document(contactId).delete();
    }
}
