package ndd.com.simplenotes.utilits

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ndd.com.simplenotes.MainActivity
import ndd.com.simplenotes.database.DatabaseRepository
lateinit var AUTH:FirebaseAuth
lateinit var CURRENT_ID:String
lateinit var REF_DATABASE: DatabaseReference
lateinit var APP_ACTIVITY:MainActivity
lateinit var REPOSITORY: DatabaseRepository
lateinit var EMAIL:String
lateinit var PASSWORD:String
const val TYPE_DATABASE ="type_database"
const val TYPE_FIREBASE="type_firebase"
const val TYPE_ROOM ="type_room"
const val ID_FIREBASE="idFirebase"
const val NAME="name"
const val TEXT="text"