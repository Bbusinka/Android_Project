package com.example.tic_tac_toe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import android.content.Context
import android.content.Intent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MainScreen(this)
        }
    }

    @Composable
    private fun MainScreen(context: Context) {
        var isLoading by remember { mutableStateOf(false) }
        LaunchedEffect(key1 = true) {
            delay(300) // Задержка в 3 секунды перед установкой isLoading в true
            isLoading = true
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextFont(text = "Kółko i krzyżyk")
            val progress by animateFloatAsState(
                targetValue = if (isLoading) 1f else 0f,
                animationSpec = tween(durationMillis = 3000)
            )
            Image(
                painter = painterResource(id = R.drawable.tictactoe),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .fillMaxHeight(0.5f)
                    .padding(vertical = 15.dp)
            )
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .fillMaxHeight(0.2f)
                    .padding(vertical = 15.dp),
                color = Color(context.getColor(R.color.status))
            )
            if (progress==1f){
                Button(
                    onClick = {
                        val intent = Intent(context, Menu::class.java)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .padding(vertical = 15.dp)
                        .fillMaxHeight(0.2f)
                        .fillMaxWidth(0.5f)
                ) {
                    Text(text = "Dalej")
                }
            }
        }
    }


    @Composable
    fun TextFont(text: String) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 45.sp,
            fontWeight = FontWeight.SemiBold,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Monospace,
            modifier = Modifier
                .padding(vertical = 45.dp)
        )
    }
}
