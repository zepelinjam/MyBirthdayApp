package com.yurcha.mybirthdayapp.domain.utils

import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

fun calculateAge(birthdayMillis: Long, now: LocalDate = LocalDate.now()): Pair<Int, Boolean> {
    val birthDate = Instant.ofEpochMilli(birthdayMillis)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val period = Period.between(birthDate, now)
    val isYears = period.years > 0
    val ageValue = period.years.takeIf { it > 0 } ?: period.months

    return ageValue to isYears
}