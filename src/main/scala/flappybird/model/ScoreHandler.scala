package flappybird.model

import flappybird.MainApp
import scalafx.animation.{KeyFrame, Timeline}
import scalafx.scene.image.ImageView
import scalafx.scene.media.AudioClip
import scalafx.scene.text.Text
import scalafx.util.Duration

import scala.collection.mutable.ListBuffer

class ScoreHandler(_bird: Bird, val scoreText: Text, val message: Text) extends Handler{
  override val bird = _bird
  var score: Int = 0
  val player: Player = MainApp.currentPlayer
  private val awardedPointsObstacles: ListBuffer[ImageView] = ListBuffer[ImageView]() //store obstacles that have been already awarded points
  private val increaseSpeedAudio: AudioClip = new AudioClip(getClass.getResource("/flappybird/soundEffect/increase_speed.mp3").toString)


  def updateScore(obstacles: ListBuffer[ImageView]): Unit = {
    var i = 0 //counter for iterating through obstacles
    while (i < obstacles.size) {
      if (obstacles(i).x.value <= bird.sprite.x.value && !awardedPointsObstacles.contains(obstacles(i))) { //check if obstacle is past bird and if obstacle has already been awarded points
        score += 1
        player.incrementScore(1)
        player.save()

        // increment velocity by 2 every 10 score earned
        if (score % 10 == 0 && score > 0) {
          bird.velocity += 2
          message.visible = true
          message.text = "Speed has been increased, good luck, keep it up!"
          increaseSpeedAudio.volume = 10
          increaseSpeedAudio.play()
          val timeline = new Timeline { //display message for 3 seconds
            keyFrames = Seq(
              KeyFrame(Duration(3000), onFinished = _ => {
                message.visible = false
              })
            )
          }
          timeline.play()
        }
        scoreText.text = score.toString
        println("SCORE " + score)
        awardedPointsObstacles+=obstacles(i)
      }
      i = i + 2 //increment 2 to avoid duplicate award for upper and lower pillar
    }
  }

}
