package com.example.promath.ui.screen

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.promath.MainActivity
import com.example.promath.ui.components.CustomDropdownMenu
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController, vm: ProfileViewModel) {
    vm.loadUserData()
    val userData by vm.userData.observeAsState()
    val isOpenDeleteAccountDialog = remember {
        mutableStateOf(false)
    }

    if (isOpenDeleteAccountDialog.value) {
        DeleteAccountDialog(vm = vm) {
            isOpenDeleteAccountDialog.value = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base100)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        val updateTheme by vm.updateTheme.observeAsState()
        val activity = LocalContext.current as MainActivity
        val currentThemeId by vm.currentThemeId.observeAsState()
        CustomDropdownMenu(list = listOf("Dark", "Cupcake", "Valentine", "Emerald"), defaultSelected = "Dark", color = palette.primary, modifier = Modifier, defaultSelect = currentThemeId!!) {
            when (it) {
                0 -> vm.setTheme("dark")
                1 -> vm.setTheme("cupcake")
                2 -> vm.setTheme("valentine")
                3 -> vm.setTheme("emerald")
            }
            vm.updateTheme.postValue(!updateTheme!!)
//            activity.recreate()
        }
        Log.i("TEST THEME", palette.toString())
        ProfileCard(navController = navController, vm = vm)
        Spacer(modifier = Modifier.height(8.dp))
        if (userData == null) {
            Button(
                onClick = {
                    navController.navigate("login_screen")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.primary,
                    contentColor = palette.primaryContent
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Войти в аккаунт")
            }
        } else {
            Button(
                onClick = {
                    vm.logoutUser()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.primary,
                    contentColor = palette.primaryContent
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Выйти из аккаунта")
            }
            Button(
                onClick = { isOpenDeleteAccountDialog.value = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.error,
                    contentColor = palette.errorContent
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Удалить аккаунт")
            }
        }
    }
}

@Composable
private fun ProfileCard(navController: NavController, vm: ProfileViewModel) {
    val username by vm.username.observeAsState()
    val userdata by vm.userData.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Профиль", color = palette.baseContent, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .height(80.dp)
                .width(80.dp),
            tint = palette.baseContent
        )
        Text(
            text = if (userdata != null && username != null) {
                username.toString()
            } else {
                "Имя пользователя"
            },
            color = palette.baseContent,
            fontSize = 16.sp
        )
        if (userdata != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Spacer(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = palette.baseContent))
            Button(
                onClick = {
                    navController.navigate("progress_screen")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.base200,
                    contentColor = palette.baseContent
                ),
                shape = RectangleShape
            ) {
                Text(text = "Ваш прогресс", fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = palette.baseContent))
            Button(
                onClick = {
                    navController.navigate("user_rating_screen")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.base200,
                    contentColor = palette.baseContent
                ),
                shape = RectangleShape
            ) {
                Text(text = "История игр", fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = palette.baseContent))
            Button(
                onClick = {
                    navController.navigate("achievements_screen")
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = palette.base200,
                    contentColor = palette.baseContent
                ),
                shape = RectangleShape
            ) {
                Text(text = "Достижения", fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier
                .height(1.dp)
                .fillMaxWidth()
                .background(color = palette.baseContent))
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun DeleteAccountDialog(vm: ProfileViewModel, onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = palette.base100
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Вы уверенны, что хотите удалить аккаунт?", color = palette.baseContent, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Отмена", color = palette.primary)
                    }
                    Spacer(modifier = Modifier.width(32.dp))
                    TextButton(onClick = {
                        vm.deleteUser()
                        onDismissRequest()
                    }) {
                        Text(text = "Удалить", color = palette.error)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewProfileScreen() {
//    DeleteAccountDialog()
//    ProfileScreen(rememberNavController())
}