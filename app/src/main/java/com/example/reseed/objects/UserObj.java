package com.example.reseed.objects;

public class UserObj {

    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String userToken;
    private String userType;

    //Constructor empty
    public void user(){    }

    public void user(Long id, String name, String surname, String email, String phone, String userToken, String userType){

        setId(id);
        setName(name);
        setSurname(surname);
        setEmail(email);
        setPhone(phone);
        setUserToken(userToken);
        setUserType(userType);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
