package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 9/24/2016.
 */
public class ProfileInput {
    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public Integer getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(Integer customerID) {
        CustomerID = customerID;
    }

    String FirstName;

    public String getEmailId() {
        return Email;
    }

    public void setEmailId(String emailId) {
        Email = emailId;
    }

    String Email;
    String LastName;
    String Mobile;
    String City;
    Integer CustomerID;

}
