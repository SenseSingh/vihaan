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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VihaanRepository
import com.example.model.Booking
import com.example.model.BookingStatus
import com.example.model.ChatMessage
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BookingConfirmationScreen(
    booking: Booking,
    onTrackBooking: () -> Unit,
    onBackToHome: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("booking_confirmation_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Confirmation Checkmark Badge
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(VihaanRoyalBlue),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Success",
                    tint = VihaanWhite,
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Booking Confirmed!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = VihaanDeepNavy
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Your booking has been placed\nsuccessfully",
                fontSize = 14.sp,
                color = VihaanSecondaryText,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(28.dp))

            // Booking Details Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = VihaanWhite),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Booking ID",
                        fontSize = 12.sp,
                        color = VihaanSecondaryText
                    )
                    Text(
                        text = booking.id,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "${booking.date} • ${booking.timeSlot}",
                        fontSize = 13.sp,
                        color = VihaanRoyalBlue,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = booking.service.title,
                        fontSize = 13.sp,
                        color = VihaanDarkText
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = "We will notify you before we arrive",
                        fontSize = 12.sp,
                        color = VihaanSecondaryText
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Buttons
            Button(
                onClick = onTrackBooking,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("track_booking_button"),
                colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Track Booking",
                    color = VihaanWhite,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onBackToHome,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("back_to_home_button"),
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(VihaanBorder))
            ) {
                Text(
                    text = "Back to Home",
                    color = VihaanDeepNavy,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun LiveTrackingScreen(
    onBack: () -> Unit,
    onOpenChat: () -> Unit,
    onViewInvoice: () -> Unit
) {
    val booking by VihaanRepository.currentBookingState.collectAsState()
    var arrivalMins by remember { mutableIntStateOf(booking.estimatedArrivalMins) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("live_tracking_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 40.dp)
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
                        text = "Live Tracking",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                }
            }

            // Status Banner with Scooter Animation
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Your Service is",
                                fontSize = 12.sp,
                                color = VihaanSecondaryText
                            )
                            Text(
                                text = when (booking.status) {
                                    BookingStatus.CONFIRMED -> "Confirmed"
                                    BookingStatus.PROFESSIONAL_ASSIGNED -> "Assigned"
                                    BookingStatus.ON_THE_WAY -> "On The Way"
                                    BookingStatus.SERVICE_STARTED -> "In Progress"
                                    BookingStatus.COMPLETED -> "Service Completed"
                                    BookingStatus.CANCELLED -> "Cancelled"
                                },
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = VihaanDeepNavy
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (booking.status == BookingStatus.COMPLETED) "Completed at 11:45 AM" else "Arriving in $arrivalMins mins",
                                fontSize = 13.sp,
                                color = VihaanRoyalBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(VihaanSurfaceLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (booking.status == BookingStatus.COMPLETED) Icons.Filled.CheckCircle else Icons.Filled.TwoWheeler,
                                contentDescription = "On the way",
                                tint = VihaanRoyalBlue,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }
            }

            // Simulated Map Route Graphic
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    shape = RoundedCornerShape(18.dp),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFFE2E8F0)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = "📍 MP Nagar Hub",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VihaanDarkText
                                    )
                                    Text(text = "Barber Rohit started", fontSize = 10.sp, color = VihaanSecondaryText)
                                }

                                Icon(
                                    imageVector = Icons.Filled.Navigation,
                                    contentDescription = "Direction",
                                    tint = VihaanRoyalBlue,
                                    modifier = Modifier.size(24.dp)
                                )

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = "🏠 Shakti Nagar",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VihaanDarkText
                                    )
                                    Text(text = "Your Doorstep", fontSize = 10.sp, color = VihaanSecondaryText)
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Route progress bar
                            LinearProgressIndicator(
                                progress = { if (booking.status == BookingStatus.COMPLETED) 1f else 0.65f },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(CircleShape),
                                color = VihaanRoyalBlue,
                                trackColor = VihaanWhite
                            )

                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Live GPS coordinates synchronized • 2.4 km away",
                                fontSize = 10.sp,
                                color = VihaanSecondaryText
                            )
                        }
                    }
                }
            }

            // Timeline Steps
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Text(
                            text = "Service Progress",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanDeepNavy
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        TrackingTimelineRow(
                            title = "Confirmed",
                            time = "10:30 AM",
                            isDone = true,
                            isCurrent = false
                        )
                        TrackingTimelineRow(
                            title = "Professional Assigned",
                            time = "10:32 AM",
                            isDone = true,
                            isCurrent = false
                        )
                        TrackingTimelineRow(
                            title = "On The Way",
                            time = "10:45 AM",
                            isDone = booking.status >= BookingStatus.ON_THE_WAY,
                            isCurrent = booking.status == BookingStatus.ON_THE_WAY
                        )
                        TrackingTimelineRow(
                            title = "Service Started",
                            time = if (booking.status >= BookingStatus.SERVICE_STARTED) "11:05 AM" else "--:--",
                            isDone = booking.status >= BookingStatus.SERVICE_STARTED,
                            isCurrent = booking.status == BookingStatus.SERVICE_STARTED
                        )
                        TrackingTimelineRow(
                            title = "Completed",
                            time = if (booking.status == BookingStatus.COMPLETED) "11:45 AM" else "--:--",
                            isDone = booking.status == BookingStatus.COMPLETED,
                            isCurrent = false,
                            isLast = true
                        )
                    }
                }
            }

            // Professional Profile Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(52.dp)
                                    .clip(CircleShape)
                                    .background(VihaanDeepNavy),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "RK",
                                    color = VihaanGold,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = booking.professional.name,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanDeepNavy
                                )
                                Text(
                                    text = booking.professional.role,
                                    fontSize = 11.sp,
                                    color = VihaanSecondaryText
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(top = 2.dp)
                                ) {
                                    Icon(Icons.Filled.Star, contentDescription = "Rating", tint = VihaanGold, modifier = Modifier.size(13.dp))
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = "${booking.professional.rating} (1,240 jobs)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = VihaanDarkText
                                    )
                                }
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            // Call Button
                            IconButton(
                                onClick = { /* simulated phone call */ },
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(VihaanSurfaceLight)
                                    .border(1.dp, VihaanBorder, CircleShape)
                            ) {
                                Icon(Icons.Filled.Phone, contentDescription = "Call", tint = VihaanRoyalBlue, modifier = Modifier.size(18.dp))
                            }
                            // Chat Button
                            IconButton(
                                onClick = onOpenChat,
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(VihaanDeepNavy)
                                    .testTag("open_chat_button")
                            ) {
                                Icon(Icons.Filled.Chat, contentDescription = "Chat", tint = VihaanGold, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }

            // Quick Status Transition Actions (Demo Controls)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanSurfaceLight),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(
                            text = "Interactive Demo Controls",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanRoyalBlue
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    VihaanRepository.updateBookingStatus(BookingStatus.SERVICE_STARTED)
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = VihaanRoyalBlue),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(vertical = 4.dp)
                            ) {
                                Text("Start Service", fontSize = 11.sp)
                            }

                            Button(
                                onClick = {
                                    VihaanRepository.updateBookingStatus(BookingStatus.COMPLETED)
                                },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = VihaanSuccess),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(vertical = 4.dp)
                            ) {
                                Text("Complete Job", fontSize = 11.sp)
                            }
                        }

                        if (booking.status == BookingStatus.COMPLETED) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = onViewInvoice,
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = VihaanGold),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("View Digital Invoice", color = VihaanDeepNavy, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TrackingTimelineRow(
    title: String,
    time: String,
    isDone: Boolean,
    isCurrent: Boolean,
    isLast: Boolean = false
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(28.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(
                        when {
                            isDone -> VihaanSuccess
                            isCurrent -> VihaanRoyalBlue
                            else -> VihaanBorder
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isDone) {
                    Icon(Icons.Filled.Check, contentDescription = "Done", tint = VihaanWhite, modifier = Modifier.size(12.dp))
                } else if (isCurrent) {
                    Box(modifier = Modifier.size(6.dp).clip(CircleShape).background(VihaanWhite))
                }
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(24.dp)
                        .background(if (isDone) VihaanSuccess else VihaanBorder)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isLast) 0.dp else 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 13.sp,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                color = if (isDone || isCurrent) VihaanDarkText else VihaanSecondaryText
            )
            Text(
                text = time,
                fontSize = 11.sp,
                color = VihaanSecondaryText
            )
        }
    }
}

@Composable
fun ChatScreen(
    onBack: () -> Unit
) {
    val messages by VihaanRepository.chatMessagesState.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val quickReplies = listOf(
        "Where are you?",
        "I am waiting.",
        "How long will you take?",
        "I have shared my location."
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("chat_screen")
    ) {
        // Chat Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = VihaanDeepNavy)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = VihaanWhite)
                }
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(VihaanGold),
                    contentAlignment = Alignment.Center
                ) {
                    Text("RK", color = VihaanDeepNavy, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Rohit Kumar",
                        color = VihaanWhite,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Text(
                        text = "Online • Expert Barber",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                }
                IconButton(onClick = { /* call */ }) {
                    Icon(Icons.Filled.Phone, contentDescription = "Call", tint = VihaanGold)
                }
            }
        }

        // Messages List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { msg ->
                if (msg.isStatusUpdate) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = msg.message,
                            fontSize = 11.sp,
                            color = VihaanSecondaryText,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(VihaanSurfaceLight)
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = if (msg.isFromCustomer) Arrangement.End else Arrangement.Start
                    ) {
                        Card(
                            shape = RoundedCornerShape(
                                topStart = 14.dp,
                                topEnd = 14.dp,
                                bottomStart = if (msg.isFromCustomer) 14.dp else 2.dp,
                                bottomEnd = if (msg.isFromCustomer) 2.dp else 14.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = if (msg.isFromCustomer) VihaanRoyalBlue else VihaanWhite
                            ),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Column(modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)) {
                                Text(
                                    text = msg.message,
                                    fontSize = 13.sp,
                                    color = if (msg.isFromCustomer) VihaanWhite else VihaanDarkText
                                )
                                Text(
                                    text = msg.timestamp,
                                    fontSize = 9.sp,
                                    color = if (msg.isFromCustomer) Color.LightGray else VihaanSecondaryText,
                                    modifier = Modifier.align(Alignment.End).padding(top = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Quick Reply Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickReplies) { reply ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(VihaanWhite)
                        .border(1.dp, VihaanBorder, RoundedCornerShape(16.dp))
                        .clickable {
                            VihaanRepository.sendChatMessage(reply, isCustomer = true)
                            coroutineScope.launch {
                                delay(1200)
                                VihaanRepository.sendChatMessage("Yes sir, arriving in 15 minutes. Taking the flyover route.", isCustomer = false)
                            }
                        }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = reply, fontSize = 11.sp, color = VihaanRoyalBlue, fontWeight = FontWeight.Medium)
                }
            }
        }

        // Input Row
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = VihaanWhite)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = { Text("Type a message...", fontSize = 13.sp) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            val text = inputText
                            inputText = ""
                            VihaanRepository.sendChatMessage(text, isCustomer = true)
                            coroutineScope.launch {
                                delay(1500)
                                VihaanRepository.sendChatMessage("Received your note! I have all sanitized tools ready.", isCustomer = false)
                            }
                        }
                    },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(VihaanDeepNavy)
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = VihaanGold)
                }
            }
        }
    }
}
