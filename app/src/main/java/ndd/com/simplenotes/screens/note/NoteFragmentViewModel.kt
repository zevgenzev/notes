package ndd.com.simplenotes.screens.note

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ndd.com.simplenotes.models.AppNote
import ndd.com.simplenotes.utilits.REPOSITORY

class NoteFragmentViewModel(application: Application) : AndroidViewModel(application) {
    fun delete(mCurrentNote: AppNote, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.delete(mCurrentNote){ onSuccess()}
        }
    }

    fun update(mCurrentNote: AppNote, onSuccess: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORY.update(mCurrentNote){ onSuccess()}
        }
    }


}