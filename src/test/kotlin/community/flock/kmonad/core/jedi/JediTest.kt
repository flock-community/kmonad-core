package community.flock.kmonad.core.jedi

import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException.BadRequest
import community.flock.kmonad.core.common.TestLogger
import community.flock.kmonad.core.common.assertThrows
import community.flock.kmonad.core.common.Logger
import community.flock.kmonad.core.common.monads.getOrHandle
import community.flock.kmonad.core.jedi.TestRepository.lukeUUID
import community.flock.kmonad.core.jedi.TestRepository.yodaUUID
import community.flock.kmonad.core.jedi.model.Jedi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class JediTest {

    private val context = object {
        operator fun invoke() = object : Context {
            override val logger: Logger = TestLogger
            override val jediRepository: Repository = TestRepository
        }
    }

    @Test
    fun testBindGet(): Unit = runBlocking {
        bindGet()
            .provide(context())
            .runUnsafe()
            .map { it.take(2).toList() }
            .map { (luke, yoda) ->
                luke.assertLuke()
                yoda.assertYoda()
            }
    }

    @Test
    fun testBindGetByUUID(): Unit = runBlocking {
        bindGet(lukeUUID)
            .provide(context())
            .runUnsafe()
            .map { it.assertLuke() }

        assertThrows(BadRequest::class) {
            bindGet("Not a UUID")
                .provide(context())
                .runUnsafe()
                .getOrHandle { throw it }
        }
    }

    @Test
    fun testBindPost(): Unit = runBlocking {
        val testJedi = Jedi(name = "TestJediName", age = 42)
        bindPost(testJedi)
            .provide(context())
            .runUnsafe()
            .map { it.assertEquals(testJedi) }

    }

    @Test
    fun testBindDelete(): Unit = runBlocking {
        bindDelete(lukeUUID)
            .provide(context())
            .runUnsafe()
            .map { it.assertLuke() }

        assertThrows(BadRequest::class) {
            bindDelete("Not a UUID")
                .provide(context())
                .runUnsafe()
                .getOrHandle { throw it }
        }
    }


    private fun Jedi.assertLuke() {
        Assertions.assertEquals(lukeUUID, id)
        Assertions.assertEquals("Luke", name)
        Assertions.assertEquals(23, age)
    }

    private fun Jedi.assertYoda() {
        Assertions.assertEquals(yodaUUID, id)
        Assertions.assertEquals("Yoda", name)
        Assertions.assertEquals(900, age)
    }

    private fun Jedi.assertEquals(jedi: Jedi) {
        Assertions.assertEquals(jedi.id, id)
        Assertions.assertEquals(jedi.name, name)
        Assertions.assertEquals(jedi.age, age)
    }

}
