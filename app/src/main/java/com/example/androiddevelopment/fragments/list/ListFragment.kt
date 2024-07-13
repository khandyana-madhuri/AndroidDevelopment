package com.example.androiddevelopment.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androiddevelopment.R
import com.example.androiddevelopment.data.NotesViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListFragment : Fragment() {
    private lateinit var viewModel: NotesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)
        val floatingBtn = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        // recycler view
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = ListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // NoteViewModel
        viewModel = ViewModelProvider(this)[NotesViewModel::class.java]
        viewModel.readAllData.observe(viewLifecycleOwner) {
            adapter.setData(it)
        }

        floatingBtn.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        return view
    }
}