package day_22

import scala.collection.mutable

/**
  * Created by christina on 27/12/15.
  */
object Game {

  case class Player(name: String, hitPoints: Int, damage: Int, mana: Int)

  case class Spell(name: String, cost: Int, healing: Int, damage: Int, armour: Int, mana: Int, effect: Int)

  val spells = Set(Spell("Magic Missle", 53, 0, 4, 0, 0, 1),
    Spell("Drain", 73, 2, 2, 0, 0, 1),
    Spell("Shield", 113, 0, 0, 7, 0, 6),
    Spell("Poison", 173, 0, 3, 0, 0, 6),
    Spell("Recharge", 229, 0, 0, 0, 101, 5)
  )


  val testSpells = List(
    spells.filter(_.name == "Recharge"),
    spells.filter(_.name == "Shield"),
    spells.filter(_.name == "Drain"),
    spells.filter(_.name == "Poison"),
    spells.filter(_.name == "Magic Missle"))

  def findMin(): Int = {

    val wizard = new Player("Wizard", 50, 0, 500)
    val boss = new Player("Boss", 58, 9, 0)
//    val wizard = new Player("Wizard", 10, 0, 250)
//    val boss = new Player("Boss", 14, 8, 0)

    play(wizard, boss, Map[Spell, Int](), 0, 0)

    minMana
  }

  def paySpell(wizard: Player, spell: Spell): Player = {
    wizard.copy(mana = wizard.mana - spell.cost)
  }

  def getNextSpells(mana: Int, spells: Set[Spell], existingEffects: Set[Spell]): Set[Spell] = {
    spells
      .filterNot(existingEffects)
      .filter(s => s.cost <= mana)
  }

  // armour is separate!
  def applyEffectsOnWizard(wizard: Player, effects: Set[Spell]): Player = {
    wizard.copy(hitPoints = wizard.hitPoints + effects.map(e => e.healing).sum,
      mana = wizard.mana + effects.map(e => e.mana).sum)
  }

  def applyEffectsOnBoss(boss: Player, effects: Set[Spell]): Player = {
    boss.copy(hitPoints = boss.hitPoints - effects.map(e => e.damage).sum)
  }

  def applyBossAttack(wizard: Player, boss: Player, effects: Set[Spell]): Player = {
    val additionalArmour = effects.map(e => e.armour).sum

    wizard.copy(hitPoints = wizard.hitPoints - Math.max(boss.damage - additionalArmour, 1))
  }


  var minMana = 100000000
  var winners = mutable.Set[Player]()

  // this runs a player turn and a boss turn or stops if one of them won
  def play(wizard: Player, boss: Player, runningEffects: Map[Spell, Int], manaSpent: Int, runs: Int): Unit = {

    if (manaSpent <= minMana) {
//      println("--- Player turn ---")
//      println("Player", wizard)
//      println("Boss", boss)
//      println("Running effects", runningEffects)

      val newWizard = applyEffectsOnWizard(wizard, runningEffects.keySet)
      //println("Player after running effects", newWizard)

      val newBoss = applyEffectsOnBoss(boss, runningEffects.keySet)
      //println("Boss after running effects", newBoss)

      // remove expired effects
      val newEffects =
        runningEffects
          .map(s => s._1 -> (s._2 - 1))
          .filter(f => f._2 > 0)

      //println("After removing expired effects: ", newEffects)

      val newSpells = getNextSpells(newWizard.mana, spells, newEffects.keySet)
      //val newSpells = testSpells(runs)

      newSpells.size match {
        case 0 => { } //println("Boss won as no new spells were available") }
        case _ => {
          // foreach spell play boss
          // add it to running effects and call play again

          newSpells.foreach(spell => {

            val newWizardAfterPaying = paySpell(newWizard, spell)
//            println("Player", newWizard , "casts", spell, "and is now", newWizardAfterPaying)
            val newWizardAfterSpell = applyEffectsOnWizard(newWizardAfterPaying, Set(spell))


//            println("--- Boss turn ---")
//            println("Initial", newWizardAfterSpell, newBoss)

            val newBossAE = applyEffectsOnBoss(newBoss, newEffects.keySet)
            //println("Boss after running effects", newBossAE)

            // remove expired effects
            val newEffectsAfterApplyingOnBoss =
              newEffects
                .map(s => s._1 -> (s._2 - 1))
                .filter(f => f._2 > 0)

            //println("After removing expired effects: ", newEffectsAfterApplyingOnBoss)

            val newBossWithSpell = applyEffectsOnBoss(newBossAE, Set(spell))
            //println("Boss after spell", newBossWithSpell)

            // we applied it once
            val newRunningEffects =
              (newEffectsAfterApplyingOnBoss + (spell -> (spell.effect - 1)))
                .filter(s => s._2 > 0)

            //println("New running effects", newRunningEffects)

            val newManaCost = manaSpent + spell.cost
//            println("Mana spent so far", newManaCost)

            newBossWithSpell.hitPoints match {
              case x if x <= 0 => {
//                println(s"Wizard won; mana spent: $newManaCost")
                if (newManaCost < minMana) {
                  minMana = newManaCost
                  println(s"Min mana so far $minMana")
                }
              }
              case _ => {
                // boss attacks
                val newWizardAfterBossAttack = applyBossAttack(newWizardAfterSpell, newBossWithSpell, newRunningEffects.keySet)
                //println("Boss attacks and this is new wizard", newWizardAfterBossAttack)
                // run the game again
//                println("Moving to new step", newWizardAfterBossAttack, newBossWithSpell, newRunningEffects)
                newWizardAfterBossAttack.hitPoints match {
                  case y if y <= 0 => println("Wizard looses")
                  case _ => play(newWizardAfterBossAttack, newBossWithSpell, newRunningEffects, newManaCost, runs + 1)
                }
              }
            }

          })

        }
      }

    }


  }

}
