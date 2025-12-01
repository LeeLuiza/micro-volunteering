package com.example.micro_volunteering.data.mapper

import com.example.micro_volunteering.data.remote.dto.response.UserResponse
import com.example.micro_volunteering.domain.model.User

class UserMapper {

    fun toDomain(user: UserResponse) = User(
        id = user.id,
        name = user.name,
        password = user.password,
        phone = user.phone,
        age = user.age,
        city = user.city,
    )
}