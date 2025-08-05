package com.example.nhamngocduc.domain.manager

class SessionManager {
    var currentUsername: String? = null
        private set

    fun login(username: String) {
        currentUsername = username
    }

    fun logout() {
        currentUsername = null
    }
}