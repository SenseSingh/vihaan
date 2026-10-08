package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VihaanRepository
import com.example.model.Booking
import com.example.model.HomeService
import com.example.model.SavedAddress
import com.example.ui.theme.*

@Composable
fun ServiceDetailScreen(
    service: HomeService,
    onBack: () -> Unit,
    onBookNow: () -> Unit
) {
    var isFavorite by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("service_detail_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 90.dp)
        ) {
            // Top Nav
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = VihaanDeepNavy)
                    }
                    Text(
                        text = "Service Details",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                    IconButton(onClick = { isFavorite = !isFavorite }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) VihaanError else VihaanDeepNavy
                        )
                    }
                }
            }

            // Hero Image Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(VihaanDeepNavy),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = when (service.categoryId) {
                                    "cat_salon" -> Icons.Filled.ContentCut
                                    "cat_cleaning" -> Icons.Filled.CleaningServices
                                    "cat_spa" -> Icons.Filled.SelfImprovement
                                    "cat_ac" -> Icons.Filled.AcUnit
                                    else -> Icons.Filled.Build
                                },
                                contentDescription = service.title,
                                tint = VihaanGold,
                                modifier = Modifier.size(56.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Vihaan Verified Professionals",
                                color = Color.LightGray,
                                fontSize = 12.sp
                            )
                        }

                        // Popular Badge
                        if (service.isPopular) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(14.dp)
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(VihaanGold)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "★ Popular",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanDeepNavy
                                )
                            }
                        }
                    }
                }
            }

            // Title, Rating, Price Section
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = service.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )

                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Rating",
                            tint = VihaanGold,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${service.rating} (${service.reviewCount} Reviews)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = VihaanDarkText
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "₹${service.price.toInt()}",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanRoyalBlue
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "₹${service.originalPrice.toInt()}",
                            fontSize = 15.sp,
                            color = VihaanSecondaryText,
                            textDecoration = TextDecoration.LineThrough
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFDCFCE7))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "30% OFF",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = VihaanSuccess
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Meta chips: 60 mins, Expert Barber, At Home
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MetaInfoPill(icon = Icons.Filled.Schedule, text = "${service.durationMinutes} Mins")
                        MetaInfoPill(icon = Icons.Filled.Verified, text = "Expert Pro")
                        MetaInfoPill(icon = Icons.Filled.Home, text = "At Home")
                    }
                }
            }

            // Description & What's Included
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Service Description",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanDeepNavy
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = service.fullDescription,
                            fontSize = 13.sp,
                            color = VihaanSecondaryText,
                            lineHeight = 18.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Service Includes",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanDeepNavy
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        service.includes.forEach { item ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.CheckCircle,
                                    contentDescription = "Included",
                                    tint = VihaanSuccess,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = item, fontSize = 13.sp, color = VihaanDarkText)
                            }
                        }

                        if (service.excludes.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = "What's Not Included",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = VihaanDeepNavy
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            service.excludes.forEach { item ->
                                Row(
                                    modifier = Modifier.padding(vertical = 2.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Cancel,
                                        contentDescription = "Excluded",
                                        tint = VihaanSecondaryText,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(text = item, fontSize = 12.sp, color = VihaanSecondaryText)
                                }
                            }
                        }
                    }
                }
            }

            // FAQs
            if (service.faqs.isNotEmpty()) {
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Frequently Asked Questions",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = VihaanDeepNavy
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            service.faqs.forEach { (q, a) ->
                                Text(
                                    text = "Q: $q",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = VihaanDarkText
                                )
                                Text(
                                    text = a,
                                    fontSize = 12.sp,
                                    color = VihaanSecondaryText,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Sticky Bottom Book Bar
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
            colors = CardDefaults.cardColors(containerColor = VihaanWhite),
            elevation = CardDefaults.cardElevation(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 14.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Price",
                        fontSize = 11.sp,
                        color = VihaanSecondaryText
                    )
                    Text(
                        text = "₹${service.price.toInt()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                }

                Button(
                    onClick = onBookNow,
                    modifier = Modifier
                        .height(48.dp)
                        .testTag("book_now_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Book Now →",
                        color = VihaanWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MetaInfoPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(VihaanSurfaceLight)
            .border(1.dp, VihaanBorder, RoundedCornerShape(8.dp))
            .padding(horizontal = 10.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = VihaanRoyalBlue, modifier = Modifier.size(14.dp))
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = text, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = VihaanDarkText)
    }
}

@Composable
fun BookingFlowScreen(
    service: HomeService,
    onBack: () -> Unit,
    onBookingConfirmed: (Booking) -> Unit
) {
    var selectedDate by remember { mutableStateOf("25 May") }
    var selectedTime by remember { mutableStateOf("11:00 AM") }
    val savedAddresses by VihaanRepository.savedAddressesState.collectAsState()
    var selectedAddress by remember { mutableStateOf(savedAddresses.first()) }
    var selectedPayment by remember { mutableStateOf("UPI") }
    val appliedCoupon by VihaanRepository.appliedCouponState.collectAsState()

    val dates = listOf(
        "Mon" to "24",
        "Tue" to "25",
        "Wed" to "26",
        "Thu" to "27",
        "Sat" to "29"
    )

    val timeSlots = listOf(
        "09:00 AM",
        "11:00 AM",
        "01:00 PM",
        "04:00 PM",
        "07:00 PM"
    )

    val discountAmount = appliedCoupon?.let { (service.price * it.discountPercent / 100.0).coerceAtMost(it.maxDiscount) } ?: 0.0
    val totalAmount = service.price - discountAmount

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("booking_flow_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = VihaanDeepNavy)
                    }
                    Text(
                        text = "Book Your Service",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                }
            }

            // Step Progress 1-2-3-4
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    listOf("1", "2", "3", "4").forEachIndexed { idx, step ->
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(if (idx < 3) VihaanDeepNavy else VihaanBorder),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = step,
                                color = if (idx < 3) VihaanWhite else VihaanSecondaryText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (idx < 3) {
                            HorizontalDivider(
                                modifier = Modifier
                                    .width(40.dp)
                                    .padding(horizontal = 4.dp),
                                color = VihaanDeepNavy,
                                thickness = 2.dp
                            )
                        }
                    }
                }
            }

            // Step 1: Select Date
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Select Date",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = VihaanDeepNavy,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(dates) { (day, num) ->
                        val dateKey = "$num $day"
                        val isSelected = selectedDate.contains(num)
                        Card(
                            modifier = Modifier
                                .clickable { selectedDate = "$num May" }
                                .width(56.dp)
                                .testTag("date_card_$num"),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected) VihaanDeepNavy else VihaanWhite
                            ),
                            border = if (isSelected) null else CardDefaults.outlinedCardBorder()
                        ) {
                            Column(
                                modifier = Modifier.padding(vertical = 10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = day,
                                    fontSize = 11.sp,
                                    color = if (isSelected) VihaanLightGold else VihaanSecondaryText
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = num,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) VihaanWhite else VihaanDarkText
                                )
                            }
                        }
                    }
                }
            }

            // Step 2: Select Time
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Select Time",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = VihaanDeepNavy,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(timeSlots) { slot ->
                        val isSelected = selectedTime == slot
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) VihaanRoyalBlue else VihaanWhite)
                                .border(
                                    1.dp,
                                    if (isSelected) VihaanRoyalBlue else VihaanBorder,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { selectedTime = slot }
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                                .testTag("time_slot_$slot")
                        ) {
                            Text(
                                text = slot,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) VihaanWhite else VihaanDarkText
                            )
                        }
                    }
                }
            }

            // Step 3: Address
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Address",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                    Text(
                        text = "Change",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VihaanRoyalBlue
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(VihaanSurfaceLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Home,
                                contentDescription = "Home Address",
                                tint = VihaanRoyalBlue,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = selectedAddress.label,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = VihaanDarkText
                            )
                            Text(
                                text = "${selectedAddress.addressLine}, ${selectedAddress.city}",
                                fontSize = 11.sp,
                                color = VihaanSecondaryText
                            )
                        }
                    }
                }
            }

            // Step 4: Payment Method
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Payment Method",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = VihaanDeepNavy,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

                val paymentMethods = listOf("Cash", "UPI", "Card", "Wallet")
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    paymentMethods.forEach { method ->
                        val isSelected = selectedPayment == method
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) VihaanDeepNavy else VihaanWhite)
                                .border(1.dp, if (isSelected) VihaanDeepNavy else VihaanBorder, RoundedCornerShape(10.dp))
                                .clickable { selectedPayment = method }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = method,
                                fontSize = 12.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) VihaanWhite else VihaanDarkText
                            )
                        }
                    }
                }
            }

            // Order Summary
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Bill Summary",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanDeepNavy
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Service Price", fontSize = 12.sp, color = VihaanSecondaryText)
                            Text("₹${service.price.toInt()}", fontSize = 12.sp, color = VihaanDarkText)
                        }

                        if (discountAmount > 0) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Coupon Discount (${appliedCoupon?.code})", fontSize = 12.sp, color = VihaanSuccess)
                                Text("-₹${discountAmount.toInt()}", fontSize = 12.sp, color = VihaanSuccess, fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Convenience Fee", fontSize = 12.sp, color = VihaanSecondaryText)
                            Text("FREE (Care+)", fontSize = 12.sp, color = VihaanRoyalBlue, fontWeight = FontWeight.SemiBold)
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp), color = VihaanBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Total Payable", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = VihaanDeepNavy)
                            Text("₹${totalAmount.toInt()}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = VihaanRoyalBlue)
                        }
                    }
                }
            }
        }

        // Bottom Continue Button
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            colors = CardDefaults.cardColors(containerColor = VihaanWhite),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Button(
                onClick = {
                    val booking = VihaanRepository.createBooking(
                        service = service,
                        date = "$selectedDate 2024",
                        timeSlot = selectedTime,
                        address = selectedAddress,
                        paymentMethod = selectedPayment
                    )
                    onBookingConfirmed(booking)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .height(50.dp)
                    .testTag("confirm_booking_button"),
                colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Continue",
                    color = VihaanWhite,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
