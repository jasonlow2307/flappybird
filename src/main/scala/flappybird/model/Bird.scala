package flappybird.model

import scalafx.scene.image.ImageView

class Bird(val sprite: ImageView) {
  var flyingTime: Double = 0.0 //Time spent flying, jump() will reset this
  val gravity: Double = 0.07 //gravitational acceleration
  val jumpStrength: Double = 50
  var velocity: Double = 10.0 //speed of pillars moving

  def moveY(amount: Double): Unit = {
    sprite.layoutY = (sprite.layoutY + amount).doubleValue()
  }


}
