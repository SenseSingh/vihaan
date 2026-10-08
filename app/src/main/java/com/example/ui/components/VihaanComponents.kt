package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.HomeService
import com.example.ui.theme.*

@Composable
fun VihaanLogo(
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    showTagline: Boolean = true,
    isDarkTheme: Boolean = true
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(RoundedCornerShape(size * 0.22f))
                .background(VihaanDeepNavy)
                .border(1.5.dp, VihaanGold.copy(alpha = 0.6f), RoundedCornerShape(size * 0.22f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_vihaan_logo),
                contentDescription = "Vihaan Emblem",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Vihaan",
            fontSize = if (size > 100.dp) 32.sp else 22.sp,
            fontWeight = FontWeight.Bold,
            color = if (isDarkTheme) VihaanWhite else VihaanDeepNavy,
            letterSpacing = 1.sp
        )
        Text(
            text = "HOME SERVICES",
            fontSize = if (size > 100.dp) 13.sp else 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = VihaanGold,
            letterSpacing = 2.sp
        )

        if (showTagline) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Your Home, Our Care.",
                fontSize = 12.sp,
                color = if (isDarkTheme) VihaanLightGold else VihaanRoyalBlue,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "Professional Services at Your Doorstep.",
                fontSize = 11.sp,
                color = if (isDarkTheme) Color.LightGray else VihaanSecondaryText
            )
        }
    }
}

@Composable
fun VihaanBottomNav(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.testTag("vihaan_bottom_nav"),
        containerColor = VihaanWhite,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple("home", "Home", Icons.Filled.Home to Icons.Outlined.Home),
            Triple("explore", "Explore", Icons.Filled.Search to Icons.Outlined.Search),
            Triple("bookings", "Bookings", Icons.Filled.CalendarMonth to Icons.Outlined.CalendarMonth),
            Triple("wallet", "Wallet", Icons.Filled.AccountBalanceWallet to Icons.Outlined.AccountBalanceWallet),
            Triple("profile", "Profile", Icons.Filled.Person to Icons.Outlined.Person)
        )

        items.forEach { (route, label, icons) ->
            val isSelected = currentRoute == route
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(route) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) icons.first else icons.second,
                        contentDescription = label,
                        tint = if (isSelected) VihaanRoyalBlue else VihaanSecondaryText
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected) VihaanRoyalBlue else VihaanSecondaryText
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = VihaanLightGold.copy(alpha = 0.35f)
                )
            )
        }
    }
}

@Composable
fun ConciergeFloatingBar(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() }
            .testTag("concierge_floating_bar"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = VihaanDeepNavy),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            listOf(VihaanGold, VihaanLightGold)
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.SmartToy,
                    contentDescription = "AI Concierge",
                    tint = VihaanDeepNavy,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Vihaan Concierge",
                        fontWeight = FontWeight.Bold,
                        color = VihaanWhite,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(VihaanGold)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "AI 2.1",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanDeepNavy
                        )
                    }
                }
                Text(
                    text = "Need any service today? Just type or speak...",
                    color = Color.LightGray,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = { onClick() },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Mic,
                    contentDescription = "Voice Assistant",
                    tint = VihaanGold
                )
            }
        }
    }
}
