package com.ivan.work.animation_togo_independence

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Color
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.*
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.*
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private val scope = CoroutineScope(Dispatchers.Main)

    private fun stopConfettiAnimation() {
        scope.cancel()
    }

    override fun onStop() {
        super.onStop()
        stopConfettiAnimation()
    }

    override fun onStart() {
        super.onStart()

        val imageView = findViewById<ImageView>(R.id.flag_image_view)
        imageView.setImageResource(R.drawable.flag)

        val flagAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            -0.03f
        ).apply {
            duration = 2000
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
            interpolator = AccelerateDecelerateInterpolator()
        }
        imageView.startAnimation(flagAnimation)


        /*val rotationAnimation = RotateAnimation(
            0f, // Angle de départ de la rotation
            20f, // Angle final de la rotation
            Animation.RELATIVE_TO_SELF, 0.5f, // Point de pivot pour la rotation (x, y)
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotationAnimation.duration = 1000 // Durée de l'animation en millisecondes
        rotationAnimation.repeatCount = Animation.INFINITE // Répéter l'animation indéfiniment
        rotationAnimation.repeatMode = Animation.REVERSE // Inverser l'animation après chaque répétition

        imageView.startAnimation(rotationAnimation)*/



        val confettiLayout = findViewById<FrameLayout>(R.id.myLayout)

        // Add the text view for the message
        val messageTextView = TextView(this)
        messageTextView.text = ""
        messageTextView.setTextColor(Color.WHITE)
        messageTextView.textSize = 124f
        messageTextView.gravity = Gravity.CENTER
        messageTextView.visibility = View.INVISIBLE
        val messageParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
        }
        messageTextView.layoutParams = messageParams
        confettiLayout.addView(messageTextView)


        scope.launch {
            while (true) {
                for (i in 0 until 10) {
                    val confettiImageView = ImageView(this@MainActivity)
                    confettiImageView.setImageResource(R.drawable.cft)
                    confettiImageView.visibility = View.INVISIBLE
                    val params = FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.gravity = Gravity.CENTER
                    confettiImageView.layoutParams = params
                    (findViewById<FrameLayout>(R.id.myLayout)).addView(confettiImageView)

                    // Set up the animation set for the confetti
                    val animationSet = AnimationSet(true)
                    animationSet.repeatCount = Animation.INFINITE
                    animationSet.repeatMode = Animation.REVERSE

                    // Scale animation to make the confetti grow and shrink
                    val scaleAnimation = ScaleAnimation(
                        0.0f,
                        1.0f,
                        0.0f,
                        1.0f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f,
                        Animation.RELATIVE_TO_SELF,
                        0.5f
                    )
                    scaleAnimation.duration = 4000
                    animationSet.addAnimation(scaleAnimation)


                    // Randomly choose the direction of the translation
                    val rand = Random()
                    val direction = rand.nextInt(4)

                    // Translation animation to make the confetti move in a specific direction
                    val translationAnimation = when (direction) {
                        0 -> TranslateAnimation(-500f, 500f, -500f, 500f) // Diagonal from top-left to bottom-right
                        1 -> TranslateAnimation(500f, -500f, -500f, 500f) // Diagonal from top-right to bottom-left
                        2 -> TranslateAnimation(0f, 0f, -1000f, 1000f) // From top to bottom
                        else -> TranslateAnimation(-1000f, 1000f, 0f, 0f) // From left to right
                    }
                    translationAnimation.duration = 5000
                    animationSet.addAnimation(translationAnimation)


                    // Alpha animation to make the confetti fade in and out
                    val alphaAnimation = AlphaAnimation(0.0f, 1.0f)
                    alphaAnimation.duration = 500
                    alphaAnimation.startOffset = rand.nextInt(2000).toLong()
                    animationSet.addAnimation(alphaAnimation)

                    messageTextView.visibility = View.VISIBLE

                    val alphaAnimation2 = AlphaAnimation(1.0f, 0.0f)
                    alphaAnimation2.duration = 500
                    alphaAnimation2.startOffset = (rand.nextInt(2000) + 500).toLong()
                    animationSet.addAnimation(alphaAnimation2)


                    // Create an ObjectAnimator for the messageTextView
                    val animator = ObjectAnimator.ofFloat(
                        messageTextView, "translationY", 0f, -200f
                    ).apply {
                        duration = 5000 // Set the duration of the animation
                        repeatCount = Animation.INFINITE // Set the animation to repeat indefinitely
                        repeatMode = ValueAnimator.REVERSE // Set the animation to reverse when it repeats
                        interpolator = AccelerateDecelerateInterpolator() // Set an interpolator for the animation
                    }

                    // Start the animator
                    animator.start()

                    confettiImageView.startAnimation(animationSet)
                }

                delay(2000) // Wait for 2 seconds before starting the animation again
            }
        }
    }




}