package com.example.btvn_mvvm.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    onAddClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 12.dp,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home button
            BottomNavItem(
                icon = Icons.Default.Home,
                isSelected = true,
                onClick = { }
            )

            // Calendar button
            BottomNavItem(
                icon = Icons.Default.DateRange,
                isSelected = false,
                onClick = { }
            )

            // Add button (FAB style)
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = Color(0xFF2196F3),
                contentColor = Color.White,
                modifier = Modifier.size(56.dp),
                shape = CircleShape,
                elevation = FloatingActionButtonDefaults.elevation(
                    defaultElevation = 8.dp,
                    pressedElevation = 12.dp
                )
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add Task",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Files button
            BottomNavItem(
                icon = Icons.Default.Folder,
                isSelected = false,
                onClick = { }
            )

            // Settings button
            BottomNavItem(
                icon = Icons.Default.Settings,
                isSelected = false,
                onClick = { }
            )
        }
    }
}

@Composable
private fun BottomNavItem(
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) Color(0xFF2196F3) else Color(0xFF9E9E9E),
            modifier = Modifier.size(24.dp)
        )
    }
}