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
  private final CollectionReference collection = FirebaseFirestore.getInstance()
      .collection(COLLECTION_PATH);


  public ContactsCollection() {}


  public Task<QuerySnapshot> findAll(String userUID) {
    return this.collection.whereEqualTo(Contact.USER_UID_FIELD, userUID).get()
        .addOnFailureListener(e -> Log.w(FIND_ALL, "Error writing document", e));
  }


  public Task<DocumentSnapshot> findOne(String docId) {
    return this.collection.document(docId).get()
        .addOnFailureListener(e -> Log.w(FIND_ALL, "Error getting document", e));
  }


  public Task<DocumentReference> insert(
      String name, String phone, String email, String address, String userUID
  ) {
    Map<String, Object> data = new HashMap<>();
    data.put(Contact.NAME_FIELD, name);
    data.put(Contact.PHONE_FIELD, phone);
    data.put(Contact.EMAIL_FIELD, email);
    data.put(Contact.ADDRESS_FIELD, address);
    data.put(Contact.USER_UID_FIELD, userUID);

    return this.collection.add(data);
  }


  public Task<Void> update(Contact contact) {
    return this.collection.document(contact.getId())
        .update(Contact.NAME_FIELD, contact.getName(), Contact.PHONE_FIELD, contact.getPhone(),
            Contact.EMAIL_FIELD, contact.getEmail(), Contact.ADDRESS_FIELD, contact.getAddress());
  }


  public Task<Void> delete(String contactId) {
    return this.collection.document(contactId).delete();
  }
}
