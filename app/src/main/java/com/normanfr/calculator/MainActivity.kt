package com.normanfr.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var previousDisplay: TextView
    private var currentInput: String = ""
    private var fullExpression: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        previousDisplay = findViewById(R.id.previousDisplay)

        setNumericButtonListeners()
        setOperatorButtonListeners()
    }

    private fun setNumericButtonListeners() {
        val listener = { v: android.view.View ->
            val b = v as Button
            currentInput += b.text.toString()
            display.text = currentInput
        }

        findViewById<Button>(R.id.button0).setOnClickListener(listener)
        findViewById<Button>(R.id.button1).setOnClickListener(listener)
        findViewById<Button>(R.id.button2).setOnClickListener(listener)
        findViewById<Button>(R.id.button3).setOnClickListener(listener)
        findViewById<Button>(R.id.button4).setOnClickListener(listener)
        findViewById<Button>(R.id.button5).setOnClickListener(listener)
        findViewById<Button>(R.id.button6).setOnClickListener(listener)
        findViewById<Button>(R.id.button7).setOnClickListener(listener)
        findViewById<Button>(R.id.button8).setOnClickListener(listener)
        findViewById<Button>(R.id.button9).setOnClickListener(listener)
        findViewById<Button>(R.id.buttonDot).setOnClickListener(listener)
    }

    private fun setOperatorButtonListeners() {
        findViewById<Button>(R.id.buttonClear).setOnClickListener {
            currentInput = ""
            fullExpression = ""
            display.text = ""
            previousDisplay.text = ""
        }

        findViewById<Button>(R.id.buttonAdd).setOnClickListener(OperatorClickListener("+"))
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener(OperatorClickListener("-"))
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener(OperatorClickListener("*"))
        findViewById<Button>(R.id.buttonDivide).setOnClickListener(OperatorClickListener("/"))

        findViewById<Button>(R.id.buttonEquals).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                fullExpression += currentInput
                val result = evaluateExpression(fullExpression)
                display.text = result
                previousDisplay.text = fullExpression
                currentInput = result
                fullExpression = ""
            }
        }

        findViewById<Button>(R.id.buttonBackspace).setOnClickListener {
            if (currentInput.isNotEmpty()) {
                currentInput = currentInput.dropLast(1)
                display.text = currentInput
            }
        }
    }

    private inner class OperatorClickListener(private val op: String) : android.view.View.OnClickListener {
        override fun onClick(v: android.view.View?) {
            if (currentInput.isNotEmpty()) {
                fullExpression += currentInput + op
                previousDisplay.text = fullExpression
                currentInput = ""
                display.text = currentInput
            }
        }
    }

    private fun evaluateExpression(expression: String): String {
        return try {
            val result = ExpressionBuilder(expression).build().evaluate()
            result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }
}
