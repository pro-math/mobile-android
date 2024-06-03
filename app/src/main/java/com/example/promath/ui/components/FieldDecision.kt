package com.example.promath.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.MainViewModel

@Composable
fun FieldDecision(vm: MainViewModel) {
    val operations = listOf("+", "-", "*", "/")
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    var isErrorAnswer by remember {
        mutableStateOf(false)
    }
    var textFieldValue by remember {
        mutableStateOf("")
    }

    val example by vm.example.observeAsState()
    var timer by remember {
        mutableIntStateOf(30)
    }
    var previousExample by remember {
        mutableStateOf("2 + 1 = 3")
    }
    Log.i("TEST GENERATE EXAMPLE", vm.example.value.toString())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = palette.base200, shape = RoundedCornerShape(size = 10.dp))
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "time: $timer s",
                color = palette.baseContent,
                fontSize = 20.sp
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = example?.exampleString ?: "2 + 2",
                color = palette.baseContent,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (textFieldValue == example?.answer.toString()) {
                            isErrorAnswer = false
                            vm.addCountSuccessAnswer()
                        } else {
                            isErrorAnswer = true
                        }
                        vm.addCountExample()
                        textFieldValue = ""
                        previousExample = example!!.exampleString + " " + example!!.answer.toString()
                        vm.loadExample()
                    }
                ),
                modifier = Modifier.weight(1F),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
                placeholder = {
                    Text(text = "Answer")
                },
                isError = isErrorAnswer,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0x00FFFFFF),
                    focusedIndicatorColor = palette.baseContent,
                    focusedTextColor = palette.baseContent,
                    errorIndicatorColor = palette.error,
                    errorContainerColor = Color(0x00FFFFFF),
                    errorTextColor = palette.baseContent,
                    focusedContainerColor = Color(0x00FFFFFF)
                ),
                maxLines = 1
            )
        }
        Text(
            text = previousExample,
            color = palette.baseContentSecondary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}