package ndd.com.simplenotes.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import ndd.com.simplenotes.models.AppNote

@Dao
interface AppRoomDao {
    @Query("SELECT * from notes_table")
    fun getAllNotes(): LiveData<List<AppNote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: AppNote)

    @Delete
    suspend fun delete(note: AppNote)
}