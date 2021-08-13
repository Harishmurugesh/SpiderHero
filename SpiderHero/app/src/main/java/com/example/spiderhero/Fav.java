package com.example.spiderhero;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "favheroes",indices = {@Index(value = {"id","name"},unique = true)})
public class Fav {
    @NonNull
    @PrimaryKey
    private String id;
    private String name;

    public Fav(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
