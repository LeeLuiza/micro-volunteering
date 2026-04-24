package com.example.micro_volunteering.data.mapper

import com.example.micro_volunteering.data.remote.dto.response.TaskResponse
import com.example.micro_volunteering.domain.model.CategoryTask
import com.example.micro_volunteering.domain.model.Task
import javax.inject.Inject

class TaskMapper @Inject constructor() {

    fun toDomain(task: TaskResponse) = Task(
        id = task.id,
        title = task.title,
        description = task.description,
        address = task.address,
        organizationName = task.organizationName,
        category = CategoryTask.fromString(task.category),
        volunteersNeeded = task.volunteersNeeded,
        date = task.date
    )
}