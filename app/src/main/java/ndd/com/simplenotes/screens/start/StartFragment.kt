package ndd.com.simplenotes.screens.start

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ndd.com.simplenotes.R
import ndd.com.simplenotes.database.firebase.AppFirebaseRepository
import ndd.com.simplenotes.databinding.FragmentStartBinding
import ndd.com.simplenotes.utilits.*

class StartFragment : Fragment() {
    private var _binding: FragmentStartBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var mViewModel: StartFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        mViewModel = ViewModelProvider(this).get(StartFragmentViewModel::class.java)

        if (AppReferences.getInitUser()) {
            mViewModel.initDatabase(AppReferences.getTypeDB()) {
                APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
            }
        } else {
            initialization()
        }
    }

    private fun initialization() {
        mBinding.btnStartRoom.setOnClickListener {
            mViewModel.initDatabase(TYPE_ROOM) {
                AppReferences.setInitUser(true)
                AppReferences.setDB(TYPE_ROOM)
                APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
            }
        }
        mBinding.btnStartFirebase.setOnClickListener {
            mBinding.btnLogin.visibility = View.VISIBLE
            mBinding.etInputEmail.visibility = View.VISIBLE
            mBinding.etInputPass.visibility = View.VISIBLE
            mBinding.btnLogin.setOnClickListener {
                val inputEmail = mBinding.etInputEmail.text.toString()
                val inputPass = mBinding.etInputPass.text.toString()
                if (inputEmail.isNotEmpty() && inputPass.isNotEmpty()) {
                    EMAIL = inputEmail
                    PASSWORD = inputPass
                    mViewModel.initDatabase(TYPE_FIREBASE) {
                        AppReferences.setInitUser(true)
                        AppReferences.setDB(TYPE_FIREBASE)
                        hideKeyboard(this.context, this.view)
                        APP_ACTIVITY.navController.navigate(R.id.action_startFragment_to_mainFragment)
                    }
                } else {
                    showToast(getString(R.string.toast_enter_email_and_pass))
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}