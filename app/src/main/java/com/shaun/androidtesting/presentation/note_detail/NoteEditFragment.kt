package com.shaun.androidtesting.presentation.note_detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shaun.androidtesting.R
import com.shaun.androidtesting.common.Resource
import com.shaun.androidtesting.common.showToast
import com.shaun.androidtesting.databinding.FragmentNoteEditBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteEditFragment : Fragment(R.layout.fragment_note_edit) {
    private var _binding: FragmentNoteEditBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<NoteEditViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNoteEditBinding.bind(view)

        setObserver()

        binding.save.setOnClickListener {


            val title = binding.title.text
            if (title.isNullOrEmpty()) {
                showToast("Title is Required")
                return@setOnClickListener
            }
            val body = binding.body.text


            if (body.isNullOrEmpty()) {
                showToast("Body is required")
                return@setOnClickListener
            }

            viewModel.addNote(title = title.toString(), body = body.toString())
        }
    }

    private fun setObserver() {

        viewModel.showErrorMessage.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    showToast(viewModel.errorMessage)
                }
            }
        }

        viewModel.noteAddStatus.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {
                    showToast(it.message)
                }
                is Resource.Idle -> Unit
                is Resource.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    findNavController().popBackStack()
                }
            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}