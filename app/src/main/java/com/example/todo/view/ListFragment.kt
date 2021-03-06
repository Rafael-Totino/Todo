package com.example.todo.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration

import com.example.todo.databinding.FragmentListBinding
import com.example.todo.viewmodel.MainViewModel

class ListFragment : Fragment() {
    private lateinit var binding: FragmentListBinding
    private val viewModel: MainViewModel by activityViewModels()
    private val args: ListFragmentArgs by navArgs()

    enum class FilterCriteria { ALL, COMPLETED, OVERDUE, TAG }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        binding.recyclerViewList.apply {

            val tasks = when (args.filterCriteria as FilterCriteria){
                FilterCriteria.ALL -> viewModel.getAll()
                FilterCriteria.OVERDUE -> viewModel.getOverdue()
                FilterCriteria.COMPLETED -> viewModel.getCompleted()
                FilterCriteria.TAG -> viewModel.getByTag(args.tag)
            }.sortedBy { task -> task.deadline}
            adapter = TaskAdapter(tasks)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }
    }
}
