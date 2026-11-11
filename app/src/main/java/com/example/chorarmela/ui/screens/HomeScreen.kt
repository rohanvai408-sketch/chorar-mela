package com.example.chorarmela.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chorarmela.data.Rhyme
import com.example.chorarmela.data.RhymeCategory
import com.example.chorarmela.data.RhymeRepository
import com.example.chorarmela.ui.components.CategoryFilterChip
import com.example.chorarmela.ui.components.HeroBannerCard
import com.example.chorarmela.ui.components.RhymeCard
import com.example.chorarmela.ui.theme.KidsGoldenYellow
import com.example.chorarmela.ui.theme.KidsSkyBlue

@Composable
fun HomeScreen(
    favorites: Set<String>,
    onRhymeSelected: (Rhyme) -> Unit,
    onFavoriteToggle: (String) -> Unit,
    onQuickListen: (Rhyme) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(RhymeCategory.ALL) }

    val filteredRhymes = remember(searchQuery, selectedCategory) {
        val categoryRhymes = RhymeRepository.getRhymesByCategory(selectedCategory)
        if (searchQuery.isBlank()) {
            categoryRhymes
        } else {
            val q = searchQuery.trim().lowercase()
            categoryRhymes.filter {
                it.titleBn.lowercase().contains(q) ||
                it.titleEn.lowercase().contains(q) ||
                it.authorBn.lowercase().contains(q) ||
                it.linesBn.any { line -> line.lowercase().contains(q) }
            }
        }
    }

    // Featured rhyme
    val featuredRhyme = remember { RhymeRepository.rhymes.firstOrNull() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_screen_list"),
        contentPadding = PaddingValues(bottom = 80.dp)
    ) {
        // App header bar
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "ছড়ার মেলা 🎠",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = KidsSkyBlue,
                                fontSize = 30.sp
                            )
                        )
                        Text(
                            text = "ছোটদের প্রিয় বাংলা ছড়া ও কবিতার রাজ্য",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                    Surface(
                        shape = CircleShape,
                        color = KidsGoldenYellow.copy(alpha = 0.2f),
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Featured",
                                tint = KidsGoldenYellow,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                }
            }
        }

        // Hero illustration banner
        item {
            HeroBannerCard()
        }

        // Search Bar
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("search_rhymes_input"),
                    placeholder = {
                        Text("ছড়ার নাম বা লেখক খুঁজুন... (Search)")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search icon",
                            tint = KidsSkyBlue
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear search"
                                )
                            }
                        }
                    },
                    shape = RoundedCornerShape(24.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = KidsSkyBlue,
                        unfocusedBorderColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }

        // Category filter chips
        item {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(RhymeCategory.entries) { category ->
                    CategoryFilterChip(
                        category = category,
                        isSelected = selectedCategory == category,
                        onClick = { selectedCategory = category }
                    )
                }
            }
        }

        // Daily Featured Rhyme Card
        if (searchQuery.isEmpty() && selectedCategory == RhymeCategory.ALL && featuredRhyme != null) {
            item {
                FeaturedSpotlightCard(
                    rhyme = featuredRhyme,
                    onClick = { onRhymeSelected(featuredRhyme) },
                    onListen = { onQuickListen(featuredRhyme) }
                )
            }
        }

        // Results Section Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (searchQuery.isBlank()) "ছড়ার তালিকা (${filteredRhymes.size})" else "অনুসন্ধানের ফলাফল (${filteredRhymes.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                if (searchQuery.isNotBlank() || selectedCategory != RhymeCategory.ALL) {
                    Text(
                        text = "ফিল্টার মুছুন",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = KidsSkyBlue,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable {
                            searchQuery = ""
                            selectedCategory = RhymeCategory.ALL
                        }
                    )
                }
            }
        }

        // Empty state
        if (filteredRhymes.isEmpty()) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🔍", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "কোনো ছড়া খুঁজে পাওয়া যায়নি",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "অন্য কোনো নাম বা শব্দ দিয়ে চেষ্টা করুন",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        } else {
            // Rhymes list
            items(filteredRhymes, key = { it.id }) { rhyme ->
                RhymeCard(
                    rhyme = rhyme,
                    isFavorite = favorites.contains(rhyme.id),
                    onCardClick = { onRhymeSelected(rhyme) },
                    onFavoriteToggle = { onFavoriteToggle(rhyme.id) },
                    onQuickListen = { onQuickListen(rhyme) }
                )
            }
        }
    }
}

@Composable
fun FeaturedSpotlightCard(
    rhyme: Rhyme,
    onClick: () -> Unit,
    onListen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick)
            .testTag("featured_rhyme_card"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, Color(0xFFFFB300))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFB300).copy(alpha = 0.25f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "👑", fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFFFFB300)
                ) {
                    Text(
                        text = "আজকের বিশেষ ছড়া",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF263238)
                        )
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = rhyme.titleBn,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3E2723)
                    )
                )
                Text(
                    text = "\"${rhyme.linesBn.firstOrNull().orEmpty()}\"",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color(0xFF5D4037)
                    ),
                    maxLines = 1
                )
            }

            IconButton(
                onClick = onListen,
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFFB300))
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "শুনুন",
                    tint = Color.White
                )
            }
        }
    }
}
