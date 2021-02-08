package ndd.com.simplenotes.screens.start

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import ndd.com.simplenotes.database.firebase.AppFirebaseRepository
import ndd.com.simplenotes.database.room.AppRoomDatabase
import ndd.com.simplenotes.database.room.AppRoomRepository
import ndd.com.simplenotes.utilits.REPOSITORY
import ndd.com.simplenotes.utilits.TYPE_FIREBASE
import ndd.com.simplenotes.utilits.TYPE_ROOM
import ndd.com.simplenotes.utilits.showToast

class StartFragmentViewModel(application: Application) : AndroidViewModel(application) {
    private val mContext = application
    fun initDatabase(type: String, onSuccess: () -> Unit) {
        when (type) {
            TYPE_ROOM -> {

                REPOSITORY = AppRoomRepository(mContext)
                onSuccess()

            }
            TYPE_FIREBASE -> {
                REPOSITORY = AppFirebaseRepository()
                REPOSITORY.connectToDatabase({onSuccess()}, {showToast(it)})
            }
        }
    }
}