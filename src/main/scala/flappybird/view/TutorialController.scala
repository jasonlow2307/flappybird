package flappybird.view

import flappybird.MainApp
import scalafx.scene.image.{Image, ImageView}
import scalafx.stage.Stage
import scalafxml.core.macros.sfxml

import scala.collection.mutable.ListBuffer

@sfxml
class TutorialController(instruction: ImageView) {
  var dialogStage: Option[Stage] = None
  var i: Int = 0
  private val instructions = ListBuffer[Image]()
  //add instructions into list buffer
  instructions += new Image(getClass.getResourceAsStream("/flappybird/image/page1.png"))
  instructions += new Image(getClass.getResourceAsStream("/flappybird/image/page2.png"))
  instructions += new Image(getClass.getResourceAsStream("/flappybird/image/page3.png"))
  instructions += new Image(getClass.getResourceAsStream("/flappybird/image/page4.png"))
  instructions += new Image(getClass.getResourceAsStream("/flappybird/image/page5.png"))
  instructions += new Image(getClass.getResourceAsStream("/flappybird/image/page6.png"))
  instructions += new Image(getClass.getResourceAsStream("/flappybird/image/page7.png"))

  updatePage()

  private def updatePage(): Unit = { //update instruction page based on counter i
    instruction.image = instructions(i)
  }

  //for next page button
  def handleNextPage(): Unit = {
    if (i + 1 < instructions.size){
      i += 1
      updatePage()
    }
  }

  //for previous page button
  def handlePreviousPage(): Unit = {
    if (i - 1 >= 0) {
      i -= 1
      updatePage()
    }
  }

  //for close button
  def handleClose(): Unit = {
    dialogStage match{
      case Some(x) => x.close()
      case None =>
    }
  }
}
