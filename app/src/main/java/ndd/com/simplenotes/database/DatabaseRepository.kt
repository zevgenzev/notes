package ndd.com.simplenotes.database

import androidx.lifecycle.LiveData
import ndd.com.simplenotes.models.AppNote

interface DatabaseRepository {
    val allNotes: LiveData<List<AppNote>>
    suspend fun insert(note: AppNote, onSuccess: () -> Unit)
    suspend fun delete(note: AppNote, onSuccess: () -> Unit)
    suspend fun update(note: AppNote, onSuccess: () -> Unit)
    fun connectToDatabase(onSuccess: () -> Unit, onError: (String) -> Unit){}
    fun signOut(){}
}