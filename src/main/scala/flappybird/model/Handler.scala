package flappybird.model

import scalafx.geometry.{BoundingBox, Bounds}
import scalafx.scene.image.ImageView

trait Handler {
  val bird: Bird //all handler need the bird object

  def getPillarBoundsInParent(pillar: ImageView): Bounds = {
    new BoundingBox(pillar.x.value, pillar.y.value, pillar.fitWidth.value, pillar.fitHeight.value)
  }
}