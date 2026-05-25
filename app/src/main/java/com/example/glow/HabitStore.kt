package com.example.glow

import androidx.compose.runtime.mutableStateListOf

class HabitStore {
    val habits = mutableStateListOf<HabitItem>()

    fun addHabit(title: String, subtitle: String) {
        habits.add(
            HabitItem(
                id = System.currentTimeMillis().toString(),
                title = title,
                subtitle = subtitle,
                isComplete = false
            )
        )
    }

    fun toggleHabit(id: String) {
        val index = habits.indexOfFirst { it.id == id }
        if (index != -1) {
            habits[index] = habits[index].copy(isComplete = !habits[index].isComplete)
        }
    }

    fun seedIfEmpty() {
        if (habits.isNotEmpty()) return

        habits.addAll(
            listOf(
                HabitItem("1", "Morning Stretch", "5 minutes", false),
                HabitItem("2", "Drink Water", "Start with one glass", true),
                HabitItem("3", "Evening Walk", "10 minutes", false)
            )
        )
    }
}