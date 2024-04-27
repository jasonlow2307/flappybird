package flappybird

import flappybird.model.Player
import flappybird.util.Database
import flappybird.view.{GameController, GameOverController, LeaderboardController, SkinController, TutorialController, UserInformationController, WelcomeController}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import scalafx.Includes._
import javafx.{scene => jfxs}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.{KeyCode, KeyEvent}
import scalafx.scene.media.AudioClip
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp {
  Database.setupDB() //set up database
  val currentPlayer: Player = Player("DEFAULT", 0)

  //game song
  private val welcomeAudio: AudioClip = new AudioClip(getClass.getResource("/flappybird/soundEffect/welcome.mp3").toString)
  welcomeAudio.volume = 3
  welcomeAudio.cycleCount = AudioClip.Indefinite

  // Reusable method to load FXML views
  private def loadFXMLView[T](fileName: String): (jfxs.Parent, T) = {
    val resource = getClass.getResource(fileName)
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.Parent]
    val controller = loader.getController[T]
    (roots, controller)
  }

  //show welcome page
  def showWelcome(): Unit = {
    // Load the FXML file using loadFXMLView method
    val (roots, controller) = loadFXMLView[WelcomeController#Controller]("view/Welcome.fxml")

    if(!welcomeAudio.isPlaying){ //play music if its not playing
      welcomeAudio.play()
    }
    // Create the scene and handle key events
    stage = new PrimaryStage {
      title = "Flappy Bird"
      scene = new Scene {
        root = roots
        stylesheets += getClass.getResource("/flappybird/view/Menu.css").toString
        stylesheets += getClass.getResource("/flappybird/view/Flappybird.css").toString
      }
      icons += new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png"))
    }
    controller.animate() //animate birds
  }

  //show tutorial page
  def showTutorial(): Unit = {
    // Load the FXML file using loadFXMLView method
    val (roots2, controller) = loadFXMLView[TutorialController#Controller]("view/Tutorial.fxml")

    val dialog = new Stage {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots2
        stylesheets += getClass.getResource("/flappybird/view/Tutorial.css").toString
        stylesheets += getClass.getResource("/flappybird/view/Flappybird.css").toString
      }
      icons += new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png"))
    }
    //pass in dialog to controller class
    controller.dialogStage = Option(dialog)
    dialog.showAndWait()
  }

  //show skin page
  var skinController: Option[SkinController#Controller] = None
  def showSkin(): Unit = {
    // Load the FXML file using loadFXMLView method
    val (roots3, controller) = loadFXMLView[SkinController#Controller]("view/Skin.fxml")
    skinController = Option(controller) //pass controller out to MainApp
    // Create the scene and handle key events
    stage = new PrimaryStage {
      title = "Flappy Bird Skins"
      scene = new Scene {
        root = roots3
        stylesheets += getClass.getResource("/flappybird/view/Game.css").toString
        stylesheets += getClass.getResource("/flappybird/view/Flappybird.css").toString
      }
      icons += new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png"))
    }
  }

  //show user information page
  def showUserInformation(): Unit = {
    currentPlayer.clear()
    // Load the FXML file using loadFXMLView method
    val (roots4, controller) = loadFXMLView[UserInformationController#Controller]("view/UserInformation.fxml")
    val dialog = new Stage {
      initModality(Modality.APPLICATION_MODAL)
      initOwner(stage)
      scene = new Scene {
        root = roots4
        stylesheets += getClass.getResource("/flappybird/view/Menu.css").toString
        stylesheets += getClass.getResource("/flappybird/view/Flappybird.css").toString
      }
      icons += new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png"))
    }
    //pass in dialog to controller page
    controller.dialogStage = Option(dialog)
    dialog.showAndWait()
  }

  //show game page
  var gameController: Option[GameController#Controller] = None
  def showGame(): Unit = {
    // Load the FXML file using loadFXMLView method
    val (roots5, controller) = loadFXMLView[GameController#Controller]("view/Game.fxml")
    gameController = Option(controller)
    // Create the scene and handle key events
    stage = new PrimaryStage {
      title = "Flappy Bird"
      scene = new Scene {
        root = roots5
        //check for space bar or enter input
        onKeyPressed = (event: KeyEvent) => {
          if (event.code.equals(KeyCode.SPACE) || event.code.equals(KeyCode.ENTER)) {
            controller.jump()
          }
        }
        stylesheets += getClass.getResource("/flappybird/view/Flappybird.css").toString
        stylesheets += getClass.getResource("/flappybird/view/Game.css").toString
      }
      icons += new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png"))
    }
  }

  //show game over page
  def showGameOver(): Unit = {
    // Load the FXML file using loadFXMLView method
    val (roots6, controller) = loadFXMLView[GameOverController#Controller]("view/GameOver.fxml")
    // Create the scene and handle key events
    stage = new PrimaryStage {
      title = "Game Over"
      scene = new Scene {
        root = roots6
        stylesheets += getClass.getResource("/flappybird/view/Menu.css").toString
        stylesheets += getClass.getResource("/flappybird/view/Flappybird.css").toString
      }
      icons += new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png"))
    }
    controller.animate() //animate birds
  }

  //show leaderboard page
  def showLeaderboard(): Unit = {
    // Load the FXML file using loadFXMLView method
    val (roots7, controller) = loadFXMLView[LeaderboardController#Controller]("view/Leaderboard.fxml")
    // Create the scene and handle key events
    stage = new PrimaryStage {
      title = "Leaderboard"
      scene = new Scene {
        root = roots7
        stylesheets += getClass.getResource("/flappybird/view/Menu.css").toString
        stylesheets += getClass.getResource("/flappybird/view/Flappybird.css").toString
      }
      icons += new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png"))
    }
  }

  showWelcome()


}
