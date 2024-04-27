package flappybird.view

import flappybird.MainApp
import flappybird.model.Player
import scalafx.beans.binding.Bindings
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.{TableColumn, TableView}
import scalafxml.core.macros.sfxml

@sfxml
class LeaderboardController(val playerTable: TableView[Player], val nameColumn: TableColumn[Player, String], val scoreColumn: TableColumn[Player, Int]){
  private val playerScores: ObservableBuffer[Player] = new ObservableBuffer[Player]()

  playerScores ++= Player.getAllPlayers //update playerScores according to values from db
  playerTable.items = playerScores

  // initialize columns's cell values
  nameColumn.cellValueFactory = a => {
    a.value.name
  }
  scoreColumn.cellValueFactory = b => {
    Bindings.createObjectBinding[Int](() => b.value.score.value, b.value.score)
  }

  //for back button
  def handleBack(): Unit = {
    MainApp.showWelcome()
  }
}
