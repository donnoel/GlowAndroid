package com.example.glow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private data class RecentWin(
    val title: String,
    val detail: String
)

@Composable
fun InsightsScreen(
    innerPadding: PaddingValues,
    habits: SnapshotStateList<HabitItem>
) {
    val completedCount = habits.count { it.isComplete }
    val totalCount = habits.size
    val weeklyCompletion = if (totalCount == 0) 0f else completedCount.toFloat() / totalCount.toFloat()
    val currentStreak = if (completedCount == 0) 0 else completedCount + 1

    val recentWins = buildList {
        if (completedCount > 0) {
            add(
                RecentWin(
                    title = "Completed today",
                    detail = "$completedCount habit${if (completedCount == 1) "" else "s"} finished"
                )
            )
        }

        val firstOpenHabit = habits.firstOrNull { !it.isComplete }
        if (firstOpenHabit != null) {
            add(
                RecentWin(
                    title = "Still in motion",
                    detail = "Next up: ${firstOpenHabit.title}"
                )
            )
        }

        if (habits.isNotEmpty()) {
            add(
                RecentWin(
                    title = "Library in use",
                    detail = "${habits.size} habit${if (habits.size == 1) "" else "s"} active right now"
                )
            )
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = "Insights",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Your rhythm, at a glance.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        item {
            InsightCard(
                title = "Current streak",
                value = "$currentStreak day${if (currentStreak == 1) "" else "s"}",
                supporting = if (completedCount == 0) {
                    "One small win gets the streak moving."
                } else {
                    "Built from what you’ve completed today."
                }
            )
        }

        item {
            WeeklySummaryCard(progress = weeklyCompletion)
        }

        item {
            Text(
                text = "Recent wins",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        if (recentWins.isEmpty()) {
            item {
                RecentWinCard(
                    win = RecentWin(
                        title = "No activity yet",
                        detail = "Add a habit from Library to begin building momentum."
                    )
                )
            }
        } else {
            items(recentWins) { win ->
                RecentWinCard(win = win)
            }
        }
    }
}

@Composable
private fun InsightCard(
    title: String,
    value: String,
    supporting: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = supporting,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun WeeklySummaryCard(progress: Float) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Weekly completion",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "${(progress * 100).toInt()}%",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.coerceIn(0f, 1f))
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(vertical = 4.dp)
                )
            }

            Text(
                text = if (progress == 0f) {
                    "No completions yet. Start with one gentle win."
                } else if (progress >= 1f) {
                    "Everything on your list is complete. Beautiful work."
                } else {
                    "Steady progress this week. Keep the pace gentle and repeatable."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun RecentWinCard(win: RecentWin) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = win.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = win.detail,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}