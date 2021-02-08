package ndd.com.simplenotes.database.room

import android.app.Application
import androidx.lifecycle.LiveData
import ndd.com.simplenotes.database.DatabaseRepository
import ndd.com.simplenotes.models.AppNote

class AppRoomRepository(context: Application) : DatabaseRepository {
    private val appRoomDao = AppRoomDatabase.getInstance(context).getAppRoomDao();
    override val allNotes: LiveData<List<AppNote>>
        get() = appRoomDao.getAllNotes()

    override suspend fun insert(note: AppNote, onSuccess: () -> Unit) {
        appRoomDao.insert(note)
        onSuccess()
    }

    override suspend fun delete(note: AppNote, onSuccess: () -> Unit) {
        appRoomDao.delete(note)
        onSuccess()
    }

}