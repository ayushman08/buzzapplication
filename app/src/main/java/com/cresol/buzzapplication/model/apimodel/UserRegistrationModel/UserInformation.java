package com.cresol.buzzapplication.model.apimodel.UserRegistrationModel;

/**
 * Created by Nitesh on 9/10/2016.
 */
//saving the data in the database
public class UserInformation {
    public String FirstName;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String LastName;
    public String Mobile;
    public String Email;
    public String City;

}
