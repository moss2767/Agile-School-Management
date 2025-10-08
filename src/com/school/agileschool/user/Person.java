package com.school.agileschool.user;

public abstract class Person {
    private String firstName;
    private String lastName;
    private String email;

    public Person(){

    }

    public Person (String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getName () {
        return firstName + " " + lastName;
    }
    public String getFirstName () {
        return firstName;
    }

    public void setFirstName (String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String generatePersonID(){
        return String.format("%s%s%d", this.firstName.substring(0, 3).toUpperCase(), this.lastName.substring(0,3).toUpperCase(), (int)(Math.random()*100));
    }
}