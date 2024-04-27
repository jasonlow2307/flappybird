package flappybird.view

import flappybird.MainApp
import flappybird.model.Player
import flappybird.util.{Animation, LeftRightAnimation, UpDownAnimation}
import scalafx.scene.image.ImageView
import scalafx.scene.text.Text
import scalafxml.core.macros.sfxml

@sfxml
class GameOverController(finalScore:Text, finalTime:Text, leftBird: ImageView, rightBird: ImageView) {
  private val upDownAnimation: UpDownAnimation = new UpDownAnimation()
  private val player: Player = MainApp.currentPlayer

  finalScore.text = MainApp.gameController.get.scoreHandler.score.toString //get score from scoreHandler
  finalTime.text = (MainApp.gameController.get.gameTime/60).toString + " seconds" //get time from gameController
  player.save() //sync score to db

  //to animate both birds
  def animate(): Unit = {
    upDownAnimation.animate(leftBird, rightBird)
  }

  //for play again button
  def handlePlayAgain(): Unit = {
    MainApp.showUserInformation()
  }

  //for main menu button
  def handleMainMenu(): Unit = {
    MainApp.showWelcome()
  }

  //for leaderboard button
  def handleLeaderboard(): Unit = {
    MainApp.showLeaderboard()
  }
}
