package com.ivan.work.animation_togo_independence

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import android.widget.ImageView

class MainActivity2 : AppCompatActivity() {
    private lateinit var myLayout: FrameLayout
    private lateinit var flagImageView: ImageView
    private lateinit var chainReactionView: View
    private lateinit var confettiView: ImageView
    private lateinit var star1View: ImageView
    private lateinit var star2View: ImageView
    private lateinit var star3View: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        myLayout = findViewById(R.id.myLayout)
        flagImageView = findViewById(R.id.flag_image_view)
        chainReactionView = findViewById(R.id.chain_reaction_view)
        confettiView = findViewById(R.id.confet_view)
        star1View = findViewById(R.id.star1_view)
        star2View = findViewById(R.id.star2_view)
        star3View = findViewById(R.id.star3_view)
    }

    fun dpToPx(context: Context, dp: Float): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5f).toInt()
    }

    override fun onStart() {
        super.onStart()

        //myLayout.background = getDrawable(R.drawable.tifff)
        confettiView.visibility = View.INVISIBLE

        val flagAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
            Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 1f
        ).apply {
            duration = 2000
            interpolator = AccelerateInterpolator()
        }
        flagImageView.startAnimation(flagAnimation)

        flagAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                myLayout.background = null
                chainReactionView.visibility = View.VISIBLE
                val chainReactionAnimator = chainReactionView.animate()

                val flagAnimation = TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    -0.1f
                ).apply {
                    duration = 2000
                    repeatCount = Animation.INFINITE
                    repeatMode = Animation.REVERSE
                    interpolator = AccelerateDecelerateInterpolator()
                }



                val layoutParams = flagImageView.layoutParams
                layoutParams.width = dpToPx(applicationContext, 800f) // Convertit 1000dp en pixels
                flagImageView.layoutParams = layoutParams

                flagImageView.setImageResource(R.drawable.togo_flag)
                // Animation pour allumer le drapeau
                val lightUpAnimation = ValueAnimator.ofFloat(0f, 1f).apply {
                    duration = 5000
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        flagImageView.alpha = value
                    }
                }

                flagImageView.startAnimation(flagAnimation);

                confettiView.visibility = View.VISIBLE
                // Animation pour faire exploser les confettis
                val confettiAnimation = ValueAnimator.ofFloat(0f, 1f).apply {
                    duration = 15000
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        confettiView.scaleX = value
                        confettiView.scaleY = value
                        confettiView.alpha = 1 - value
                    }
                }

                star1View.visibility = View.VISIBLE
                star2View.visibility = View.VISIBLE
                star3View.visibility = View.VISIBLE
                // Animation pour faire clignoter les étoiles
                val starsAnimation = ValueAnimator.ofFloat(0f, 1f).apply {
                    duration = 3000
                    repeatCount = ValueAnimator.INFINITE
                    repeatMode = ValueAnimator.REVERSE
                    addUpdateListener {
                        val value = it.animatedValue as Float
                        star1View.alpha = value
                        star2View.alpha = value
                        star3View.alpha = value
                    }
                }

                // Jouer les animations en séquence
                chainReactionAnimator
                    .setDuration(5000)
                    .withEndAction { chainReactionView.visibility = View.GONE }
                    .withStartAction {
                        lightUpAnimation.start()
                        confettiAnimation.start()
                        starsAnimation.start()
                    }
                    .start()
            }
        })
    }

}