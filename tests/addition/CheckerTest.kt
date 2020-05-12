package addition

import junit.framework.TestCase
import org.junit.Test

/**
 * Some tests in order to check the functionality of the program.
 */
class CheckerTest : TestCase() {
    var desk = Board()

    @Test
    fun testGetPossibleMoves() {
        desk[0, 1] = Checker(Color.BLACK)
        assertEquals(listOf(1 to 0, 1 to 2), desk.getPossibleMoves(0, 1))
        desk[7, 0] = Checker(Color.WHITE)
        assertEquals(listOf(6 to 1), desk.getPossibleMoves(7, 0))
        desk[0, 1] = Queen(Color.WHITE)
        assertEquals(listOf(1 to 2, 2 to 3, 3 to 4, 4 to 5, 5 to 6, 6 to 7, 1 to 0), desk.getPossibleMoves(0, 1))
        desk[0, 1] = Queen(Color.BLACK)
        assertEquals(listOf(1 to 2, 2 to 3, 3 to 4, 4 to 5, 5 to 6, 6 to 7, 1 to 0), desk.getPossibleMoves(0, 1))
    }

    @Test
    fun testCanEat() {
        desk[6, 3] = Checker(Color.WHITE)
        desk[5, 2] = Checker(Color.BLACK)
        desk[5, 4] = Checker(Color.BLACK)
        desk[1, 6] = Checker(Color.BLACK)

        assertEquals(true, desk[6, 3]!!.canEat(6, 3).first)
        assertEquals(true, desk[5, 2]!!.canEat(5, 2).first)
        assertEquals(true, desk[5, 4]!!.canEat(5, 4).first)
        assertEquals(false, desk[1, 6]!!.canEat(1, 6).first)

        assertEquals(listOf(4 to 5, 4 to 1), desk[6, 3]!!.canEat(6, 3).second)
        assertEquals(listOf(7 to 4), desk[5, 2]!!.canEat(5, 2).second)
        assertEquals(listOf(7 to 2), desk[5, 4]!!.canEat(5, 4).second)
        assertEquals(emptyList<Int>(), desk[1, 6]!!.canEat(1, 6).second)

        assertEquals(listOf(5 to 4, 5 to 2), desk[6, 3]!!.canEat(6, 3).third)
        assertEquals(listOf(6 to 3), desk[5, 2]!!.canEat(5, 2).third)
        assertEquals(listOf(6 to 3), desk[5, 4]!!.canEat(5, 4).third)
        assertEquals(emptyList<Int>(), desk[1, 6]!!.canEat(1, 6).third)

        desk[6, 3] = Queen(Color.WHITE)
        desk[5, 2] = Queen(Color.BLACK)
        desk[5, 4] = Queen(Color.BLACK)
        desk[1, 6] = Queen(Color.BLACK)

        assertEquals(listOf(4 to 5, 3 to 6, 2 to 7, 4 to 1, 3 to 0), desk[6, 3]!!.canEat(6, 3).second)
        assertEquals(listOf(7 to 4), desk[5, 2]!!.canEat(5, 2).second)
        assertEquals(listOf(7 to 2), desk[5, 4]!!.canEat(5, 4).second)
        assertEquals(emptyList<Int>(), desk[1, 6]!!.canEat(1, 6).second)

    }

    @Test
    fun testisOpposite() {
        desk[7, 7] = Checker(Color.WHITE)
        desk[5, 3] = Checker(Color.BLACK)
        assertEquals(true, desk[7, 7]!!.isOpposite(desk[5, 3]))
        assertEquals(true, desk[5, 3]!!.isOpposite(desk[7, 7]))
        assertEquals(false, desk[5, 3]!!.isOpposite(desk[5, 3]))
    }

    @Test
    fun testTestToString() {
        assertEquals("black_checker", Checker(Color.BLACK).toString())
        assertEquals("white_checker", Checker(Color.WHITE).toString())
        assertEquals("black_queen", Queen(Color.BLACK).toString())
        assertEquals("white_queen", Queen(Color.WHITE).toString())
    }

}