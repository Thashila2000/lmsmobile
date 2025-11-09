package com.example.lmsmobile.data.repository


import com.example.lmsmobile.data.local.NoteDao
import com.example.lmsmobile.data.model.Note

class NoteRepository(private val dao: NoteDao) {

    fun getNotes() = dao.getNotes()
    suspend fun addNote(note: Note) = dao.addNote(note)
    suspend fun deleteNote(note: Note) = dao.deleteNote(note)
}
