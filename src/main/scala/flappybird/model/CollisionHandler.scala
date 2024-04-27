package flappybird.model

import javafx.geometry.Bounds
import scalafx.scene.image.ImageView

import scala.collection.mutable.ListBuffer

class CollisionHandler(_bird: Bird) extends Handler{
  override val bird: Bird = _bird
  def checkCollision(obstacles: ListBuffer[ImageView]): Boolean = {
    val birdBounds: Bounds = bird.sprite.boundsInParent.value

    for (obstacle <- obstacles) { //iterate through obstacles
      val pillarBounds = getPillarBoundsInParent(obstacle)
      if (birdBounds.intersects(pillarBounds)) { //if bird's bound intersect with pillar's bound
        return true
      }
    }
    false
  }

}
