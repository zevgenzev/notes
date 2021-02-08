package ndd.com.simplenotes.screens.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import ndd.com.simplenotes.utilits.REPOSITORY

class MainFragmentViewModel(application: Application) : AndroidViewModel(application) {
    val allNotes = REPOSITORY.allNotes
    fun signOut() {
        REPOSITORY.signOut()
    }
}