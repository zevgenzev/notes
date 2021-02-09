package ndd.com.simplenotes.database.firebase

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import ndd.com.simplenotes.database.DatabaseRepository
import ndd.com.simplenotes.models.AppNote
import ndd.com.simplenotes.utilits.*

class AppFirebaseRepository : DatabaseRepository {

    init {
        AUTH = FirebaseAuth.getInstance()
    }

    override val allNotes: LiveData<List<AppNote>> get() = AllNotesLivaData()

    override suspend fun insert(note: AppNote, onSuccess: () -> Unit) {
        val idNote = REF_DATABASE.push().key.toString()
        val mapNote = HashMap<String, Any>()
        Log.e("insert", "idNote : $idNote, userId = $CURRENT_ID")
        mapNote[ID_FIREBASE] = idNote
        mapNote[NAME] = note.name
        mapNote[TEXT] = note.text
        REF_DATABASE.child(idNote)
            .updateChildren(mapNote)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override suspend fun delete(note: AppNote, onSuccess: () -> Unit) {
        Log.e("delete", "delete: idNote = ${note.idFirebase}, current_id = $CURRENT_ID")
        REF_DATABASE.child(note.idFirebase).removeValue()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override suspend fun update(note: AppNote, onSuccess: () -> Unit) {
        val mapNote = HashMap<String, Any>()
//        Log.e("update", "idFirebase : ${note.idFirebase}, userId = $CURRENT_ID")
        mapNote[ID_FIREBASE] = note.idFirebase
        mapNote[NAME] = note.name
        mapNote[TEXT] = note.text
        REF_DATABASE.child(note.idFirebase)
            .updateChildren(mapNote)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { showToast(it.message.toString()) }
    }

    override fun connectToDatabase(onSuccess: () -> Unit, onError: (String) -> Unit) {
        if (AppReferences.getInitUser()) {
            initRefDatabase()
            onSuccess()
        } else {
            AUTH.signInWithEmailAndPassword(EMAIL, PASSWORD)
                .addOnSuccessListener {
                    initRefDatabase()
                    onSuccess()
                }
                .addOnFailureListener {
                    AUTH.createUserWithEmailAndPassword(EMAIL, PASSWORD)
                        .addOnSuccessListener {
                            initRefDatabase()
                            onSuccess()
                        }
                        .addOnFailureListener {
                            onError(it.message.toString())
                        }
                }
        }
    }

    private fun initRefDatabase() {
        CURRENT_ID = AUTH.currentUser?.uid.toString()
        REF_DATABASE = FirebaseDatabase.getInstance().reference.child(CURRENT_ID)
    }

    override fun signOut() {
        AUTH.signOut()
    }
}