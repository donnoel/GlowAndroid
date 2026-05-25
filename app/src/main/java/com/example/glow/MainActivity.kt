package com.example.glow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.glow.ui.theme.GlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GlowTheme {
                GlowApp()
            }
        }
    }
}

private enum class GlowTab(val title: String) {
    Today("Today"),
    Insights("Insights"),
    Library("Library")
}

@Composable
fun GlowApp() {
    var selectedTab by rememberSaveable { mutableStateOf(GlowTab.Today) }
    val habitStore = remember { HabitStore().apply { seedIfEmpty() } }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                GlowTab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = selectedTab == tab,
                        onClick = { selectedTab = tab },
                        icon = { },
                        label = { Text(tab.title) }
                    )
                }
            }
        }
    ) { innerPadding ->
        when (selectedTab) {
            GlowTab.Today -> TodayScreen(
                innerPadding = innerPadding,
                habits = habitStore.habits,
                onToggleHabit = habitStore::toggleHabit
            )
            GlowTab.Insights -> InsightsScreen(
                innerPadding = innerPadding,
                habits = habitStore.habits
            )
            GlowTab.Library -> LibraryScreen(
                innerPadding = innerPadding,
                onAddTemplate = { title, detail ->
                    habitStore.addHabit(title, detail)
                    selectedTab = GlowTab.Today
                }
            )
        }
    }
}

@Composable
private fun GlowScreenTitle(title: String, innerPadding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}
