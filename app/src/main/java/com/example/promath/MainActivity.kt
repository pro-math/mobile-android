package com.example.promath

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.domain.models.ResultModel
import com.example.domain.usecase.GetCurrentThemeUseCase
import com.example.domain.usecase.GetTokenFromLocalStorageUseCase
import com.example.promath.ui.components.ModeSelectionProgress
import com.example.promath.ui.screen.AchievementsScreen
import com.example.promath.ui.screen.LoginScreen
import com.example.promath.ui.screen.MainScreen
import com.example.promath.ui.screen.ProfileScreen
import com.example.promath.ui.screen.ProgressScreen
import com.example.promath.ui.screen.RatingScreen
import com.example.promath.ui.screen.RegistrationScreen
import com.example.promath.ui.screen.UserRatingScreen
import com.example.promath.ui.theme.ProMathTheme
import com.example.promath.ui.themenew.palette
import com.example.promath.ui.themenew.paletteCupcake
import com.example.promath.ui.themenew.paletteDark
import com.example.promath.ui.themenew.paletteEmerald
import com.example.promath.ui.themenew.paletteValentine
import com.example.promath.viewmodel.AchievementViewModel
import com.example.promath.viewmodel.LoginViewModel
import com.example.promath.viewmodel.MainViewModel
import com.example.promath.viewmodel.ProfileViewModel
import com.example.promath.viewmodel.ProgressViewModel
import com.example.promath.viewmodel.RatingViewModel
import com.example.promath.viewmodel.RegistrationViewModel
import com.example.promath.viewmodel.UserRatingViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModel<MainViewModel>()
    private val loginViewModel by viewModel<LoginViewModel>()
    private val registrationViewModel by viewModel<RegistrationViewModel>()
    private val getTokenFromLocalStorageUseCase by inject<GetTokenFromLocalStorageUseCase>()
    private val profileViewModel by inject<ProfileViewModel>()
    private val getCurrentThemeUseCase by inject<GetCurrentThemeUseCase>()
    private val ratingViewModel by inject<RatingViewModel>()
    private val userRatingViewModel by inject<UserRatingViewModel>()
    private val progressViewModel by inject<ProgressViewModel>()
    private val achievementViewModel by inject<AchievementViewModel>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProMathTheme(
                getCurrentThemeUseCase = getCurrentThemeUseCase
            ) {

                this.window.statusBarColor = palette.base100.toArgb()
                val updateTheme by profileViewModel.updateTheme.observeAsState()
                Log.i("TEST THEME", updateTheme.toString())
                var selectedItem by remember {
                    mutableIntStateOf(0)
                }
                if (updateTheme == true) {
                    selectedItem = 2
                }
                val navController = rememberNavController()
//                val paletteTheme by palette
                Scaffold(
                    containerColor = palette.base100,
                    bottomBar = {
                        NavigationBar(
                            containerColor = palette.base200
                        ) {
                            NavigationBarItem(
                                selected = selectedItem == 0,
                                onClick = {
                                    selectedItem = 0
                                    navController.navigate("main_screen")
                                },
                                icon = {
                                    Icon(imageVector = Icons.Default.Home, contentDescription = null)
                                },
                                label = {
                                    Text(text = "Игра")
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = palette.primary,
                                    indicatorColor = Color(0x00FFFFFF),
                                    selectedTextColor = palette.primary,
                                    unselectedIconColor = palette.baseContent,
                                    unselectedTextColor = palette.baseContent
                                )
                            )
                            NavigationBarItem(
                                selected = selectedItem == 1,
                                onClick = {
                                    selectedItem = 1
                                    navController.navigate("rating_screen")
                                },
                                icon = {
                                    Icon(imageVector = Icons.Default.Star, contentDescription = null)
                                },
                                label = {
                                    Text(text = "Рейтинг")
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = palette.primary,
                                    indicatorColor = Color(0x00FFFFFF),
                                    selectedTextColor = palette.primary,
                                    unselectedIconColor = palette.baseContent,
                                    unselectedTextColor = palette.baseContent
                                )
                            )
                            NavigationBarItem(
                                selected = selectedItem == 2,
                                onClick = {
                                    selectedItem = 2
                                    navController.navigate("profile_screen")
                                },
                                icon = {
                                    Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
                                },
                                label = {
                                    Text(text = "Профиль")
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = palette.primary,
                                    indicatorColor = Color(0x00FFFFFF),
                                    selectedTextColor = palette.primary,
                                    unselectedIconColor = palette.baseContent,
                                    unselectedTextColor = palette.baseContent
                                )
                            )
                        }
                    }
                ) {
                    val startScreen = "main_screen"
                    Column(
                        modifier = Modifier.padding(bottom = it.calculateBottomPadding())
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = startScreen,
                            enterTransition = {
                                EnterTransition.None
                            },
                            exitTransition = {
                                ExitTransition.None
                            }
                        ) {
                            composable(
                                route = "main_screen"
                            ) {
                                MainScreen(vm = mainViewModel)
                            }
                            composable(
                                route = "rating_screen"
                            ) {
                                RatingScreen(vm = ratingViewModel)
                            }
                            composable(
                                route = "user_rating_screen"
                            ) {
                                UserRatingScreen(vm = userRatingViewModel)
                            }
                            composable(
                                route = "profile_screen"
                            ) {
                                ProfileScreen(navController = navController, vm = profileViewModel)
                            }
                            composable(
                                route = "login_screen"
                            ) {
                                LoginScreen(vm = loginViewModel, navController = navController)
                            }
                            composable(
                                route = "registration_screen"
                            ) {
                                RegistrationScreen(vm = registrationViewModel, navController = navController)
                            }
                            composable(
                                route = "progress_screen"
                            ) {
                                ProgressScreen(vm = progressViewModel)
                            }
                            composable(
                                route = "achievements_screen"
                            ) {
                                AchievementsScreen(vm = achievementViewModel)
                            }
                        }
                    }
                }

//                val data =
//                    mapOf(
//                        LocalDate.parse("2022-07-01") to 2f,
//                        LocalDate.parse("2022-07-02") to 6f,
//                        LocalDate.parse("2022-07-04") to 4f,
//                    )
//                val xToDateMapKey = ExtraStore.Key<Map<Float, LocalDate>>()
//                val xToDates = data.keys.associateBy { it.toEpochDay().toFloat() }
//                val cartesianChartModelProducer = remember { CartesianChartModelProducer.build() }
//                cartesianChartModelProducer.tryRunTransaction {
//                    lineSeries { series(xToDates.keys, data.values) }
//                    updateExtras { it[xToDateMapKey] = xToDates }
//                }
//                val dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM")
//                val formatter = CartesianValueFormatter { x, chartValues, _ ->
//                    (chartValues.model.extraStore[xToDateMapKey][x] ?: LocalDate.ofEpochDay(x.toLong()))
//                        .format(dateTimeFormatter)
//                }
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(color = palette.base100)
//                        .padding(16.dp),
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Text(text = "Progress", color = palette.baseContent, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
//                    Spacer(modifier = Modifier.height(8.dp))
////                    ModeSelectionProgress(vm = vm)
//                    Spacer(modifier = Modifier.height(8.dp))
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
//                            .padding(8.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        CartesianChartHost(
//                            chart = rememberCartesianChart(
//                                rememberLineCartesianLayer(
//                                    listOf(rememberLineSpec(DynamicShader.color(palette.primary)))
//                                ),
//                                startAxis = rememberStartAxis(),
//                                bottomAxis = rememberBottomAxis(
//                                    valueFormatter = formatter
//                                )
//                            ),
//                            modelProducer = cartesianChartModelProducer
//                        )
//                    }
//                }

            }
        }
    }
}