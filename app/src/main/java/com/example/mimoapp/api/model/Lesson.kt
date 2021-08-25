package com.example.mimoapp.api.model

import java.io.Serializable

data class Lesson(
    val content: List<Content>,
    val id: Int,
    val input: Input?
):Serializable