package day_22

import day_22.Game.Player

/**
  * Created by christina on 27/12/15.
  */
object Runner extends App {

  val spell = Game.spells.filter(_.name == "Poison")

  val newBoss = Game.applyEffectsOnBoss(new Player("Boss", 13, 8, 0), spell)
  println(newBoss)

  val wizard = Player("wizard", 10, 0, 21)
  val newWizard = Game.applyEffectsOnWizard(wizard, Game.spells.filter(_.name == "Recharge"))

  println(newWizard)


  println(s"Min mana to win: ${ Game findMin() }")

}
