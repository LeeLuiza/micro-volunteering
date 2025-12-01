package com.example.micro_volunteering.data.mapper

import com.example.micro_volunteering.data.remote.dto.response.TaskResponse
import com.example.micro_volunteering.domain.model.Task

class TaskMapper {

    fun toDomain(task: TaskResponse) = Task(
        id = task.id
    )
}