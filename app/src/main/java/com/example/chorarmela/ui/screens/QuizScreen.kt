package com.example.chorarmela.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chorarmela.ui.theme.KidsGoldenYellow
import com.example.chorarmela.ui.theme.KidsMintGreen
import com.example.chorarmela.ui.theme.KidsSkyBlue

data class QuizQuestion(
    val prompt: String,
    val options: List<String>,
    val correctIndex: Int,
    val rhymeTitle: String,
    val explanation: String
)

val quizQuestions = listOf(
    QuizQuestion(
        prompt = "হাট্টিমা টিম টিম, তারা মাঠে পাড়ে ___ ?",
        options = listOf("ডিম 🥚", "আম 🥭", "ফুল 🌸", "পাখি 🐦"),
        correctIndex = 0,
        rhymeTitle = "হাট্টিমা টিম টিম",
        explanation = "সঠিক চরণ: 'তারা মাঠে পাড়ে ডিম, তাদের খাড়া দুটো শিং'।"
    ),
    QuizQuestion(
        prompt = "বুলবুলিতে ধান খেয়েছে, ___ ?",
        options = listOf("খাজনা দেব কিসে 🌾", "বাড়ি যাব কবে 🏡", "গান গাব কবে 🎵", "ভাত খাব কিসে 🍚"),
        correctIndex = 0,
        rhymeTitle = "খোকা ঘুমাল পাড়া জুড়াল",
        explanation = "সঠিক চরণ: 'বুলবুলিতে ধান খেয়েছে, খাজনা দেব কিসে?'।"
    ),
    QuizQuestion(
        prompt = "আতা গাছে তোতা পাখি, ডালিম গাছে ___ ?",
        options = listOf("মৌ 🍯", "ফল 🍎", "পাতা 🍃", "ফুল 🌺"),
        correctIndex = 0,
        rhymeTitle = "আতা গাছে তোতা পাখি",
        explanation = "সঠিক চরণ: 'আতা গাছে তোতা পাখি, ডালিম গাছে মৌ'।"
    ),
    QuizQuestion(
        prompt = "আমাদের ছোট নদী চলে ___ ?",
        options = listOf("আঁকে বাঁকে 🌊", "সোজা পথে 🛣️", "ধীরে ধীরে ⛵", "বন ঘেঁষে 🌲"),
        correctIndex = 0,
        rhymeTitle = "আমাদের ছোট নদী",
        explanation = "রবীন্দ্রনাথের কবিতা: 'আমাদের ছোট নদী চলে আঁকে বাঁকে, বৈশাখ মাসে তার হাঁটু জল থাকে'।"
    ),
    QuizQuestion(
        prompt = "বাবুরাম সাপুড়ে, কোথা যাস বাপুরে? আয় তবে দেখে যা, দুটো ___ রেখে যা!",
        options = listOf("সাপ 🐍", "মাছ 🐟", "ব্যাঙ 🐸", "পাখি 🦜"),
        correctIndex = 0,
        rhymeTitle = "বাবুরাম সাপুড়ে",
        explanation = "সুকুমার রায়ের ছড়া: 'আয় তবে দেখে যা, দুটো সাপ রেখে যা!'।"
    ),
    QuizQuestion(
        prompt = "আমপাতা জোড়া জোড়া, মারব চাবুক চড়ব ___ !",
        options = listOf("ঘোড়া 🐎", "হাতি 🐘", "পালকি 🚪", "গাড়ি 🚗"),
        correctIndex = 0,
        rhymeTitle = "আমপাতা জোড়া জোড়া",
        explanation = "জনপ্রিয় খেলার ছড়া: 'আমপাতা জোড়া জোড়া, মারব চাবুক চড়ব ঘোড়া'।"
    ),
    QuizQuestion(
        prompt = "ভোর হলো দোর খোলো, ___ ওঠ রে!",
        options = listOf("খুকুমণি 👧", "খোকা ভাই 👦", "টিয়া পাখি 🦜", "সোনামণি 👶"),
        correctIndex = 0,
        rhymeTitle = "ভোর হলো দোর খোলো",
        explanation = "কাজী নজরুল ইসলামের প্রভাতী: 'ভোর হলো দোর খোলো, খুকুমণি ওঠ রে!'।"
    ),
    QuizQuestion(
        prompt = "তাই তাই তাই, ___ বাড়ি যাই!",
        options = listOf("মামার 🍬", "নানার 🎁", "খালার 🌸", "দাদার 🎈"),
        correctIndex = 0,
        rhymeTitle = "তাই তাই তাই",
        explanation = "তালি দেওয়ার ছড়া: 'তাই তাই তাই, মামার বাড়ি যাই, মামার বাড়ি ভারী মজা, কিল চড় নাই'।"
    ),
    QuizQuestion(
        prompt = "চাঁদ উঠেছে ফুল ফুটেছে, কদম তলায় ___ ?",
        options = listOf("কে 🌸", "সে 🌼", "যে 🌺", "যেথা 🌷"),
        correctIndex = 0,
        rhymeTitle = "চাঁদ উঠেছে ফুল ফুটেছে",
        explanation = "ঘুমপাড়ানি ছড়া: 'চাঁদ উঠেছে ফুল ফুটেছে, কদম তলায় কে? হাতি নাচছে ঘোড়া নাচছে সোনামণির বে'।"
    ),
    QuizQuestion(
        prompt = "বাক বাকুম পায়রা, মাথায় দিয়ে ___ !",
        options = listOf("টায়রা 👑", "টুপি 🎩", "পাগড়ি 👳", "ফুল 🌹"),
        correctIndex = 0,
        rhymeTitle = "বাক বাকুম পায়রা",
        explanation = "কবিতা: 'বাক বাকুম পায়রা, মাথায় দিয়ে টায়রা, বউ সাজবে কাল কি, চড়বে সোনার পালকি'।"
    )
)

@Composable
fun QuizScreen(
    highScore: Int,
    onSaveHighScore: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    var score by remember { mutableIntStateOf(0) }
    var selectedOptionIndex by remember { mutableStateOf<Int?>(null) }
    var isSubmitted by remember { mutableStateOf(false) }
    var isQuizCompleted by remember { mutableStateOf(false) }

    val currentQuestion = quizQuestions[currentIndex]

    fun nextQuestion() {
        if (currentIndex < quizQuestions.lastIndex) {
            currentIndex++
            selectedOptionIndex = null
            isSubmitted = false
        } else {
            isQuizCompleted = true
            onSaveHighScore(score)
        }
    }

    fun restartQuiz() {
        currentIndex = 0
        score = 0
        selectedOptionIndex = null
        isSubmitted = false
        isQuizCompleted = false
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("quiz_screen"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "ছড়ার মেলা খেলা 🎮",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = KidsSkyBlue
                    )
                )
                Text(
                    text = "ছড়ার শূন্যস্থান পূরণ করে সঠিক উত্তর দাও!",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = KidsGoldenYellow.copy(alpha = 0.2f)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Score",
                        tint = KidsGoldenYellow,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$score / ${quizQuestions.size}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFE65100)
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isQuizCompleted) {
            // Completion / Celebration Screen
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .testTag("quiz_completed_card"),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                border = BorderStroke(2.dp, Color(0xFFFFB300))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "🎉 🏆 🌟", fontSize = 48.sp)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "দারুণ খেলেছ সোনামণি!",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFFE65100)
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "তোমার স্কোর: $score / ${quizQuestions.size}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3E2723)
                        )
                    )
                    if (score > highScore) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFFFB300),
                            modifier = Modifier.padding(top = 8.dp)
                        ) {
                            Text(
                                text = "✨ নতুন রেকর্ড! ✨",
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    } else {
                        Text(
                            text = "সর্বোচ্চ স্কোর: $highScore",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF795548)
                            ),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { restartQuiz() },
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = KidsSkyBlue),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("quiz_restart_button")
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Restart")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "আবার খেলো 🔄",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        } else {
            // Question Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quiz_question_card"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.5.dp, Color(0xFFE0E0E0)),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = KidsSkyBlue.copy(alpha = 0.12f)
                        ) {
                            Text(
                                text = "প্রশ্ন ${currentIndex + 1} / ${quizQuestions.size}",
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = KidsSkyBlue
                                )
                            )
                        }
                        Text(
                            text = "ছড়া: ${currentQuestion.rhymeTitle}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = currentQuestion.prompt,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            lineHeight = 28.sp,
                            color = Color(0xFF263238)
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Options
                    currentQuestion.options.forEachIndexed { optIndex, optionText ->
                        val isSelected = selectedOptionIndex == optIndex
                        val isCorrect = optIndex == currentQuestion.correctIndex

                        val btnBg by animateColorAsState(
                            when {
                                isSubmitted && isCorrect -> Color(0xFFE8F5E9)
                                isSubmitted && isSelected && !isCorrect -> Color(0xFFFFEBEE)
                                isSelected -> Color(0xFFE1F5FE)
                                else -> Color(0xFFF5F5F5)
                            },
                            label = "opt_bg"
                        )

                        val borderCol by animateColorAsState(
                            when {
                                isSubmitted && isCorrect -> KidsMintGreen
                                isSubmitted && isSelected && !isCorrect -> Color(0xFFE53935)
                                isSelected -> KidsSkyBlue
                                else -> Color(0xFFE0E0E0)
                            },
                            label = "opt_border"
                        )

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .clickable(enabled = !isSubmitted) {
                                    selectedOptionIndex = optIndex
                                    isSubmitted = true
                                    if (optIndex == currentQuestion.correctIndex) {
                                        score++
                                    }
                                }
                                .testTag("quiz_option_$optIndex"),
                            color = btnBg,
                            shape = RoundedCornerShape(14.dp),
                            border = BorderStroke(1.5.dp, borderCol)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = optionText,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = if (isSubmitted && isCorrect) Color(0xFF1B5E20)
                                                else if (isSubmitted && isSelected && !isCorrect) Color(0xFFB71C1C)
                                                else Color(0xFF37474F)
                                    )
                                )

                                if (isSubmitted) {
                                    if (isCorrect) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Correct",
                                            tint = KidsMintGreen
                                        )
                                    } else if (isSelected) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Wrong",
                                            tint = Color(0xFFE53935)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // Explanation & Next Button
                    AnimatedVisibility(visible = isSubmitted) {
                        Column(modifier = Modifier.padding(top = 16.dp)) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFF1F8E9),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = currentQuestion.explanation,
                                    modifier = Modifier.padding(12.dp),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(0xFF2E7D32)
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = { nextQuestion() },
                                shape = RoundedCornerShape(14.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = KidsSkyBlue),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("quiz_next_button")
                            ) {
                                Text(
                                    text = if (currentIndex < quizQuestions.lastIndex) "পরের প্রশ্ন 👉" else "ফলাফল দেখুন 🏆",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
