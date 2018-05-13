package com.cresol.buzzapplication.model;

/**
 * Created by Saurabh on 9/24/2016.
 */
public class ProfileOutput {
    String status;

    String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ReadProfileInfo getProfileData() {
        return profileData;
    }

    public void setProfileData(ReadProfileInfo profileData) {
        this.profileData = profileData;
    }

    public ReadProfileInfo profileData;

}
