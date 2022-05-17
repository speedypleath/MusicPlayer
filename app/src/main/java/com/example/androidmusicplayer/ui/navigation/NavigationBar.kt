package com.example.androidmusicplayer.ui.navigation

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.androidmusicplayer.ui.screen.AppScreen
import com.example.androidmusicplayer.ui.theme.White

@Composable
fun NavigationBar(
    allScreens: List<AppScreen>,
    onTabSelected: (AppScreen) -> Unit,
    currentScreen: AppScreen,
) {
    BottomNavigation(
        modifier = Modifier
            .semantics { contentDescription = "Navigation bar" }
            .fillMaxWidth()
            .absolutePadding(left = 16.dp, right = 16.dp)
            .clip(RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    ),
                    startY = 90f
                ),
                alpha = 0.9f
            ),
        contentColor = White,
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
    )
    {
        allScreens.forEach { screen ->
            BottomNavigationItem(
                label = { Text(screen.name) },
                icon = { Icon(imageVector = screen.icon, contentDescription = "") },
                onClick = { onTabSelected(screen) },
                selected = currentScreen == screen,
                modifier = Modifier.semantics {
                    contentDescription = screen.name + " button"
                }
            )
        }
    }
}