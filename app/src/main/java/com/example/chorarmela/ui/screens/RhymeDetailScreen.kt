package com.example.chorarmela.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chorarmela.audio.RhymeAudioPlayer
import com.example.chorarmela.data.Rhyme
import com.example.chorarmela.data.RhymeRepository
import com.example.chorarmela.ui.theme.KidsCoralPink
import com.example.chorarmela.ui.theme.KidsGoldenYellow
import com.example.chorarmela.ui.theme.KidsSkyBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RhymeDetailScreen(
    rhyme: Rhyme,
    audioPlayer: RhymeAudioPlayer,
    isFavorite: Boolean,
    fontSizeScale: Float,
    onBack: () -> Unit,
    onFavoriteToggle: () -> Unit,
    onFontSizeChange: (Float) -> Unit,
    onSelectRhyme: (Rhyme) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler {
        audioPlayer.stop()
        onBack()
    }

    DisposableEffect(rhyme.id) {
        onDispose {
            audioPlayer.stop()
        }
    }

    var showPhonetic by remember { mutableStateOf(false) }
    var isRepeatEnabled by remember { mutableStateOf(false) }
    val accentColor = Color(rhyme.themeColorHex)

    // Current rhyme position in list for Prev / Next
    val currentIndex = remember(rhyme.id) {
        RhymeRepository.rhymes.indexOfFirst { it.id == rhyme.id }
    }
    val prevRhyme = if (currentIndex > 0) RhymeRepository.rhymes[currentIndex - 1] else null
    val nextRhyme = if (currentIndex in 0 until RhymeRepository.rhymes.lastIndex) RhymeRepository.rhymes[currentIndex + 1] else null

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = rhyme.titleBn,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            audioPlayer.stop()
                            onBack()
                        },
                        modifier = Modifier.testTag("detail_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ফিরে যান"
                        )
                    }
                },
                actions = {
                    // Font size cycle button (1.0 -> 1.25 -> 1.5 -> 1.0)
                    IconButton(
                        onClick = {
                            val nextScale = when {
                                fontSizeScale < 1.15f -> 1.25f
                                fontSizeScale < 1.35f -> 1.5f
                                else -> 1.0f
                            }
                            onFontSizeChange(nextScale)
                        },
                        modifier = Modifier.testTag("font_size_button")
                    ) {
                        Text(
                            text = if (fontSizeScale > 1.3f) "A++" else if (fontSizeScale > 1.1f) "A+" else "A",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = KidsSkyBlue
                            )
                        )
                    }

                    // Favorite toggle
                    IconButton(
                        onClick = onFavoriteToggle,
                        modifier = Modifier.testTag("detail_fav_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "পছন্দ",
                            tint = if (isFavorite) KidsCoralPink else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Rhyme Header badge
            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                color = accentColor.copy(alpha = 0.15f),
                border = BorderStroke(2.dp, accentColor)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = rhyme.iconEmoji,
                        fontSize = 42.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = rhyme.titleBn,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = (26 * fontSizeScale).sp,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = rhyme.titleEn,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = accentColor.copy(alpha = 0.12f)
            ) {
                Text(
                    text = "✍️ ${rhyme.authorBn} (${rhyme.authorEn})",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = accentColor
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Audio Player Control Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("audio_player_controls"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                border = BorderStroke(1.5.dp, Color(0xFFC5E1A5))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (audioPlayer.isPlaying) "🔊 ছড়া আবৃত্তি হচ্ছে..." else "🎧 ছড়া আবৃত্তি শুনুন",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF33691E)
                        )
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Repeat toggle
                        IconButton(
                            onClick = {
                                isRepeatEnabled = !isRepeatEnabled
                                audioPlayer.toggleRepeat()
                            },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (isRepeatEnabled) Color(0xFF7CB342) else Color.White)
                        ) {
                            Text(
                                text = "🔁",
                                fontSize = 20.sp
                            )
                        }

                        // Big Play / Stop Button
                        Surface(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .clickable {
                                    if (audioPlayer.isPlaying) {
                                        audioPlayer.stop()
                                    } else {
                                        audioPlayer.playPoem(rhyme.linesBn, repeat = isRepeatEnabled)
                                    }
                                }
                                .testTag("audio_play_pause_button"),
                            shape = CircleShape,
                            color = if (audioPlayer.isPlaying) KidsCoralPink else KidsSkyBlue,
                            shadowElevation = 4.dp
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = if (audioPlayer.isPlaying) Icons.Default.Close else Icons.Default.PlayArrow,
                                    contentDescription = if (audioPlayer.isPlaying) "থামান" else "আবৃত্তি করুন",
                                    tint = Color.White,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }

                        // Background Music Box Chime Melody
                        IconButton(
                            onClick = { audioPlayer.toggleMelody() },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(if (audioPlayer.isMelodyPlaying) KidsGoldenYellow else Color.White)
                        ) {
                            Text(
                                text = "🎵",
                                fontSize = 20.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Speed controls & Transliteration toggle
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "গতি: ",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF558B2F)
                                )
                            )
                            listOf(0.8f to "০.৮x", 1.0f to "১.০x", 1.2f to "১.২x").forEach { (speed, label) ->
                                val isSelected = audioPlayer.speechRate == speed
                                Surface(
                                    modifier = Modifier
                                        .padding(horizontal = 2.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { audioPlayer.updateSpeechRate(speed) },
                                    color = if (isSelected) Color(0xFF558B2F) else Color.White,
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = label,
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (isSelected) Color.White else Color(0xFF558B2F),
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }

                        // English transliteration toggle button
                        Surface(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { showPhonetic = !showPhonetic },
                            color = if (showPhonetic) KidsSkyBlue else Color.White,
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, KidsSkyBlue)
                        ) {
                            Text(
                                text = if (showPhonetic) "🔤 উচ্চারণ চালু" else "🔤 উচ্চারণ দেখুন",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = if (showPhonetic) Color.White else KidsSkyBlue,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Poem Lyrics Parchment Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("poem_lyrics_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFDF8)),
                border = BorderStroke(1.5.dp, Color(0xFFFFE082)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "📖 ছড়ার চরণসমূহ (লাইনে চাপ দিয়ে শুনুন)",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color(0xFF8D6E63),
                            fontWeight = FontWeight.Medium
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    rhyme.linesBn.forEachIndexed { index, lineBn ->
                        val isHighlighted = audioPlayer.isPlaying && audioPlayer.currentLineIndex == index
                        val lineEn = rhyme.linesEn.getOrNull(index)

                        val bgColor by animateColorAsState(
                            if (isHighlighted) Color(0xFFFFECB3) else Color.Transparent,
                            label = "line_bg"
                        )
                        val textColor by animateColorAsState(
                            if (isHighlighted) Color(0xFFBF360C) else MaterialTheme.colorScheme.onSurface,
                            label = "line_text"
                        )
                        val lineScale by animateFloatAsState(
                            if (isHighlighted) 1.04f else 1.0f,
                            label = "line_scale"
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(bgColor)
                                .scale(lineScale)
                                .clickable {
                                    audioPlayer.speakSingleLine(lineBn, index)
                                }
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                                .testTag("poem_line_$index"),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    if (isHighlighted) {
                                        Text(
                                            text = "🔊 ",
                                            fontSize = 18.sp,
                                            modifier = Modifier.padding(end = 4.dp)
                                        )
                                    }
                                    Text(
                                        text = lineBn,
                                        style = MaterialTheme.typography.headlineSmall.copy(
                                            fontSize = (21 * fontSizeScale).sp,
                                            fontWeight = if (isHighlighted) FontWeight.Bold else FontWeight.SemiBold,
                                            color = textColor,
                                            lineHeight = (32 * fontSizeScale).sp
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                }

                                if (showPhonetic && !lineEn.isNullOrBlank()) {
                                    Text(
                                        text = lineEn,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color(0xFF78909C),
                                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                                        ),
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(top = 2.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Meaning & Story Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0F7FA))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "Meaning",
                            tint = Color(0xFF00838F)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "ছড়ার ভাবার্থ ও গল্প (Meaning)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF006064)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = rhyme.meaning,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF004D40),
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fun Fact Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF3E0))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "💡",
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "মজার তথ্য (Fun Fact)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFE65100)
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = rhyme.funFact,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFFBF360C),
                            lineHeight = 22.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Navigation to Prev / Next Rhyme
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (prevRhyme != null) {
                    FilledTonalButton(
                        onClick = { onSelectRhyme(prevRhyme) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.testTag("prev_rhyme_button")
                    ) {
                        Text("👈 আগের ছড়া: ${prevRhyme.titleBn}")
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                if (nextRhyme != null) {
                    Button(
                        onClick = { onSelectRhyme(nextRhyme) },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsSkyBlue),
                        modifier = Modifier.testTag("next_rhyme_button")
                    ) {
                        Text("পরের ছড়া 👉")
                    }
                }
            }
        }
    }
}
