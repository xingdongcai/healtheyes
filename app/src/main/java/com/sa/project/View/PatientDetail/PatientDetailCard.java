package com.sa.project.View.PatientDetail;

/**
 * DataHolder for PatientDetailActivity to display patient's detail information
 */
public class PatientDetailCard {
    private String fullName;
    private String birthDate;
    private String gender;
    private String city;
    private String state;
    private String country;

    public PatientDetailCard(String fullName, String birthDate, String gender, String city, String state, String country) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public String getFullName() {
        return fullName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }
}
