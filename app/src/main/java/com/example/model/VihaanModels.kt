package com.example.model

enum class UserRole {
    CUSTOMER,
    PROFESSIONAL,
    ADMIN
}

data class UserProfile(
    val id: String = "user_rahul",
    val name: String = "Rahul Sharma",
    val email: String = "rahul.sharma@example.com",
    val phone: String = "+91 98765 43210",
    val role: UserRole = UserRole.CUSTOMER,
    val city: String = "Bhopal, MP",
    val walletBalance: Double = 1250.0,
    val rewardPoints: Int = 650,
    val membership: String = "GOLD", // FREE, GOLD, PREMIUM
    val avatarUrl: String = ""
)

data class ServiceCategory(
    val id: String,
    val name: String,
    val iconName: String,
    val description: String = "",
    val serviceCount: Int = 10
)

data class HomeService(
    val id: String,
    val categoryId: String,
    val title: String,
    val shortDescription: String,
    val fullDescription: String,
    val price: Double,
    val originalPrice: Double,
    val durationMinutes: Int,
    val rating: Double,
    val reviewCount: Int,
    val isPopular: Boolean = false,
    val includes: List<String> = emptyList(),
    val excludes: List<String> = emptyList(),
    val faqs: List<Pair<String, String>> = emptyList()
)

data class Professional(
    val id: String,
    val name: String,
    val role: String,
    val rating: Double,
    val reviewsCount: Int,
    val experienceYears: Int,
    val completedJobs: Int,
    val isVerified: Boolean = true,
    val phone: String = "+91 98200 12345",
    val languages: List<String> = listOf("Hindi", "English"),
    val vehicleInfo: String = "Honda Activa (MP 04 AB 1234)",
    val kycStatus: String = "VERIFIED" // PENDING, VERIFIED, REJECTED
)

enum class BookingStatus {
    CONFIRMED,
    PROFESSIONAL_ASSIGNED,
    ON_THE_WAY,
    SERVICE_STARTED,
    COMPLETED,
    CANCELLED
}

data class SavedAddress(
    val id: String,
    val label: String, // Home, Work, Other
    val addressLine: String,
    val city: String,
    val pincode: String,
    val landmark: String = "",
    val isDefault: Boolean = false
)

data class Booking(
    val id: String = "VH24561",
    val userId: String = "user_rahul",
    val service: HomeService,
    val professional: Professional,
    val date: String = "24 May 2024",
    val timeSlot: String = "11:00 AM",
    val address: SavedAddress,
    val status: BookingStatus = BookingStatus.ON_THE_WAY,
    val subtotal: Double = 299.0,
    val discount: Double = 60.0,
    val tax: Double = 18.0,
    val convenienceFee: Double = 0.0,
    val totalAmount: Double = 257.0,
    val paymentMethod: String = "UPI (Google Pay)",
    val paymentStatus: String = "PAID",
    val estimatedArrivalMins: Int = 18,
    val createdAt: Long = System.currentTimeMillis(),
    val warrantyDays: Int = 7
)

data class WalletTransaction(
    val id: String,
    val title: String,
    val date: String,
    val amount: Double,
    val isCredit: Boolean,
    val type: String // CASHBACK, REFERRAL, BOOKING_PAYMENT, TOPUP
)

data class Coupon(
    val code: String,
    val title: String,
    val description: String,
    val discountPercent: Int,
    val maxDiscount: Double,
    val minOrderAmount: Double,
    val expiryDate: String
)

data class ChatMessage(
    val id: String,
    val senderName: String,
    val isFromCustomer: Boolean,
    val message: String,
    val timestamp: String,
    val isStatusUpdate: Boolean = false
)

data class SupportTicket(
    val id: String,
    val issue: String,
    val category: String,
    val priority: String, // LOW, MEDIUM, HIGH
    val status: String, // OPEN, IN_PROGRESS, RESOLVED
    val createdAt: String,
    val lastUpdate: String
)
