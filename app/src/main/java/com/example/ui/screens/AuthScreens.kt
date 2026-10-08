package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.VihaanRepository
import com.example.model.UserRole
import com.example.ui.components.VihaanLogo
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onTimeout: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        onTimeout()
    }

    val infiniteTransition = rememberInfiniteTransition(label = "dots")
    val dotAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanDeepNavy)
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            VihaanLogo(
                size = 140.dp,
                showTagline = true,
                isDarkTheme = true
            )

            Spacer(modifier = Modifier.height(48.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                repeat(5) { index ->
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(VihaanGold.copy(alpha = dotAlpha))
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Loading...",
                color = Color.LightGray,
                fontSize = 12.sp,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit,
    onLoginClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanOffWhite)
            .testTag("onboarding_screen")
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 40.dp)
            ) {
                Text(
                    text = "Welcome to",
                    fontSize = 16.sp,
                    color = VihaanSecondaryText,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Vihaan",
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = VihaanDeepNavy
                )
                Text(
                    text = "HOME SERVICES",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = VihaanGold,
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Premium Services\nfor Your Beautiful Home",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = VihaanSecondaryText
                )
            }

            // Visual Center Illustration Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = VihaanWhite)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(160.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(VihaanDeepNavy),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_vihaan_logo),
                            contentDescription = "Vihaan Luxury Services",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Your Home, Our Care.",
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy,
                        fontSize = 17.sp
                    )
                    Text(
                        text = "Professional Services at Your Doorstep.",
                        color = VihaanSecondaryText,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }

            // Bottom Buttons
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = onGetStarted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("get_started_button"),
                    colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Get Started",
                        color = VihaanWhite,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account? ",
                        color = VihaanSecondaryText,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "Login",
                        color = VihaanRoyalBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable { onLoginClick() }
                            .testTag("onboarding_login_text")
                    )
                }
            }
        }
    }
}

@Composable
fun LoginScreen(
    onSendOtp: (String) -> Unit,
    onQuickDemoLogin: (UserRole) -> Unit,
    onBack: () -> Unit
) {
    var mobileNumber by remember { mutableStateOf("9876543210") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanWhite)
            .padding(24.dp)
            .testTag("login_screen"),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            IconButton(
                onClick = onBack,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = VihaanDeepNavy)
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Welcome Back 👋",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = VihaanDeepNavy
            )
            Text(
                text = "Let's get you logged in",
                fontSize = 14.sp,
                color = VihaanSecondaryText
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Mobile Number",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = VihaanDarkText
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = mobileNumber,
                onValueChange = { if (it.length <= 10) mobileNumber = it },
                leadingIcon = {
                    Text(
                        text = "🇮🇳 +91  ",
                        fontWeight = FontWeight.Bold,
                        color = VihaanDarkText,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("mobile_input"),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onSendOtp(mobileNumber) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("send_otp_button"),
                colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                shape = RoundedCornerShape(12.dp),
                enabled = mobileNumber.length >= 10
            ) {
                Text(
                    text = "Send OTP",
                    color = VihaanWhite,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Or Continue with Social / Google
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = VihaanBorder)
                Text(
                    text = "  or Continue with  ",
                    fontSize = 12.sp,
                    color = VihaanSecondaryText
                )
                HorizontalDivider(modifier = Modifier.weight(1f), color = VihaanBorder)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Google Sign In Button
            OutlinedButton(
                onClick = {
                    VihaanRepository.switchUserRole(UserRole.CUSTOMER)
                    onQuickDemoLogin(UserRole.CUSTOMER)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("google_signin_button"),
                shape = RoundedCornerShape(12.dp),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = Brush.horizontalGradient(listOf(VihaanGold, VihaanRoyalBlue)))
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Google",
                    tint = VihaanRoyalBlue
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sign in with Google",
                    color = VihaanDeepNavy,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Demo quick login pills
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = VihaanSurfaceLight),
            shape = RoundedCornerShape(16.dp),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Quick Demo / Persona Switcher",
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
                        onClick = { onQuickDemoLogin(UserRole.CUSTOMER) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = VihaanRoyalBlue),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(vertical = 6.dp)
                    ) {
                        Text("Customer", fontSize = 11.sp, color = VihaanWhite)
                    }
                    Button(
                        onClick = { onQuickDemoLogin(UserRole.PROFESSIONAL) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = VihaanGold),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(vertical = 6.dp)
                    ) {
                        Text("Professional", fontSize = 11.sp, color = VihaanDeepNavy, fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = { onQuickDemoLogin(UserRole.ADMIN) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(vertical = 6.dp)
                    ) {
                        Text("Admin", fontSize = 11.sp, color = VihaanWhite)
                    }
                }
            }
        }
    }
}

@Composable
fun OtpVerificationScreen(
    mobileNumber: String,
    onVerifySuccess: () -> Unit,
    onChangeNumber: () -> Unit
) {
    var otpDigits by remember { mutableStateOf(listOf("1", "2", "3", "4", "5", "6")) }
    var timerSeconds by remember { mutableIntStateOf(28) }
    var isError by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = timerSeconds) {
        if (timerSeconds > 0) {
            delay(1000)
            timerSeconds--
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VihaanWhite)
            .padding(24.dp)
            .testTag("otp_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onChangeNumber) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = VihaanDeepNavy)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(VihaanSurfaceLight)
                .border(1.dp, VihaanGold.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "OTP",
                tint = VihaanRoyalBlue,
                modifier = Modifier.size(34.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Enter OTP",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = VihaanDeepNavy
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "We have sent 6 digit code\nto +91 $mobileNumber",
            fontSize = 13.sp,
            color = VihaanSecondaryText,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // 6 digit boxes
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            otpDigits.forEachIndexed { index, digit ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(VihaanOffWhite)
                        .border(
                            1.5.dp,
                            if (digit.isNotEmpty()) VihaanRoyalBlue else VihaanBorder,
                            RoundedCornerShape(10.dp)
                        )
                        .testTag("otp_box_$index"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = digit,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = VihaanDeepNavy
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = if (timerSeconds > 0) "Resend OTP in 00:${if (timerSeconds < 10) "0$timerSeconds" else timerSeconds}" else "Resend OTP",
            fontSize = 13.sp,
            color = if (timerSeconds > 0) VihaanSecondaryText else VihaanRoyalBlue,
            fontWeight = if (timerSeconds > 0) FontWeight.Normal else FontWeight.Bold,
            modifier = Modifier.clickable(enabled = timerSeconds == 0) {
                timerSeconds = 30
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (otpDigits.joinToString("") == "123456" || otpDigits.all { it.isNotEmpty() }) {
                    onVerifySuccess()
                } else {
                    isError = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("verify_otp_button"),
            colors = ButtonDefaults.buttonColors(containerColor = VihaanDeepNavy),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Verify OTP",
                color = VihaanWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Didn't get the code? Resend OTP",
            fontSize = 13.sp,
            color = VihaanRoyalBlue,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable {
                timerSeconds = 30
                otpDigits = listOf("1", "2", "3", "4", "5", "6")
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Change Number",
            fontSize = 12.sp,
            color = VihaanSecondaryText,
            modifier = Modifier.clickable { onChangeNumber() }
        )
    }
}
