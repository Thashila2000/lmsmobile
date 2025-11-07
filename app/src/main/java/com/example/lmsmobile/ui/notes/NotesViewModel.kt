package com.example.lmsmobile.ui.notes


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lmsmobile.data.local.AppDatabase
import com.example.lmsmobile.data.model.Note
import com.example.lmsmobile.data.repository.NoteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NotesViewModel(app: Application) : AndroidViewModel(app) {

    private val dao = AppDatabase.getDatabase(app).noteDao()
    private val repo = NoteRepository(dao)

    val notes = repo.getNotes().stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(), emptyList()
    )

    fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repo.addNote(Note(title = title, content = content))
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch { repo.deleteNote(note) }
    }
}
