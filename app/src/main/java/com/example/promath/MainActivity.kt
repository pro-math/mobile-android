package com.example.promath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.promath.ui.screen.LoginScreen
import com.example.promath.ui.screen.MainScreen
import com.example.promath.ui.screen.ProfileScreen
import com.example.promath.ui.screen.RatingScreen
import com.example.promath.ui.theme.ProMathTheme
import com.example.promath.ui.themenew.palette

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProMathTheme {
                var selectedItem by remember {
                    mutableIntStateOf(0)
                }
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        NavigationBar(
                            containerColor = palette.base200
                        ) {
                            NavigationBarItem(
                                selected = selectedItem == 0,
                                onClick = {
                                    selectedItem = 0
                                    navController.navigate("home_screen")
                                },
                                icon = {
                                    Icon(imageVector = Icons.Default.Home, contentDescription = null)
                                },
                                label = {
                                    Text(text = "Home")
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
                                    Text(text = "Rating")
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
                                    Text(text = "Profile")
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
                    val padding = it
                    val startScreen = "home_screen"
                    NavHost(navController = navController, startDestination = startScreen) {
                        composable(
                            route = "home_screen",
                            exitTransition = null,
                            enterTransition = null
                        ) {
                            MainScreen()
                        }
                        composable(
                            route = "rating_screen",
                            exitTransition = null,
                            enterTransition = null
                        ) {
                            RatingScreen()
                        }
                        composable(
                            route = "profile_screen",
                            exitTransition = null,
                            enterTransition = null
                        ) {
                            ProfileScreen()
                        }
                        composable(
                            route = "login_screen",
                            exitTransition = null,
                            enterTransition = null
                        ) {
                            LoginScreen()
                        }
                    }
                }
            }
        }
    }
}