package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ConciergeResponse
import com.example.data.VihaanConciergeService
import com.example.data.VihaanRepository
import com.example.model.HomeService
import com.example.ui.theme.*
import kotlinx.coroutines.launch

data class ConciergeBubble(
    val id: String,
    val isUser: Boolean,
    val text: String,
    val actionText: String? = null,
    val targetServiceId: String? = null,
    val actionType: String? = null
)

@Composable
fun ConciergeScreen(
    onBack: () -> Unit,
    onNavigateService: (HomeService) -> Unit,
    onNavigateTracking: () -> Unit,
    onNavigateWallet: () -> Unit,
    onNavigateCare: () -> Unit,
    onNavigateEmergency: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var isThinking by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    val bubbles = remember {
        mutableStateListOf(
            ConciergeBubble(
                id = "b0",
                isUser = false,
                text = "Namaste Rahul! I am your Vihaan AI Concierge. How can I care for your home today? You can ask me to troubleshoot home appliances, find top stylists, or track your live service.",
                actionText = "Explore Trending Services",
                actionType = "NAVIGATE_EXPLORE"
            )
        )
    }

    val suggestedPrompts = listOf(
        "My AC is not cooling",
        "Book a haircut under ₹500",
        "Where is my professional?",
        "Show my wallet & points",
        "Show active coupon offers",
        "Emergency water leak fix"
    )

    fun handleSend(text: String) {
        if (text.isBlank()) return
        val userBubble = ConciergeBubble(
            id = "user_${System.currentTimeMillis()}",
            isUser = true,
            text = text
        )
        bubbles.add(userBubble)
        inputText = ""
        isThinking = true

        coroutineScope.launch {
            val response: ConciergeResponse = VihaanConciergeService.queryConcierge(text)
            isThinking = false
            bubbles.add(
                ConciergeBubble(
                    id = "bot_${System.currentTimeMillis()}",
                    isUser = false,
                    text = response.reply,
                    actionText = response.suggestedActionText,
                    targetServiceId = response.targetServiceId,
                    actionType = response.actionType
                )
            )
            listState.animateScrollToItem(bubbles.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("concierge_screen")
    ) {
        // Concierge Header
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = VihaanDeepNavy),
            elevation = CardDefaults.cardElevation(4.dp)
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
                        .size(40.dp)
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
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Vihaan Concierge",
                            color = VihaanWhite,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(VihaanGold)
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "Gemini 2.1",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = VihaanDeepNavy
                            )
                        }
                    }
                    Text(
                        text = "Smart Home Assistant • Online",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Messages Stream
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(bubbles) { b ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = if (b.isUser) Alignment.End else Alignment.Start
                ) {
                    Card(
                        modifier = Modifier.widthIn(max = 310.dp),
                        shape = RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (b.isUser) 16.dp else 4.dp,
                            bottomEnd = if (b.isUser) 4.dp else 16.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = if (b.isUser) VihaanRoyalBlue else VihaanWhite
                        ),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(
                                text = b.text,
                                fontSize = 13.sp,
                                color = if (b.isUser) VihaanWhite else VihaanDarkText,
                                lineHeight = 19.sp
                            )

                            // Actionable Button if provided by Concierge
                            if (b.actionText != null) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = {
                                        when (b.actionType) {
                                            "NAVIGATE_SERVICE" -> {
                                                val srv = VihaanRepository.services.firstOrNull { it.id == b.targetServiceId }
                                                    ?: VihaanRepository.services.first()
                                                onNavigateService(srv)
                                            }
                                            "NAVIGATE_TRACKING" -> onNavigateTracking()
                                            "NAVIGATE_WALLET" -> onNavigateWallet()
                                            "NAVIGATE_CARE" -> onNavigateCare()
                                            "NAVIGATE_EMERGENCY" -> onNavigateEmergency()
                                            else -> onNavigateService(VihaanRepository.services.first())
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                                    shape = RoundedCornerShape(8.dp),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = b.actionText,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VihaanGold
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (isThinking) {
                item {
                    Row(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = VihaanRoyalBlue,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Vihaan Concierge is thinking...",
                            fontSize = 12.sp,
                            color = VihaanSecondaryText
                        )
                    }
                }
            }
        }

        // Suggested Prompts Chips
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(suggestedPrompts) { prompt ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(VihaanWhite)
                        .border(1.dp, VihaanBorder, RoundedCornerShape(16.dp))
                        .clickable { handleSend(prompt) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = prompt,
                        fontSize = 11.sp,
                        color = VihaanRoyalBlue,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Chat Input Box
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
                    placeholder = { Text("Ask Concierge anything...", fontSize = 13.sp) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("concierge_input_field"),
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { handleSend(inputText) },
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(VihaanDeepNavy)
                        .testTag("concierge_send_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send",
                        tint = VihaanGold
                    )
                }
            }
        }
    }
}
