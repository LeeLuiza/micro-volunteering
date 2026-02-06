package com.example.micro_volunteering.domain.model

enum class CategoryTask(val category: String) {
    ECOLOGY("ecology"),
    ANIMAL("animal"),
    SOCIAL_ASSIST("social assist"),
    CAR("car"),
    MENTAL("mental"),
    EVENT("event"),
    OTHER("other");

    companion object {
        fun fromString(value: String): CategoryTask {
            return entries.find {
                it.category.equals(value, ignoreCase = true)
            } ?: OTHER
        }
    }
}