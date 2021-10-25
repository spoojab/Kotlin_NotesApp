package com.e.kotlin_notesapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.e.database.Note
import com.e.database.NoteDatabase
import com.e.kotlin_notesapp.R.layout.fragment_home
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         recyclerView=view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.setHasFixedSize(true)


        launch {

            context?.let {
                val notes=NoteDatabase(it).getNoteDao().getNotes()
                recyclerView.adapter=RecyclerViewAdapter(notes)



            }
        }

        val addbtn:FloatingActionButton=view.findViewById(R.id.addbutton)
        addbtn.setOnClickListener{
            val action=HomeFragmentDirections.addNote()
            Navigation.findNavController(it).navigate(action)


        }



    }




}