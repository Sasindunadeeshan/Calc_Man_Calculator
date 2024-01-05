package com.example.calc_man

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var workingsTV: TextView
    private lateinit var resultTV: TextView
    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TextViews
        workingsTV = findViewById(R.id.workingsTV)
        resultTV = findViewById(R.id.resultTV)
    }

    fun numberAction(view: View) {
        if (view is Button) {
            if (view.text == ".") {
                if (canAddDecimal) {
                    workingsTV.append(view.text)
                    canAddDecimal = false
                }
            } else {
                workingsTV.append(view.text)
                canAddOperation = true
            }
        }
    }


    fun operationAction(view: View) {
        if (view is Button && canAddOperation)
        {
            workingsTV.append(view.text)
            canAddOperation=false
            canAddDecimal = true
        }
    }

    fun allClearAction(view: View) {
        workingsTV.text = ""
        resultTV.text = ""
    }

    fun backspaceAction(view: View) {
        val length = workingsTV.length()
        if (length>0)
            workingsTV.text=workingsTV.text.subSequence(0,length-1)
    }

    private fun calculateResult(): String {
        val digitsOperators = digitsOperators()
        if (digitsOperators.isEmpty()) return ""

        val timeDivision = timesDivisionCalculate(digitsOperators)
        if (timeDivision.isEmpty() || timeDivision[0] !is Float) return ""

        val result = addSubtractCalculate(timeDivision as List<Any>)
        return result.toString()
    }



    private fun addSubtractCalculate(passedList: List<Any>): Float {
        var result = passedList[0] as Float

        for (i in 1 until passedList.size step 2) {
            val operator = passedList[i] as Char
            val nextDigit = passedList[i + 1] as Float
            when (operator) {
                '+' -> result += nextDigit
                '-' -> result -= nextDigit
            }
        }

        return result
    }


    private fun timesDivisionCalculate(passedList: List<Any>): List<Any> {
        var list = passedList
        while (list.contains('x') || list.contains('/')) {
            list = calcTimeDiv(list)
        }
        return list
    }


    private fun calcTimeDiv(passedList: List<Any>): MutableList<Any> {

        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for (i in passedList.indices)
        {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i-1] as Float
                val nextDigit = passedList[i+1] as Float

                when (operator)
                {
                    'x' ->
                    {
                        newList.add(prevDigit * nextDigit)
                    }
                    '/' ->
                    {
                        newList.add(prevDigit/nextDigit)
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if (i> restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    private fun digitsOperators() : MutableList<Any>{
        var list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in workingsTV.text)
        {
            if (character.isDigit() || character == '.')
                currentDigit += character
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }
        if (currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }

    fun equalAction(view: View) {
        resultTV.text = calculateResult()
    }


}
