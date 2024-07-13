package com.example.androiddevelopment.fragments.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androiddevelopment.R
import com.example.androiddevelopment.data.Note
import com.example.androiddevelopment.data.NotesViewModel


class AddFragment : Fragment() {

    private lateinit var viewModel : NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]

        val saveBtn = view.findViewById<Button>(R.id.idBtn)
        saveBtn.setOnClickListener {
            insertDataToDatabase()
        }


        return view
    }

    private fun insertDataToDatabase() {
        val noteTitle = view?.findViewById<EditText>(R.id.idEdtNoteName)?.text.toString()
        val noteDesc = view?.findViewById<EditText>(R.id.idEdtNoteDesc)?.text.toString()
        if(inputCheck(noteTitle,noteDesc)) {
            val note = Note(0, noteTitle, noteDesc)
            viewModel.addNote(note)
            Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    fun inputCheck(title: String, desc: String) : Boolean {
        return !(TextUtils.isEmpty(title) && TextUtils.isEmpty(desc))
    }
}