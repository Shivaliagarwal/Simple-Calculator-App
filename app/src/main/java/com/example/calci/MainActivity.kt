package com.example.calci

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var lastNumeric:Boolean=false
    var lastDot:Boolean=false
    private var tvInput:TextView? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        tvInput=findViewById(R.id.tvInput)
    }
    fun Digit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric=true
        lastDot=false
    }
    fun clear(view: View){
        tvInput?.text=""
    }
    fun onDecimal(view: View){
        if(lastNumeric && !lastDot){
            tvInput?.append(".")
            lastNumeric=false
            lastDot=true
        }
    }
    fun onOperator(view: View){
        tvInput?.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric=false
                lastDot=false
            }
        }
    }
    private fun remove(result:String):String{
        var value=result
        if(result.contains(".0"))
            value=result.substring(0,result.length-2)
        return value

    }
    private fun isOperatorAdded(value:String):Boolean{
        return if (value.startsWith("-")){
            false
        }else
        {
            value.contains("÷")||
                    value.contains("x")||
                    value.contains("-")||
                    value.contains("+")

        }
    }
    fun onEqual(view: View){
        if(lastNumeric){
            var tvValue= tvInput?.text.toString()
            var prefix=""

                try {
                    if (tvValue.startsWith("-")) {
                        prefix = "-"
                        tvValue = tvValue.substring(1)
                    }
                    if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                        if(prefix.isNotEmpty())
                        {
                            one=prefix+one
                        }
                    var res = one.toDouble() - two.toDouble()
                    tvInput?.text =remove(res.toString())

                }else if (tvValue.contains("÷")) {
                        val splitValue = tvValue.split("÷")
                        var one = splitValue[0]
                        var two = splitValue[1]
                        if(prefix.isNotEmpty())
                        {
                            one=prefix+one
                        }
                        var res = one.toDouble() / two.toDouble()
                        tvInput?.text = remove(res.toString())

                    }else if (tvValue.contains("x")) {
                        val splitValue = tvValue.split("x")
                        var one = splitValue[0]
                        var two = splitValue[1]
                        if(prefix.isNotEmpty())
                        {
                            one=prefix+one
                        }
                        var res = one.toDouble() * two.toDouble()
                        tvInput?.text = remove(res.toString())

                    } else if (tvValue.contains("+")) {
                        val splitValue = tvValue.split("+")
                        var one = splitValue[0]
                        var two = splitValue[1]
                        if(prefix.isNotEmpty())
                        {
                            one=prefix+one
                        }
                        var res = one.toDouble() + two.toDouble()
                        tvInput?.text = remove(res.toString())

                    }
            }
            catch (e:ArithmeticException){
                e.printStackTrace()

            }

        }
    }
}