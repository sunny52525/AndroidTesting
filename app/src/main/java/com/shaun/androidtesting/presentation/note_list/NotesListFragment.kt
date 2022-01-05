package com.shaun.androidtesting.presentation.note_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shaun.androidtesting.R
import com.shaun.androidtesting.common.Resource
import com.shaun.androidtesting.common.showToast
import com.shaun.androidtesting.databinding.FragmentNotesListBinding
import com.shaun.androidtesting.presentation.note_list.adapter.NoteListAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<NoteListViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotesListBinding.bind(view)

        binding.addNoteButton.setOnClickListener {
            val isEdit = false
            val noteId = 0L
            val action = NotesListFragmentDirections.actionNotesListFragmentToNoteEditFragment(
                isEdit,
                noteId
            )
            findNavController().navigate(action)

        }


        setObservers()

    }

    private fun setObservers() {
        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            notes?.let {
                when (it) {
                    is Resource.Error -> {
                        showToast(it.message)
                    }
                    is Resource.Idle -> Unit
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false


                        notes.data?.let {
                            val notesAdapter = NoteListAdapter(noteList = notes.data)
                            binding.recyclerView.adapter = notesAdapter
                            binding.recyclerView.layoutManager =
                                LinearLayoutManager(requireContext())


                        }
                    }
                }
            }
        }


        viewModel.showErrorMessage.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    showToast(viewModel.errorMessage)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}