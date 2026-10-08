package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object VihaanRepository {

    val sampleCustomer = UserProfile(
        id = "cust_001",
        name = "Rahul Sharma",
        email = "rahul.sharma@example.com",
        phone = "+91 98765 43210",
        role = UserRole.CUSTOMER,
        city = "Bhopal, MP",
        walletBalance = 1250.0,
        rewardPoints = 650,
        membership = "GOLD"
    )

    val sampleProfessional = UserProfile(
        id = "pro_001",
        name = "Rohit Kumar",
        email = "rohit.barber@vihaan.com",
        phone = "+91 98200 12345",
        role = UserRole.PROFESSIONAL,
        city = "Bhopal, MP",
        walletBalance = 4850.0,
        rewardPoints = 1200,
        membership = "PRO_VERIFIED"
    )

    val sampleAdmin = UserProfile(
        id = "admin_001",
        name = "Admin Vihaan",
        email = "admin@vihaan.demo",
        phone = "+91 99999 88888",
        role = UserRole.ADMIN,
        city = "Headquarters, Bhopal",
        walletBalance = 50000.0,
        rewardPoints = 9999,
        membership = "ADMIN"
    )

    val categories = listOf(
        ServiceCategory("cat_salon", "Salon", "face", "Haircuts, beard grooming, styling", 12),
        ServiceCategory("cat_beauty", "Beauty", "spa", "Facials, waxing, manicures & glow", 18),
        ServiceCategory("cat_spa", "Spa", "self_improvement", "Full body massage, stress relief", 8),
        ServiceCategory("cat_cleaning", "Cleaning", "cleaning_services", "Deep cleaning, bathroom & kitchen", 14),
        ServiceCategory("cat_ac", "AC Repair", "ac_unit", "Servicing, gas refill, cooling check", 9),
        ServiceCategory("cat_electric", "Electrician", "bolt", "Wiring, switchboard, inverter fix", 15),
        ServiceCategory("cat_plumber", "Plumbing", "plumbing", "Tap repairs, pipe leakage, fittings", 11),
        ServiceCategory("cat_pest", "Pest Control", "pest_control", "Bed bugs, termites, cockroach control", 6),
        ServiceCategory("cat_emergency", "Emergency", "emergency", "90-min urgent doorstep assistance", 5)
    )

    val services = listOf(
        HomeService(
            id = "srv_haircut",
            categoryId = "cat_salon",
            title = "Premium Haircut",
            shortDescription = "Expert hairstyling, wash & grooming at home",
            fullDescription = "Experience a salon-grade haircut tailored to your face structure by top-rated certified stylists. Includes deep conditioning wash, personalized cut, blow-dry styling, and refreshing finishing mist.",
            price = 299.0,
            originalPrice = 399.0,
            durationMinutes = 60,
            rating = 4.8,
            reviewCount = 1256,
            isPopular = true,
            includes = listOf("Hair Wash & Conditioning", "Custom Precision Haircut", "Hair Styling & Blow Dry", "Finishing Cologne Mist", "Post-service cleanup"),
            excludes = listOf("Chemical Hair Coloring", "Beard Styling (Available as add-on)"),
            faqs = listOf(
                "Will the barber bring tools?" to "Yes, full sterilized kit with disposable cape & hygiene sheets.",
                "How much space is needed?" to "A single chair near a mirror or power plug is sufficient."
            )
        ),
        HomeService(
            id = "srv_deep_cleaning",
            categoryId = "cat_cleaning",
            title = "Deep Home Cleaning",
            shortDescription = "Full home sanitization, floor scrubbing & stain removal",
            fullDescription = "Comprehensive deep cleaning covering living areas, bedrooms, kitchen degreasing, bathroom scrubbing with eco-friendly specialized chemicals and industrial vacuum cleaners.",
            price = 599.0,
            originalPrice = 899.0,
            durationMinutes = 180,
            rating = 4.9,
            reviewCount = 2840,
            isPopular = true,
            includes = listOf("Deep floor scrubbing", "Appliance exterior degreasing", "Cobweb removal & wall dusting", "Bathroom sanitization"),
            excludes = listOf("Balcony pressure wash", "Interior cupboard decluttering"),
            faqs = listOf(
                "Do I need to supply chemicals?" to "No, our verified team brings all certified eco-safe solutions."
            )
        ),
        HomeService(
            id = "srv_body_spa",
            categoryId = "cat_spa",
            title = "Full Body Spa & Massage",
            shortDescription = "Aromatherapy relaxation with soothing essential oils",
            fullDescription = "Swedish and deep-tissue relaxation techniques by verified certified therapists. Uses warm almond and lavender oils for holistic rejuvenation and muscle pain release.",
            price = 799.0,
            originalPrice = 1199.0,
            durationMinutes = 75,
            rating = 4.9,
            reviewCount = 920,
            isPopular = true,
            includes = listOf("Warm herbal oil massage", "Aromatherapy diffuser setup", "Disposable linen kit", "Head & neck acupressure"),
            excludes = listOf("Steam bath facility")
        ),
        HomeService(
            id = "srv_ac_service",
            categoryId = "cat_ac",
            title = "AC Power Jet Service",
            shortDescription = "High-pressure jet wash, cooling test & gas check",
            fullDescription = "Intense power-jet cleaning of indoor and outdoor coils. Eliminates 99% bacteria, foul odor, and improves cooling efficiency by up to 40%.",
            price = 499.0,
            originalPrice = 699.0,
            durationMinutes = 45,
            rating = 4.7,
            reviewCount = 3120,
            isPopular = true,
            includes = listOf("Indoor unit power-jet wash", "Outdoor unit deep clean", "Cooling & gas pressure audit", "Drain tray cleaning"),
            excludes = listOf("Gas refilling charges (charged per PSI if required)")
        ),
        HomeService(
            id = "srv_plumbing",
            categoryId = "cat_plumber",
            title = "Tap & Pipe Leak Repair",
            shortDescription = "Fix leaking taps, drainage clog & flush cisterns",
            fullDescription = "Professional plumbing repair for kitchen sinks, bathroom fixtures, overhead tank valves, and water pressure balancing.",
            price = 199.0,
            originalPrice = 299.0,
            durationMinutes = 40,
            rating = 4.6,
            reviewCount = 850,
            includes = listOf("Leakage diagnosis", "Fixture tight fitting / seal", "Test run under pressure"),
            excludes = listOf("Cost of replacement pipe/tap hardware")
        ),
        HomeService(
            id = "srv_electrician",
            categoryId = "cat_electric",
            title = "Emergency Electrical Fix",
            shortDescription = "Short circuit, MCB tripping, fan & switch repairs",
            fullDescription = "Rapid diagnostics of electrical faults, circuit breaker tripping, switchboard wiring, and heavy appliance socket testing.",
            price = 199.0,
            originalPrice = 299.0,
            durationMinutes = 35,
            rating = 4.8,
            reviewCount = 1420,
            includes = listOf("Multi-meter circuit check", "Safe insulated repair", "Load balance inspection"),
            excludes = listOf("Cost of MCB or copper wiring supplies")
        )
    )

    val sampleAddresses = listOf(
        SavedAddress("addr_1", "Home", "123, Shakti Nagar, Near Chetak Bridge", "Bhopal", "462024", landmark = "Opposite State Bank", isDefault = true),
        SavedAddress("addr_2", "Work", "Tech Park Tower B, 4th Floor, MP Nagar Zone 2", "Bhopal", "462011", landmark = "Near Dainik Bhaskar", isDefault = false)
    )

    val sampleProfessionalRohit = Professional(
        id = "pro_rohit",
        name = "Rohit Kumar",
        role = "Expert Barber & Grooming Stylist",
        rating = 4.9,
        reviewsCount = 1240,
        experienceYears = 5,
        completedJobs = 1240,
        isVerified = true,
        phone = "+91 98200 12345",
        languages = listOf("Hindi", "English"),
        vehicleInfo = "Honda Activa (MP 04 AB 1234)",
        kycStatus = "VERIFIED"
    )

    val sampleCoupons = listOf(
        Coupon("FIRST20", "20% OFF", "Enjoy 20% flat discount on your very first booking", 20, 100.0, 249.0, "31 Dec 2026"),
        Coupon("HOME150", "Flat ₹150 OFF", "Save ₹150 on home deep cleaning packages", 25, 150.0, 499.0, "30 Nov 2026"),
        Coupon("CLEAN100", "Flat ₹100 OFF", "Special monsoon cleaning discount across all services", 15, 100.0, 399.0, "15 Nov 2026")
    )

    // Reactive states
    val currentUserState = MutableStateFlow(sampleCustomer)
    val savedAddressesState = MutableStateFlow(sampleAddresses)
    val selectedAddressState = MutableStateFlow(sampleAddresses.first())
    val couponsState = MutableStateFlow(sampleCoupons)
    val appliedCouponState = MutableStateFlow<Coupon?>(sampleCoupons.first())

    val currentBookingState = MutableStateFlow(
        Booking(
            id = "VH24561",
            userId = "cust_001",
            service = services.first(),
            professional = sampleProfessionalRohit,
            date = "24 May 2024",
            timeSlot = "11:00 AM",
            address = sampleAddresses.first(),
            status = BookingStatus.ON_THE_WAY,
            subtotal = 299.0,
            discount = 60.0,
            tax = 18.0,
            totalAmount = 257.0,
            paymentMethod = "UPI (Google Pay)",
            paymentStatus = "PAID",
            estimatedArrivalMins = 18
        )
    )

    val bookingHistoryState = MutableStateFlow(
        listOf(
            Booking(
                id = "VH24561",
                userId = "cust_001",
                service = services.first(),
                professional = sampleProfessionalRohit,
                date = "24 May 2024",
                timeSlot = "11:00 AM",
                address = sampleAddresses.first(),
                status = BookingStatus.ON_THE_WAY,
                subtotal = 299.0,
                discount = 60.0,
                tax = 18.0,
                totalAmount = 257.0,
                paymentMethod = "UPI (Google Pay)"
            ),
            Booking(
                id = "VH23908",
                userId = "cust_001",
                service = services[1], // Deep Cleaning
                professional = Professional("pro_neha", "Neha Sharma", "Senior Housekeeping Specialist", 4.8, 620, 4, 620),
                date = "12 May 2024",
                timeSlot = "02:00 PM",
                address = sampleAddresses.first(),
                status = BookingStatus.COMPLETED,
                subtotal = 599.0,
                discount = 100.0,
                tax = 36.0,
                totalAmount = 535.0,
                paymentMethod = "Vihaan Wallet"
            ),
            Booking(
                id = "VH22140",
                userId = "cust_001",
                service = services[3], // AC Service
                professional = Professional("pro_arjun", "Arjun Singh", "Certified HVAC Engineer", 4.9, 940, 6, 940),
                date = "18 Apr 2024",
                timeSlot = "10:00 AM",
                address = sampleAddresses.first(),
                status = BookingStatus.COMPLETED,
                subtotal = 499.0,
                discount = 50.0,
                tax = 27.0,
                totalAmount = 476.0,
                paymentMethod = "Credit Card"
            )
        )
    )

    val walletTransactionsState = MutableStateFlow(
        listOf(
            WalletTransaction("tx_01", "Booking Cashback", "24 May 2024", 120.0, true, "CASHBACK"),
            WalletTransaction("tx_02", "Referral Bonus", "23 May 2024", 200.0, true, "REFERRAL"),
            WalletTransaction("tx_03", "Used on Booking #VH23908", "22 May 2024", 299.0, false, "BOOKING_PAYMENT"),
            WalletTransaction("tx_04", "Added via UPI", "15 May 2024", 1000.0, true, "TOPUP")
        )
    )

    val chatMessagesState = MutableStateFlow(
        listOf(
            ChatMessage("msg_1", "System", false, "Rohit Kumar has been assigned to your booking.", "10:32 AM", true),
            ChatMessage("msg_2", "Rohit Kumar", false, "Namaste Rahul ji! I have started from MP Nagar and will reach in 18 minutes.", "10:45 AM"),
            ChatMessage("msg_3", "Rahul Sharma", true, "Okay Rohit, our flat is on the 2nd floor, bell is working.", "10:47 AM"),
            ChatMessage("msg_4", "Rohit Kumar", false, "Understood sir! I have disposable hygiene kit with me.", "10:48 AM")
        )
    )

    val supportTicketsState = MutableStateFlow(
        listOf(
            SupportTicket("TCK-941", "AC Service Warranty Query", "Warranty", "MEDIUM", "OPEN", "24 May 2024", "10:15 AM"),
            SupportTicket("TCK-820", "Refund for Rescheduled Slot", "Billing", "LOW", "RESOLVED", "10 May 2024", "Resolved on 11 May")
        )
    )

    // Helper functions
    fun switchUserRole(role: UserRole) {
        val updated = when (role) {
            UserRole.CUSTOMER -> sampleCustomer
            UserRole.PROFESSIONAL -> sampleProfessional
            UserRole.ADMIN -> sampleAdmin
        }
        currentUserState.value = updated
    }

    fun addMoneyToWallet(amount: Double) {
        val current = currentUserState.value
        val newBalance = current.walletBalance + amount
        currentUserState.value = current.copy(walletBalance = newBalance)
        val newTx = WalletTransaction(
            id = "tx_${System.currentTimeMillis()}",
            title = "Added via UPI / Cards",
            date = "Today",
            amount = amount,
            isCredit = true,
            type = "TOPUP"
        )
        walletTransactionsState.value = listOf(newTx) + walletTransactionsState.value
    }

    fun createBooking(
        service: HomeService,
        date: String,
        timeSlot: String,
        address: SavedAddress,
        paymentMethod: String
    ): Booking {
        val discount = appliedCouponState.value?.let { (service.price * it.discountPercent / 100.0).coerceAtMost(it.maxDiscount) } ?: 0.0
        val tax = (service.price - discount) * 0.18
        val total = service.price - discount + tax

        val booking = Booking(
            id = "VH${(10000..99999).random()}",
            userId = currentUserState.value.id,
            service = service,
            professional = sampleProfessionalRohit,
            date = date,
            timeSlot = timeSlot,
            address = address,
            status = BookingStatus.CONFIRMED,
            subtotal = service.price,
            discount = discount,
            tax = tax,
            totalAmount = total,
            paymentMethod = paymentMethod,
            paymentStatus = "PAID",
            estimatedArrivalMins = 18
        )
        currentBookingState.value = booking
        bookingHistoryState.value = listOf(booking) + bookingHistoryState.value
        return booking
    }

    fun updateBookingStatus(status: BookingStatus) {
        val updated = currentBookingState.value.copy(status = status)
        currentBookingState.value = updated
        bookingHistoryState.value = bookingHistoryState.value.map {
            if (it.id == updated.id) updated else it
        }
    }

    fun sendChatMessage(text: String, isCustomer: Boolean = true) {
        val msg = ChatMessage(
            id = "msg_${System.currentTimeMillis()}",
            senderName = if (isCustomer) currentUserState.value.name else "Rohit Kumar",
            isFromCustomer = isCustomer,
            message = text,
            timestamp = "Just now"
        )
        chatMessagesState.value = chatMessagesState.value + msg
    }

    fun addAddress(label: String, line: String, landmark: String, pincode: String) {
        val addr = SavedAddress(
            id = "addr_${System.currentTimeMillis()}",
            label = label,
            addressLine = line,
            city = "Bhopal",
            pincode = pincode,
            landmark = landmark
        )
        savedAddressesState.value = savedAddressesState.value + addr
        selectedAddressState.value = addr
    }

    fun addSupportTicket(issue: String, category: String) {
        val ticket = SupportTicket(
            id = "TCK-${(100..999).random()}",
            issue = issue,
            category = category,
            priority = "MEDIUM",
            status = "OPEN",
            createdAt = "Today",
            lastUpdate = "Awaiting agent assignment"
        )
        supportTicketsState.value = listOf(ticket) + supportTicketsState.value
    }
}
