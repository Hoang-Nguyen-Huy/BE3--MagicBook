/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dm;

import java.sql.Date;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class User {
    
    private String UserId;
    private String firstName;
    private String lastName;
    private Date dob;
    private String sex;
    private String country;
    private String phone;
    private String email;
    private String password;
    private String avatar;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String UserId, String firstName, String lastName, Date dob, String sex, String country, String phone, String email, String password, String avatar) {
        this.UserId = UserId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.sex = sex;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public User(String firstName, String lastName, Date dob, String sex, String country, String phone, String email, String password, String avatar) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.sex = sex;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public User(String firstName, String lastName, Date dob, String sex, String country, String phone, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
        this.sex = sex;
        this.country = country;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public User() {
        
    }
    
    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" + "UserId=" + UserId + ", firstName=" + firstName + ", lastName=" + lastName + ", dob=" + dob + ", sex=" + sex + ", country=" + country + ", phone=" + phone + ", email=" + email + ", password=" + password + ", avatar=" + avatar + '}';
    }

}
