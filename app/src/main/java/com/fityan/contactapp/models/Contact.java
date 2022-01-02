package com.fityan.contactapp.models;

public class Contact {
  public static final String NAME_FIELD = "name";
  public static final String PHONE_FIELD = "phone";
  public static final String EMAIL_FIELD = "email";
  public static final String ADDRESS_FIELD = "address";
  public static final String USER_UID_FIELD = "user_uid";

  private String id;
  private String name;
  private String phone;
  private String email;
  private String address;
  private String userUID;


  public Contact() {}


  public Contact(
      String id, String name, String phone, String email, String address, String userUID
  ) {
    this.id = id;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.address = address;
    this.userUID = userUID;
  }


  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public String getPhone() {
    return phone;
  }


  public void setPhone(String phone) {
    this.phone = phone;
  }


  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public String getAddress() {
    return address;
  }


  public void setAddress(String address) {
    this.address = address;
  }


  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public String getUserUID() {
    return userUID;
  }


  public void setUserUID(String userUID) {
    this.userUID = userUID;
  }
}
