package flappybird.view

import flappybird.MainApp
import flappybird.model.Player
import scalafx.beans.property.StringProperty
import scalafx.scene.control.TextField
import scalafx.scene.text.Text
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

@sfxml
class UserInformationController(_name: TextField, error: Text) {
  private val player = MainApp.currentPlayer
  var dialogStage: Option[Stage] = None

  def createPlayer(): Unit = { //create player based on name input
    if (StringProperty(_name.text.value)().length != 0){ //validation checking for empty name
      player.name = StringProperty(_name.text.value)
      println("User created with name" + player.name)
      player.save()
      MainApp.showGame()
      //close dialog after saving name
      dialogStage match {
        case Some(x) => x.close
        case None =>
      }
    } else {
      error.visible = true
    }

  }

}
