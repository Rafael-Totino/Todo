package com.example.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.todo.model.Repository

/*a view Model funciona como uma API, dando acesso do repositorio(banco de dados) as outras camadas de codigo e interface*/
class MainViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val tasksFile = "tasks.json"
    }

    private val repository = Repository()

    init {
        application.resources.assets.open(tasksFile).use { repository.loadData(it)}
    }

    fun getAll() = repository.getPending()

    fun getOverdue() = repository.getOverdue()

    fun getCompleted() = repository.getCompleted()

    fun getPending() = repository.getPending()

    fun getById(id: Long) = repository.getById(id)

    fun getByTag(tag: String) = repository.getByTag(tag)

    fun getAllTags() = repository.getTags()
}