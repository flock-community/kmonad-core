package community.flock.kmonad.core.sith

import community.flock.kmonad.core.common.TestLogger
import community.flock.kmonad.core.common.Logger
import community.flock.kmonad.core.sith.TestRepository.sidiousUUID
import community.flock.kmonad.core.sith.TestRepository.vaderUUID
import community.flock.kmonad.core.sith.model.Sith
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SithTest {

    private val context = object {
        operator fun invoke() = object : Context {
            override val logger: Logger = TestLogger
            override val sithRepository: Repository = TestRepository
        }
    }

    @Test
    fun testBindGet() = runBlocking {
        context().bindGet()
            .take(2).toList()
            .let { (sidious, vader) ->
                sidious.assertSidious()
                vader.assertVader()
            }
    }

    @Test
    fun testBindGetByUUID() = runBlocking {
        context().bindGet(sidiousUUID).assertSidious()
    }

    @Test
    fun testBindPost(): Unit = runBlocking {
        val testSith = Sith(name = "TestSithName", age = 42)
        context().bindPost(testSith).assertEquals(testSith)
    }

    @Test
    fun testBindDelete() = runBlocking {
        context().bindDelete(sidiousUUID).assertSidious()
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
