package com.example.spiderhero;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface FavDao {

    @Query("SELECT * FROM favheroes")
    List<Fav> getFav();
    @Query("SELECT * FROM favheroes WHERE id=:id")
    Fav getById(String id);

    @Insert
    void Insert(Fav fav);
    @Delete
    void Delete(Fav fav);
}
