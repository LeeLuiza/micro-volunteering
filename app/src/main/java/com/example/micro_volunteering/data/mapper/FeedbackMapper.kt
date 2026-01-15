package com.example.micro_volunteering.data.mapper

import com.example.micro_volunteering.data.remote.dto.response.FeedbackResponse
import com.example.micro_volunteering.domain.model.Feedback

class FeedbackMapper {

    fun toDomain(dto: FeedbackResponse) = Feedback(
        id = dto.id,
        text = dto.text,
        nameUser = dto.nameUser,
        countStars = dto.countStars
    )
}