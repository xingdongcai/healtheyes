package com.sa.project.Model.DataHolder;

/**
 * Represents a patient of the current login practitioner.
 */
public class Patient {
    private String id;
    private String fullName;
    private String givenName;
    private String familyName;
    private String birthDate;
    private String gender;
    private String city;
    private String state;
    private String country;

    public Patient(String id, String fullName, String givenName, String familyName, String birthDate, String gender, String city, String state, String country) {
        this.id = id;
        this.fullName = fullName;
        this.givenName = givenName;
        this.familyName = familyName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public Patient(){

    }

    public Patient(String id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
