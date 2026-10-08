package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.example.model.Booking
import com.example.model.HomeService
import com.example.model.UserRole
import com.example.ui.components.VihaanBottomNav
import com.example.ui.screens.*
import com.example.ui.theme.*

enum class Screen {
    SPLASH,
    ONBOARDING,
    LOGIN,
    OTP,
    HOME,
    EXPLORE,
    SERVICE_DETAIL,
    BOOKING_FLOW,
    BOOKING_CONFIRMED,
    LIVE_TRACKING,
    CHAT,
    WALLET,
    CARE_PLUS,
    CONCIERGE,
    BOOKING_HISTORY,
    DIGITAL_INVOICE,
    EMERGENCY,
    PRO_DASHBOARD,
    ADMIN_DASHBOARD,
    PROFILE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VihaanTheme {
                VihaanApp()
            }
        }
    }
}

@Composable
fun VihaanApp() {
    var currentScreen by remember { mutableStateOf(Screen.SPLASH) }
    var previousScreen by remember { mutableStateOf(Screen.HOME) }
    var currentBottomTab by remember { mutableStateOf("home") }
    var selectedService by remember { mutableStateOf(VihaanRepository.services.first()) }
    var activeBooking by remember { mutableStateOf(VihaanRepository.currentBookingState.value) }
    var otpMobile by remember { mutableStateOf("9876543210") }
    var showPersonaDialog by remember { mutableStateOf(false) }

    val user by VihaanRepository.currentUserState.collectAsState()

    fun navigateTo(screen: Screen) {
        previousScreen = currentScreen
        currentScreen = screen
    }

    // Handle back button behavior for sub-screens
    if (currentScreen !in listOf(Screen.HOME, Screen.SPLASH, Screen.ONBOARDING, Screen.LOGIN)) {
        BackHandler {
            when (currentScreen) {
                Screen.PRO_DASHBOARD, Screen.ADMIN_DASHBOARD -> {
                    VihaanRepository.switchUserRole(UserRole.CUSTOMER)
                    currentScreen = Screen.HOME
                }
                Screen.SERVICE_DETAIL, Screen.EXPLORE, Screen.WALLET, Screen.PROFILE, Screen.BOOKING_HISTORY, Screen.CONCIERGE -> {
                    currentScreen = Screen.HOME
                    currentBottomTab = "home"
                }
                Screen.BOOKING_FLOW -> currentScreen = Screen.SERVICE_DETAIL
                Screen.BOOKING_CONFIRMED -> currentScreen = Screen.HOME
                Screen.LIVE_TRACKING -> currentScreen = Screen.HOME
                Screen.CHAT -> currentScreen = Screen.LIVE_TRACKING
                Screen.DIGITAL_INVOICE -> currentScreen = Screen.BOOKING_HISTORY
                Screen.OTP -> currentScreen = Screen.LOGIN
                else -> currentScreen = Screen.HOME
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .testTag("vihaan_main_scaffold"),
        contentWindowInsets = WindowInsets.systemBars,
        bottomBar = {
            // Show bottom navigation bar on primary customer tabs
            if (currentScreen in listOf(Screen.HOME, Screen.EXPLORE, Screen.BOOKING_HISTORY, Screen.WALLET, Screen.PROFILE)) {
                VihaanBottomNav(
                    currentRoute = currentBottomTab,
                    onNavigate = { route ->
                        currentBottomTab = route
                        currentScreen = when (route) {
                            "home" -> Screen.HOME
                            "explore" -> Screen.EXPLORE
                            "bookings" -> Screen.BOOKING_HISTORY
                            "wallet" -> Screen.WALLET
                            "profile" -> Screen.PROFILE
                            else -> Screen.HOME
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentScreen) {
                Screen.SPLASH -> {
                    SplashScreen(
                        onTimeout = { currentScreen = Screen.ONBOARDING }
                    )
                }

                Screen.ONBOARDING -> {
                    OnboardingScreen(
                        onGetStarted = { currentScreen = Screen.LOGIN },
                        onLoginClick = { currentScreen = Screen.LOGIN }
                    )
                }

                Screen.LOGIN -> {
                    LoginScreen(
                        onSendOtp = { mobile ->
                            otpMobile = mobile
                            currentScreen = Screen.OTP
                        },
                        onQuickDemoLogin = { role ->
                            VihaanRepository.switchUserRole(role)
                            when (role) {
                                UserRole.CUSTOMER -> {
                                    currentScreen = Screen.HOME
                                    currentBottomTab = "home"
                                }
                                UserRole.PROFESSIONAL -> currentScreen = Screen.PRO_DASHBOARD
                                UserRole.ADMIN -> currentScreen = Screen.ADMIN_DASHBOARD
                            }
                        },
                        onBack = { currentScreen = Screen.ONBOARDING }
                    )
                }

                Screen.OTP -> {
                    OtpVerificationScreen(
                        mobileNumber = otpMobile,
                        onVerifySuccess = {
                            currentScreen = Screen.HOME
                            currentBottomTab = "home"
                        },
                        onChangeNumber = { currentScreen = Screen.LOGIN }
                    )
                }

                Screen.HOME -> {
                    CustomerHomeScreen(
                        onSelectService = { srv ->
                            selectedService = srv
                            currentScreen = Screen.SERVICE_DETAIL
                        },
                        onCategoryClick = { _ ->
                            currentScreen = Screen.EXPLORE
                            currentBottomTab = "explore"
                        },
                        onOpenConcierge = { currentScreen = Screen.CONCIERGE },
                        onOpenWallet = {
                            currentScreen = Screen.WALLET
                            currentBottomTab = "wallet"
                        },
                        onOpenCarePlus = { currentScreen = Screen.CARE_PLUS },
                        onOpenEmergency = { currentScreen = Screen.EMERGENCY },
                        onOpenNotifications = { currentScreen = Screen.BOOKING_HISTORY },
                        onOpenSearch = {
                            currentScreen = Screen.EXPLORE
                            currentBottomTab = "explore"
                        },
                        onRoleSwitchClick = { showPersonaDialog = true }
                    )
                }

                Screen.EXPLORE -> {
                    ExploreScreen(
                        onSelectService = { srv ->
                            selectedService = srv
                            currentScreen = Screen.SERVICE_DETAIL
                        },
                        onBack = {
                            currentScreen = Screen.HOME
                            currentBottomTab = "home"
                        }
                    )
                }

                Screen.SERVICE_DETAIL -> {
                    ServiceDetailScreen(
                        service = selectedService,
                        onBack = { currentScreen = Screen.HOME },
                        onBookNow = { currentScreen = Screen.BOOKING_FLOW }
                    )
                }

                Screen.BOOKING_FLOW -> {
                    BookingFlowScreen(
                        service = selectedService,
                        onBack = { currentScreen = Screen.SERVICE_DETAIL },
                        onBookingConfirmed = { booking ->
                            activeBooking = booking
                            currentScreen = Screen.BOOKING_CONFIRMED
                        }
                    )
                }

                Screen.BOOKING_CONFIRMED -> {
                    BookingConfirmationScreen(
                        booking = activeBooking,
                        onTrackBooking = { currentScreen = Screen.LIVE_TRACKING },
                        onBackToHome = {
                            currentScreen = Screen.HOME
                            currentBottomTab = "home"
                        }
                    )
                }

                Screen.LIVE_TRACKING -> {
                    LiveTrackingScreen(
                        onBack = { currentScreen = Screen.HOME },
                        onOpenChat = { currentScreen = Screen.CHAT },
                        onViewInvoice = { currentScreen = Screen.DIGITAL_INVOICE }
                    )
                }

                Screen.CHAT -> {
                    ChatScreen(
                        onBack = { currentScreen = Screen.LIVE_TRACKING }
                    )
                }

                Screen.WALLET -> {
                    WalletScreen(
                        onBack = {
                            currentScreen = Screen.HOME
                            currentBottomTab = "home"
                        },
                        onOpenCoupons = { /* Coupons action */ }
                    )
                }

                Screen.CARE_PLUS -> {
                    CarePlusScreen(
                        onBack = { currentScreen = Screen.HOME }
                    )
                }

                Screen.CONCIERGE -> {
                    ConciergeScreen(
                        onBack = { currentScreen = Screen.HOME },
                        onNavigateService = { srv ->
                            selectedService = srv
                            currentScreen = Screen.SERVICE_DETAIL
                        },
                        onNavigateTracking = { currentScreen = Screen.LIVE_TRACKING },
                        onNavigateWallet = {
                            currentScreen = Screen.WALLET
                            currentBottomTab = "wallet"
                        },
                        onNavigateCare = { currentScreen = Screen.CARE_PLUS },
                        onNavigateEmergency = { currentScreen = Screen.EMERGENCY }
                    )
                }

                Screen.BOOKING_HISTORY -> {
                    BookingHistoryScreen(
                        onBack = {
                            currentScreen = Screen.HOME
                            currentBottomTab = "home"
                        },
                        onTrackBooking = { booking ->
                            activeBooking = booking
                            VihaanRepository.currentBookingState.value = booking
                            currentScreen = Screen.LIVE_TRACKING
                        },
                        onViewInvoice = { booking ->
                            activeBooking = booking
                            currentScreen = Screen.DIGITAL_INVOICE
                        },
                        onBookAgain = { booking ->
                            selectedService = booking.service
                            currentScreen = Screen.BOOKING_FLOW
                        }
                    )
                }

                Screen.DIGITAL_INVOICE -> {
                    DigitalInvoiceScreen(
                        booking = activeBooking,
                        onBack = { currentScreen = Screen.BOOKING_HISTORY }
                    )
                }

                Screen.EMERGENCY -> {
                    EmergencyBookingScreen(
                        onBack = { currentScreen = Screen.HOME },
                        onBookEmergency = { emgSrv ->
                            selectedService = emgSrv
                            currentScreen = Screen.BOOKING_FLOW
                        }
                    )
                }

                Screen.PRO_DASHBOARD -> {
                    ProfessionalDashboardScreen(
                        onSwitchToCustomer = {
                            VihaanRepository.switchUserRole(UserRole.CUSTOMER)
                            currentScreen = Screen.HOME
                            currentBottomTab = "home"
                        },
                        onOpenChat = { currentScreen = Screen.CHAT }
                    )
                }

                Screen.ADMIN_DASHBOARD -> {
                    AdminDashboardScreen(
                        onSwitchToCustomer = {
                            VihaanRepository.switchUserRole(UserRole.CUSTOMER)
                            currentScreen = Screen.HOME
                            currentBottomTab = "home"
                        }
                    )
                }

                Screen.PROFILE -> {
                    ProfileScreen(
                        onOpenBookings = {
                            currentScreen = Screen.BOOKING_HISTORY
                            currentBottomTab = "bookings"
                        },
                        onOpenWallet = {
                            currentScreen = Screen.WALLET
                            currentBottomTab = "wallet"
                        },
                        onOpenCarePlus = { currentScreen = Screen.CARE_PLUS },
                        onOpenCoupons = { /* Coupons */ },
                        onSwitchRole = { showPersonaDialog = true },
                        onLogout = { currentScreen = Screen.LOGIN }
                    )
                }
            }

            // Role / Persona Switching Dialog
            if (showPersonaDialog) {
                AlertDialog(
                    onDismissRequest = { showPersonaDialog = false },
                    title = {
                        Text(
                            text = "Switch User Role",
                            fontWeight = FontWeight.Bold,
                            color = VihaanDeepNavy
                        )
                    },
                    text = {
                        Column {
                            Text(
                                text = "Select persona to test app features:",
                                fontSize = 13.sp,
                                color = VihaanSecondaryText
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            // Customer Option
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        VihaanRepository.switchUserRole(UserRole.CUSTOMER)
                                        currentScreen = Screen.HOME
                                        currentBottomTab = "home"
                                        showPersonaDialog = false
                                    },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (user.role == UserRole.CUSTOMER) VihaanSurfaceLight else VihaanWhite
                                ),
                                border = if (user.role == UserRole.CUSTOMER) CardDefaults.outlinedCardBorder() else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.Person, contentDescription = "Customer", tint = VihaanRoyalBlue)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("Customer (Rahul Sharma)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("Book services, live tracking, wallet", fontSize = 11.sp, color = VihaanSecondaryText)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Professional Option
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        VihaanRepository.switchUserRole(UserRole.PROFESSIONAL)
                                        currentScreen = Screen.PRO_DASHBOARD
                                        showPersonaDialog = false
                                    },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (user.role == UserRole.PROFESSIONAL) VihaanSurfaceLight else VihaanWhite
                                ),
                                border = if (user.role == UserRole.PROFESSIONAL) CardDefaults.outlinedCardBorder() else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.Engineering, contentDescription = "Pro", tint = VihaanGold)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("Professional (Rohit Kumar)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("Accept jobs, earnings, navigation", fontSize = 11.sp, color = VihaanSecondaryText)
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Admin Option
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        VihaanRepository.switchUserRole(UserRole.ADMIN)
                                        currentScreen = Screen.ADMIN_DASHBOARD
                                        showPersonaDialog = false
                                    },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = if (user.role == UserRole.ADMIN) VihaanSurfaceLight else VihaanWhite
                                ),
                                border = if (user.role == UserRole.ADMIN) CardDefaults.outlinedCardBorder() else null
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Filled.AdminPanelSettings, contentDescription = "Admin", tint = VihaanDeepNavy)
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Column {
                                        Text("Super Admin Portal", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("KYC approvals, stats, coupon manager", fontSize = 11.sp, color = VihaanSecondaryText)
                                    }
                                }
                            }
                        }
                    },
                    confirmButton = {
                        TextButton(onClick = { showPersonaDialog = false }) {
                            Text("Cancel", color = VihaanSecondaryText)
                        }
                    }
                )
            }
        }
    }
}
