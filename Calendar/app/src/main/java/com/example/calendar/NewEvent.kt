package com.example.calendar

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import com.example.calendar.database.EventEntity
import com.example.calendar.database.EventDAO
import com.example.calendar.database.MyDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class NewEvent : AppCompatActivity() {

    private lateinit var  date1 : TextView
    private lateinit var  date2 : TextView

    private lateinit var  time1 : EditText
    private lateinit var  time2 : EditText

    private lateinit var mTimePicker: TimePickerDialog
    private lateinit var mTimePicker1: TimePickerDialog

    private lateinit var back: ImageButton
    private lateinit var save: ImageButton

    private lateinit var nazwa: EditText
    private lateinit var miejsce: EditText
    private lateinit var opis: EditText


    private var datestart = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
    private var dateend = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_event)

        date1 = findViewById(R.id.textDate1)
        date2 = findViewById(R.id.textDate2)

        time1 = findViewById(R.id.textTime1)
        time2 = findViewById(R.id.textTime2)

        back = findViewById(R.id.back)
        save = findViewById(R.id.save)

        nazwa = findViewById(R.id.nazwa)
        miejsce = findViewById(R.id.miejsce)
        opis = findViewById(R.id.opis)

        back.setOnClickListener {
            this.finish()
            val main = Intent(this, MainActivity::class.java)
            startActivity(main)}

        save.setOnClickListener {
            if(nazwa.text.isEmpty()||miejsce.text.isEmpty()||opis.text.isEmpty()||date1.length()<9||date2.length()<9||time1.text.isEmpty()||time2.text.isEmpty()){
                Toast.makeText(this, "Podaj wszystkie dane", Toast.LENGTH_SHORT).show()
            }
            else{
                val newEvent = EventEntity(title = nazwa.text.toString(), place = miejsce.text.toString(), description = opis.text.toString(), startTime = datestart.toString()+" "+time1.text.toString(), endTime = dateend.toString()+" "+time2.text.toString())

                // Запуск корутины для выполнения операции ввода-вывода в фоновом потоке
                lifecycleScope.launch {
                    withContext(Dispatchers.IO) {
                        val db = MyDB.get(applicationContext)
                        db.EventDAO().insert(newEvent)
                    }
                }
            this.finish()
            val main = Intent(this, MainActivity::class.java)
            startActivity(main)}
        }


        var cal = Calendar.getInstance()

        val mcurrentTime = cal
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        var myFormat = "dd.MM.yyyy" // mention the format you need
        var sdf = SimpleDateFormat(myFormat, Locale.US)


        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            datestart =sdf.format(cal.time)
            date1.text = "Data, od " + datestart

        }

        date1.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        val dateSetListener1 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            if (datestart < (sdf.format(cal.time))){
                dateend = sdf.format(cal.time)
                date2.text ="Data, do " + dateend
            }
            else{
                Toast.makeText(this, "Błąd wprowadzania danych!", Toast.LENGTH_SHORT).show()
            }
        }

        date2.setOnClickListener {
            DatePickerDialog(this, dateSetListener1,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                time1.setText(String.format("%d : %d", hourOfDay, minute))
            }
        }, hour, minute, true)

        time1.setOnClickListener{ v -> mTimePicker.show() }

        mTimePicker1 = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                time2.setText(String.format("%d : %d", hourOfDay, minute))
            }
        }, hour, minute, true)

        time2.setOnClickListener{ v -> mTimePicker1.show() }


    }

    override fun onBackPressed() {
        this.finish()
        val main = Intent(this, MainActivity::class.java)
        startActivity(main)
    }
}