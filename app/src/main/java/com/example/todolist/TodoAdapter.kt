package com.example.procrastinate

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.procrastinate.databinding.ItemTodoBinding

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    inner class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodotitle: TextView, isChecked: Boolean) {
        if(isChecked) {
            tvTodotitle.paintFlags = tvTodotitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodotitle.paintFlags = tvTodotitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]

        with(holder) {
            with(todos[position]) {

                // Code war nicht replizierbar, angepasst nach https://www.geeksforgeeks.org/how-to-use-view-binding-in-recyclerview-adapter-class-in-android/

                binding.tvTodoTitle.text = curTodo.title
                binding.cbDone.isChecked = curTodo.isChecked
                toggleStrikeThrough(binding.tvTodoTitle, curTodo.isChecked)
                binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
                    toggleStrikeThrough(binding.tvTodoTitle, isChecked)
                    curTodo.isChecked = !curTodo.isChecked
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}
