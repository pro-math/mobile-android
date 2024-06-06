package com.example.promath.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.domain.models.ResultModel
import com.example.promath.ui.themenew.palette
import com.example.promath.utils.isValidLogin
import com.example.promath.utils.isValidPassword
import com.example.promath.viewmodel.LoginViewModel
import com.example.promath.viewmodel.RegistrationViewModel

@Composable
fun LoginCard(vm: LoginViewModel, navController: NavController) {

    var login by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }
    val loginResult by vm.loginResult.observeAsState()

    if (loginResult != null && loginResult!!.status == ResultModel.Status.SUCCESS) {
        navController.navigate("main_screen")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login", color = palette.baseContent, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        if (loginResult != null && loginResult!!.status == ResultModel.Status.FAILURE) {
            Text(text = loginResult!!.message.toString(), color = palette.error, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = login,
            onValueChange = {
                login = it
            },
            placeholder = {
                Text(text = "Login")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0x00FFFFFF),
                unfocusedTextColor = palette.baseContent,
                focusedContainerColor = Color(0x00FFFFFF),
                focusedTextColor = palette.baseContent,
                focusedIndicatorColor = palette.baseContent
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            },
            trailingIcon = {
                val icon = if(showPassword) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                }

                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0x00FFFFFF),
                unfocusedTextColor = palette.baseContent,
                focusedContainerColor = Color(0x00FFFFFF),
                focusedTextColor = palette.baseContent,
                focusedIndicatorColor = palette.baseContent
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                vm.loginUser(login, password)
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = palette.primary,
                contentColor = palette.primaryContent
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Accept")
        }
    }
}

@Composable
fun RegistrationCard(vm: RegistrationViewModel, navController: NavController) {

    var login by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var password2 by remember {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }
    var correctPassword by remember {
        mutableStateOf(true)
    }
    val registrationResult by vm.registrationResult.observeAsState()

    if (registrationResult != null && registrationResult!!.status == ResultModel.Status.SUCCESS) {
        navController.navigate("main_screen")
    }
    var isValidLogin by remember {
        mutableStateOf(true)
    }
    var isValidPassword by remember {
        mutableStateOf(true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Registration", color = palette.baseContent, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        if (registrationResult != null && registrationResult!!.status == ResultModel.Status.FAILURE) {
            Text(text = registrationResult!!.message.toString(), color = palette.error, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
        if (!correctPassword) {
            Text(text = "Password mismatch", color = palette.error, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        } else if (!isValidLogin) {
            Text(text = "The login must be longer than 2 and shorter than 26 characters. Consist of Latin letters, numbers and -, _, .", color = palette.error, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        } else if (!isValidPassword) {
            Text(text = "The password must be longer than 7 and shorter than 26 characters. Must contain at least 1 uppercase letter, 1 lowercase letter, 1 number and 1 special character", color = palette.error, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = login,
            onValueChange = {
                login = it
            },
            placeholder = {
                Text(text = "Login")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            },
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0x00FFFFFF),
                unfocusedTextColor = palette.baseContent,
                focusedContainerColor = Color(0x00FFFFFF),
                focusedTextColor = palette.baseContent,
                focusedIndicatorColor = palette.baseContent
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = {
                password = it
            },
            placeholder = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            },
            trailingIcon = {
                val icon = if(showPassword) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                }

                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0x00FFFFFF),
                unfocusedTextColor = palette.baseContent,
                focusedContainerColor = Color(0x00FFFFFF),
                focusedTextColor = palette.baseContent,
                focusedIndicatorColor = palette.baseContent
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password2,
            onValueChange = {
                password2 = it
            },
            placeholder = {
                Text(text = "Confirm password")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            },
            trailingIcon = {
                val icon = if(showPassword) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                }

                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(imageVector = icon, contentDescription = null)
                }
            },
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color(0x00FFFFFF),
                unfocusedTextColor = palette.baseContent,
                focusedContainerColor = Color(0x00FFFFFF),
                focusedTextColor = palette.baseContent,
                focusedIndicatorColor = palette.baseContent
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (password == password2) {
                    correctPassword = true
                    if (isValidLogin(login)) {
                        isValidLogin = true
                        if (isValidPassword(password)) {
                            isValidPassword = true
                            vm.registrationUser(
                                login = login,
                                password = password
                            )
                        } else {
                            isValidPassword = false
                        }
                    } else {
                        isValidLogin = false
                    }
                } else {
                    correctPassword = false
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = palette.primary,
                contentColor = palette.primaryContent
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Accept")
        }
    }
}
