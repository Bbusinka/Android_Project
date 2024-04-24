package com.example.saper

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var choice : Int = 1
    private var flaggedMines = 0
    private var status  = Status.ONGOING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpBoard()
    }

    private fun setUpBoard() {

        val cellBoard = Array(9) { Array(9) { MineCell(this) }}

        option.setOnClickListener{
            choice = if(choice==1) {
                option.setImageResource(R.drawable.flag)
                2
            }else{
                option.setImageResource(R.drawable.bomb)
                1
            }
        }

        var counter = 1
        var isFirstClick = true

        val params1 = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            0
        )
        val params2 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        for(i in 0 until 9){
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = params1
            params1.weight  = 2.0F

            for(j in 0 until 9){
                val button = MineCell(this)

                cellBoard[i][j] = button

                button.id = counter
                button.textSize = 18.0F
                button.layoutParams = params2
                params2.weight = 1.0F
                button.setBackgroundResource(R.drawable.ten)
                button.setOnClickListener{

                    if(isFirstClick){
                        isFirstClick = false
                        setMines(i,cellBoard)
                    }

                    move(choice, i, j, cellBoard)
                    display(cellBoard)
                }
                linearLayout.addView(button)
                counter++
            }
            board.addView(linearLayout)
        }
    }

    private fun setMines(row:Int, cellBoard:Array<Array<MineCell>>) {
        var i=1
        while (i <= 9) {
            val r = (Random(System.nanoTime()).nextInt(0, 9))
            val c = (Random(System.nanoTime()).nextInt(0, 9))
            if (r == row || cellBoard[r][c].isMine) continue
            cellBoard[r][c].isMine = true
            cellBoard[r][c].value = -1
            updateNeighbours(r, c, cellBoard)
            i++
        }
    }

    private fun updateNeighbours(row: Int, column: Int, cellBoard: Array<Array<MineCell>>) {
        for (i in movement) {
            for (j in movement) {
                if(((row+i) in 0 until 9) && ((column+j) in 0 until 9) && cellBoard[row+i][column+j].value != MINE)
                    cellBoard[row+i][column+j].value++
            }
        }
    }

    private fun move(choice: Int, x: Int, y: Int, cellBoard: Array<Array<MineCell>>): Boolean {
        val cell = cellBoard[x][y]
        if (cell.isRevealed && choice == 1) return false
        when (choice) {
            1 -> {
                if (cell.isMarked) return false
                if (cell.value == MINE) {
                    gameLost()
                    return true
                }
                cell.isRevealed = true
                if (cell.value == 0) {
                    handleZero(x, y, cellBoard)
                }
                checkStatus(cellBoard)
            }
            2 -> {
                if (cell.isRevealed) return false
                if (cell.isMarked) {
                    flaggedMines--
                    cell.setBackgroundResource(R.drawable.ten)
                    cell.isMarked = false
                } else {
                    if (flaggedMines == 9) {
                        Toast.makeText(this, "Nie możesz zaznaczyć więcej niż 9 min", Toast.LENGTH_LONG).show()
                        return false
                    }
                    flaggedMines++
                    cell.isMarked = true
                }
                checkStatus(cellBoard)
            }
            else -> return false
        }
        return true
    }

    private val xDir = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
    private val yDir = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)

    private fun handleZero(x: Int, y: Int, cellBoard: Array<Array<MineCell>>) {
        cellBoard[x][y].isRevealed = true
        for (i in xDir.indices) {
            val xstep = x + xDir[i]
            val ystep = y + yDir[i]
            if (xstep in 0 until 9 && ystep in 0 until 9) {
                val cell = cellBoard[xstep][ystep]
                if (cell.value > 0 && !cell.isMarked) {
                    cell.isRevealed = true
                } else if (!cell.isRevealed && !cell.isMarked && cell.value == 0) {
                    handleZero(xstep, ystep, cellBoard)
                }
            }
        }
    }

    private fun checkStatus(cellBoard: Array<Array<MineCell>>) {
        val hasUnrevealedCells = cellBoard.flatten().any { !it.isRevealed }
        val hasUnmarkedMines = cellBoard.flatten().any { it.value == MINE && !it.isMarked }
        if (!hasUnrevealedCells || !hasUnmarkedMines) { gameWon() }
    }

    private fun gameWon() {
        status = Status.WON
        AlertDialog.Builder(this)
            .setTitle("Gratulacje! Wygrałeś")
            .setCancelable(false)
            .setPositiveButton("Restart Game") { _, _ ->
                finish()
                startActivity(intent)
            }.show()
    }

    private fun gameLost() {
        status = Status.LOST
        AlertDialog.Builder(this)
            .setMessage("Ups, przegrałeś")
            .setTitle("Nadepnąłeś na minę!")
            .setCancelable(false)
            .setPositiveButton("Restart Game") { _, _ ->
                finish()
                startActivity(intent)
            }.show()
    }

    private fun display(cellBoard:Array<Array<MineCell>>) {
        cellBoard.forEach { row -> row.forEach {
            if(it.isRevealed) setNumberImage(it)
            else if (it.isMarked)
                it.setBackgroundResource(R.drawable.flag1)
            else if (status == Status.LOST && it.value == MINE)
                it.setBackgroundResource(R.drawable.mine)

            if(status == Status.LOST && it.isMarked && !it.isMine)
                it.setBackgroundResource(R.drawable.crossedflag)
            else if (status == Status.WON && it.value == MINE)
                it.setBackgroundResource(R.drawable.flag1)
        }
        }
    }

    private fun setNumberImage(button: MineCell) {
        if(button.value==0) button.setBackgroundResource(R.drawable.zero)
        if(button.value==1) button.setBackgroundResource(R.drawable.one)
        if(button.value==2) button.setBackgroundResource(R.drawable.two)
        if(button.value==3) button.setBackgroundResource(R.drawable.three)
        if(button.value==4) button.setBackgroundResource(R.drawable.four)
        if(button.value==5) button.setBackgroundResource(R.drawable.five)
        if(button.value==6) button.setBackgroundResource(R.drawable.six)
        if(button.value==7) button.setBackgroundResource(R.drawable.seven)
        if(button.value==8) button.setBackgroundResource(R.drawable.eight)
    }

    companion object{
        const val MINE = -1
        val movement = intArrayOf(-1, 0, 1)
    }

    enum class Status { WON, ONGOING, LOST }

    class MineCell : AppCompatButton {
        constructor(context: Context?) : super(context!!)
        constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
        constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context!!,
            attrs,
            defStyleAttr
        )
        var value:Int = 0
        var isRevealed: Boolean = false
        var isMarked: Boolean = false
        var isMine : Boolean = false
    }
}