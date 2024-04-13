package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.todoapp.R
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailTaskActivity : AppCompatActivity() {
    private val viewModel: DetailTaskViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val title = findViewById<TextInputEditText>(R.id.detail_ed_title)
        val desc = findViewById<TextInputEditText>(R.id.detail_ed_description)
        val duedate = findViewById<TextInputEditText>(R.id.detail_ed_due_date)

        val taskId = intent.getIntExtra(TASK_ID, 0)
        viewModel.setTaskId(taskId)
        viewModel.task.observe(this){ task ->
            task?.let {
                title.setText(task.title)
                desc.setText(task.description)
                duedate.setText(DateConverter.convertMillisToString(task.dueDateMillis))
            }
        }

        findViewById<Button>(R.id.btn_delete_task).setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.deleteTask()
            }
            finish()
        }
    }
}