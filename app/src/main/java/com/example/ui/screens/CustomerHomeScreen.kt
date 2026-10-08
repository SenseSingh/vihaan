package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.VihaanRepository
import com.example.model.HomeService
import com.example.model.ServiceCategory
import com.example.ui.components.ConciergeFloatingBar
import com.example.ui.theme.*

@Composable
fun CustomerHomeScreen(
    onSelectService: (HomeService) -> Unit,
    onCategoryClick: (ServiceCategory) -> Unit,
    onOpenConcierge: () -> Unit,
    onOpenWallet: () -> Unit,
    onOpenCarePlus: () -> Unit,
    onOpenEmergency: () -> Unit,
    onOpenNotifications: () -> Unit,
    onOpenSearch: () -> Unit,
    onRoleSwitchClick: () -> Unit
) {
    val user by VihaanRepository.currentUserState.collectAsState()
    var selectedCity by remember { mutableStateOf("Bhopal, MP") }
    var showCityDialog by remember { mutableStateOf(false) }

    val cities = listOf("Bhopal, MP", "Delhi, NCR", "Lucknow, UP", "Mumbai, MH", "Bengaluru, KA", "Pune, MH")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("customer_home_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 140.dp)
        ) {
            // Top Section (Greeting, City, Profile)
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Good Morning",
                                fontSize = 14.sp,
                                color = VihaanSecondaryText
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "👋", fontSize = 14.sp)
                        }
                        Text(
                            text = user.name.split(" ").firstOrNull() ?: "User",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanDeepNavy
                        )
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { showCityDialog = true }
                                .padding(vertical = 2.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Location",
                                tint = VihaanRoyalBlue,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = selectedCity,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = VihaanRoyalBlue
                            )
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowDown,
                                contentDescription = "Select City",
                                tint = VihaanRoyalBlue,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Notifications button
                        IconButton(
                            onClick = onOpenNotifications,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(VihaanWhite)
                                .border(1.dp, VihaanBorder, CircleShape)
                        ) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = VihaanGold,
                                        contentColor = VihaanDeepNavy
                                    ) {
                                        Text("2", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Notifications,
                                    contentDescription = "Notifications",
                                    tint = VihaanDeepNavy,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        // Avatar / Role Switcher
                        Box(
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(VihaanDeepNavy)
                                .border(1.5.dp, VihaanGold, CircleShape)
                                .clickable { onRoleSwitchClick() }
                                .testTag("avatar_button"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.name.firstOrNull()?.toString() ?: "U",
                                fontWeight = FontWeight.Bold,
                                color = VihaanGold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }

            // Search Bar
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .clickable { onOpenSearch() }
                        .testTag("home_search_bar"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite),
                    elevation = CardDefaults.cardElevation(2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = VihaanSecondaryText,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Search for services...",
                            fontSize = 14.sp,
                            color = VihaanSecondaryText,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = onOpenConcierge,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Mic,
                                contentDescription = "Voice Search",
                                tint = VihaanRoyalBlue
                            )
                        }
                    }
                }
            }

            // Special Offer Banner Carousel
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .testTag("special_offer_banner"),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    listOf(VihaanDeepNavy, VihaanRoyalBlue)
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(VihaanGold)
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "SPECIAL OFFER",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VihaanDeepNavy
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "20% OFF\non First Booking",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanWhite,
                                    lineHeight = 24.sp
                                )
                                Spacer(modifier = Modifier.height(12.dp))
                                Button(
                                    onClick = { onSelectService(VihaanRepository.services.first()) },
                                    colors = ButtonDefaults.buttonColors(containerColor = VihaanGold),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = "BOOK NOW →",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VihaanDeepNavy
                                    )
                                }
                            }

                            // Luxury Gift/Crown badge illustration
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .clip(CircleShape)
                                    .background(VihaanWhite.copy(alpha = 0.1f))
                                    .border(1.dp, VihaanGold.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_vihaan_logo),
                                    contentDescription = "Offer Badge",
                                    modifier = Modifier.size(72.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Categories Section
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Categories",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                    Text(
                        text = "View All",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VihaanRoyalBlue,
                        modifier = Modifier.clickable { onCategoryClick(VihaanRepository.categories.first()) }
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(VihaanRepository.categories) { cat ->
                        CategoryIconPill(category = cat, onClick = { onCategoryClick(cat) })
                    }
                }
            }

            // Quick Book Horizontal Grid Cards
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Quick Book",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                    Text(
                        text = "See All",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VihaanRoyalBlue,
                        modifier = Modifier.clickable { onCategoryClick(VihaanRepository.categories.first()) }
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    items(VihaanRepository.services) { service ->
                        QuickBookCard(
                            service = service,
                            onBook = { onSelectService(service) }
                        )
                    }
                }
            }

            // Vihaan Care+ Membership Card
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable { onOpenCarePlus() }
                        .testTag("care_plus_home_card"),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanDeepNavy),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Vihaan Care+",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanWhite
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Icon(
                                    imageVector = Icons.Filled.Stars,
                                    contentDescription = "Care+",
                                    tint = VihaanGold,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(VihaanGold)
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    text = "GOLD MEMBER",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanDeepNavy
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        val benefits = listOf(
                            "✓ Priority Booking",
                            "✓ Exclusive Offers",
                            "✓ Free Inspection Visit",
                            "✓ Up to 30% Extra Discount",
                            "✓ Birthday Special Gift"
                        )
                        benefits.forEach { b ->
                            Text(
                                text = b,
                                fontSize = 12.sp,
                                color = Color.LightGray,
                                modifier = Modifier.padding(vertical = 2.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "₹499 / Month",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanGold
                                )
                                Text(
                                    text = "Billed Annually",
                                    fontSize = 11.sp,
                                    color = Color.Gray
                                )
                            }

                            Button(
                                onClick = onOpenCarePlus,
                                colors = ButtonDefaults.buttonColors(containerColor = VihaanGold),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "Join Now",
                                    color = VihaanDeepNavy,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }

            // Emergency 90-min Service Banner
            item {
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clickable { onOpenEmergency() }
                        .testTag("emergency_banner"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F2)),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(Color(0xFFFCA5A5), Color(0xFFEF4444))))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(CircleShape)
                                    .background(VihaanError.copy(alpha = 0.15f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Warning,
                                    contentDescription = "Emergency",
                                    tint = VihaanError,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Emergency 90-Min Fix",
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanDarkText,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Electrician, plumber & water leaks",
                                    fontSize = 11.sp,
                                    color = VihaanSecondaryText
                                )
                            }
                        }

                        Button(
                            onClick = onOpenEmergency,
                            colors = ButtonDefaults.buttonColors(containerColor = VihaanError),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text("Book Now", fontSize = 11.sp, color = VihaanWhite, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Floating Bottom Concierge Assistant Pill
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 68.dp)
        ) {
            ConciergeFloatingBar(onClick = onOpenConcierge)
        }

        // City Selector Dialog
        if (showCityDialog) {
            AlertDialog(
                onDismissRequest = { showCityDialog = false },
                title = { Text("Select Your City", fontWeight = FontWeight.Bold) },
                text = {
                    Column {
                        cities.forEach { city ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedCity = city
                                        showCityDialog = false
                                    }
                                    .padding(vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.LocationCity,
                                    contentDescription = city,
                                    tint = if (selectedCity == city) VihaanRoyalBlue else VihaanSecondaryText
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = city,
                                    fontWeight = if (selectedCity == city) FontWeight.Bold else FontWeight.Normal,
                                    color = if (selectedCity == city) VihaanRoyalBlue else VihaanDarkText
                                )
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showCityDialog = false }) {
                        Text("Close", color = VihaanRoyalBlue)
                    }
                }
            )
        }
    }
}

@Composable
fun CategoryIconPill(
    category: ServiceCategory,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .width(68.dp)
            .testTag("cat_${category.id}")
    ) {
        val icon = when (category.id) {
            "cat_salon" -> Icons.Filled.Face
            "cat_beauty" -> Icons.Filled.Spa
            "cat_spa" -> Icons.Filled.SelfImprovement
            "cat_cleaning" -> Icons.Filled.CleaningServices
            "cat_ac" -> Icons.Filled.AcUnit
            "cat_electric" -> Icons.Filled.Bolt
            "cat_plumber" -> Icons.Filled.Plumbing
            "cat_emergency" -> Icons.Filled.Emergency
            else -> Icons.Filled.HomeRepairService
        }

        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(VihaanWhite)
                .border(1.dp, VihaanBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = category.name,
                tint = VihaanRoyalBlue,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = category.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = VihaanDarkText,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun QuickBookCard(
    service: HomeService,
    onBook: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(155.dp)
            .clickable { onBook() }
            .testTag("quick_book_${service.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = VihaanWhite),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(84.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(VihaanSurfaceLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (service.categoryId) {
                        "cat_salon" -> Icons.Filled.ContentCut
                        "cat_cleaning" -> Icons.Filled.CleaningServices
                        "cat_spa" -> Icons.Filled.SelfImprovement
                        "cat_ac" -> Icons.Filled.AcUnit
                        else -> Icons.Filled.Build
                    },
                    contentDescription = service.title,
                    tint = VihaanRoyalBlue,
                    modifier = Modifier.size(36.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = service.title,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = VihaanDarkText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Rating",
                    tint = VihaanGold,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "${service.rating}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VihaanDarkText
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "from ₹${service.price.toInt()}",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanRoyalBlue
                    )
                    Text(
                        text = "₹${service.originalPrice.toInt()}",
                        fontSize = 10.sp,
                        color = VihaanSecondaryText,
                        textDecoration = TextDecoration.LineThrough
                    )
                }

                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(VihaanDeepNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Book",
                        tint = VihaanWhite,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
