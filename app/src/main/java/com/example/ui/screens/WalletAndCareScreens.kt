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
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.VihaanRepository
import com.example.model.WalletTransaction
import com.example.ui.theme.*

@Composable
fun WalletScreen(
    onBack: () -> Unit,
    onOpenCoupons: () -> Unit
) {
    val user by VihaanRepository.currentUserState.collectAsState()
    val transactions by VihaanRepository.walletTransactionsState.collectAsState()
    var showAddMoneyDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("wallet_screen")
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
                        text = "Wallet & Rewards",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                }
            }

            // Balance & Rewards Card (matching user reference mockup)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 8.dp)
                        .testTag("wallet_balance_card"),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFFC99723), Color(0xFFDDA83B), Color(0xFFE9BA53))
                                )
                            )
                            .padding(22.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Total Balance",
                                    fontSize = 12.sp,
                                    color = Color(0xFF2A1C00),
                                    fontWeight = FontWeight.Medium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "₹${user.walletBalance.toInt()}",
                                    fontSize = 30.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanWhite
                                )
                            }

                            Column(horizontalAlignment = Alignment.End) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "Reward Points",
                                        fontSize = 12.sp,
                                        color = Color(0xFF2A1C00),
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clip(CircleShape)
                                            .background(VihaanWhite.copy(alpha = 0.3f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text("+", fontSize = 11.sp, color = VihaanWhite, fontWeight = FontWeight.Bold)
                                    }
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "${user.rewardPoints} pts",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VihaanWhite
                                )
                            }
                        }
                    }
                }
            }

            // Quick Actions: Add Money, Transactions, Coupons, History
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    WalletActionButton(
                        icon = Icons.Filled.AddCard,
                        label = "Add Money",
                        onClick = { showAddMoneyDialog = true }
                    )
                    WalletActionButton(
                        icon = Icons.Filled.ReceiptLong,
                        label = "Transactions",
                        onClick = { /* already on transactions */ }
                    )
                    WalletActionButton(
                        icon = Icons.Filled.Discount,
                        label = "Coupons",
                        onClick = onOpenCoupons
                    )
                    WalletActionButton(
                        icon = Icons.Filled.History,
                        label = "History",
                        onClick = { /* history filter */ }
                    )
                }
            }

            // Recent Transactions
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Transactions",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                    Text(
                        text = "View All",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = VihaanRoyalBlue
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
            }

            items(transactions) { tx ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = VihaanWhite),
                    elevation = CardDefaults.cardElevation(1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(38.dp)
                                    .clip(CircleShape)
                                    .background(if (tx.isCredit) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = if (tx.isCredit) Icons.Filled.ArrowDownward else Icons.Filled.ArrowUpward,
                                    contentDescription = tx.title,
                                    tint = if (tx.isCredit) VihaanSuccess else VihaanError,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = tx.title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = VihaanDarkText
                                )
                                Text(
                                    text = tx.date,
                                    fontSize = 11.sp,
                                    color = VihaanSecondaryText
                                )
                            }
                        }

                        Text(
                            text = "${if (tx.isCredit) "+" else "-"}₹${tx.amount.toInt()}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (tx.isCredit) VihaanSuccess else VihaanError
                        )
                    }
                }
            }
        }

        // Add Money Dialog
        if (showAddMoneyDialog) {
            AlertDialog(
                onDismissRequest = { showAddMoneyDialog = false },
                title = { Text("Add Money to Wallet", fontWeight = FontWeight.Bold) },
                text = {
                    Column {
                        Text("Select top-up amount:", fontSize = 13.sp, color = VihaanSecondaryText)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            listOf(500.0, 1000.0, 2000.0).forEach { amt ->
                                Button(
                                    onClick = {
                                        VihaanRepository.addMoneyToWallet(amt)
                                        showAddMoneyDialog = false
                                    },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("+₹${amt.toInt()}", fontSize = 12.sp)
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    TextButton(onClick = { showAddMoneyDialog = false }) {
                        Text("Cancel", color = VihaanSecondaryText)
                    }
                }
            )
        }
    }
}

@Composable
fun WalletActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .width(72.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(VihaanWhite)
                .border(1.dp, VihaanBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = VihaanRoyalBlue,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = VihaanDarkText
        )
    }
}

@Composable
fun CarePlusScreen(
    onBack: () -> Unit
) {
    var selectedPlan by remember { mutableStateOf("monthly") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanDeepNavy)
            .testTag("care_plus_screen")
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
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = VihaanWhite)
                    }
                    Text(
                        text = "Vihaan Care+",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanWhite
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Filled.Stars,
                        contentDescription = "Gold Star",
                        tint = VihaanGold,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(VihaanRoyalBlue)
                            .border(2.dp, VihaanGold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_vihaan_logo),
                            contentDescription = "Care+ Emblem",
                            modifier = Modifier.size(80.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Care+ GOLD MEMBER",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanGold
                    )
                    Text(
                        text = "Our Premium All-Inclusive Membership",
                        fontSize = 13.sp,
                        color = Color.LightGray
                    )
                }
            }

            // Benefits Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0C2444)),
                    border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(VihaanGold, VihaanLightGold)))
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Membership Privileges",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = VihaanWhite
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        val benefits = listOf(
                            "✓ Priority Booking during peak weekend hours",
                            "✓ Free Biannual Home & AC Inspection Visit",
                            "✓ Up to 30% Extra Discount on all home services",
                            "✓ Zero Convenience Fee on every booking",
                            "✓ Exclusive Vetted Top-5% Master Professionals",
                            "✓ Birthday Special Complimentary Grooming Gift",
                            "✓ Dedicated 24/7 VIP Concierge Support"
                        )
                        benefits.forEach { b ->
                            Text(
                                text = b,
                                fontSize = 13.sp,
                                color = VihaanWhite,
                                modifier = Modifier.padding(vertical = 4.dp),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            // Plan Selection
            item {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedPlan = "monthly" },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedPlan == "monthly") VihaanGold else Color(0xFF0C2444)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Monthly",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedPlan == "monthly") VihaanDeepNavy else VihaanWhite
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "₹499",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedPlan == "monthly") VihaanDeepNavy else VihaanGold
                            )
                            Text(
                                text = "Per month",
                                fontSize = 10.sp,
                                color = if (selectedPlan == "monthly") VihaanDarkText else Color.Gray
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .clickable { selectedPlan = "yearly" },
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (selectedPlan == "yearly") VihaanGold else Color(0xFF0C2444)
                        )
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Annual (Save 20%)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedPlan == "yearly") VihaanDeepNavy else VihaanWhite
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "₹4,999",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (selectedPlan == "yearly") VihaanDeepNavy else VihaanGold
                            )
                            Text(
                                text = "Per year",
                                fontSize = 10.sp,
                                color = if (selectedPlan == "yearly") VihaanDarkText else Color.Gray
                            )
                        }
                    }
                }
            }

            // Bottom CTA
            item {
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* Join membership */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .height(52.dp)
                        .testTag("join_care_plus_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = VihaanGold),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Join Care+ Gold",
                        color = VihaanDeepNavy,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
