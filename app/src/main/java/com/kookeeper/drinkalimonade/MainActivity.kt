package com.kookeeper.drinkalimonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

private const val STATE_SELECT = "select"
private const val STATE_SQUEEZE = "squeeze"
private const val STATE_DRINK = "drink"
private const val STATE_RESET = "reset"

class MainActivity : AppCompatActivity() {

    private var squeezeCount: Int = 0
    private var lemonSize: Int = 0
    private var state = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image: ImageView = findViewById(R.id.imageView)
        updateState()
        image.setOnClickListener {
            lemonClick()
        }
        image.setOnLongClickListener {
            lemonLongClick()
            false
        }
    }

    private fun lemonClick() {
        when(state) {
            STATE_SELECT -> {
                lemonSize = pick()
                squeezeCount = 0
                updateState()
            }
            STATE_SQUEEZE -> {
                squeezeCount++
                lemonSize--
                if (lemonSize == 0)
                    updateState()
            }
            STATE_DRINK -> {
                lemonSize = -1
                updateState()
            }
            STATE_RESET -> {
                updateState()
            }
        }
    }

    private fun setViewElements() {
        val tv: TextView = findViewById(R.id.textView2)
        val image: ImageView = findViewById(R.id.imageView)
        when(state) {
            STATE_SELECT -> {
                tv.text = getString(R.string.select_string)
                image.setImageResource(R.drawable.lemon_tree)
            }
            STATE_SQUEEZE -> {
                tv.text = getString(R.string.squeeze_string)
                image.setImageResource(R.drawable.lemon_squeeze)
            }
            STATE_DRINK -> {
                tv.text = getString(R.string.drink_string)
                image.setImageResource(R.drawable.lemon_drink)
            }
            STATE_RESET -> {
                tv.text = getString(R.string.reset_string)
                image.setImageResource(R.drawable.lemon_restart)
            }
        }
    }

    private fun showSnackbar() {
        val squeezeText = getString(R.string.squeeze_count, lemonSize)
        if (state != STATE_SQUEEZE)
            return
        Snackbar.make(findViewById(R.id.constraint_layout), squeezeText, Snackbar.LENGTH_SHORT).show()
    }

    private fun pick(): Int {
        return (2..4).random()
    }

    private fun updateState() {
        state = when(state) {
            STATE_SELECT -> STATE_SQUEEZE
            STATE_SQUEEZE -> STATE_DRINK
            STATE_DRINK -> STATE_RESET
            STATE_RESET -> STATE_SELECT
            else -> STATE_SELECT
        }
        setViewElements()
    }

    private fun lemonLongClick() {
        showSnackbar()
    }
}