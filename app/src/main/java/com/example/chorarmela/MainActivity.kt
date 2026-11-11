package com.example.chorarmela

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import com.example.chorarmela.audio.RhymeAudioPlayer
import com.example.chorarmela.data.Rhyme
import com.example.chorarmela.data.RhymeRepository
import com.example.chorarmela.data.UserPreferencesRepository
import com.example.chorarmela.ui.screens.FavoritesScreen
import com.example.chorarmela.ui.screens.HomeScreen
import com.example.chorarmela.ui.screens.QuizScreen
import com.example.chorarmela.ui.screens.RhymeDetailScreen
import com.example.chorarmela.ui.theme.ChorarMelaTheme
import com.example.chorarmela.ui.theme.KidsCoralPink
import com.example.chorarmela.ui.theme.KidsSkyBlue

enum class NavTab(
    val titleBn: String,
    val icon: ImageVector,
    val emoji: String
) {
    HOME("ছড়ার মেলা", Icons.Filled.Home, "📖"),
    FAVORITES("প্রিয় ছড়া", Icons.Filled.Favorite, "❤️"),
    QUIZ("ছড়ার খেলা", Icons.Filled.Star, "🎮")
}

class MainActivity : ComponentActivity() {

    private lateinit var audioPlayer: RhymeAudioPlayer
    private lateinit var preferencesRepo: UserPreferencesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        audioPlayer = RhymeAudioPlayer(this)
        preferencesRepo = UserPreferencesRepository(this)

        setContent {
            ChorarMelaTheme {
                ChorarMelaApp(
                    audioPlayer = audioPlayer,
                    preferencesRepo = preferencesRepo
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayer.release()
    }
}

@Composable
fun ChorarMelaApp(
    audioPlayer: RhymeAudioPlayer,
    preferencesRepo: UserPreferencesRepository
) {
    var selectedTab by remember { mutableStateOf(NavTab.HOME) }
    var activeRhyme by remember { mutableStateOf<Rhyme?>(null) }

    val favorites by preferencesRepo.favorites.collectAsState()
    val fontSizeScale by preferencesRepo.fontSizeScale.collectAsState()
    val highScore by preferencesRepo.quizHighScore.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        if (activeRhyme != null) {
            val rhyme = activeRhyme!!
            RhymeDetailScreen(
                rhyme = rhyme,
                audioPlayer = audioPlayer,
                isFavorite = favorites.contains(rhyme.id),
                fontSizeScale = fontSizeScale,
                onBack = {
                    audioPlayer.stop()
                    activeRhyme = null
                },
                onFavoriteToggle = {
                    preferencesRepo.toggleFavorite(rhyme.id)
                },
                onFontSizeChange = { newScale ->
                    preferencesRepo.setFontSizeScale(newScale)
                },
                onSelectRhyme = { newRhyme ->
                    audioPlayer.stop()
                    activeRhyme = newRhyme
                }
            )
        } else {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier
                            .navigationBarsPadding()
                            .testTag("bottom_nav_bar"),
                        containerColor = MaterialTheme.colorScheme.surface
                    ) {
                        NavTab.entries.forEach { tab ->
                            val isSelected = selectedTab == tab
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = {
                                    audioPlayer.stop()
                                    selectedTab = tab
                                },
                                icon = {
                                    Icon(
                                        imageVector = tab.icon,
                                        contentDescription = tab.titleBn,
                                        tint = if (isSelected) {
                                            if (tab == NavTab.FAVORITES) KidsCoralPink else KidsSkyBlue
                                        } else {
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                        }
                                    )
                                },
                                label = {
                                    Text(
                                        text = tab.titleBn,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = if (tab == NavTab.FAVORITES) {
                                        KidsCoralPink.copy(alpha = 0.15f)
                                    } else {
                                        KidsSkyBlue.copy(alpha = 0.15f)
                                    }
                                ),
                                modifier = Modifier.testTag("nav_item_${tab.name.lowercase()}")
                            )
                        }
                    }
                }
            ) { paddingValues ->
                val contentModifier = Modifier.padding(paddingValues)
                when (selectedTab) {
                    NavTab.HOME -> {
                        HomeScreen(
                            favorites = favorites,
                            onRhymeSelected = { rhyme ->
                                activeRhyme = rhyme
                            },
                            onFavoriteToggle = { id ->
                                preferencesRepo.toggleFavorite(id)
                            },
                            onQuickListen = { rhyme ->
                                activeRhyme = rhyme
                                audioPlayer.playPoem(rhyme.linesBn)
                            },
                            modifier = contentModifier
                        )
                    }
                    NavTab.FAVORITES -> {
                        FavoritesScreen(
                            favorites = favorites,
                            onRhymeSelected = { rhyme ->
                                activeRhyme = rhyme
                            },
                            onFavoriteToggle = { id ->
                                preferencesRepo.toggleFavorite(id)
                            },
                            onQuickListen = { rhyme ->
                                activeRhyme = rhyme
                                audioPlayer.playPoem(rhyme.linesBn)
                            },
                            modifier = contentModifier
                        )
                    }
                    NavTab.QUIZ -> {
                        QuizScreen(
                            highScore = highScore,
                            onSaveHighScore = { score ->
                                preferencesRepo.saveHighScore(score)
                            },
                            modifier = contentModifier
                        )
                    }
                }
            }
        }
    }
}
