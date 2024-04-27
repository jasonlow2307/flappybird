package flappybird.view

import flappybird.MainApp
import flappybird.model.{Bird, CollisionHandler, ObstaclesHandler, ScoreHandler}
import scalafx.animation.AnimationTimer
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import scalafx.scene.media.AudioClip
import scalafx.scene.text.Text

import scala.collection.mutable.ListBuffer
import scala.util.Random

@sfxml
class GameController(val birdSprite: ImageView, val plane: AnchorPane, val startButton: Button, val scoreText: Text, val message: Text) {
  val bird: Bird = new Bird(birdSprite)
  val random: Random = new Random()
  var obstacles: ListBuffer[ImageView] = ListBuffer[ImageView]()
  var gameTime: Int = 0 //keep track of time for game over screen
  val scoreHandler: ScoreHandler = new ScoreHandler(bird, scoreText, message)
  private var gameLoop: Option[AnimationTimer] = None
  private val obstaclesHandler: ObstaclesHandler = new ObstaclesHandler(bird, plane)
  private val collisionHandler: CollisionHandler = new CollisionHandler(bird)
  private val jumpAudio: AudioClip = new AudioClip(getClass.getResource("/flappybird/soundEffect/jump.mp3").toString)
  private val gameOverAudio: AudioClip = new AudioClip(getClass.getResource("/flappybird/soundEffect/game_over.wav").toString)

  def initialize(): Unit = {
    println("initialized")
    // Enable the button when the game starts and hide it after clicking
    startButton.disable = true
    startButton.visible = false

    //initialize bird sprite
    MainApp.skinController match {
      case Some(x) => bird.sprite.image = x.selectedSkin //if user has clicked into skin page
      case None => bird.sprite.image = new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png")) //if user hasn't selected a skin yet
    }
    bird.sprite.toFront()
    println(bird.sprite.image)
    //calls update() every frame
    gameLoop = Option(AnimationTimer(t => {
      update()
    }))
    //create first batch of obstacles
    obstacles ++= (obstaclesHandler.createObstacles())
    gameLoop.get.start()
    println("Plane height" + (plane.height).doubleValue())
  }

  def update(): Unit = {
    //increment time
    bird.flyingTime = bird.flyingTime + 1
    gameTime = gameTime + 1
    //move bird downwards
    bird.moveY(bird.gravity * bird.flyingTime)

    //move obstacles towards bird
    obstaclesHandler.moveObstacles(obstacles)
    //create new obstacles after a fixed period of time
    if (gameTime % 75 == 0) {
      obstacles ++= (obstaclesHandler.createObstacles())
    }

    //check for collision
    if (collisionHandler.checkCollision(obstacles)) {
      println("Bird collided with obstacle")
      MainApp.showGameOver()
      stopGame()
    }

    //check if bird falls below map
    if (isBirdDead) {
      println("Bird died :(")
      MainApp.showGameOver()
      stopGame()
    }

    //update score
    scoreHandler.updateScore(obstacles)
  }

  private def isBirdDead: Boolean = {
    val birdY = (bird.sprite.layoutY + bird.sprite.y).doubleValue() //to get Double from NumberBinding
    birdY >= plane.getHeight
  }

  private def stopGame(): Unit = {
    //play game over audio
    gameOverAudio.volume = 10
    gameOverAudio.play()
    bird.sprite.y = 0
    bird.sprite.layoutY = 100.0
    gameLoop.get.stop()
  }

  def jump(): Unit = {
    println("Jumped")
    jumpAudio.volume = 10
    jumpAudio.play()
    if ((bird.sprite.layoutY + bird.sprite.y <= bird.jumpStrength).get()){ //prevent bird from jumping higher than plane
      bird.moveY(-(bird.sprite.layoutY + bird.sprite.y).doubleValue()) //to get Double from NumberBinding
      bird.flyingTime = 0 //reset flyingTime
      return
    }

    bird.moveY(-bird.jumpStrength)
    bird.flyingTime = 0 //reset flyingTime
  }

  //for help menu button
  def help(): Unit = {
    MainApp.showTutorial()
  }

  //for close menu button
  def close(): Unit = {
    System.exit(0)
  }
}

