package com.example.todo.model

import com.beust.klaxon.Klaxon
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class Repository {
    private var tasks: List<Task>? = null

    private data class TaskJson(val id: Long, val tittle: String, val deadline: String, val completed: String, val tags: String){
        val formatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US)
        fun toTask() = Task(
            id,tittle,
            formatter.parse(deadline) ?: Date(),
            completed == "Y",
            tags.split(",").map { it.trim() }
        )
    }


    fun loadData(inputStream: InputStream){
        tasks = Klaxon().parseArray<TaskJson>(inputStream)?.map { it.toTask() } ?: emptyList()
    }

    private fun validTasks(): List<Task> = tasks ?: throw Exception("Repository has not been initialized")

    fun getOverdue(): List<Task> = validTasks().filter { !it.completed && it.deadline.before(Date())}

    fun getPending(): List<Task> = validTasks().filter { !it.completed }

    fun getCompleted(): List<Task> = validTasks().filter { it.completed }

    fun getTags(): List<String> = mutableSetOf<String>().also {
        getPending().forEach { task ->
            it.addAll(task.tags)
        }
    }.toList()

    /*
    *     Return a list of Tasks that contain the given tag.
    */
    fun getByTag(tag: String): List<Task> = mutableListOf<Task>().also {
        getPending().forEach { task ->
            if (task.tags.contains(tag)) it.add(task)
        }
    }

    fun getById(id: Long): Task = validTasks().find { it.id == id } ?: throw Exception("Could not find a task with the given id")
}