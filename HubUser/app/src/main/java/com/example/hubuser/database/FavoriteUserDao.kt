package com.example.hubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)
    @Delete
    fun delete(user: FavoriteUser)
    //maybe ill add timestamp later so it can be ordered by timestamp
    @Query("SELECT * FROM users")
    fun getFavoriteUsers(): LiveData<List<FavoriteUser>>
    @Query("SELECT EXISTS(SELECT username FROM users WHERE username =:username) ")
    fun isFavorite(username: String): LiveData<Boolean>
}
