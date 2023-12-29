package com.kirdevelopment.testrickmasters.presentation.ui.my_home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kirdevelopment.testrickmasters.R
import com.kirdevelopment.testrickmasters.presentation.ui.my_home.cameras.CamerasScreen
import com.kirdevelopment.testrickmasters.presentation.ui.my_home.doors.DoorsScreen
import com.kirdevelopment.testrickmasters.presentation.ui.theme.Black
import com.kirdevelopment.testrickmasters.presentation.ui.theme.LightBlue
import com.kirdevelopment.testrickmasters.presentation.ui.theme.LightGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyHomeScreen() {
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
    val tabTitles =
        listOf(stringResource(id = R.string.cameras), stringResource(id = R.string.doors))

    Column {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            title = {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(id = R.string.my_house),
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Medium,
                        color = Black
                    )
                }
            },
            colors = topAppBarColors(
                containerColor = LightGray
            )
        )

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = LightGray,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = LightBlue
                )
            }
        ) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = Black,
                            fontSize = 17.sp
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> CamerasScreen()
            1 -> DoorsScreen()
        }
    }
}