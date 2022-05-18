package community.flock.kmonad.core.sith

import community.flock.kmonad.core.common.Logger
import community.flock.kmonad.core.common.TestLogger
import community.flock.kmonad.core.sith.TestSithRepository.Companion.sidiousUUID
import community.flock.kmonad.core.sith.TestSithRepository.Companion.vaderUUID
import community.flock.kmonad.core.sith.model.Sith
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SithTest {

    private val sithContext = object {
        operator fun invoke() = object : SithContext {
            override val logger: Logger = TestLogger
            override val sithRepository: SithRepository = TestSithRepository()
        }
    }

    @Test
    fun testBindGet() = runBlocking {
        sithContext().bindGet().getOrThrow()
            .take(2).toList()
            .let { (sidious, vader) ->
                sidious.assertSidious()
                vader.assertVader()
            }
    }

    @Test
    fun testBindGetByUUID(): Unit = runBlocking {
        sithContext().bindGet(sidiousUUID).getOrThrow().assertSidious()
    }

    @Test
    fun testBindPost(): Unit = runBlocking {
        val testSith = Sith(name = "TestSithName", age = 42)
        sithContext().bindPost(testSith).getOrThrow().assertEquals(testSith)
    }

    @Test
    fun testBindDelete() = runBlocking {
        sithContext().bindDelete(sidiousUUID).getOrThrow().assertSidious()
    }


    private fun Sith.assertSidious() {
        assertEquals(sidiousUUID, id)
        assertEquals("Darth Sidious", name)
        assertEquals(86, age)
    }

    private fun Sith.assertVader() {
        assertEquals(vaderUUID, id)
        assertEquals("Darth Vader", name)
        assertEquals(45, age)
    }

    private fun Sith.assertEquals(sith: Sith) {
        assertEquals(sith.name, name)
        assertEquals(sith.age, age)
    }

}
