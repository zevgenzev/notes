package ndd.com.simplenotes.screens.note

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ndd.com.simplenotes.R
import ndd.com.simplenotes.databinding.FragmentNoteBinding
import ndd.com.simplenotes.databinding.NoteItemBinding
import ndd.com.simplenotes.models.AppNote
import ndd.com.simplenotes.utilits.APP_ACTIVITY
import ndd.com.simplenotes.utilits.REPOSITORY


class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val mBinding get() = _binding!!
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

    private fun initialization() {
        setHasOptionsMenu(true)
        mBinding.tvNoteName.text = mCurrentNote.name
        mBinding.tvNoteBody.text = mCurrentNote.text
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
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}