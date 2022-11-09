package com.example.rockpaperscissor

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import android.view.animation.AnimationUtils
import androidx.core.graphics.rotationMatrix
import androidx.databinding.DataBindingUtil
import com.example.rockpaperscissor.databinding.ActivityRpsactivityBinding

class RPSActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRpsactivityBinding; // declaring the view binding variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rpsactivity)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_rpsactivity); // initializing the view binding variable for the activity rps
    }


    /*
     * method to reset the game once the play click "play again" button
     */
    fun restartGame(view: View){
        // setting the result views to their invisible state
        binding.result.visibility = View.INVISIBLE;
        binding.resultView.visibility = View.INVISIBLE;
        binding.resultInfo.visibility = View.INVISIBLE;
        binding.restart.visibility = View.INVISIBLE;
        binding.paper.visibility = View.INVISIBLE;

        binding.rock.visibility = View.VISIBLE;// making required components visible
        binding.rock.animate().apply {
            duration=500;
            rotationMatrix(360F);
        }


        // reassigning respective values
        binding.paper.setImageBitmap(BitmapFactory.decodeResource(resources, R.drawable.paper));
        binding.paper.visibility = View.VISIBLE; // making required components visible
        binding.paper.animate().apply {
            duration=500;
            rotationMatrix(360F);
        }

        binding.scissor.visibility = View.VISIBLE; // making required components visible
        binding.scissor.animate().apply {
            duration=500;
            rotationMatrix(360F);
        }
    }


    fun beginGame(view:View){
        // setting left motion to the rock view
        binding.rock.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_right));

        // setting right motion to the scissor view
        binding.scissor.startAnimation(AnimationUtils.loadAnimation(this, R.anim.move_left));

        // setting the result views to their visible state
        binding.result.visibility = View.VISIBLE;
        binding.resultView.visibility = View.VISIBLE;
        binding.resultInfo.visibility = View.VISIBLE;
        binding.restart.visibility = View.VISIBLE;
        binding.paper.visibility = View.VISIBLE;


        binding.rock.visibility = View.INVISIBLE;// making required components invisible
        binding.scissor.visibility = View.INVISIBLE; // making required components invisible
    }
}