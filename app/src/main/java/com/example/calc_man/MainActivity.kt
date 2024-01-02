package com.example.calc_man

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun numberAction(view: View) {}
    fun operationAction(view: View) {}

    fun allClearAction(view: View) {
        workingsTV.text = ""
        resultTV.text =""
    }

    fun backspaceAction(view: View) {}
    fun equalAction(view: View) {}
}