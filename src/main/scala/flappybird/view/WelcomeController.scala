  package flappybird.view

  import flappybird.MainApp
  import flappybird.util.{Animation, LeftRightAnimation}
  import scalafx.scene.image.ImageView
  import scalafxml.core.macros.sfxml

  @sfxml
  class WelcomeController(leftBird: ImageView, rightBird: ImageView) {
    private val leftRightAnimation = new LeftRightAnimation()

    //to animate birds
    def animate(): Unit = {
      leftRightAnimation.animate(leftBird, rightBird)
    }

    //for start game button
    def handleStart(): Unit = {
      MainApp.showUserInformation()
    }

    //for how to play button
    def handleTutorial(): Unit = {
      MainApp.showTutorial()
    }

    //for select skin button
    def handleSkin(): Unit = {
      MainApp.showSkin()
    }

    //for leaderboard button
    def handleLeaderboard(): Unit = {
      MainApp.showLeaderboard()
    }
  }