package domain

import com.yurcha.mybirthdayapp.domain.utils.calculateAge
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class AgeCalculatorTest {

    @Test
    fun `calculates age in months when baby is less than a year old`() {
        val now = LocalDate.of(2025, 6, 13)
        val birthDate = now.minusMonths(7)
        val birthdayMillis = birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val (age, isYears) = calculateAge(birthdayMillis, now)

        assertEquals(7, age)
        assertFalse(isYears)
    }

    @Test
    fun `calculates age in years when baby is older than a year`() {
        val now = LocalDate.of(2025, 6, 13)
        val birthDate = now.minusYears(2).minusMonths(3)
        val birthdayMillis = birthDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        val (age, isYears) = calculateAge(birthdayMillis, now)

        assertEquals(2, age)
        assertTrue(isYears)
    }
}