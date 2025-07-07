package com.example.btvn_mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.btvn_mvvm.ui.navigation.HomeworkNavigation
import com.example.btvn_mvvm.ui.theme.BTVN_MVVMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BTVN_MVVMTheme {
                HomeworkNavigation()
            }
        }
    }
}