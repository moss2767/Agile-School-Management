package com.school.agileschool.user;

import java.util.UUID;

public abstract class Person {
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public Person(){

    }

    public Person (String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.id = this.generatePersonID();
        this.ensureUniqueID();
    }

    public String getId() {
        return id;
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
        if(this.firstName.length() < 3 || this.lastName.length() < 3){
            return String.format("%s%s%d",this.firstName.toUpperCase(), this.lastName.toUpperCase(), (int) (Math.random() * 100));
        } else {
            return String.format("%s%s%d", this.firstName.substring(0, 3).toUpperCase(), this.lastName.substring(0, 3).toUpperCase(), (int) (Math.random() * 100));
        }
    }

    private void ensureUniqueID() {
        while (true) {
            if (!checkIfIDExistInPersistentStorage(this.id)) {
                break;
            }
            this.id = this.generatePersonID();
        }
    }

    abstract boolean checkIfIDExistInPersistentStorage(String id);
}