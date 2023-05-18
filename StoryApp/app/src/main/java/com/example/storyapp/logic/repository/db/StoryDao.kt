package com.example.storyapp.logic.repository.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.storyapp.logic.repository.retrofit.responses.ListStoryItem

@Dao
interface StoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStory(story: List<ListStoryItem>)

    @Query("SELECT * FROM story_entity")
    fun getAllStory(): PagingSource<Int, ListStoryItem>

    @Query("DELETE FROM story_entity")
    suspend fun deleteAll()
}