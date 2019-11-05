package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity() {

    //region 2. maze definition
    companion object {
        val path = arrayOf(1, 2, 1, 1, 2, 1)
    }
    //endregion

    //region 3. properties
    private val currentStepKey = "currentStepKey"
    private val lastDirectionKey = "lastDirectionKey"
    private val defaultValueForCurrentStep = -1
    private var currentStep = defaultValueForCurrentStep
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //region 1. button setup
        left_button.setOnClickListener {
            //region 5. movement
            goToNextRoom("left")
            //endregion
        }

        right_button.setOnClickListener {
            //region 5. movement
            goToNextRoom("right")
            //endregion
        }

        restart_button.setOnClickListener {
            startActivity(Intent(this@GameActivity, GameActivity::class.java))
        }
        //endregion
    }

    override fun onResume() {
        super.onResume()

        //region load data from intent
        currentStep = intent.extras?.getInt(currentStepKey) ?: defaultValueForCurrentStep
        val direction = intent.extras?.getString(lastDirectionKey) ?: ""

        text_field.text = getMessage(direction, currentStep)
        //endregion
    }

    //region 4. movement definition
    fun goToNextRoom(direction: String) {
        val intent = Intent(this@GameActivity, GameActivity::class.java)
        val bundle = Bundle()
        bundle.putInt(currentStepKey, ++currentStep)
        bundle.putString(lastDirectionKey, direction)
        intent.putExtras(bundle)

        startActivity(intent)
    }
    //endregion

    //region 6. room messages
    fun getMessage(direction: String, step: Int): String {
        return when {
            step >= path.size -> "Max steps achieved. Please hit reset to start again."
            direction.isEmpty() -> "New game"
            direction == "left" && path[step] == 1 -> insideRoom(step)
            direction == "right" && path[step] == 2 -> insideRoom(step)
            else -> "The trap got you. Game Over. Please try again."
        }
    }

    fun insideRoom(step: Int): String {
        return when {
            step >= path.size - 1 -> return "Congratulations!!! You found a way out of maze."
            else -> "You see another room with 2 doors"
        }
    }
    //endregion
}
