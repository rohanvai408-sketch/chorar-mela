package com.example.chorarmela.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import java.util.Locale
import kotlin.concurrent.thread
import kotlin.math.sin

class RhymeAudioPlayer(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    var isTtsReady by mutableStateOf(false)
        private set

    var isPlaying by mutableStateOf(false)
        private set

    var isPaused by mutableStateOf(false)
        private set

    var currentLineIndex by mutableIntStateOf(-1)
        private set

    var speechRate by mutableFloatStateOf(0.9f)
    var speechPitch by mutableFloatStateOf(1.1f)
    var isMelodyPlaying by mutableStateOf(false)
        private set

    private var activeLines: List<String> = emptyList()
    private var isRepeating: Boolean = false

    // Music box chime generator
    private var melodyTrack: AudioTrack? = null
    @Volatile
    private var isMelodyRunning = false

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val ttsEngine = tts ?: return
            val bengaliBd = Locale.forLanguageTag("bn-BD")
            val bengaliIn = Locale.forLanguageTag("bn-IN")

            val result = when {
                ttsEngine.isLanguageAvailable(bengaliBd) >= TextToSpeech.LANG_AVAILABLE ->
                    ttsEngine.setLanguage(bengaliBd)
                ttsEngine.isLanguageAvailable(bengaliIn) >= TextToSpeech.LANG_AVAILABLE ->
                    ttsEngine.setLanguage(bengaliIn)
                else -> ttsEngine.setLanguage(Locale.getDefault())
            }

            ttsEngine.setSpeechRate(speechRate)
            ttsEngine.setPitch(speechPitch)

            ttsEngine.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    val line = utteranceId?.toIntOrNull()
                    if (line != null) {
                        currentLineIndex = line
                    }
                }

                override fun onDone(utteranceId: String?) {
                    val finishedIndex = utteranceId?.toIntOrNull()
                    if (finishedIndex != null && finishedIndex == activeLines.lastIndex) {
                        if (isRepeating) {
                            playPoem(activeLines, repeat = true)
                        } else {
                            isPlaying = false
                            currentLineIndex = -1
                        }
                    }
                }

                @Deprecated("Deprecated in Java")
                override fun onError(utteranceId: String?) {
                    isPlaying = false
                    currentLineIndex = -1
                }
            })
            isTtsReady = true
        }
    }

    fun playPoem(lines: List<String>, repeat: Boolean = false) {
        if (!isTtsReady) return
        stop()
        activeLines = lines
        isRepeating = repeat
        isPlaying = true
        isPaused = false

        tts?.setSpeechRate(speechRate)
        tts?.setPitch(speechPitch)

        for (i in lines.indices) {
            val text = lines[i]
            val params = Bundle().apply {
                putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, i.toString())
            }
            tts?.speak(text, TextToSpeech.QUEUE_ADD, params, i.toString())
        }
    }

    fun speakSingleLine(line: String, index: Int) {
        if (!isTtsReady) return
        stop()
        isPlaying = true
        currentLineIndex = index
        tts?.setSpeechRate(speechRate)
        tts?.setPitch(speechPitch)
        val params = Bundle().apply {
            putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, index.toString())
        }
        tts?.speak(line, TextToSpeech.QUEUE_FLUSH, params, index.toString())
    }

    fun stop() {
        tts?.stop()
        isPlaying = false
        isPaused = false
        currentLineIndex = -1
    }

    fun updateSpeechRate(rate: Float) {
        speechRate = rate
        tts?.setSpeechRate(rate)
    }

    fun toggleRepeat() {
        isRepeating = !isRepeating
    }

    fun toggleMelody() {
        if (isMelodyPlaying) {
            stopMelody()
        } else {
            startMelody()
        }
    }

    fun startMelody() {
        if (isMelodyRunning) return
        isMelodyRunning = true
        isMelodyPlaying = true

        thread(name = "ChimeMelodyThread") {
            try {
                val sampleRate = 44100
                val minBufferSize = AudioTrack.getMinBufferSize(
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT
                )

                val audioTrack = AudioTrack.Builder()
                    .setAudioAttributes(
                        AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build()
                    )
                    .setAudioFormat(
                        AudioFormat.Builder()
                            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                            .setSampleRate(sampleRate)
                            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                            .build()
                    )
                    .setBufferSizeInBytes(minBufferSize * 2)
                    .setTransferMode(AudioTrack.MODE_STREAM)
                    .build()

                melodyTrack = audioTrack
                audioTrack.play()

                // Pleasant pentatonic music box notes (C5, D5, E5, G5, A5, C6)
                val notes = doubleArrayOf(523.25, 587.33, 659.25, 783.99, 880.00, 1046.50)
                // A sweet gentle nursery chime melody sequence
                val melodySequence = intArrayOf(
                    0, 2, 4, 2, 0, 4, 3, 2,
                    1, 3, 5, 3, 1, 4, 2, 0
                )

                var step = 0
                while (isMelodyRunning) {
                    val noteIdx = melodySequence[step % melodySequence.size]
                    val freq = notes[noteIdx]
                    val durationMs = 380
                    val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
                    val buffer = ShortArray(numSamples)

                    for (i in 0 until numSamples) {
                        val t = i.toDouble() / sampleRate
                        // Soft bell chime decay envelope
                        val envelope = (1.0 - (i.toDouble() / numSamples)) * 0.22
                        // Fundamental + gentle harmonic for chime bell sound
                        val sample = (sin(2.0 * Math.PI * freq * t) * 0.7 +
                                      sin(4.0 * Math.PI * freq * t) * 0.3) * envelope
                        buffer[i] = (sample * Short.MAX_VALUE).toInt().toShort()
                    }

                    if (isMelodyRunning) {
                        audioTrack.write(buffer, 0, buffer.size)
                    }
                    step++
                }

                audioTrack.stop()
                audioTrack.release()
            } catch (_: Exception) {
            } finally {
                melodyTrack = null
                isMelodyRunning = false
                isMelodyPlaying = false
            }
        }
    }

    fun stopMelody() {
        isMelodyRunning = false
        isMelodyPlaying = false
    }

    fun release() {
        stop()
        stopMelody()
        tts?.shutdown()
        tts = null
    }
}
