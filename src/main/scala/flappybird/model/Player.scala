package flappybird.model

import flappybird.util.Database
import scalafx.beans.property.{IntegerProperty, StringProperty}
import scalikejdbc.{DB, scalikejdbcSQLInterpolationImplicitDef}

import scala.util.Try

class Player(var name: StringProperty, var score: IntegerProperty) {

  def incrementScore(amount: Int): Unit = {
    score = IntegerProperty((score + amount).intValue())
  }

  def clear(): Unit = { //reset player name and score
    name = StringProperty("Default")
    score = IntegerProperty(0)
  }

  def save(): Try[Int] = { //create or update player record
    if (!(isExist)) {
      Try(DB autoCommit { implicit session =>
        sql"""
          insert into player (name, score) values
            (${name.value}, ${score.value})
        """.update.apply()
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
        update player
        set
        name = ${name.value} ,
        score   = ${score.value}
         where name = ${name.value}
        """.update.apply()
      })
    }

  }

  private def isExist: Boolean = { //check if a player already exist in table
    DB readOnly { implicit session =>
      sql"""
        select * from player where
        name = ${name.value}
      """.map(rs => rs.string("name")).single.apply()
    } match {
      case Some(x) => true
      case None => false
    }

  }
}


object Player extends Database {
  def apply(
             name: String,
           score: Int
           ): Player = {

    new Player(StringProperty(name), IntegerProperty(score))

  }

  def initializeTable(): Boolean = { //create table, will only be called if table doesn't exist
    DB autoCommit { implicit session =>
      sql"""
      create table player (
        id int not null GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
        name varchar(64),
        score int
      )
      """.execute.apply()
    }
  }

  def getAllPlayers: List[Player] = { //get all players other than default and sort descendingly
    DB readOnly { implicit session =>
      sql"""
        SELECT * FROM player
        WHERE name <> 'DEFAULT'
        ORDER BY score DESC
      """.map(rs => Player(rs.string("name"), rs.int("score"))).list.apply()
    }
  }

}
