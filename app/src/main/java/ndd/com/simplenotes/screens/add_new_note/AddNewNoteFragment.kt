package ndd.com.simplenotes.screens.add_new_note

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ndd.com.simplenotes.R
import ndd.com.simplenotes.databinding.FragmentAddNewNoteBinding
import ndd.com.simplenotes.models.AppNote
import ndd.com.simplenotes.utilits.APP_ACTIVITY
import ndd.com.simplenotes.utilits.hideKeyboard
import ndd.com.simplenotes.utilits.showToast


class AddNewNoteFragment : Fragment() {
    private var _binding: FragmentAddNewNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var mViewModel: AddNewNoteViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddNewNoteBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    private fun initialization() {
        mViewModel = ViewModelProvider(this).get(AddNewNoteViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            val name = binding.etInputNoteName.text.toString()
            val body = binding.etInputNoteBody.text.toString()
            if (name.isEmpty()) {
                showToast(getString(R.string.toast_enter_note_name))
            } else {
                mViewModel.insert(AppNote(name = name, text = body)) {
                    lifecycleScope.launch(Dispatchers.Main) {
                        APP_ACTIVITY.navController.navigate(R.id.action_addNewNoteFragment_to_mainFragment)
                    }
                }
                hideKeyboard(this.context, this.view)


            }
        }
    }

}