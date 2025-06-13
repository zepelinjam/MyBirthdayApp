package com.yurcha.mybirthdayapp.domain.model

data class Baby(
    val name: String,
    val birthday: Long?,
    val photoUri: String
) {
    companion object {
        fun empty() = Baby(
            name = "",
            birthday = null,
            photoUri = ""
        )
    }
}