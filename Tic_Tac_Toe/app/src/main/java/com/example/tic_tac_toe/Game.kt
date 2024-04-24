package com.example.tic_tac_toe
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sqrt
import kotlin.random.Random

class Game : ComponentActivity() {
    private var currentPlayer by mutableStateOf(if (Random.nextBoolean()) "X" else "O")
    private var gameResult by mutableStateOf<String?>(null)
    private var cells by mutableStateOf(mutableStateListOf<String>())
    private var selectedSize by mutableStateOf<String?>(null)
    private var selectedRule by mutableStateOf<String?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedSize = intent.getStringExtra("selectedSize")
        selectedRule = intent.getStringExtra("selectedRule")
        setContent {
            MainScreen(this)
        }
    }

    @Composable
    private fun MainScreen(context: Context) {
        val size = selectedSize?.split(" x ")?.get(0)?.toInt() ?: 3
        if (cells.isEmpty()) {
            for (i in 0 until size * size) {
                cells.add("")
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Row(
                modifier = Modifier
                    .background(color = Color(context.getColor(R.color.but1)))
                    .fillMaxWidth()
                    .fillMaxHeight(0.12f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {TextFont(text = "Pole: "+selectedSize.toString())
                TextFont(text = "Reguła: "+selectedRule.toString())}
            Divider(color = Color.Black, thickness = 1.dp)
            Row(
                modifier = Modifier
                    .background(color = Color(context.getColor(R.color.but1)))
                    .fillMaxWidth()
                    .fillMaxHeight(0.12f),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {TextFont(text = "Bieżący gracz: $currentPlayer")}

            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .aspectRatio(1f)
                    .background(Color.LightGray)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    repeat(size) { rowIndex ->
                        Row(modifier = Modifier.weight(1f)) {
                            repeat(size) { columnIndex ->
                                Cell(
                                    text = cells[rowIndex * size + columnIndex],
                                    onClick = {
                                        if (cells[rowIndex * size + columnIndex].isEmpty() && gameResult.isNullOrEmpty()) {
                                            cells[rowIndex * size + columnIndex] = currentPlayer
                                            currentPlayer = if (currentPlayer == "X") "O" else "X"
                                            checkGameResult(cells)
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f)
                                )
                            }
                        }
                    }
                }
            }


            if (gameResult != null) {
                AlertDialog(
                    onDismissRequest = { resetGame() },
                    title = {
                        Text(
                            text = if (gameResult == "Remis") "Remis!" else "Gracz $gameResult wygrał!"
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = { resetGame() }
                        ) {
                            Text(text = "Play Again")
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

        }
    }

    @Composable
    private fun Cell(text: String, onClick: () -> Unit, modifier: Modifier) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = Color.Transparent)
                .border(width = 1.dp, color = Color.Black)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun TextFont(text: String) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily.SansSerif
        )
    }

    private fun checkGameResult(cells: List<String>) {
        val size = sqrt(cells.size.toDouble()).toInt()


        for (i in 0 until size) {
            var countX = 0
            var countO = 0

            for (j in 0 until size) {
                val cell = cells[i * size + j]
                if (cell == "X") {
                    countX++
                } else if (cell == "O") {
                    countO++
                }
            }

            if (countX == selectedRule?.toIntOrNull()) {
                gameResult = "X"
                return
            } else if (countO == selectedRule?.toIntOrNull()) {
                gameResult = "O"
                return
            }
        }


        for (i in 0 until size) {
            var count = 1
            var symbol = cells[i]
            for (j in 1 until size) {
                val cell = cells[j * size + i]
                if (cell == symbol && cell.isNotEmpty()) {
                    count++
                    if (count == selectedRule?.toIntOrNull()) {
                        gameResult = symbol
                        return
                    }
                } else {
                    count = 1
                    symbol = cell
                }
            }
        }


        var count = 1
        var symbol = cells[0]
        for (i in 1 until size) {
            val cell = cells[i * size + i]
            if (cell == symbol && cell.isNotEmpty()) {
                count++
                if (count == selectedRule?.toIntOrNull()) {
                    gameResult = symbol
                    return
                }
            } else {
                count = 1
                symbol = cell
            }
        }


        count = 1
        symbol = cells[size - 1]
        for (i in 1 until size) {
            val cell = cells[(i * size) + (size - i - 1)]
            if (cell == symbol && cell.isNotEmpty()) {
                count++
                if (count == selectedRule?.toIntOrNull()) {
                    gameResult = symbol
                    return
                }
            } else {
                count = 1
                symbol = cell
            }
        }

        if (cells.all { it.isNotEmpty() }) {
            gameResult = "Remis"
        }
    }

    private fun resetGame() {
        cells.fill("")
        currentPlayer = if (Random.nextBoolean()) "X" else "O"
        gameResult = null
    }
}
