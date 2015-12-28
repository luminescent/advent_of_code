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

    play2(wizard, boss, Map[Spell, Int](), 0)

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

  def applyBossAttack(wizard: Player, boss: Player, armour: Int): Player = {
    wizard.copy(hitPoints = wizard.hitPoints - Math.max(boss.damage - armour, 1))
  }

  def applyEffectsOnTurn(player1: Player, player2: Player, runningEffects: Map[Spell, Int]): (Player, Player, Map[Spell, Int]) = {

    val wizard = player1.name match {
      case "Boss" => player2
      case _ => player1
    }
    val boss = player1.name match {
      case "Boss" => player1
      case _ => player2
    }

    val newWizard = applyEffectsOnWizard(wizard, runningEffects.keySet)
    val newBoss = applyEffectsOnBoss(boss, runningEffects.keySet)

    val newEffects = runningEffects
      .map(e => e._1 -> (e._2 - 1))
      .filter(_._2 > 0)

    (newWizard, newBoss, newEffects)
  }

  var minMana = 100000000
  var winners = mutable.Set[Player]()

  def getArmour(runningEffects: Map[Spell, Int]): Int = {
    val hasArmour = runningEffects.find(e => e._1.armour > 0)
    hasArmour match {
      case None => 0
      case Some(a) => a._1.armour
    }
  }



  def play3(player1: Player, player2: Player, runningEffects: Map[Spell, Int], manaSpent: Int, runs: Int): Unit = {
    if (manaSpent <= minMana) {

      val (wizard, boss, remainingEffects) = applyEffectsOnTurn(player1, player2, runningEffects)

      player1.name match {
        case "Wizard" =>
          boss.hitPoints match {
            case x if x <= 0 => if (manaSpent < minMana) {
              minMana = manaSpent
              println(s"Min mana so far $minMana")
            }
            case _ =>
              val nextSpells = getNextSpells(wizard.mana, spells, remainingEffects.keySet)
              nextSpells.foreach(spell => {
                val newWizard = paySpell(wizard, spell)
                play3(boss, newWizard, remainingEffects + (spell -> spell.effect), manaSpent + spell.cost, runs)
              })
          }

        case _ =>
          boss.hitPoints match {
            case x if x <= 0 => if (manaSpent < minMana) {
              minMana = manaSpent
              println(s"Min mana so far $minMana")
            }
            case _ =>
              // we have to look before applying the effect as armour does not get saved against the wizard
              val armour = getArmour(runningEffects)
              val newWizard = applyBossAttack(wizard, boss, armour)
              // check points
              if (newWizard.hitPoints > 0)
                play3(newWizard, boss, remainingEffects, manaSpent, runs + 1)
          }
      }
    }
  }

  def play2(wizard: Player, boss: Player, runningEffects: Map[Spell, Int], manaSpent: Int): Unit = {

    if (manaSpent <= minMana){

      val hardWiz = wizard.copy(hitPoints = wizard.hitPoints - 1)
      if (hardWiz.hitPoints > 0) {

        val(postEffectWizPlayerTurn, postEffectBossPlayerTurn, remainingEffects) = applyEffectsOnTurn(hardWiz, boss, runningEffects)
        if (postEffectBossPlayerTurn.hitPoints > 0) {

          val nextSpells = getNextSpells(postEffectWizPlayerTurn.mana, spells, remainingEffects.keySet)
          nextSpells.foreach(spell => {
            val newManaCost = manaSpent + spell.cost

            val (wizardAfterSpell, bossAfterSpell, newSpell) =
              applyEffectsOnTurn(postEffectWizPlayerTurn, postEffectBossPlayerTurn, Set(spell).map(s => s -> s.effect).toMap)

            val payingWiz = paySpell(wizardAfterSpell, spell)

            bossAfterSpell.hitPoints match {
              case z if z <= 0 =>
                if (newManaCost < minMana) {
                  minMana = newManaCost
                  println(s"Min mana so far $minMana")
                }
              case _ =>
                val effectsWithSpell = remainingEffects ++ newSpell

                // we have to apply effects on this turn too
                val (postEffectWizardBossTurn, postEffectBossBossTurn, effectsOnBossTurn) = applyEffectsOnTurn(payingWiz, bossAfterSpell, effectsWithSpell)
                postEffectBossBossTurn.hitPoints match {
                  case w if w <= 0 =>
                    if (newManaCost < minMana) {
                      minMana = newManaCost
                      println(s"Min mana so far $minMana")
                    }
                  case _ =>
                    val armour = getArmour(effectsWithSpell)
                    val attackedWiz = applyBossAttack(postEffectWizardBossTurn, postEffectBossBossTurn, armour)
                    if (attackedWiz.hitPoints > 0)
                      play2(attackedWiz, postEffectBossBossTurn, effectsOnBossTurn, newManaCost)
                }
            }
          })

        }

      }

    }
  }


}
