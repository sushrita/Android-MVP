package com.skedsoft.compartirit.data;

/**
 * CompartirIt, All rights Reserved
 * Created by Sanjeet on 14-Oct-16.
 */

public class Contact {
    private int id;
    private String name;
    private String email;
    private boolean selected;

    Contact(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
