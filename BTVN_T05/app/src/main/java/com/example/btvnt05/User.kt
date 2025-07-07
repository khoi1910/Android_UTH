package com.example.btvnt05

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val dateOfBirth: String = "Chưa cập nhật",
    val profileImageUrl: String = ""
)

enum class AuthState {
    LOADING, SUCCESS, ERROR, IDLE
}