package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.VihaanRepository
import com.example.model.BookingStatus
import com.example.model.Coupon
import com.example.model.UserRole
import com.example.ui.theme.*

@Composable
fun ProfessionalDashboardScreen(
    onSwitchToCustomer: () -> Unit,
    onOpenChat: () -> Unit
) {
    val booking by VihaanRepository.currentBookingState.collectAsState()
    var isAvailable by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("pro_dashboard_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanDeepNavy)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(VihaanGold),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("RK", fontWeight = FontWeight.Bold, color = VihaanDeepNavy, fontSize = 16.sp)
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column {
                                    Text("Good Morning,", fontSize = 12.sp, color = Color.LightGray)
                                    Text("Rohit Kumar", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = VihaanWhite)
                                    Text("Master Barber Stylist", fontSize = 11.sp, color = VihaanGold)
                                }
                            }

                            // Switch Persona Button
                            Button(
                                onClick = onSwitchToCustomer,
                                colors = ButtonDefaults.buttonColors(containerColor = VihaanRoyalBlue),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text("Switch User", fontSize = 11.sp, color = VihaanWhite)
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // Earnings & Job Counter Cards
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2B52))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("Today's Earnings", fontSize = 11.sp, color = Color.LightGray)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("₹2,450", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = VihaanGold)
                                }
                            }

                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2B52))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("Today's Jobs", fontSize = 11.sp, color = Color.LightGray)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("6 (4 done)", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = VihaanWhite)
                                }
                            }

                            Card(
                                modifier = Modifier.weight(0.9f),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2B52))
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Text("Rating", fontSize = 11.sp, color = Color.LightGray)
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text("★ 4.9", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = VihaanLightGold)
                                }
                            }
                        }
                    }
                }
            }

            // Availability Toggle
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Online for Bookings", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = VihaanDarkText)
                            Text(if (isAvailable) "Receiving customer requests nearby" else "Currently offline", fontSize = 11.sp, color = VihaanSecondaryText)
                        }
                        Switch(
                            checked = isAvailable,
                            onCheckedChange = { isAvailable = it },
                            colors = SwitchDefaults.colors(checkedThumbColor = VihaanGold, checkedTrackColor = VihaanDeepNavy)
                        )
                    }
                }
            }

            // Current Assigned Job
            item {
                Text(
                    text = "Current Assigned Order",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = VihaanDeepNavy,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite),
                    elevation = CardDefaults.cardElevation(3.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("ORDER #${booking.id}", fontWeight = FontWeight.Bold, color = VihaanRoyalBlue, fontSize = 13.sp)
                            StatusPill(status = booking.status)
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                        Text(booking.service.title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = VihaanDarkText)
                        Text("Customer: Rahul Sharma (+91 98765 43210)", fontSize = 13.sp, color = VihaanSecondaryText)
                        Text("Address: ${booking.address.addressLine}, Bhopal", fontSize = 12.sp, color = VihaanDarkText)
                        Text("Scheduled Slot: ${booking.timeSlot}", fontSize = 12.sp, color = VihaanRoyalBlue, fontWeight = FontWeight.SemiBold)

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = VihaanBorder)

                        // Pro Actions
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = onOpenChat,
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Icon(Icons.Filled.Chat, contentDescription = "Chat", modifier = Modifier.size(16.dp), tint = VihaanDeepNavy)
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Chat", fontSize = 11.sp, color = VihaanDeepNavy)
                            }

                            Button(
                                onClick = {
                                    if (booking.status == BookingStatus.ON_THE_WAY) {
                                        VihaanRepository.updateBookingStatus(BookingStatus.SERVICE_STARTED)
                                    } else if (booking.status == BookingStatus.SERVICE_STARTED) {
                                        VihaanRepository.updateBookingStatus(BookingStatus.COMPLETED)
                                    }
                                },
                                modifier = Modifier.weight(1.4f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (booking.status == BookingStatus.SERVICE_STARTED) VihaanSuccess else VihaanDeepNavy
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = when (booking.status) {
                                        BookingStatus.ON_THE_WAY -> "Start Service"
                                        BookingStatus.SERVICE_STARTED -> "Complete Job"
                                        BookingStatus.COMPLETED -> "Job Completed ✓"
                                        else -> "Mark Arrived"
                                    },
                                    fontSize = 11.sp,
                                    color = VihaanWhite,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Payout Information
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Weekly Payout Summary", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = VihaanDeepNavy)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Pending Payout (Bank Transfer)", fontSize = 12.sp, color = VihaanSecondaryText)
                            Text("₹4,850.00", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = VihaanSuccess)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Bank Account", fontSize = 12.sp, color = VihaanSecondaryText)
                            Text("HDFC Bank •••• 9102", fontSize = 12.sp, color = VihaanDarkText)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdminDashboardScreen(
    onSwitchToCustomer: () -> Unit
) {
    val coupons by VihaanRepository.couponsState.collectAsState()
    var newCouponCode by remember { mutableStateOf("") }
    var newCouponDiscount by remember { mutableStateOf("") }
    var showAddCouponDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("admin_dashboard_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanDeepNavy)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Vihaan Super Admin", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = VihaanWhite)
                                Text("Marketplace Management Portal", fontSize = 12.sp, color = VihaanGold)
                            }
                            Button(
                                onClick = onSwitchToCustomer,
                                colors = ButtonDefaults.buttonColors(containerColor = VihaanGold),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text("Customer Mode", color = VihaanDeepNavy, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            }
                        }

                        Spacer(modifier = Modifier.height(18.dp))

                        // 4 KPI Stats
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            AdminKpiCard("Customers", "14,250", modifier = Modifier.weight(1f))
                            AdminKpiCard("Pros", "1,820", modifier = Modifier.weight(1f))
                            AdminKpiCard("Bookings", "184", modifier = Modifier.weight(1f))
                            AdminKpiCard("Revenue", "₹1.42L", modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            // KYC Verification Section
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Professional KYC Verification",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = VihaanDeepNavy,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text("Suresh Patel (Electrician)", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                Text("Aadhaar + Trade Certificate Submitted", fontSize = 11.sp, color = VihaanSecondaryText)
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Button(
                                    onClick = { /* Approve */ },
                                    colors = ButtonDefaults.buttonColors(containerColor = VihaanSuccess),
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                                ) {
                                    Text("Approve", fontSize = 11.sp)
                                }
                                OutlinedButton(
                                    onClick = { /* Reject */ },
                                    shape = RoundedCornerShape(6.dp),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp)
                                ) {
                                    Text("Reject", fontSize = 11.sp, color = VihaanError)
                                }
                            }
                        }
                    }
                }
            }

            // Manage Promo Coupons
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
                        text = "Active Marketplace Coupons",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                    IconButton(onClick = { showAddCouponDialog = true }) {
                        Icon(Icons.Filled.AddCircle, contentDescription = "Add Coupon", tint = VihaanRoyalBlue)
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }

            items(coupons) { c ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(c.code, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = VihaanRoyalBlue)
                            Text(c.description, fontSize = 11.sp, color = VihaanSecondaryText)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(0xFFDCFCE7))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("${c.discountPercent}% OFF", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = VihaanSuccess)
                        }
                    }
                }
            }
        }

        // Add Coupon Dialog
        if (showAddCouponDialog) {
            AlertDialog(
                onDismissRequest = { showAddCouponDialog = false },
                title = { Text("Create Promo Coupon", fontWeight = FontWeight.Bold) },
                text = {
                    Column {
                        OutlinedTextField(
                            value = newCouponCode,
                            onValueChange = { newCouponCode = it.uppercase() },
                            label = { Text("Coupon Code (e.g. FESTIVE30)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        OutlinedTextField(
                            value = newCouponDiscount,
                            onValueChange = { newCouponDiscount = it },
                            label = { Text("Discount % (e.g. 30)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            if (newCouponCode.isNotBlank() && newCouponDiscount.isNotBlank()) {
                                val discount = newCouponDiscount.toIntOrNull() ?: 20
                                val coupon = Coupon(
                                    code = newCouponCode,
                                    title = "$discount% OFF",
                                    description = "Special promotional coupon",
                                    discountPercent = discount,
                                    maxDiscount = 200.0,
                                    minOrderAmount = 499.0,
                                    expiryDate = "31 Dec 2026"
                                )
                                VihaanRepository.couponsState.value = VihaanRepository.couponsState.value + coupon
                                showAddCouponDialog = false
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy)
                    ) {
                        Text("Add Coupon")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showAddCouponDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

@Composable
fun AdminKpiCard(title: String, value: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0F2B52))
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 9.sp, color = Color.LightGray)
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = VihaanGold)
        }
    }
}
