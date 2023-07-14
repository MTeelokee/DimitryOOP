import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

// Cours 13 Juillet 2023 Dimitry


data class Position(val x: Int, val y: Int) {

    fun delta(x: Int, y: Int): Position {
        return Position(this.x + x, this.y + y)
    }

    fun multiplier(i: Int): Position {
        return Position(this.x * i, this.y * i)
    }

    infix fun `move by`(delta: Delta): Position {
        return Position(this.x + delta.x, this.y + delta.y)
    }

    // exemple de surchage d'operateur
    // l'assignation (=) ne peut etre surchargé
    operator fun times(i: Int): Position {
        return Position(this.x * i, this.y * i)
    }

    operator fun plus(delta: Delta): Position {
        return Position(this.x + delta.x, this.y + delta.y)
    }

    // opérateur unaire 1 seule opérande celle de droite  _

    operator fun unaryMinus(): Position {
        return Position(-x, -y)
    }


}

class PositionTest {

    @Test
    fun `apply a delta of x2,y-3 to a given position`() {
        val randomPosition = Position(5, 2)
        val transformedPosition = randomPosition.delta(2, -3)
        assertThat(transformedPosition).isEqualTo(Position(7, -1))
    }

    @Test
    fun `apply a delta of x2,y-3 with a operator function to a given position`() {
        val randomPosition = Position(5, 2)
        val delta = Delta(2, -3)
        val transformedPosition = randomPosition + delta
        assertThat(transformedPosition).isEqualTo(Position(7, -1))
    }

    // Fonction infix permettant de ne pas utiliser la notation pointé
    @Test
    fun `apply a delta with an infix function of x2,y-3 to a given position`() {
        val `my position` = Position(5, 2)
        val modifier = Delta(2, -3)

        val transformedPosition = `my position` `move by` modifier
        assertThat(transformedPosition).isEqualTo(Position(7, -1))
    }

    @Test
    fun `apply a delta`() {
        // given
        val randomPosition = Position(5, 2)
        // when
        val changedPosition = randomPosition * 3
        //then
        assertThat(changedPosition).isEqualTo(Position(15, 6))
    }

    @Test
    fun `apply unaryMinus`() {
        val position = Position(10, 6)/*
                val changedPosition = position.unaryMinus()
        */
        val changedPosition = -position

        assertThat(changedPosition).isEqualTo(Position(-10, -6))

    }

}


data class Delta(val x: Int, val y: Int)

// Partie aprem sur les interfaces

// Enoncé
// Controler les rovers a distance : controler la position actuelle des vehicules et pouvoir les envoyer à une certaine position
// Centre de mission s'occupe de donner les instructions a nos rovers
//  liste de nos rovers : curiosity 1 case par 1 case et tourner a d et g , opporunity galere à se deplacer , supercopter peut voler directement à l'endroit voulu

interface RemotelyControlled {
    fun retrievePosition(): Position
    fun move(target: Position)

}

class ControlCenter
    (supercopter: RemotelyControlled, opportunity: RemotelyControlled) {

    //  A VERIFIER Le bloc de code init sera initialisé après le constructeur primaire et avant le constructeur secondaire
    init {
        supercopter.retrievePosition()
        opportunity.retrievePosition()
        supercopter.move(Position(2, 2))
        opportunity.move(Position(0, 0))
    }

    // Deuxieme version avec un constructeur secondaire

//    constructor(supercopter: RemotelyControlled, opportunity: RemotelyControlled)
//    // Le bloc de code init sera initialisé après le constructeur primaire et avant le constructeur secondaire
//    // On ne peut pas passer de
//    {
//        supercopter.retrievePosition()
//        opportunity.retrievePosition()
//        supercopter.move(Position(2, 2))
//        opportunity.move(Position(0, 0))
//    }
//


}

// On va pouvoir passer n'importe quel rover du moment qu'ils se sont engagé a respecter ce contrat d'interface
//Réifier = rendre concret un concept abstrait
val ctrl = ControlCenter(Supercopter(), Opportunity())

class Supercopter() : RemotelyControlled {
    override fun retrievePosition(): Position {
        return Position(5, 5)
    }

    override fun move(target: Position) {
        println("fadoufadoufadou")
    }

    fun toptop() {}
}

class Opportunity() : RemotelyControlled {
    override fun retrievePosition(): Position {
        val tp = 1
        return Position(5 + tp, 5 + tp)
    }

    override fun move(target: Position) {
        println("BrrrrBrrrrrrr")
    }

}
