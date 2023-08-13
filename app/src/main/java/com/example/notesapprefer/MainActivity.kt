package com.example.notesapprefer

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.notesapprefer.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var btnCreateNote: Button
    private lateinit var fabAdd: FloatingActionButton
    private lateinit var recyclerNotes: RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var llNoNotes: LinearLayout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)



        btnCreateNote = binding.btnCreateNote
        fabAdd = binding.fabAdd
        recyclerNotes = binding.recyclerNotes
        llNoNotes = binding.llNoNotes
        recyclerNotes.layoutManager = GridLayoutManager(this, 2)
        databaseHelper = DatabaseHelper.getInstance(this)


        showNotes()

        fabAdd.setOnClickListener {
            val dialog = Dialog(this@MainActivity)
            dialog.setContentView(R.layout.add_note_lay)

            val edtTitle: EditText
            val edtContent: EditText
            val btnAdd: Button

            edtTitle = dialog.findViewById(R.id.edtTitle)
            edtContent = dialog.findViewById(R.id.edtContent)
            btnAdd = dialog.findViewById(R.id.btnAdd)

            btnAdd.setOnClickListener {
                val title = edtTitle.text.toString()
                val content = edtContent.text.toString()

                if (content.isNotBlank()) {
                    databaseHelper.noteDao().addNote(Note(title, content))
                    showNotes()

                    dialog.dismiss()
                } else {
                    Toast.makeText(this@MainActivity, "Please Enter Something!", Toast.LENGTH_SHORT).show()
                }
            }

            dialog.show()
        }

        btnCreateNote.setOnClickListener {
            fabAdd.performClick()
        }
    }

    fun showNotes() {
        val arrNotes = databaseHelper.noteDao().getNotes()

        if (arrNotes.isNotEmpty()) {
            recyclerNotes.visibility = View.VISIBLE
            llNoNotes.visibility = View.GONE

            recyclerNotes.adapter = RecyclerNotesAdapter(this,
                arrNotes as ArrayList<Note>, databaseHelper)
        } else {
            llNoNotes.visibility = View.VISIBLE
            recyclerNotes.visibility = View.GONE
        }
    }

//    private fun initVar() {
//        btnCreateNote = findViewById(R.id.btnCreateNote)
//        fabAdd = findViewById(R.id.fabAdd)
//        recyclerNotes = findViewById(R.id.recyclerNotes)
//        llNoNotes = findViewById(R.id.llNoNotes)
//
//        recyclerNotes.layoutManager = GridLayoutManager(this, 2)
//
//        databaseHelper = DatabaseHelper.getInstance(this)
//    }
}
