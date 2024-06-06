package com.example.promath.ui.screen

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(navController: NavController, vm: ProfileViewModel) {
    vm.loadUserData()
    val userData by vm.userData.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = palette.base100)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ProfileCard(vm = vm)
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
                Text(text = "Login")
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
                Text(text = "Logout")
            }
        }
    }
}

@Composable
private fun ProfileCard(vm: ProfileViewModel) {
    val username by vm.username.observeAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Profile", color = palette.baseContent, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = null,
            modifier = Modifier
                .height(80.dp)
                .width(80.dp),
            tint = palette.baseContent
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = username.toString(),
                color = palette.baseContent,
                fontSize = 16.sp
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Default.Create, contentDescription = null, tint = palette.baseContent)
            }
        }
    }
}

@Composable
@Preview
fun PreviewProfileScreen() {
//    ProfileScreen(rememberNavController())
}