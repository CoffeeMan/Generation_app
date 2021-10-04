package com.bignerdranch.android.generationapp

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var textResult: TextView
    private lateinit var picker: DatePicker
    private lateinit var buttonResult: Button
    private lateinit var sPref: SharedPreferences

    private val SAVED_TEXT = "saved_text"
    private val SAVED_YEAR = "saved_year"
    private val SAVED_MONTH = "saved_month"
    private val SAVED_DAY = "saved_day"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textResult = findViewById(R.id.text_view_result)
        picker = findViewById(R.id.date_picker)
        buttonResult = findViewById(R.id.button_result)
        loadData()
    }

    fun getResult(view: View) {
        when (picker.year) {
            in 1946..1964 -> textResult.text = getString(R.string.baby_boomer)
            in 1965..1980 -> textResult.text = getString(R.string.gen_x)
            in 1981..1996 -> textResult.text = getString(R.string.gen_y)
            in 1997..2012 -> textResult.text = getString(R.string.gen_z)
            else -> textResult.text = getString(R.string.invalid_argument)
        }
        saveData()
    }

    private fun saveData() {
        sPref = getPreferences(MODE_PRIVATE)
        val ed: SharedPreferences.Editor = sPref.edit()
        ed.putString(SAVED_TEXT, textResult.text.toString())
        ed.putInt(SAVED_YEAR, picker.year)
        ed.putInt(SAVED_MONTH, picker.month)
        ed.putInt(SAVED_DAY, picker.dayOfMonth)
        ed.apply()
    }

    private fun loadData() {
        sPref = getPreferences(MODE_PRIVATE)
        val savedText: String? = sPref.getString(SAVED_TEXT, "")
        textResult.text = savedText
        val year = sPref.getInt(SAVED_YEAR, 1970)
        val month = sPref.getInt(SAVED_MONTH, 1)
        val day = sPref.getInt(SAVED_DAY, 1)
        picker.updateDate(year, month, day)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putString("KEY", textResult.text.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        textResult.text = savedInstanceState.getString("KEY")
    }
}