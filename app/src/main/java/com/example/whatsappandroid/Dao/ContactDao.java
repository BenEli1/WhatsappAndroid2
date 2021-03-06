package com.example.whatsappandroid.Dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.whatsappandroid.CreatedClasses.Contact;

import java.util.List;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM Contact WHERE username= :username")
    List<Contact> index(String username);
    @Query("SELECT * FROM Contact WHERE id = :id and username= :username")
    Contact get(String id,String username);
    @Insert
    void insert(Contact... contacts);
    @Update
    void update(Contact... contacts);
    @Delete
    void delete(Contact... contacts);

}
