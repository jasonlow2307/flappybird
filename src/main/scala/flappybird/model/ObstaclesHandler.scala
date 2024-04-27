
package flappybird.model

import scalafx.geometry.{BoundingBox, Bounds}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane

import scala.collection.mutable.ListBuffer
import scala.util.Random

class ObstaclesHandler(_bird: Bird, val plane: AnchorPane) extends Handler{
  override val bird: Bird = _bird
  private val random: Random = new Random()

  def createObstacles(): ListBuffer[ImageView] = {
    val width: Double = 25
    val _x: Double = plane.width.doubleValue()
    val space: Int = 150
    val minHeight: Int = 50
    val maxHeight: Int = (plane.height - space - minHeight).intValue()
    val topPillarHeight: Double = minHeight + random.nextInt((maxHeight - minHeight + 1).intValue()) //use random to generate the position of the space
    val bottomPillarHeight: Double = (plane.height - space - topPillarHeight).doubleValue()

    val topPillar = new ImageView { //create top obstacle
      x = _x
      y = 0
      fitWidth = width
      fitHeight = topPillarHeight
      image = new Image(getClass.getResourceAsStream("/flappybird/image/top_pillar.png"))
    }

    val bottomPillar = new ImageView { //create bottom obstacle
      x = _x
      y = (plane.height - bottomPillarHeight).doubleValue()
      fitWidth = width
      fitHeight = bottomPillarHeight
      image = new Image(getClass.getResourceAsStream("/flappybird/image/bottom_pillar.png"))
    }

    //add pillars to plane
    plane.getChildren.addAll(topPillar, bottomPillar)
    println("Obstacles created")
    //return as ListBuffer
    ListBuffer(topPillar, bottomPillar)
  }

  def moveObstacles(obstacles: ListBuffer[ImageView]): Unit = {
    val outOfScreen = ListBuffer[ImageView]()

    for (pillar <- obstacles) {
      movePillar(pillar, -bird.velocity) //move each pillar towards the bird

      if ((pillar.x <= -pillar.fitWidth).get()) { //if pillar moves out of screen
        outOfScreen += pillar
      }
    }
    obstacles --= outOfScreen //remove out of screen obstacles from obstacles list
    for (pillar <- outOfScreen) { //remove out of screen obstacles from plane
      plane.children.remove(pillar)
    }
  }

  private def movePillar(pillar: ImageView, amount: Double): Unit = { //move pillar
    pillar.x = (pillar.x + amount).doubleValue()
  }

}