package dev.josed20.pc02moviles2220015022200171

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dev.josed20.pc02moviles2220015022200171.presentation.navigation.AppNavigation
import dev.josed20.pc02moviles2220015022200171.ui.theme.PC02MOVILES2220015022200171Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PC02MOVILES2220015022200171Theme {
                AppNavigation()
            }
        }
    }
}
