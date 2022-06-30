
package com.nqphu.model;


public class ProfileModel {
    private int id;
    private String username;
    private String fullname;
    private String pwd;
    private String email;
    private String gender;
    private String dob;
    private String address;
    private String phone;

    public ProfileModel() {
    }

    public ProfileModel(int id, String username, String fullname, String pwd, String email, String gender, String dob, String address, String phone) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.pwd = pwd;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    
}
