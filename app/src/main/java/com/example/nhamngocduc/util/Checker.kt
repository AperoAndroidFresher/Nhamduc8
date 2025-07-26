package com.example.nhamngocduc.util

object Checker {
    private fun checkNoWhiteSpaceAndNotEmpty(string: String) : Boolean {
        return string
            .count{it == ' '} <= 0
                && string.isNotEmpty()
    }

    fun checkUsername(username: String): Boolean {
        return username
            .lowercase()
            .filterNot { it in 'a'..'z' || it in '0'..'9' }
            .count() <= 0
                && checkNoWhiteSpaceAndNotEmpty(username)
    }

    fun checkPassword(password: String): Boolean {
        return password
            .lowercase()
            .filterNot { it in 'a'..'z' || it in '0'..'9' }
            .count() <= 0
                && checkNoWhiteSpaceAndNotEmpty(password)
    }
    fun checkConfirmPassword(passwordConfirm: String, password: String): Boolean {
        return passwordConfirm == password
    }

    fun checkEmail(email: String): Boolean {
        return email
            .filterNot { it in 'a'..'z'
                    || it in '0'..'9'
                    || it == '.' || it == '_' || it == '-' }
            .count() <= 0
                && checkNoWhiteSpaceAndNotEmpty(email)
                && email.length > 10
    }

    fun checkEmailTail(email: String): Boolean {
        val suffix = "@apero.vn"
        return email.endsWith(suffix)
    }

    fun checkProfilePhoneNumber(phoneNumber: String) = phoneNumber.length in 10..15

    fun checkProfileUniversityName(universityName: String) = universityName.length > 3

    fun checkProfileUsername(name: String) = name.length in 1..30
}