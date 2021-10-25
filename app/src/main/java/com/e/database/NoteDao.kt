package com.e.database

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NoteDao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    suspend fun getNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addnote(note:Note)

    @Insert
    suspend fun addMultipleNote(vararg note:Note)

    @Update
    suspend fun noteUpdate(note:Note)

    @Delete
    suspend fun noteDelete(note:Note)
}
