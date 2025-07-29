package com.example.nhamngocduc.util

object Checker {
    fun checkInputsNotEmpty(vararg input: String) : Boolean {
        for (s in input) {
            if (s.isEmpty()) return false
        }

        return true
    }
    private fun noWhiteSpace(string: String) : Boolean {
        return string
            .count{it == ' '} <= 0
    }

    fun checkUsername(username: String): Boolean {
        return username
            .lowercase()
            .filterNot { it in 'a'..'z' || it in '0'..'9' }
            .count() <= 0
                && noWhiteSpace(username)
    }

    fun checkPassword(password: String): Boolean {
        return password
            .lowercase()
            .filterNot { it in 'a'..'z' || it in '0'..'9' }
            .count() <= 0
                && noWhiteSpace(password)
    }
    fun isConfirmPasswordMatching(passwordConfirm: String, password: String): Boolean {
        return passwordConfirm == password
    }

    fun checkEmail(email: String): Boolean {
        return email
            .filterNot { it in 'a'..'z'
                    || it in '0'..'9'
                    || it == '.' || it == '_' || it == '-' }
            .count() <= 0
                && noWhiteSpace(email)
                && email.length > 10
    }

    fun checkEmailTail(email: String): Boolean {
        val suffix = "@apero.vn"
        return email.endsWith(suffix)
    }

    fun checkProfilePhoneNumber(phoneNumber: String) = phoneNumber.trim().length in 10..15

    fun checkProfileUniversityName(universityName: String) = universityName.trim().length >= 3

    fun checkProfileUsername(name: String) = name.trim().length in 3..30
}