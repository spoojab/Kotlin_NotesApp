package com.e.kotlin_notesapp

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.os.AsyncTask.execute
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.room.CoroutinesRoom.Companion.execute
import com.e.database.Note
import com.e.database.NoteDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch


class AddNoteFragment : BaseFragment() {

private var noteGot:Note?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val savebtn: FloatingActionButton =view.findViewById(R.id.save_button)
        val title:EditText=view.findViewById(R.id.title)
        val note:EditText=view.findViewById(R.id.note)


        arguments?.let {

            noteGot=AddNoteFragmentArgs.fromBundle(it).noteargs
            title.setText(noteGot?.title)
            note.setText(noteGot?.note)



        }
        savebtn.setOnClickListener{

            val edt_title=title.text.toString().trim()
            val edt_note=note.text.toString().trim()
            if(edt_title.isEmpty()){
                title.error="This cannot be Empty!!"
                title.requestFocus()
                return@setOnClickListener
            }
            if(edt_note.isEmpty()){
                note.error="This cannot be Empty!!"
                note.requestFocus()
                return@setOnClickListener
            }
            launch {


                context?.let {
                    val noteClass= Note(edt_title,edt_note)
                    if(noteGot==null){
                        NoteDatabase(it).getNoteDao().addnote(noteClass)
                        Toast.makeText(it,"Note Saved",Toast.LENGTH_LONG).show()
                    }
                    else{
                        noteClass.id=noteGot!!.id
                        NoteDatabase(it).getNoteDao().noteUpdate(noteClass)
                        Toast.makeText(it,"Note Updated",Toast.LENGTH_LONG).show()
                    }


                }
            }
            val action=AddNoteFragmentDirections.saveNote()
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.delete->
                if(noteGot!=null) deleteNote()
                else{
                    Toast.makeText(context,"Note is Empty !!",Toast.LENGTH_LONG).show()
                }
            R.id.share-> {
                if (noteGot == null) {

                    Toast.makeText(context,"Note is Empty !!",Toast.LENGTH_LONG).show()
                } else {
                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT, noteGot!!.title + ":" +
                                    "\n" + noteGot!!.note
                        )
                        type = "text/plain"
                    }

                    val shareIntent = Intent.createChooser(sendIntent, "Send Using")
                    startActivity(shareIntent)
                }
            }
        }

        return super.onOptionsItemSelected(item)


    }

    private fun deleteNote() {
        AlertDialog.Builder(context).apply {
            setTitle("Are you sure?")
            setMessage("Once deleted you cannot undo.")
            setPositiveButton("Yes"){_,_->
                launch{
                    context?.let {

                        NoteDatabase(it).getNoteDao().noteDelete(noteGot!!)
                        Toast.makeText(it,"Note Deleted",Toast.LENGTH_LONG).show()
                        val action=AddNoteFragmentDirections.saveNote()
                        view?.let { it1 -> Navigation.findNavController(it1).navigate(action) }
                    }
                }
            }
            setNegativeButton("No"){_,_->


            }


        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.menu,menu)

    }

   /* private fun saveNote(note:Note){
        class SaveNote:AsyncTask<Void,Void,Void>(){
            override fun doInBackground(vararg p0: Void?): Void? {
                NoteDatabase(activity!!).getNoteDao().addnote(note)
                return null
            }

            override fun onPostExecute(result: Void?) {
                super.onPostExecute(result)

                Log.d("DATABASE","Note Saved")

            }


        }
        SaveNote().execute()*/
    //}


}