package ndd.com.simplenotes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ndd.com.simplenotes.databinding.ActivityMainBinding
import ndd.com.simplenotes.utilits.APP_ACTIVITY
import ndd.com.simplenotes.utilits.AppReferences

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        toolbar = binding.toolbar
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        setSupportActionBar(toolbar)
        title = getString(R.string.title)
        AppReferences.getPreferences(this)
    }

}