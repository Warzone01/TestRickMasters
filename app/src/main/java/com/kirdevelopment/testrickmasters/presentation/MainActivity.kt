package com.kirdevelopment.testrickmasters.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kirdevelopment.testrickmasters.presentation.ui.my_home.MyHomeScreen
import com.kirdevelopment.testrickmasters.presentation.ui.theme.LightGray
import com.kirdevelopment.testrickmasters.presentation.ui.theme.TestRickMastersTheme
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Realm.init(this)
        setContent {
            TestRickMastersTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = LightGray
                ) {
                    MyHomeScreen()
                }
            }
        }
    }
}
