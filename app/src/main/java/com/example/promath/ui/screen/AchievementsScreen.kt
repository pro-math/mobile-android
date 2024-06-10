package com.example.promath.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.promath.ui.components.AchievementItem
import com.example.promath.ui.themenew.palette
import com.example.promath.viewmodel.AchievementViewModel

@Composable
fun AchievementsScreen(vm: AchievementViewModel) {
    val selectedTab = remember {
        mutableIntStateOf(0)
    }
    vm.loadAll()
    vm.loadUser()
    val all by vm.allAchievement.observeAsState()
    val user by vm.userAchievement.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize().background(color = palette.base100)
    ) {
        TabRow(
            selectedTabIndex = selectedTab.intValue,
            containerColor = palette.base200,
            contentColor = palette.baseContent,
            indicator = @Composable { tabPositions ->
                if (selectedTab.intValue < tabPositions.size) {
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab.intValue]),
                        color = palette.primary
                    )
                }
            }
        ) {
            Tab(
                selected = selectedTab.intValue == 0,
                onClick = { selectedTab.intValue = 0 },
                text = { Text("Все достижения") },
                selectedContentColor = palette.primary,
                unselectedContentColor = palette.baseContent
            )
            Tab(
                selected = selectedTab.intValue == 1,
                onClick = { selectedTab.intValue = 1 },
                text = { Text("Ваши достижения") },
                selectedContentColor = palette.primary,
                unselectedContentColor = palette.baseContent
            )
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            if (selectedTab.intValue == 0) {
                all!!.forEach {
                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                        AchievementItem(it)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            } else {
                user!!.forEach {
                    item {
                        Spacer(modifier = Modifier.height(4.dp))
                        AchievementItem(it)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewAchievementsScreen() {
//    AchievementsScreen()
}