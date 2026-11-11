package com.example.chorarmela.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserPreferencesRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("chorar_mela_prefs", Context.MODE_PRIVATE)

    private val _favorites = MutableStateFlow<Set<String>>(loadFavorites())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    private val _fontSizeScale = MutableStateFlow(loadFontSizeScale())
    val fontSizeScale: StateFlow<Float> = _fontSizeScale.asStateFlow()

    private val _quizHighScore = MutableStateFlow(loadHighScore())
    val quizHighScore: StateFlow<Int> = _quizHighScore.asStateFlow()

    private fun loadFavorites(): Set<String> {
        return prefs.getStringSet(KEY_FAVORITES, setOf("hattima_tim_tim", "amader_chhoto_nodi", "aata_gache_tota_pakhi")) ?: emptySet()
    }

    fun toggleFavorite(rhymeId: String) {
        val current = _favorites.value.toMutableSet()
        if (current.contains(rhymeId)) {
            current.remove(rhymeId)
        } else {
            current.add(rhymeId)
        }
        prefs.edit().putStringSet(KEY_FAVORITES, current).apply()
        _favorites.value = current
    }

    fun isFavorite(rhymeId: String): Boolean {
        return _favorites.value.contains(rhymeId)
    }

    private fun loadFontSizeScale(): Float {
        return prefs.getFloat(KEY_FONT_SIZE, 1.0f)
    }

    fun setFontSizeScale(scale: Float) {
        prefs.edit().putFloat(KEY_FONT_SIZE, scale).apply()
        _fontSizeScale.value = scale
    }

    private fun loadHighScore(): Int {
        return prefs.getInt(KEY_HIGH_SCORE, 0)
    }

    fun saveHighScore(score: Int) {
        if (score > _quizHighScore.value) {
            prefs.edit().putInt(KEY_HIGH_SCORE, score).apply()
            _quizHighScore.value = score
        }
    }

    companion object {
        private const val KEY_FAVORITES = "favorite_rhyme_ids"
        private const val KEY_FONT_SIZE = "rhyme_font_size"
        private const val KEY_HIGH_SCORE = "quiz_high_score"
    }
}
