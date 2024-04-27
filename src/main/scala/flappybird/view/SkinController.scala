package flappybird.view

import flappybird.MainApp
import scalafx.scene.control.Button
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import scalafx.Includes._
import scalafx.event.ActionEvent

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

@sfxml
class SkinController(skin1: ImageView, skin2: ImageView, skin3: ImageView, skin4: ImageView, skin5: ImageView, skin6: ImageView, skin7: ImageView, button1: Button, button2: Button, button3: Button, button4: Button, button5: Button, button6: Button, button7: Button) {
  var selectedSkin: Image = new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png")) //default skin
  private val skins = ListBuffer[Image]() //store images of skins
  skins += new Image(getClass.getResourceAsStream("/flappybird/image/bird_1.png"))
  skins += new Image(getClass.getResourceAsStream("/flappybird/image/bird_2.png"))
  skins += new Image(getClass.getResourceAsStream("/flappybird/image/bird_3.png"))
  skins += new Image(getClass.getResourceAsStream("/flappybird/image/bird_4.png"))
  skins += new Image(getClass.getResourceAsStream("/flappybird/image/bird_5.png"))
  skins += new Image(getClass.getResourceAsStream("/flappybird/image/bird_6.png"))
  skins += new Image(getClass.getResourceAsStream("/flappybird/image/bird_left.png"))

  private val skinSlots = mutable.HashMap[Button, ImageView]() //assign a button to each imageview
  skinSlots += button1 -> skin1
  skinSlots += button2 -> skin2
  skinSlots += button3 -> skin3
  skinSlots += button4 -> skin4
  skinSlots += button5 -> skin5
  skinSlots += button6 -> skin6
  skinSlots += button7 -> skin7

  //assigning skin image to imageView
  private var i: Int = 0
  for (skinSlot <- skinSlots) {
    skinSlot._2.image = skins(i)
    if (i == skins.size - 1) { //last index of i (default skin)
      skinSlot._1.text = "Selected"
    }
    i+=1
  }

  //called when any select button is clicked
  def handleSelect(event: ActionEvent): Unit = {
    val button: scalafx.scene.control.Button = event.source.asInstanceOf[javafx.scene.control.Button]
    button.text = "Selected"
    selectedSkin = skinSlots(button).image.value

    val otherButtons = skinSlots.keySet-button
    for (otherButton <- otherButtons){
      otherButton.text = "Choose Me!"
    }
  }

  //called when back button is clicked
  def handleBack(): Unit = {
    MainApp.showWelcome()
  }
}
