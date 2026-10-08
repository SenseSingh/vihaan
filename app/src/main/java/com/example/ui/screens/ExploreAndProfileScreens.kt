package com.example.ui.screens

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
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
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
import com.example.model.HomeService
import com.example.model.ServiceCategory
import com.example.model.UserRole
import com.example.ui.theme.*

@Composable
fun ExploreScreen(
    onSelectService: (HomeService) -> Unit,
    onBack: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf<String?>("all") }

    val filteredServices = VihaanRepository.services.filter { srv ->
        val matchesCategory = selectedCategory == "all" || srv.categoryId == selectedCategory
        val matchesQuery = searchQuery.isBlank() || srv.title.contains(searchQuery, ignoreCase = true) || srv.shortDescription.contains(searchQuery, ignoreCase = true)
        matchesCategory && matchesQuery
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("explore_screen")
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
                        text = "Explore Home Services",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                }
            }

            // Search input
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search haircut, deep cleaning, AC...") },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search", tint = VihaanSecondaryText) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(14.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Filter chips
            item {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedCategory == "all",
                            onClick = { selectedCategory = "all" },
                            label = { Text("All Services") }
                        )
                    }
                    items(VihaanRepository.categories) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat.id,
                            onClick = { selectedCategory = cat.id },
                            label = { Text(cat.name) }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
            }

            // Service Cards
            items(filteredServices) { srv ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .clickable { onSelectService(srv) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(VihaanSurfaceLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = when (srv.categoryId) {
                                    "cat_salon" -> Icons.Filled.ContentCut
                                    "cat_cleaning" -> Icons.Filled.CleaningServices
                                    "cat_spa" -> Icons.Filled.SelfImprovement
                                    "cat_ac" -> Icons.Filled.AcUnit
                                    else -> Icons.Filled.Build
                                },
                                contentDescription = srv.title,
                                tint = VihaanRoyalBlue,
                                modifier = Modifier.size(28.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = srv.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = VihaanDeepNavy
                            )
                            Text(
                                text = srv.shortDescription,
                                fontSize = 11.sp,
                                color = VihaanSecondaryText,
                                maxLines = 1
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Icon(Icons.Filled.Star, contentDescription = "Rating", tint = VihaanGold, modifier = Modifier.size(13.dp))
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "${srv.rating} (${srv.reviewCount})",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = VihaanDarkText
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "• ${srv.durationMinutes} mins",
                                    fontSize = 11.sp,
                                    color = VihaanSecondaryText
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(top = 4.dp)
                            ) {
                                Text(
                                    text = "₹${srv.price.toInt()}",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanRoyalBlue
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "₹${srv.originalPrice.toInt()}",
                                    fontSize = 11.sp,
                                    color = VihaanSecondaryText,
                                    textDecoration = TextDecoration.LineThrough
                                )
                            }
                        }

                        Button(
                            onClick = { onSelectService(srv) },
                            colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Book", fontSize = 12.sp, color = VihaanWhite)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EmergencyBookingScreen(
    onBack: () -> Unit,
    onBookEmergency: (HomeService) -> Unit
) {
    val emergencyServices = listOf(
        HomeService("srv_emg_elec", "cat_electric", "Emergency Electrician", "Urgent power failure, short circuit & spark fix", "Immediate certified electrician visit within 90 minutes with test meters and safety equipment.", 249.0, 399.0, 30, 4.9, 810),
        HomeService("srv_emg_plumb", "cat_plumber", "Emergency Plumber", "Burst pipe, overflow & water blockage fix", "Immediate plumber dispatch with leak sealant and pressure valve tools.", 249.0, 399.0, 30, 4.8, 640),
        HomeService("srv_emg_ac", "cat_ac", "Urgent AC Breakdown", "Compressor trip, water leakage & smoke check", "Priority HVAC technician dispatch with refrigerant analyzer.", 499.0, 699.0, 45, 4.7, 520)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("emergency_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
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
                        text = "90-Min Emergency Fix",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanError
                    )
                }
            }

            // Emergency Notice Banner
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(Color(0xFFFCA5A5)))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(VihaanError),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Timer, contentDescription = "Timer", tint = VihaanWhite)
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text("Guaranteed 90-Minute Doorstep Arrival", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = VihaanDarkText)
                            Text("Nearest verified technician will be dispatched immediately upon booking.", fontSize = 11.sp, color = VihaanSecondaryText)
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Select Emergency Service",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = VihaanDeepNavy,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            items(emergencyServices) { srv ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(srv.title, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = VihaanDeepNavy)
                            Text(srv.shortDescription, fontSize = 11.sp, color = VihaanSecondaryText)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("Arrival: Under 90 mins • ₹${srv.price.toInt()}", fontSize = 12.sp, color = VihaanError, fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = { onBookEmergency(srv) },
                            colors = ButtonDefaults.buttonColors(containerColor = VihaanError),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("Dispatch", fontSize = 12.sp, color = VihaanWhite, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(
    onOpenBookings: () -> Unit,
    onOpenWallet: () -> Unit,
    onOpenCarePlus: () -> Unit,
    onOpenCoupons: () -> Unit,
    onSwitchRole: () -> Unit,
    onLogout: () -> Unit
) {
    val user by VihaanRepository.currentUserState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("profile_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Profile Card Header
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanDeepNavy)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(VihaanGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.firstOrNull()?.toString() ?: "U",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                color = VihaanDeepNavy
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(user.name, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = VihaanWhite)
                        Text(user.phone, fontSize = 13.sp, color = Color.LightGray)
                        Spacer(modifier = Modifier.height(8.dp))

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(VihaanGold)
                                .padding(horizontal = 10.dp, vertical = 3.dp)
                        ) {
                            Text("CARE+ ${user.membership} MEMBER", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = VihaanDeepNavy)
                        }
                    }
                }
            }

            // Menu Items
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        ProfileMenuItem(icon = Icons.Filled.CalendarMonth, title = "My Bookings", subtitle = "View active & past orders", onClick = onOpenBookings)
                        ProfileMenuItem(icon = Icons.Filled.AccountBalanceWallet, title = "Wallet & Rewards", subtitle = "₹${user.walletBalance.toInt()} balance • ${user.rewardPoints} points", onClick = onOpenWallet)
                        ProfileMenuItem(icon = Icons.Filled.Stars, title = "Vihaan Care+ Membership", subtitle = "Gold privileges & perks", onClick = onOpenCarePlus)
                        ProfileMenuItem(icon = Icons.Filled.Discount, title = "Offers & Coupons", subtitle = "Save with promo codes", onClick = onOpenCoupons)
                        ProfileMenuItem(icon = Icons.Filled.Security, title = "7-Day Service Warranty", subtitle = "Claim service issues", onClick = { /* warranty dialog */ })
                        ProfileMenuItem(icon = Icons.Filled.SupportAgent, title = "Customer Support", subtitle = "FAQs & raise a ticket", onClick = { /* support */ })
                    }
                }
            }

            // Role / Switch Persona Button
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanSurfaceLight),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Active Persona: ${user.role.name}", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = VihaanRoyalBlue)
                        Spacer(modifier = Modifier.height(6.dp))
                        Button(
                            onClick = onSwitchRole,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = VihaanRoyalBlue),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text("Switch Persona (Customer / Pro / Admin)", fontSize = 12.sp, color = VihaanWhite)
                        }
                    }
                }
            }

            // Logout Button
            item {
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(48.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Logout, contentDescription = "Logout", tint = VihaanError, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Logout", color = VihaanError, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(VihaanSurfaceLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = title, tint = VihaanRoyalBlue, modifier = Modifier.size(20.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = VihaanDarkText)
            Text(subtitle, fontSize = 11.sp, color = VihaanSecondaryText)
        }
        Icon(Icons.Filled.ChevronRight, contentDescription = "Go", tint = VihaanSecondaryText, modifier = Modifier.size(18.dp))
    }
}
