package com.example

import com.example.data.VihaanRepository
import com.example.model.BookingStatus
import com.example.model.UserRole
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun verifyVihaanRepositoryInitialState() {
        assertNotNull(VihaanRepository.currentUserState.value)
        assertEquals("Rahul Sharma", VihaanRepository.currentUserState.value.name)
        assertTrue(VihaanRepository.services.isNotEmpty())
        assertTrue(VihaanRepository.categories.isNotEmpty())

        val haircut = VihaanRepository.services.first()
        assertEquals("Premium Haircut", haircut.title)
        assertEquals(299.0, haircut.price, 0.01)

        val activeBooking = VihaanRepository.currentBookingState.value
        assertEquals("VH24561", activeBooking.id)
        assertEquals(BookingStatus.ON_THE_WAY, activeBooking.status)

        // Switch role test
        VihaanRepository.switchUserRole(UserRole.PROFESSIONAL)
        assertEquals("Rohit Kumar", VihaanRepository.currentUserState.value.name)

        VihaanRepository.switchUserRole(UserRole.CUSTOMER)
        assertEquals("Rahul Sharma", VihaanRepository.currentUserState.value.name)
    }
}
