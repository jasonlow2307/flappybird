package flappybird.util

import scalafx.animation.TranslateTransition
import scalafx.scene.image.ImageView
import scalafx.Includes._

trait Animation {
  def animate(leftBird: ImageView, rightBird: ImageView): Unit
}

class LeftRightAnimation extends Animation {
  override def animate(leftBird: ImageView, rightBird: ImageView): Unit = {
    // animate left bird
    val leftTransition: TranslateTransition = new TranslateTransition(javafx.util.Duration.seconds(5), leftBird){
      // Set the fromX and toX properties to define the starting and ending X positions
      fromX = 31
      toX = 493
      // Set the cycle count to INDEFINITE for continuous animation
      cycleCount = TranslateTransition.Indefinite
      // Set the auto-reverse property to true for the animation to reverse back to the starting position
      autoReverse = true
    }

    // Play the animation
    leftTransition.play()

    // animate right bird
    val rightTransition: TranslateTransition = new TranslateTransition(javafx.util.Duration.seconds(5), rightBird){
      // Set the fromX and toX properties to define the starting and ending X positions
      fromX = 0
      toX = -420
      // Set the cycle count to INDEFINITE for continuous animation
      cycleCount = TranslateTransition.Indefinite
      // Set the auto-reverse property to true for the animation to reverse back to the starting position
      autoReverse = true
    }

    // Play the animation
    rightTransition.play()
  }
}

class UpDownAnimation extends Animation {
  override def animate(leftBird: ImageView, rightBird: ImageView): Unit = {
    // animate left bird
    val leftTransition: TranslateTransition = new TranslateTransition(javafx.util.Duration.seconds(5), leftBird){
      // Set the fromY and toY properties to define the starting and ending Y positions
      fromY = -30
      toY = 270
      // Set the cycle count to INDEFINITE for continuous animation
      cycleCount = TranslateTransition.Indefinite
      // Set the auto-reverse property to true for the animation to reverse back to the starting position
      autoReverse = true
    }
    // Play the animation
    leftTransition.play()

    // animate right bird
    val rightTransition: TranslateTransition = new TranslateTransition(javafx.util.Duration.seconds(5), rightBird){
      // Set the fromY and toY properties to define the starting and ending Y positions
      fromY = 0
      toY = -300
      // Set the cycle count to INDEFINITE for continuous animation
      cycleCount = TranslateTransition.Indefinite
      // Set the auto-reverse property to true for the animation to reverse back to the starting position
      autoReverse = true
    }

    // Play the animation
    rightTransition.play()
  }
}