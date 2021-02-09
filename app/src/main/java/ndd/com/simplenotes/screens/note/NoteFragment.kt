package ndd.com.simplenotes.screens.note

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ndd.com.simplenotes.R
import ndd.com.simplenotes.databinding.FragmentNoteBinding
import ndd.com.simplenotes.models.AppNote
import ndd.com.simplenotes.utilits.APP_ACTIVITY
import ndd.com.simplenotes.utilits.hideKeyboard
import ndd.com.simplenotes.utilits.showToast


class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var startedNoteName: String
    private lateinit var startedNoteBody: String
    private lateinit var mViewModel: NoteFragmentViewModel
    private lateinit var mCurrentNote: AppNote

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        mCurrentNote = arguments?.getSerializable("note") as AppNote
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        initialization()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initialization() {
        setHasOptionsMenu(true)
        startedNoteName = mCurrentNote.name
        startedNoteBody = mCurrentNote.text
        mBinding.tvNoteName.setText(startedNoteName)
        mBinding.tvNoteBody.setText(startedNoteBody)
        mBinding.tvNoteName.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_UP -> {
                        mBinding.tvNoteName.isCursorVisible = true
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })
        mBinding.tvNoteBody.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_UP -> {
                        mBinding.tvNoteBody.isCursorVisible = true
                    }
                }
                return v?.onTouchEvent(event) ?: true
            }
        })


        mViewModel = ViewModelProvider(this).get(NoteFragmentViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> mViewModel.delete(mCurrentNote) {
                lifecycleScope.launch(Dispatchers.Main) {
                    APP_ACTIVITY.navController.navigate(
                        R.id.action_noteFragment_to_mainFragment
                    )
                }
            }
            R.id.menu_save -> {
                if (mBinding.tvNoteName.text.toString() == startedNoteName &&
                    mBinding.tvNoteBody.text.toString() == startedNoteBody
                ) {
                    showToast("Nothing has changed")
                } else {
                    val newNote = AppNote(id=mCurrentNote.id, name = mBinding.tvNoteName.text.toString(),
                    text = mBinding.tvNoteBody.text.toString(), idFirebase = mCurrentNote.idFirebase)
                    mViewModel.update(newNote) {
                        lifecycleScope.launch(Dispatchers.Main) {
                            hideKeyboard(context, view)
                            val bundle = Bundle()
                            bundle.putSerializable("note", newNote)
                            APP_ACTIVITY.navController.navigate(R.id.action_noteFragment_self, bundle)
                            showToast("Saved")
                        }
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}