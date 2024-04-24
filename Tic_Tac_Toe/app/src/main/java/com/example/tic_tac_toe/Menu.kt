package com.example.tic_tac_toe

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Menu : ComponentActivity() {
    private var selectedSizeOption by mutableStateOf("3 x 3")
    private var selectedRuleOption by mutableStateOf("3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen(this)
        }
    }

    @Composable
    private fun MainScreen(context: Context) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .background(color = Color(context.getColor(R.color.but1)))
                    .fillMaxWidth()
                    .fillMaxHeight(0.12f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextFont(text = "Wybierz rozmiar pola")
                val options = remember {
                    val startSize = 3
                    val endSize = 20
                    val optionsList = mutableListOf<String>()
                    for (size in startSize..endSize) {
                        optionsList.add("$size x $size")
                    }
                    optionsList
                }
                var expandedSize by remember { mutableStateOf(false) }
                var selectedOption by remember { mutableStateOf(options[0]) }

                Column {
                    Button(
                        onClick = { expandedSize = true },
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp).width(intrinsicSize = IntrinsicSize.Max)
                    ) {
                        Text(text = selectedOption)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }

                    DropdownMenu(
                        expanded = expandedSize,
                        onDismissRequest = { expandedSize = false },
                        modifier = Modifier.width(100.dp)
                            .height(200.dp)
                            .offset(x = 20.dp, y=0.dp)
                    ) {
                        options.forEach { option ->
                            DropdownMenuItem(text = { Text(text = option)}, onClick = {
                                selectedOption = option
                                selectedSizeOption = option
                                expandedSize = false
                            })
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .background(color = Color(context.getColor(R.color.but1)))
                    .fillMaxWidth()
                    .fillMaxHeight(0.12f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically

            ) {
                TextFont(text = "Wybierz regułę")
                val rulesOptions = remember { mutableStateListOf<String>() }
                val selectedSize = selectedSizeOption.split(" ")[0].toInt()
                if (selectedSize==3){
                    rulesOptions.clear()
                    rulesOptions.add("3")
                }else if (selectedSize==4){
                    rulesOptions.clear()
                    rulesOptions.add("4")
                }else if (selectedSize==5){
                    rulesOptions.clear()
                    rulesOptions.add("4")
                    rulesOptions.add("5")
                }else{
                    rulesOptions.clear()
                    rulesOptions.add("4")
                    rulesOptions.add("5")
                    rulesOptions.add("6")}
                var expandedRule by remember { mutableStateOf(false) }

                Column {
                    Button(
                        onClick = { expandedRule = true },
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp).width(100.dp)
                    ) {
                        Text(text = selectedRuleOption)
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }

                    DropdownMenu(
                        expanded = expandedRule,
                        onDismissRequest = { expandedRule = false },
                        modifier = Modifier.width(100.dp)
                            .height(150.dp)
                            .offset(x = 20.dp, y = 0.dp)
                    ) {
                        rulesOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(text = option) },
                                onClick = {
                                    selectedRuleOption = option
                                    expandedRule = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Button(
                onClick = {
                    val intent = Intent(context, Game::class.java)
                    intent.putExtra("selectedSize", selectedSizeOption)
                    intent.putExtra("selectedRule", selectedRuleOption)
                    startActivity(intent)
                },
                modifier = Modifier
                    .fillMaxHeight(0.12f)
                    .fillMaxWidth(0.5f)
            ) {
                Text(text = "Dalej")
            }
        }
    }

    @Composable
    fun TextFont(text: String) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 15.sp,
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily.SansSerif
        )
    }
}
