package day_21

/**
  * Created by christina on 26/12/15.
  */


object Game {

  case class Player(name: String, hitPoints: Int, damage: Int, armour: Int)
  case class Item(name: String, cost: Int, damage: Int, armour: Int)
  case class ItemsChoice(weapon: Item, armour: Option[Item], rings: List[Item])

  object Play {

    def takeTurn(attacker: Player, defender: Player): Player = {
      defender.copy(hitPoints = defender.hitPoints - Math.max(attacker.damage - defender.armour, 1))
    }

    def applyItem(player: Player, item: Item): Player = {
      Player(player.name, player.hitPoints, player.damage + item.damage, player.armour + item.armour)
    }

    def applyItemsChoice(player: Player, itemsChoice: ItemsChoice): Player = {
      val items = (itemsChoice.weapon :: itemsChoice.rings) ++ itemsChoice.armour

      items.foldLeft(player)((p, item) => applyItem(p, item))
    }

    def playUntilEnd(player1: Player, player2: Player): Player = {
      if (player1.hitPoints <= 0)
        return player2

      if (player2.hitPoints <= 0)
        return player1

      val newAttacker = takeTurn(player1, player2)
      //println(newAttacker, " <- ", player1, player2)
      newAttacker.hitPoints match {
        case x if x <= 0 => player1
        case _ => playUntilEnd(newAttacker, player1)
      }
    }
  }

  val weapons = List(Item("Dagger", 8, 4, 0),
    Item("Shortsword", 10, 5, 0),
    Item("Warhammer", 25, 6, 0),
    Item("Longsword", 40, 7, 0),
    Item("Greataxe", 74, 8, 0))

  val armours = List(Item("Leather", 13, 0, 1),
    Item("Chainmail", 31, 0, 2),
    Item("Splintmail", 53, 0, 3),
    Item("Brandedmail", 75, 0, 4),
    Item("Platemail", 102, 0, 5))

  val rings = List(Item("Damage +1", 25, 1, 0),
    Item("Damage +2", 50, 2, 0),
    Item("Damage +3", 100, 3, 0),
    Item("Defense +1", 20, 0, 1),
    Item("Defense +2", 40, 0, 2),
    Item("Defense +3", 80, 0, 3))


  def itemChoiceCost(itemsChoice: ItemsChoice) : Int = {
    itemsChoice.weapon.cost +
      (itemsChoice.armour match {
        case None => 0
        case Some(x) => x.cost
      }) +
      itemsChoice.rings.map(r => r.cost).sum
  }

  val choices = for {
    weaponChoice <- weapons
    armourChoice <- None :: armours.map(a => Some(a))

    ringsChoice <-  List(List[Item]()) ++ rings.map(r => List(r)) ++ (for {
      first <- rings
      second <- rings
      if first != second
    } yield List(first, second))

  } yield ItemsChoice(weaponChoice, armourChoice, ringsChoice)

  val me = Player("me", 100, 0, 0)
  val boss = Player("boss", 104, 8, 1)

  def findMinGold(): Int = {
    val computedChoices = choices.map(c => {
      val meWithItems = Play.applyItemsChoice(me, c)

      val winner = Play.playUntilEnd(meWithItems, boss)

      winner.name match {
        case "me" => itemChoiceCost(c)
        case _ => 1000000
      }
    })

    computedChoices.min
  }

  def findMaxGoldToLoose(): Int = {
    val computedChoices = choices.map(c => {
      val meWithItems = Play.applyItemsChoice(me, c)

      val winner = Play.playUntilEnd(meWithItems, boss)

      winner.name match {
        case "me" => 0
        case _ => itemChoiceCost(c)
      }
    })

    computedChoices.max
  }

  def test(): Unit = {
    val m = Player("me", 8, 5, 5)
    val b = Player("boss", 12, 7, 2)

    val winner = Play.playUntilEnd(m, b)
    println(winner)

    (0 to weapons.length).flatMap(i => weapons.combinations(i)).foreach(c => println(c))

  }




}


