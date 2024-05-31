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
import androidx.compose.material.icons.filled.AccountCircle
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.promath.ui.themenew.palette

@Composable
fun LoginCard() {

    var login by remember {
        mutableStateOf("")
    }
    var showPassword by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200, shape = RoundedCornerShape(10.dp))
            .padding(16.dp)
    ) {
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
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = login,
            onValueChange = {
                login = it
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
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = palette.primary,
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Login")
        }
    }
}

@Composable
@Preview
fun PreviewLoginCard() {
    LoginCard()
}