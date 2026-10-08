package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.VihaanRepository
import com.example.model.Booking
import com.example.model.BookingStatus
import com.example.ui.theme.*

@Composable
fun BookingHistoryScreen(
    onBack: () -> Unit,
    onTrackBooking: (Booking) -> Unit,
    onViewInvoice: (Booking) -> Unit,
    onBookAgain: (Booking) -> Unit
) {
    val bookings by VihaanRepository.bookingHistoryState.collectAsState()
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("All", "Ongoing", "Completed")

    val filteredBookings = when (selectedTab) {
        1 -> bookings.filter { it.status != BookingStatus.COMPLETED && it.status != BookingStatus.CANCELLED }
        2 -> bookings.filter { it.status == BookingStatus.COMPLETED }
        else -> bookings
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("booking_history_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
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
                        text = "Booking History",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                }
            }

            // Tab Row
            item {
                TabRow(
                    selectedTabIndex = selectedTab,
                    containerColor = VihaanWhite,
                    contentColor = VihaanRoyalBlue,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    tabTitles.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            if (filteredBookings.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Filled.EventBusy,
                            contentDescription = "Empty",
                            tint = VihaanSecondaryText,
                            modifier = Modifier.size(54.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No bookings found in this tab",
                            color = VihaanSecondaryText,
                            fontSize = 14.sp
                        )
                    }
                }
            }

            items(filteredBookings) { b ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = b.id,
                                fontWeight = FontWeight.Bold,
                                color = VihaanRoyalBlue,
                                fontSize = 13.sp
                            )
                            StatusPill(status = b.status)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = b.service.title,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanDeepNavy
                        )
                        Text(
                            text = "${b.date} • ${b.timeSlot}",
                            fontSize = 12.sp,
                            color = VihaanSecondaryText
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Pro: ${b.professional.name}",
                                fontSize = 12.sp,
                                color = VihaanDarkText,
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = "₹${b.totalAmount.toInt()}",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = VihaanDeepNavy
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = VihaanBorder)

                        // Action Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (b.status == BookingStatus.COMPLETED) {
                                OutlinedButton(
                                    onClick = { onViewInvoice(b) },
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text("Invoice", fontSize = 12.sp, color = VihaanDeepNavy)
                                }
                                Button(
                                    onClick = { onBookAgain(b) },
                                    colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Text("Book Again", fontSize = 12.sp, color = VihaanWhite)
                                }
                            } else {
                                Button(
                                    onClick = { onTrackBooking(b) },
                                    colors = ButtonDefaults.buttonColors(containerColor = VihaanRoyalBlue),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp)
                                ) {
                                    Text("Track Live", fontSize = 12.sp, color = VihaanWhite, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatusPill(status: BookingStatus) {
    val (label, bg, fg) = when (status) {
        BookingStatus.CONFIRMED -> Triple("Confirmed", Color(0xFFE0E7FF), VihaanRoyalBlue)
        BookingStatus.PROFESSIONAL_ASSIGNED -> Triple("Assigned", Color(0xFFFEF3C7), Color(0xFF92400E))
        BookingStatus.ON_THE_WAY -> Triple("On The Way", Color(0xFFDCFCE7), VihaanSuccess)
        BookingStatus.SERVICE_STARTED -> Triple("In Progress", Color(0xFFEDE9FE), Color(0xFF5B21B6))
        BookingStatus.COMPLETED -> Triple("Completed", Color(0xFFDCFCE7), VihaanSuccess)
        BookingStatus.CANCELLED -> Triple("Cancelled", Color(0xFFFEE2E2), VihaanError)
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bg)
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Text(text = label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = fg)
    }
}

@Composable
fun DigitalInvoiceScreen(
    booking: Booking,
    onBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("digital_invoice_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 30.dp)
        ) {
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
                        text = "Digital Tax Invoice",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                }
            }

            // PDF-Style Paper Sheet
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(modifier = Modifier.padding(22.dp)) {
                        // Header
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "VIHAAN HOME SERVICES",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = VihaanDeepNavy
                                )
                                Text(
                                    text = "GSTIN: 23AABCV1234F1Z8",
                                    fontSize = 10.sp,
                                    color = VihaanSecondaryText
                                )
                                Text(
                                    text = "Shakti Nagar, Bhopal (MP) - 462024",
                                    fontSize = 10.sp,
                                    color = VihaanSecondaryText
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(VihaanDeepNavy),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_vihaan_logo),
                                    contentDescription = "Emblem",
                                    modifier = Modifier.size(42.dp)
                                )
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 14.dp), color = VihaanBorder)

                        // Meta details
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Billed To:", fontSize = 11.sp, color = VihaanSecondaryText)
                                Text("Rahul Sharma", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = VihaanDarkText)
                                Text("+91 98765 43210", fontSize = 11.sp, color = VihaanSecondaryText)
                                Text("Bhopal, MP", fontSize = 11.sp, color = VihaanSecondaryText)
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text("Invoice No:", fontSize = 11.sp, color = VihaanSecondaryText)
                                Text("VH-INV-8491", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = VihaanRoyalBlue)
                                Text("Booking ID: ${booking.id}", fontSize = 11.sp, color = VihaanSecondaryText)
                                Text("Date: ${booking.date}", fontSize = 11.sp, color = VihaanSecondaryText)
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // Itemized Bill Table
                        Text(
                            text = "Itemized Description",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = VihaanDeepNavy
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(VihaanSurfaceLight)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Service Item", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = VihaanDarkText)
                            Text("Amount", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = VihaanDarkText)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(booking.service.title, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = VihaanDarkText)
                                Text("Performed by ${booking.professional.name}", fontSize = 10.sp, color = VihaanSecondaryText)
                            }
                            Text("₹${booking.subtotal.toInt()}", fontSize = 12.sp, color = VihaanDarkText)
                        }

                        if (booking.discount > 0) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Promo Discount", fontSize = 12.sp, color = VihaanSuccess)
                                Text("-₹${booking.discount.toInt()}", fontSize = 12.sp, color = VihaanSuccess, fontWeight = FontWeight.Bold)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("GST @ 18%", fontSize = 12.sp, color = VihaanSecondaryText)
                            Text("₹${booking.tax.toInt()}", fontSize = 12.sp, color = VihaanDarkText)
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Care+ Convenience Fee Waiver", fontSize = 12.sp, color = VihaanRoyalBlue)
                            Text("₹0.00", fontSize = 12.sp, color = VihaanRoyalBlue)
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = VihaanBorder)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Total Paid Amount", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = VihaanDeepNavy)
                            Text("₹${booking.totalAmount.toInt()}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = VihaanRoyalBlue)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "Payment Mode: ${booking.paymentMethod} • Status: ${booking.paymentStatus}",
                            fontSize = 11.sp,
                            color = VihaanSecondaryText
                        )

                        Spacer(modifier = Modifier.height(14.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFFDCFCE7))
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "✓ 7 Days Vihaan Service Warranty Covered",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = VihaanSuccess
                            )
                        }
                    }
                }
            }

            // Buttons
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { /* Simulated download */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Download, contentDescription = "Download", tint = VihaanGold, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Download PDF", fontSize = 13.sp, color = VihaanWhite)
                    }

                    OutlinedButton(
                        onClick = { /* Simulated share */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Share, contentDescription = "Share", tint = VihaanDeepNavy, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Share Invoice", fontSize = 13.sp, color = VihaanDeepNavy)
                    }
                }
            }
        }
    }
}
