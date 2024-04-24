package com.example.calendar

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.calendar.database.MyDB
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : AppCompatActivity(), AdapterView.AdapterClickListener {

    private lateinit var  btnAdd : FloatingActionButton
    private lateinit var db : MyDB
    private lateinit var search : EditText
    private lateinit var search_but : ImageButton
    private lateinit var data : ImageButton
    private var date = SimpleDateFormat("dd.MM.yyyy").format(System.currentTimeMillis())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnAdd= findViewById(R.id.add)
        search= findViewById(R.id.edit_search)
        search_but= findViewById(R.id.search)
        data= findViewById(R.id.data)
        btnAdd.setOnClickListener{
            onClick()
        }
        db = MyDB.get(this)
        val recyclerView = findViewById<RecyclerView>(R.id.lista)
        val adapter = AdapterView(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        db.EventDAO().getAll().observe(this, { events ->
            adapter.events = events
            adapter.notifyDataSetChanged()
        })
        adapter.addOnClickListener(this)
        search_but.setOnClickListener {
            if(!search.text.isEmpty()){
                lifecycleScope.launch {
                    val db = MyDB.get(applicationContext)
                    val events = withContext(Dispatchers.IO) {
                        db.EventDAO().searchTitle("%"+search.text.toString()+"%")
                    }
                    adapter.events = events
                    adapter.notifyDataSetChanged()
                }
            }
        }

        var cal = Calendar.getInstance()
        var myFormat = "dd.MM.yyyy"
        var sdf = SimpleDateFormat(myFormat, Locale.US)

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            date = sdf.format(cal.time)

                lifecycleScope.launch {
                    val db = MyDB.get(applicationContext)
                    val events = withContext(Dispatchers.IO) {
                        db.EventDAO().searchDate("%"+date+"%")
                    }
                    adapter.events = events
                    adapter.notifyDataSetChanged()
                }

        }
        data.setOnClickListener {
            DatePickerDialog(this, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                val searchTerm = s.toString()
                if (searchTerm.isEmpty()) {
                    lifecycleScope.launch {
                        db.EventDAO().getAll().observe(this@MainActivity, { events ->
                            adapter.events = events
                            adapter.notifyDataSetChanged()

                        })
                    }
                }
            }
        })
    }

    fun onClick(){
        this.finish()
        val addIntent = Intent(this, NewEvent::class.java)
        startActivity(addIntent)
    }

    override fun onClick(id: Long) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Usunięcie wydarzenia")
        builder.setMessage("Usunąć zapis?")
        builder.setPositiveButton("Tak") { dialog, which ->
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    val db = MyDB.get(applicationContext)
                    db.EventDAO().delete(id)
                }
            }
        }
        builder.setNegativeButton("Nie") { dialog, which ->
        }
        val dialog = builder.create()
        dialog.show()
    }

}