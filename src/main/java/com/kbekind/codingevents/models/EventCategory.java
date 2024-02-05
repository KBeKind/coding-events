package com.kbekind.codingevents.models;

import jakarta.persistence.Entity;

@Entity
public class EventCategory extends AbstractEntity {

    String name;

     public EventCategory(String name) {
        this.name = name;
    }

    public EventCategory() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
