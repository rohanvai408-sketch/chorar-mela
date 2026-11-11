package com.example.chorarmela.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chorarmela.data.Rhyme
import com.example.chorarmela.data.RhymeRepository
import com.example.chorarmela.ui.components.RhymeCard
import com.example.chorarmela.ui.theme.KidsCoralPink

@Composable
fun FavoritesScreen(
    favorites: Set<String>,
    onRhymeSelected: (Rhyme) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onQuickListen: (Rhyme) -> Unit,
    modifier: Modifier = Modifier
) {
    val favoriteRhymes = remember(favorites) {
        RhymeRepository.rhymes.filter { favorites.contains(it.id) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .testTag("favorites_screen")
    ) {
        // Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Text(
                text = "আমার প্রিয় ছড়া ❤️",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = KidsCoralPink
                )
            )
            Text(
                text = "আপনার পছন্দ করে রাখা ছড়াগুলোর সংগ্রহ (${favoriteRhymes.size}টি)",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }

        if (favoriteRhymes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "💖", fontSize = 56.sp)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "এখনও কোনো ছড়া পছন্দ করা হয়নি",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "ছড়ার পাশে হার্ট (❤️) আইকন চেপে আপনার পছন্দের ছড়া এখানে সংরক্ষণ করুন!",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp)
            ) {
                items(favoriteRhymes, key = { it.id }) { rhyme ->
                    RhymeCard(
                        rhyme = rhyme,
                        isFavorite = true,
                        onCardClick = { onRhymeSelected(rhyme) },
                        onFavoriteToggle = { onFavoriteToggle(rhyme.id) },
                        onQuickListen = { onQuickListen(rhyme) }
                    )
                }
            }
        }
    }
}
