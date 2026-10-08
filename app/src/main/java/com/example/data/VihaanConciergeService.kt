package com.example.data

import com.example.model.HomeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class ConciergeResponse(
    val reply: String,
    val suggestedActionText: String? = null,
    val targetServiceId: String? = null,
    val actionType: String? = null // NAVIGATE_SERVICE, NAVIGATE_TRACKING, NAVIGATE_WALLET, NAVIGATE_CARE, NONE
)

object VihaanConciergeService {

    suspend fun queryConcierge(prompt: String): ConciergeResponse = withContext(Dispatchers.IO) {
        val lower = prompt.trim().lowercase()

        // Natural language understanding engine with domain-specific Vihaan AI answers
        when {
            lower.contains("ac") && (lower.contains("cool") || lower.contains("serv") || lower.contains("repair") || lower.contains("leak")) -> {
                ConciergeResponse(
                    reply = "I understand your AC is facing issues! Usually this is caused by choked cooling coils, clogged air filters, or low refrigerant gas. I recommend our AC Power Jet Service (₹499) which includes complete coil wash, pressure audit, and 30-day cooling warranty.",
                    suggestedActionText = "View AC Jet Service (₹499)",
                    targetServiceId = "srv_ac_service",
                    actionType = "NAVIGATE_SERVICE"
                )
            }
            lower.contains("haircut") || lower.contains("salon") || lower.contains("barber") || lower.contains("groom") -> {
                ConciergeResponse(
                    reply = "Our top-rated grooming option is the Premium Haircut by Rohit Kumar (4.8 ★, 1,256 reviews) at ₹299 (Original ₹399, 30% OFF). It includes hair wash, customized cut, blow-dry styling, and hygiene disposable kit at your doorstep.",
                    suggestedActionText = "Book Premium Haircut (₹299)",
                    targetServiceId = "srv_haircut",
                    actionType = "NAVIGATE_SERVICE"
                )
            }
            lower.contains("clean") || lower.contains("deep clean") || lower.contains("sofa") || lower.contains("bathroom") -> {
                ConciergeResponse(
                    reply = "I found our Deep Home Cleaning package (4.9 ★, ₹599). Our certified team brings industrial vacuum machines, eco-safe sanitizers, and covers complete floor scrubbing, kitchen degreasing & bathroom sanitization.",
                    suggestedActionText = "Book Deep Home Cleaning (₹599)",
                    targetServiceId = "srv_deep_cleaning",
                    actionType = "NAVIGATE_SERVICE"
                )
            }
            lower.contains("where is") || lower.contains("track") || lower.contains("status") || lower.contains("arriving") -> {
                val booking = VihaanRepository.currentBookingState.value
                ConciergeResponse(
                    reply = "Your service professional ${booking.professional.name} is currently ${booking.estimatedArrivalMins} minutes away riding towards ${booking.address.addressLine}. You can track his live scooter movement on the map.",
                    suggestedActionText = "Open Live Tracking",
                    actionType = "NAVIGATE_TRACKING"
                )
            }
            lower.contains("wallet") || lower.contains("balance") || lower.contains("point") -> {
                val user = VihaanRepository.currentUserState.value
                ConciergeResponse(
                    reply = "Your current Vihaan Wallet balance is ₹${user.walletBalance.toInt()} and you have earned ${user.rewardPoints} reward points! You can redeem points directly at checkout for extra savings.",
                    suggestedActionText = "Open Wallet & Rewards",
                    actionType = "NAVIGATE_WALLET"
                )
            }
            lower.contains("offer") || lower.contains("coupon") || lower.contains("discount") -> {
                ConciergeResponse(
                    reply = "Active Offers today:\n• 'FIRST20' - 20% OFF on your booking\n• 'HOME150' - Flat ₹150 OFF on Deep Cleaning\n• 'CLEAN100' - Flat ₹100 OFF monsoon special\nWould you like me to apply 'FIRST20' to your cart?",
                    suggestedActionText = "Use FIRST20",
                    actionType = "APPLY_COUPON"
                )
            }
            lower.contains("emergency") || lower.contains("urgent") || lower.contains("leak") || lower.contains("pipe") || lower.contains("short circuit") -> {
                ConciergeResponse(
                    reply = "Vihaan Emergency Doorstep Services can have a verified technician at your address within 90 minutes! Available for electrical hazards, burst pipes, and lockouts.",
                    suggestedActionText = "Book 90-Min Emergency Fix",
                    actionType = "NAVIGATE_EMERGENCY"
                )
            }
            lower.contains("care") || lower.contains("membership") || lower.contains("gold") -> {
                ConciergeResponse(
                    reply = "Vihaan Care+ Gold Membership costs ₹499/month and grants you zero convenience fees, up to 30% discount on all bookings, free biannual AC/home safety inspection, and guaranteed 15-minute emergency priority slots.",
                    suggestedActionText = "Explore Vihaan Care+",
                    actionType = "NAVIGATE_CARE"
                )
            }
            else -> {
                ConciergeResponse(
                    reply = "Namaste! I am your Vihaan AI Concierge. I can assist you in booking salon, deep cleaning, electricians, emergency plumbers, or tracking your active professional Rohit Kumar in real-time.",
                    suggestedActionText = "Explore All Services",
                    actionType = "NAVIGATE_EXPLORE"
                )
            }
        }
    }
}
